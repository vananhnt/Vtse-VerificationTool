package test;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;

import cfg.build.ASTGenerator;
import cfg.build.ControlFlowGraph;
import cfg.build.ControlFlowGraphBuilder;

public class Test {
	public static void  main(String[] args) {
		ASTGenerator ast = new ASTGenerator("./test.c");
		IASTFunctionDefinition func = ast.getFunction(0);
		//System.out.println(func.getDeclarator());
		
		IASTNode[] nodes = func.getDeclarator().getChildren();
		
		for (IASTNode node : nodes) {
			if (node instanceof IASTParameterDeclaration) {
				IASTNode[] innerChild = ((IASTParameterDeclaration) node).getChildren();
				for (IASTNode innerNode : innerChild) {
					System.out.println(innerNode.getClass());
				}
			}
		}
		//DeclSpecifier : kieu tra ve cua ham
		//Declarator : ten cua ham, vd: test(), sum(int i)
		//declarator.getName: ten, vd: test, sum
		//declarator.getChildren() -> CPPASTParameterDeclaration int i,..
		//parameterDeclaration.getChildren -> Declarator : tham bien cua ham, vd: a, b, n, ...
		
		
		//ControlFlowGraph cfg = (new ControlFlowGraphBuilder()).build(func);
		//cfg.unfold().DFS();
 	
	}
}
