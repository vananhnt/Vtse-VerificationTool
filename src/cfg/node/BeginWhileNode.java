package cfg.node;

import java.io.Serializable;
import java.util.Map;

/*
 * @va
 * beginNode cua WhileStatement va DoStatement
 */
public class BeginWhileNode extends BeginNode implements Serializable{
	
	public BeginWhileNode deepCopy(Map<CFGNode, CFGNode> isomorphism) {
		BeginWhileNode copy = (BeginWhileNode) isomorphism.get(this);
		if (copy == null) {
			copy = new BeginWhileNode();
			isomorphism.put(this, copy);
			copy.setEndNode(this.getEndNode());
		}
		return copy;
	}
	public void printNode() {
		System.out.println("BeginWhileNode");
	}
}
