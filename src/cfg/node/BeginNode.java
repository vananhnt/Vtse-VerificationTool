package cfg.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTStatement;
// begin Node ko chua du lieu
/* node begin the statements if/ for/ while....
 * don't contain data * 
 */
public class BeginNode extends CFGNode implements Serializable{	
	private CFGNode endNode;

	public CFGNode getEndNode() {
		return endNode;
	}
	
	public BeginNode deepCopy(Map<CFGNode, CFGNode> isomorphism) {
		BeginNode copy = (BeginNode) isomorphism.get(this);
		if (copy == null) {
			copy = new BeginNode();
			isomorphism.put(this, copy);
			copy.endNode = this.deepCopy(isomorphism);
		}
		return copy;
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
