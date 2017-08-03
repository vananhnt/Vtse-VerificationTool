package cfg.node;

import java.lang.Thread.State;

import org.eclipse.cdt.core.dom.ast.IASTStatement;

import cfg.utils.ExpressionHelper;

public class FunctionCallNode extends PlainNode {
	public FunctionCallNode() {}
	
	public FunctionCallNode(IASTStatement statement) {
		super(statement);
	}
	public void printNode() {
		System.out.println("FunctionCallNode: " + ExpressionHelper.toString(statement));
	}
}
