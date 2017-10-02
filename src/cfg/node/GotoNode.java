package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTGotoStatement;
import org.eclipse.cdt.core.dom.ast.IASTLabelStatement;
import org.eclipse.cdt.core.dom.ast.IASTName;

public class GotoNode extends CFGNode {

	private IASTGotoStatement gotoStatement;
	
	public CFGNode getLabelNode() {
		return next;
	}
	public GotoNode() {}
	
	public GotoNode(IASTGotoStatement st) {
		gotoStatement = st;
	}
	public IASTName getLabelName() {
		IASTName name = gotoStatement.getName();
		return name;
		
	}
	public void printNode() {
		System.out.println("GotoNode: " + gotoStatement.getName());
	}
}
