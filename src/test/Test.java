package test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import cfg.build.ASTGenerator;
import cfg.build.VtseCFG;
import cfg.utils.Variable;

/**
 * @author va
 */
public class Test {
	public static void  main(String[] args) throws FileNotFoundException {
		
		//ast.print();
	
		//*Parameters:
		//DeclSpecifier : kieu tra ve cua ham
		//Declarator : ten cua ham, vd: test(), sum(int i)
		//declarator.getName: ten, vd: test, sum
		//declarator.getChildren() -> CPPASTParameterDeclaration int i,..
		//parameterDeclaration.getChildren -> Declarator : tham bien cua ham, vd: a, b, n, ...
	
		ASTGenerator ast = new ASTGenerator("./TestInput.c");
		VtseCFG total = new VtseCFG(ast.getListFunction().get(1), ast);
		total.unfold();
		total.index();
		total.printGraph();
		total.printFormular(System.out);
		
		/*
		 * Xu li th b += a
		 * IterationNode
		 */
	}
}
