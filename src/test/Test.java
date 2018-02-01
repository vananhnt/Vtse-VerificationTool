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
		//ASTFactory ast = new ASTFactory("./kratos/transmitter.12.c");
		ASTFactory ast = new ASTFactory("./TestInput.c");
		VtseCFG cfg = new VtseCFG(ast.getFunction("main"), ast);
		//ast.print();
		//cfg.printBoundary();
		cfg.unfold();
		//cfg.index();
		cfg.printGraph();
		//cfg.printMeta();
		//cfg.getVm().printList();
		//cfg.printSMTFormula(System.out);

		
		
	}
}
