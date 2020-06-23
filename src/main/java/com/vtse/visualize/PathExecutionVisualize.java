package com.vtse.visualize;

import com.vtse.app.solver.SMTInput;
import com.vtse.app.solver.Z3Runner;
import com.vtse.app.verification.FunctionVerification;
import com.vtse.app.verification.report.DefineFun;
import com.vtse.app.verification.report.Report;
import com.vtse.app.verification.report.VerificationReport;
import com.vtse.app.verification.userassertion.UserInput;
import com.vtse.cfg.build.UnfoldCFG;
import com.vtse.cfg.build.VtseCFG;
import com.vtse.cfg.index.FormulaCreater;
import com.vtse.cfg.index.Variable;
import com.vtse.cfg.node.CFGNode;
import com.vtse.cfg.node.DecisionNode;
import org.eclipse.cdt.core.dom.ast.IASTExpression;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PathExecutionVisualize {
    VtseCFG vtseCFG;
    int nLoops;
    VerificationReport verificationReport;

    public PathExecutionVisualize(VtseCFG vtseCFG, VerificationReport verificationReport) {
        this.vtseCFG = vtseCFG;
        this.verificationReport = verificationReport;
    }
    public List<CFGNode> findPathToFail() throws IOException {
        if(this.verificationReport.getParameters() == null){
            return new ArrayList<CFGNode>();
        }
        VtseCFG cfg = this.vtseCFG;
        UserInput userInput = new UserInput();
        ArrayList<Variable> params = cfg.getInitVariables();
        params.add(new Variable(cfg.getTypeFunction(), "return"));
        userInput.setParameter(params);

        CFGNode start = cfg.getStart();
        CFGNode iter = start;
        List<CFGNode> nodes = new ArrayList<>();

        SMTInput smtInput = this.verificationReport.getSmtInput();
        List<String> formulas = new ArrayList<>();
        for(DefineFun param: this.verificationReport.getParameters()){
            String temp = userInput.createZ3Assertion(param.getExpression(), cfg.getNameFunction());
            formulas.add(temp);
        }
        while(iter.getNext() != null){
            nodes.add(iter);
            if (iter instanceof DecisionNode){
                IASTExpression condition = ((DecisionNode) iter).getCondition();
                String conditionStr = FormulaCreater.createFormula(condition);
                String notConditionStr = FormulaCreater.createFormula(FormulaCreater.NEGATIVE, conditionStr);
//                String conditionStrWithIndex = userInput.createUserAssertion(conditionStr, cfg.getNameFunction());
//                String notConditionStrWithIndex = userInput.createUserAssertion(notConditionStr, cfg.getNameFunction());

                formulas.add(conditionStr);
                SMTInput decisionNodeSMTInput = new SMTInput(smtInput.getVariableList(),formulas);
//                File tempFile = File.createTempFile("temp", ".tmp");
//                FileOutputStream fo = new FileOutputStream(tempFile);
                File tempFile = new File("smt/temp.txt");
                FileOutputStream fo = new FileOutputStream(tempFile);
                decisionNodeSMTInput.printInputToOutputStreamAssert(fo);

                List<String> result = Z3Runner.runZ3(tempFile.getAbsolutePath());
                Report report = new Report();
                report.setListParameter(cfg.getInitVariables());
                report.setFunctionName(cfg.getNameFunction());
                int mode = 0;
                if (nLoops < 100) mode = FunctionVerification.INVARIANT_MODE; //change from true* to true
                report.setMode(mode);
                VerificationReport verReport = report.generateReport(result);

                if(verReport.getStatus() == VerificationReport.FALSE){
                    iter = ((DecisionNode) iter).getThenNode();
                } else {
                    //remove last formula. Last formula is conditionStr
                    formulas.remove(formulas.size()-1);
                    //add notConditionStr
                    formulas.add(notConditionStr);
                    iter = ((DecisionNode) iter).getElseNode();
                }

            } else {
                String currentFormula = iter.getFormula();
                if (currentFormula != null) {
                    formulas.add(currentFormula);
                }
                iter = iter.getNext();
            }
        }
        return nodes;
    }
    public static void print(List<String> formulas){
        for(String formula: formulas){
            System.out.println(formula);
        }
    }
}
