package test;

import java.io.IOException;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import app.verification.FunctionVerification;
import app.verification.report.VerificationReport;
import cfg.build.ASTFactory;

public class TestFunction {
	public static void main(String[] args) {
		ASTFactory ast = new ASTFactory("./TestInput.c");
		IASTFunctionDefinition function = ast.getFunction(0);
		
		FunctionVerification functionVerification = new FunctionVerification();
		try {
			VerificationReport report = functionVerification.verify(ast, function, "", "", 3);
			System.out.println(report.getCounterEx());
		} catch (IOException e) {
			e.printStackTrace();
		}
 /*		
		VtseCFG cfg = new VtseCFG(function);
		
		cfg.unfold();
		cfg.index();
		
		//cfg.getVm().getVariableList();
		
		//System.out.println(cfg.getVm().getVariable("return"));
		
		SMTInput smtInput = new SMTInput();
		smtInput.setFormula(cfg.createFormular());
		smtInput.setVariableList(cfg.getVm().getVariableList());
		
		UserInput userInput = new UserInput();
		userInput.setParameter(cfg.getInitVariables());
		
		for (Variable var: cfg.getInitVariables()) {
			System.out.println(var);
		}
*/
		
//		File smtImputFile = new File("./z3/bin/test/input.smt.txt");
		
//		try {
//			FileOutputStream is = new FileOutputStream(smtImputFile);
//			smtInput.printInputToOutputStream(is);
//			smtInput.printInputToOutputStream(System.out);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		//cfg.printMeta();
		//cfg.getExit().printNode();
		//cfg.printGraph();
		//cfg.printFormular(System.out);
		//cfg.getVm().printList();
		//PrintStream out = new PrintStream("smt.txt");
		
		//PrintStream out = System.out;
		//cfg.printSMTFormula(out);
		
		//cfg.printMeta();
		//cfg.printFormular(System.out);
		

		
		
		
//		System.out.println( cfg.getNameFunction());
//		System.out.println( cfg.getTypeFunction());
	}
}
