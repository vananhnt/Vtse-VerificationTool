package cfg.node;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
// Node is parent of all Node in cfg
public class CFGNode implements Serializable{	
	private CFGNode next;	
	private boolean vistited;
	public CFGNode(){		
	}
	
	public CFGNode(CFGNode next){
		this.vistited = false;
		this.next = next;		
	}
	
	public CFGNode deepCopy(Map<CFGNode, CFGNode> isomorphism) {
		CFGNode copy = isomorphism.get(this);
		if (copy == null) {
			copy = new CFGNode();
			isomorphism.put(this, copy);
			copy.next = this.deepCopy(isomorphism);
		}
		return copy;
	}
	
	public CFGNode getNext() {
		return next;
	}

	public void setNext(CFGNode next) {
		this.next = next;
	}

	public ArrayList<CFGNode> adjacent() {
		ArrayList<CFGNode> adj = new ArrayList<>();
		adj.add(next);
		return adj;
	}
// print
		
	public void printNode(){	
		if (this != null) System.out.println(this.getClass());
	}

	public boolean isVistited() {
		return vistited;
	}

	public void setVistited(boolean vistited) {
		this.vistited = vistited;
	}

}
