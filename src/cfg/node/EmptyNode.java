package cfg.node;

import java.io.Serializable;
import java.util.Map;

public class EmptyNode extends CFGNode implements Serializable{
	
	public EmptyNode deepCopy(Map<CFGNode, CFGNode> isomorphism) {
		EmptyNode copy = (EmptyNode) isomorphism.get(this);
		if (copy == null) {
			copy = new EmptyNode();
			isomorphism.put(this, copy);
			copy.setNext(this.deepCopy(isomorphism).getNext());
		}
		return copy;
	}
	public void printNode() {
		System.out.println("EmptyNode");
	}
}
