package test;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import cfg.build.ASTGenerator;
import cfg.build.VtseCFG;

/**
 * @author va
 *
 */
public class Test {
	public static void  main(String[] args) throws FileNotFoundException {
		ASTGenerator ast = new ASTGenerator("./TestInput.c");
		IASTFunctionDefinition func = ast.getFunction(0);
		//ast.print();
	
		//*Parameters:
		//DeclSpecifier : kieu tra ve cua ham
		//Declarator : ten cua ham, vd: test(), sum(int i)
		//declarator.getName: ten, vd: test, sum
		//declarator.getChildren() -> CPPASTParameterDeclaration int i,..
		//parameterDeclaration.getChildren -> Declarator : tham bien cua ham, vd: a, b, n, ...
		
		VtseCFG cfg = new VtseCFG(func);
	
		cfg.unfold();
		cfg.index();
		cfg.printMeta();
		//cfg.getExit().printNode();
		//cfg.printGraph();
		cfg.printFormular(System.out);
		//cfg.getVm().printList();
		//PrintStream out = new PrintStream("smt.txt");
		
		//PrintStream out = System.out;
		//cfg.printSMTFormula(out);
		
	}
}
