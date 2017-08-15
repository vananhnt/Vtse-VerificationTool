package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import cfg.build.ASTGenerator;
import cfg.build.VtseCFG;

/**
 * @author va
 *
 */
public class Test {
	public static void  main(String[] args) throws FileNotFoundException {
		ASTGenerator ast = new ASTGenerator("./float-cdfpl/newton_1_1_true_unreach_call.c");
	
		//ast.print();
	
		//*Parameters:
		//DeclSpecifier : kieu tra ve cua ham
		//Declarator : ten cua ham, vd: test(), sum(int i)
		//declarator.getName: ten, vd: test, sum
		//declarator.getChildren() -> CPPASTParameterDeclaration int i,..
		//parameterDeclaration.getChildren -> Declarator : tham bien cua ham, vd: a, b, n, ...
		
		VtseCFG cfg = new VtseCFG(ast.getFunction(0), ast);
		
		cfg.unfold();
		cfg.index();
		cfg.printGraph();
		//cfg.printFormular(System.out);
		//System.out.println("return_" + cfg.getNameFunction());
		//System.out.println(cfg.getVm().getVariable("return_" + cfg.getNameFunction()).toString());
		//cfg.getVm().printList();
		PrintStream out = new PrintStream(new File("./smt.txt"));
		
		cfg.printSMTFormula(out);
	}
}
