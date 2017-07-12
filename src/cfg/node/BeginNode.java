package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTStatement;
// begin Node ko chua du lieu
/* node begin the statements if/ for/ while....
 * don't contain data * 
 */
public class BeginNode extends CFGNode{	
	private CFGNode endNode;

	public CFGNode getEndNode() {
		return endNode;
	}

	public void setEndNode(CFGNode endNode) {
		this.endNode = endNode;
	}
	
}
