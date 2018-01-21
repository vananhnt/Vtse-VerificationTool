package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import cfg.build.ASTFactory;
import cfg.build.VtseCFG;

/**
 * @author va
 *
 */
public class Test {
	public static void  main(String[] args) throws FileNotFoundException {
		ASTFactory ast = new ASTFactory("./kratos/token_ring.10.c");
		VtseCFG cfg = new VtseCFG(ast.getFunction("main"), ast);
		
		//cfg.printBoundary();
		//cfg.unfold();
		//cfg.index();
		cfg.printGraph();
		//cfg.printMeta();
		//cfg.getVm().printList();
		//cfg.printSMTFormula(System.out);
		//System.out.println();
		//System.out.println(cfg.createInfixFormula());
	
		//System.out.println("return_" + cfg.getNameFunction());
		//System.out.println(cfg.getVm().getVariable("return_" + cfg.getNameFunction()).toString());
		
		//PrintStream out = new PrintStream(new File("./smt.txt"));
		//out.println(cfg.createInfixFormula());
		//cfg.va_printFormular(System.out);
		//cfg.printSMTFormula(out);
		//cfg.printFormular(System.out);
		
		
	}
}
