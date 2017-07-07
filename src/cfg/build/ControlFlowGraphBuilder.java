package cfg.build;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;

public class ControlFlowGraphBuilder {
	
	/*
	 * Builds the graph.
	 */
	
	public ControlFlowGraph build (IASTFunctionDefinition def) {
		return createSubGraph(def.getBody());
	}

	private ControlFlowGraph createSubGraph(IASTStatement body) {
		if (body instanceof IASTCompoundStatement) {
			IASTCompoundStatement comp = (IASTCompoundStatement) body;
			for (IASTStatement statement : comp.getStatements()) {
				createSubGraph(statement);
			}
		} else if (body instanceof IASTIfStatement) {
			return createIf((IASTIfStatement) body);
		} else if (body instanceof IASTForStatement) {
			return createFor((IASTForStatement) body);
		} else if (body instanceof IASTReturnStatement) {
			System.out.println("return");
		}
		return null;
	}

	private ControlFlowGraph createIf(IASTIfStatement body) {
		System.out.println("IfStatement");
		return null;
	}

	private ControlFlowGraph createFor(IASTForStatement body) {
		System.out.println("ForStatement");
		return null;
	}
	
	/*/
	 * main: print the graph
	 */
	public static void  main(String[] args) {
		IASTFunctionDefinition func = (new ASTGenerator("./test.c")).getFunction(0);
		ControlFlowGraph cfg = (new ControlFlowGraphBuilder()).build(func);
		
		

	}
}
