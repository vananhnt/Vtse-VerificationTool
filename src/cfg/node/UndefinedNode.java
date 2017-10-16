package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTNode;

public class UndefinedNode extends CFGNode{
	private IASTNode node;
	public UndefinedNode() {}
	public UndefinedNode(IASTNode data) {
		node = data;
	}
	public void printNode() {
		if (node != null)
		System.out.println("Undefinded Element " + node.getClass().getSimpleName());
	}
	public IASTNode getNode() {
		return node;
	}
	public void setNode(IASTNode node) {
		this.node = node;
	}
	
}
