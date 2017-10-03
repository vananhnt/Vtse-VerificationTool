package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import cfg.build.ASTFactory;
import cfg.build.VtseCFG;
import cfg.utils.Variable;

/**
 * @author va
 *
 */
public class Test {
	public static void  main(String[] args) throws FileNotFoundException {
		ASTFactory ast = new ASTFactory("./testFunc.cpp");
		
		ast.print();
	
		//*Parameters:
		//DeclSpecifier : kieu tra ve cua ham
		//Declarator : ten cua ham, vd: test(), sum(int i)
		//declarator.getName: ten, vd: test, sum
		//declarator.getChildren() -> CPPASTParameterDeclaration int i,..
		//parameterDeclaration.getChildren -> Declarator : tham bien cua ham, vd: a, b, n, ...
		
		VtseCFG cfg = new VtseCFG(ast.getFunction("transmit3"), ast);
		
		//cfg.printBoundary();
//		cfg.unfold();
		cfg.index();
		cfg.printGraph();
		//cfg.printMeta();
		
		//cfg.printSMTFormula(System.out);
		//cfg.getVm().printList();
		//cfg.printFormular(System.out);
		//cfg.getVm().printList();
		//System.out.println("return_" + cfg.getNameFunction());
		//System.out.println(cfg.getVm().getVariable("return_" + cfg.getNameFunction()).toString());
		//cfg.getVm().printList();
		//PrintStream out = new PrintStream(new File("./smt.txt"));
		
	//	cfg.printSMTFormula(out);
	}
}
