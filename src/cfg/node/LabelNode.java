package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTLabelStatement;

public class LabelNode extends CFGNode {
	private IASTLabelStatement statement;
	public LabelNode() {}
	public LabelNode(IASTLabelStatement stmt ) {
		statement = stmt;
	}
	public void printNode() {
		if (statement != null)
		System.out.println("LabelNode: " + statement.getName());
	}
}
