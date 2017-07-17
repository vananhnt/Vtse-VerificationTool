package cfg.node;

import java.io.Serializable;
import java.util.Map;

public class EndConditionNode extends EndNode implements Serializable {
	public EndConditionNode (){
	}
	public EndConditionNode(CFGNode node){
		super(node);
	}
	public EndConditionNode deepCopy(Map<CFGNode, CFGNode> isomorphism) {
		EndConditionNode copy = (EndConditionNode) isomorphism.get(this);
		if (copy == null) {
			copy = new EndConditionNode();
			isomorphism.put(this, copy);
			copy.setNext(this.deepCopy(isomorphism).getNext());
		}
		return copy;
	}
	
	public void printNode() {
		System.out.println("EndConditionNode");
	}
}
