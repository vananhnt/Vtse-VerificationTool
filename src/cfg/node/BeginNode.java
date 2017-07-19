package cfg.node;

import java.util.ArrayList;
// begin Node ko chua du lieu
/* node begin the statements if/ for/ while....
 * don't contain data * 
 */

public class BeginNode extends CFGNode {	
	private CFGNode endNode;

	public CFGNode getEndNode() {
		return endNode;
	}
	
	public ArrayList<CFGNode> adjacent() {
		ArrayList<CFGNode> adj = new ArrayList<>();
		adj.add(this.getNext());
		adj.add(endNode);
		return adj;
	}
	
	public void setEndNode(CFGNode endNode) {
		this.endNode = endNode;
	}
	
	
}
