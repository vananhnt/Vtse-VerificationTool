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
		ASTGenerator ast = new ASTGenerator("./TestInput.c");
		
		//ast.print();
	
		//*Parameters:
		//DeclSpecifier : kieu tra ve cua ham
		//Declarator : ten cua ham, vd: test(), sum(int i)
		//declarator.getName: ten, vd: test, sum
		//declarator.getChildren() -> CPPASTParameterDeclaration int i,..
		//parameterDeclaration.getChildren -> Declarator : tham bien cua ham, vd: a, b, n, ...
		
		
		ArrayList<IASTFunctionDefinition> funcList = ast.getListFunction();
		for (IASTFunctionDefinition func : funcList) {
			VtseCFG cfg = new VtseCFG(func);
			cfg.unfold();
			cfg.index();
			cfg.printMeta();
			System.out.println();
		}
		
	}
}
