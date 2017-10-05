package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTNode;

import cfg.build.ASTFactory;
import cfg.build.VtseCFG;
import cfg.build.index.Variable;
import cfg.build.index.VariableManager;

/**
 * @author va
 *
 */
public class Test {
	public static void  main(String[] args) throws FileNotFoundException {
		ASTFactory ast = new ASTFactory("./testFunc.cpp");
		
//		ast.print();
//		ArrayList<IASTDeclaration> nodes = ast.getGlobarVarList();
//		for (IASTDeclaration node : nodes) {
//			System.out.println(node.getRawSignature());
//		}
		
//		ArrayList<String> nodes = ast.getGlobarVarStrList();
//		for (String node :nodes) {
//			System.out.println(node);
//		}
		//*Parameters:
		//DeclSpecifier : kieu tra ve cua ham
		//Declarator : ten cua ham, vd: test(), sum(int i)
		//declarator.getName: ten, vd: test, sum
		//declarator.getChildren() -> CPPASTParameterDeclaration int i,..
		//parameterDeclaration.getChildren -> Declarator : tham bien cua ham, vd: a, b, n, ...
		
		VtseCFG cfg = new VtseCFG(ast.getFunction("transmit3"), ast);

		//cfg.printBoundary();
		//cfg.unfold();
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
