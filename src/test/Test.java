package test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import cfg.build.ASTGenerator;
import cfg.build.VtseCFG;

/**
 * @author va
 * loi sua sau: chua index -> return printGraph mat bien
 */
public class Test {
	public static void  main(String[] args) throws FileNotFoundException {
		ASTGenerator ast = new ASTGenerator("./TestInput.c");
		ArrayList<IASTFunctionDefinition> funcList = ast.getListFunction();
		
		VtseCFG cfg;
		
		
		//*Parameters:
		//DeclSpecifier : kieu tra ve cua ham
		//Declarator : ten cua ham, vd: test(), sum(int i)
		//declarator.getName: ten, vd: test, sum
		//declarator.getChildren() -> CPPASTParameterDeclaration int i,..
		//parameterDeclaration.getChildren -> Declarator : tham bien cua ham, vd: a, b, n, ...
		for (IASTFunctionDefinition func : funcList) {
			cfg = new VtseCFG(func);
			System.out.println(cfg.getNameFunction());
			cfg.unfold();
			cfg.index();
			cfg.printGraph();
//			System.out.println();
		}
		

	}
}
