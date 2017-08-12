package test;

import java.io.FileNotFoundException;

import cfg.build.ASTGenerator;
import cfg.build.VtseCFG;

/**
 * @author va
 */
public class Test {
	public static void  main(String[] args) throws FileNotFoundException {
		
	
	
		//*Parameters:
		//DeclSpecifier : kieu tra ve cua ham
		//Declarator : ten cua ham, vd: test(), sum(int i)
		//declarator.getName: ten, vd: test, sum
		//declarator.getChildren() -> CPPASTParameterDeclaration int i,..
		//parameterDeclaration.getChildren -> Declarator : tham bien cua ham, vd: a, b, n, ...
	
		ASTGenerator ast = new ASTGenerator("./testInput.c");
		//ast.print();
		//VtseCFG total = new VtseCFG(ast.getMain());
		VtseCFG total = new VtseCFG(ast.getFunction("main"), ast);
		total.unfold();
		total.index();
		total.printGraph();
		//total.getVm().printList();
	
	}
}
