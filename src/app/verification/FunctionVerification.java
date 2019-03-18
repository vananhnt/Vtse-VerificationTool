package app.verification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.internal.core.model.FunctionDeclaration;

import app.solver.SMTInput;
import app.solver.Z3Runner;
import app.verification.report.Report;
import app.verification.report.VerificationReport;
import app.verification.userassertion.UserInput;
import cfg.build.ASTFactory;
import cfg.build.VtseCFG;
import cfg.index.Variable;

public class FunctionVerification {
    static String SMTINPUT_DIR = "smt/";
    public static int UNFOLD_MODE = 0;
    public static int INVARIANT_MODE = 1;

    public FunctionVerification() {
    }

    /**
     * verify a function with pre-condition and post-condition
     * @param function: function to verify
     * @param preCondition
     * @param postCondition
     * @return	verification report
     * @throws IOException
     */

    public static VerificationReport verify(ASTFactory ast, IASTFunctionDefinition function, String preCondition, String postCondition, int nLoops, int mode)
            throws IOException {

        long begin = System.currentTimeMillis();

        VtseCFG cfg = new VtseCFG(function, ast);
        if (mode == UNFOLD_MODE) {
            cfg.unfold(nLoops);
        } else {
            cfg.invariant();
        }
        cfg.index();
        // cfg.printGraph();
        // cfg.printMeta();
        // cfg.printFormular(System.out);

        SMTInput smtInput = new SMTInput(cfg.getVm().getVariableList(), cfg.createFormular());

        String constraintTemp;

        List<String> constraints = new ArrayList<>();
        UserInput userInput = new UserInput();
        ArrayList<Variable> params = cfg.getInitVariables();
        params.add(new Variable(cfg.getTypeFunction(), "return"));
        userInput.setParameter(params);

        // add pre-condition
        if (preCondition != null && !preCondition.equals("")) {
            constraintTemp = userInput.createUserAssertion(preCondition, cfg.getNameFunction());
            constraints.add(constraintTemp);
        }

        // add user's assertion
        constraintTemp = userInput.createUserAssertion(postCondition,cfg.getNameFunction());
        constraintTemp = "(not " + constraintTemp + ")";
        constraints.add(constraintTemp);

        smtInput.setConstrainst(constraints);

        long end = System.currentTimeMillis();

        String functionName = cfg.getNameFunction();
        String path = SMTINPUT_DIR + functionName + ".smt";

        FileOutputStream fo = new FileOutputStream(new File(path));
        smtInput.printInputToOutputStream(fo);

        List<String> result = Z3Runner.runZ3(path);

        Report report = new Report();
        report.setListParameter(cfg.getInitVariables());
        report.setFunctionName(cfg.getNameFunction());
        VerificationReport verReport = report.generateReport(result);

        verReport.print();
        result.forEach(System.out::println);

        verReport.setFunctionName(cfg.getNameFunction());
        verReport.setGenerateConstraintTime((int)(end-begin));
        verReport.setPreCondition(preCondition);
        verReport.setPostCondition(postCondition);

        return verReport;
    }
}
