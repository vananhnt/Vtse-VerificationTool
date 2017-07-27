package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import app.solver.SMTInput;
import cfg.build.ASTGenerator;
import cfg.build.VtseCFG;

public class TestFunction {
	public static void main(String[] args) {
		ASTGenerator ast = new ASTGenerator("./TestInput.c");
		IASTFunctionDefinition function = ast.getFunction(0);
		
		VtseCFG cfg = new VtseCFG(function);
		
		cfg.unfold();
		cfg.index();
		
		//cfg.getVm().getVariableList();
		
		//System.out.println(cfg.getVm().getVariable("return"));
		
		SMTInput smtInput = new SMTInput();
		smtInput.setFormula(cfg.createFormular());
		smtInput.setVariableList(cfg.getVm().getVariableList());
		
		File smtImputFile = new File("./z3/bin/test/input.smt.txt");
		
		try {
			FileOutputStream is = new FileOutputStream(smtImputFile);
			smtInput.printInputToOutputStream(is);
			smtInput.printInputToOutputStream(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		
		
		System.out.println( cfg.getNameFunction());
		System.out.println( cfg.getTypeFunction());
	}
}
