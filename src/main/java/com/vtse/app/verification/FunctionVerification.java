package com.vtse.app.verification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vtse.app.solver.SMTInput;
import com.vtse.app.solver.Z3Runner;
import com.vtse.app.verification.report.VerificationReport;
import com.vtse.app.verification.userassertion.UserInput;
import com.vtse.cfg.build.ASTFactory;
import com.vtse.cfg.build.VtseCFG;
import com.vtse.cfg.index.Variable;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import com.vtse.app.verification.report.Report;

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
        SMTInput smtInput;
        if (mode == INVARIANT_MODE) {
            cfg.invariant();
            cfg.index();
            smtInput = new SMTInput(cfg.getVm().getVariableList(), cfg.createInvariantFormula());
        } else {
            cfg.ungoto();
            cfg.unfold(nLoops);
//            cfg.unfold(10);
            cfg.index();
            smtInput = new SMTInput(cfg.getVm().getVariableList(), cfg.createFormulas() );
        }

        // java.cfg.printGraph();
        // java.cfg.printMeta();
        // java.cfg.printFormular(System.out);

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
        smtInput.printInputToOutputStreamAssert(fo);

        List<String> result = Z3Runner.runZ3(path);

        Report report = new Report();
        report.setListParameter(cfg.getInitVariables());
        report.setFunctionName(cfg.getNameFunction());
        if (nLoops < 100) mode = FunctionVerification.INVARIANT_MODE; //change from true* to true
        report.setMode(mode);
        VerificationReport verReport = report.generateReport(result);

        verReport.print();
        System.out.println("Z3 result:");
        result.forEach(System.out::println);

        verReport.setFunctionName(cfg.getNameFunction());
        verReport.setGenerateConstraintTime((int)(end-begin));
        verReport.setPreCondition(preCondition);
        verReport.setPostCondition(postCondition);

        return verReport;
    }
}
