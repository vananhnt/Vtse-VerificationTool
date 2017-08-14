package test;

import java.io.FileNotFoundException;

import cfg.build.ASTGenerator;
import cfg.build.VtseCFG;

/**
 * @author va
 *
 */
public class Test {
	public static void  main(String[] args) throws FileNotFoundException {
		ASTGenerator ast = new ASTGenerator("./test.c");
	
		//ast.print();
	
		//*Parameters:
		//DeclSpecifier : kieu tra ve cua ham
		//Declarator : ten cua ham, vd: test(), sum(int i)
		//declarator.getName: ten, vd: test, sum
		//declarator.getChildren() -> CPPASTParameterDeclaration int i,..
		//parameterDeclaration.getChildren -> Declarator : tham bien cua ham, vd: a, b, n, ...
		
		VtseCFG cfg = new VtseCFG(ast.getMain(), ast);
		
		cfg.unfold();
		cfg.index();
		cfg.printGraph();
		//cfg.printFormular(System.out);
		//System.out.println("return_" + cfg.getNameFunction());
		//System.out.println(cfg.getVm().getVariable("return_" + cfg.getNameFunction()).toString());
		cfg.getVm().printList();
	}
}
