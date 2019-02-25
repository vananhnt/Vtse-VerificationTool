package cfg.node;

/*
 * @va
 * beginNode cua WhileStatement va DoStatement
 */

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

public class BeginWhileNode extends BeginNode {
	private IASTNode whileStatement;

	public BeginWhileNode() {
	}
	public BeginWhileNode(IASTNode stm) {
		whileStatement = stm;
	}
	public IASTNode getWhileStatement() {
		return whileStatement;
	}
	public void setWhileStatement(IASTWhileStatement stm) {
		whileStatement = stm;
	}
	public void printNode() {
		System.out.println("BeginWhileNode");
	}
}
