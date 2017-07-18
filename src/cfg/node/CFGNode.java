package cfg.node;
import java.util.ArrayList;

public class CFGNode {	
	private CFGNode next;	
	private boolean vistited;
	public CFGNode(){		
	}
	
	public CFGNode(CFGNode next){
		this.vistited = false;
		this.next = next;		
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
