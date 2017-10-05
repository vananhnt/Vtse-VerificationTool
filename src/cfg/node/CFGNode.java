package cfg.node;

import java.io.PrintStream;
import java.util.ArrayList;

import cfg.build.index.VariableManager;

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
	public String toString() {
		return "";
	}
	public void index(VariableManager vm) {
	}

	public boolean isVistited() {
		return vistited;
	}
	public String getFormula() {
		return null;	
	}
	public void printFormular(PrintStream ps) {
		ps.println(getFormula());
	}
	public void setVistited(boolean vistited) {
		this.vistited = vistited;
	}

}
