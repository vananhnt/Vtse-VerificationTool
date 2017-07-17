package cfg.node;

import java.io.Serializable;
import java.util.Map;

public class BeginForNode extends BeginNode implements Serializable{
	public BeginForNode(){}
	
	public BeginForNode deepCopy(Map<CFGNode, CFGNode> isomorphism) {
		BeginForNode copy = (BeginForNode) isomorphism.get(this);
		if (copy == null) {
			copy = new BeginForNode();
			isomorphism.put(this, copy);
			copy.setEndNode(this.getEndNode());
		}
		return copy;
	}
	public void printNode() {
		System.out.println("BeginForNode ");
	}
}
