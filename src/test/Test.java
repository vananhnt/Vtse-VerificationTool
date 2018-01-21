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
	//	ASTFactory ast = new ASTFactory("./kratos/bist_cell.c");
		ASTFactory ast = new ASTFactory("./floats-cdfpl-func/newton_1_1_true_unreach_call.c");

//		for (IASTFunctionDefinition func : ast.getListFunction()) {
//			System.out.println(FunctionHelper.getFunctionName(func));
//		}
		//ast.print();
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
		
		VtseCFG cfg = new VtseCFG(ast.getFunction("newton_1_1_true_unreach_call"), ast);
		
		//cfg.printBoundary();
		//cfg.unfold();
		//cfg.index();
		cfg.printGraph();
		//cfg.printMeta();
		//cfg.getVm().printList();
		//cfg.printSMTFormula(System.out);
		//System.out.println();
		//System.out.println(cfg.createInfixFormula());
	
		//System.out.println("return_" + cfg.getNameFunction());
		//System.out.println(cfg.getVm().getVariable("return_" + cfg.getNameFunction()).toString());
		
		//PrintStream out = new PrintStream(new File("./smt.txt"));
		//out.println(cfg.createInfixFormula());
		//cfg.va_printFormular(System.out);
		//cfg.printSMTFormula(out);
		//cfg.printFormular(System.out);
		
		
	}
}
