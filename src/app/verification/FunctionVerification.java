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
import cfg.build.ASTGenerator;
import cfg.build.VtseCFG;
import cfg.utils.Variable;

public class FunctionVerification {
	
	FunctionDeclaration function;
	String precondition;
	String postcondition;
	int nLoops = 5;
	
	static String SMTINPUT_DIR = "smt/";

	public FunctionVerification() {
		
	}
	
	public void setNumberOfLoops(int nLoops) {
		this.nLoops = nLoops;
	}

	
	/**
	 * verify a function with pre-condition and post-condition
	 * @param function: function to verify
	 * @param preCondition
	 * @param postCondition
	 * @return	verification report
	 * @throws IOException 
	 */
	public VerificationReport verify(IASTFunctionDefinition function, String preCondition, String postCondition) 
			throws IOException {
		
		long begin = System.currentTimeMillis();
		
		VtseCFG cfg = new VtseCFG(function);
		cfg.unfold();
		cfg.index();
		//cfg.printGraph();
		cfg.printMeta();
		cfg.printFormular(System.out);
		
		SMTInput smtInput = new SMTInput(cfg.getVm().getVariableList(), cfg.createFormular());
		
		String constraintTemp;

		List<String> constraints = new ArrayList<>();
		UserInput userInput = new UserInput();
		ArrayList<Variable> params = cfg.getInitVariables();
		params.add(cfg.getReturn());
		userInput.setParameter(params);
		
		// add pre-condition
		if (preCondition != null && !preCondition.equals("")) {
			constraintTemp = userInput.createUserAssertion(preCondition);
			constraints.add(constraintTemp);
		}
		
		// add user's assertion
		constraintTemp = userInput.createUserAssertion(postCondition);
		constraintTemp = "(not " + constraintTemp + ")";
		constraints.add(constraintTemp);
		
		smtInput.setConstrainst(constraints);
		
		long end = System.currentTimeMillis();
		
		String functionName = cfg.getNameFunction();
		String path = SMTINPUT_DIR + functionName + ".smt";
		FileOutputStream fo = new FileOutputStream(new File(path));
	    smtInput.printInputToOutputStream(fo);
	    
	    List<String> result = Z3Runner.runZ3(path);
	    
	    result.forEach(System.out::println);
	    Report report = new Report();
	    report.setListParameter(cfg.getInitVariables());
	    VerificationReport verReport = report.generateReport(result);
	    verReport.setFunctionName(cfg.getNameFunction());
	    verReport.setGenerateConstraintTime((int)(end-begin));
	    verReport.setPreCondition(preCondition);
	    verReport.setPostCondition(postCondition);
		
		return verReport;
	}
	public VerificationReport verify(ASTGenerator ast, IASTFunctionDefinition function, String preCondition, String postCondition) 
			throws IOException {
		
		long begin = System.currentTimeMillis();
		
		VtseCFG cfg = new VtseCFG(function, ast);
		cfg.unfold();
		cfg.index();
		//cfg.printGraph();
		cfg.printMeta();
		cfg.printFormular(System.out);
		
		SMTInput smtInput = new SMTInput(cfg.getVm().getVariableList(), cfg.createFormular());
		
		String constraintTemp;

		List<String> constraints = new ArrayList<>();
		UserInput userInput = new UserInput();
		ArrayList<Variable> params = cfg.getInitVariables();
		params.add(cfg.getReturn());
		userInput.setParameter(params);
		
		// add pre-condition
		if (preCondition != null && !preCondition.equals("")) {
			constraintTemp = userInput.createUserAssertion(preCondition);
			constraints.add(constraintTemp);
		}
		
		// add user's assertion
		constraintTemp = userInput.createUserAssertion(postCondition);
		constraintTemp = "(not " + constraintTemp + ")";
		constraints.add(constraintTemp);
		
		smtInput.setConstrainst(constraints);
		
		long end = System.currentTimeMillis();
		
		String functionName = cfg.getNameFunction();
		String path = SMTINPUT_DIR + functionName + ".smt";
		FileOutputStream fo = new FileOutputStream(new File(path));
	    smtInput.printInputToOutputStream(fo);
	    
	    List<String> result = Z3Runner.runZ3(path);
	    
	    result.forEach(System.out::println);
	    Report report = new Report();
	    report.setListParameter(cfg.getInitVariables());
	    VerificationReport verReport = report.generateReport(result);
	    verReport.setFunctionName(cfg.getNameFunction());
	    verReport.setGenerateConstraintTime((int)(end-begin));
	    verReport.setPreCondition(preCondition);
	    verReport.setPostCondition(postCondition);
		
		return verReport;
	}
}
