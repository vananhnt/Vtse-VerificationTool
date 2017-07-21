package cfg.node;

import java.util.ArrayList;

import cfg.utils.VariableManager;

public abstract class CFGNode {	
	protected CFGNode next;	
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

	public void index(VariableManager vm) {
	}

	public boolean isVistited() {
		return vistited;
	}
	public String getFormula() {
		return null;
		
	}
	
	public void setVistited(boolean vistited) {
		this.vistited = vistited;
	}

}
