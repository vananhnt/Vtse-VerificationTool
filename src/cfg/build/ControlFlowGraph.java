package cfg.build;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import cfg.node.BeginNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;
import cfg.node.IterationNode;


public class ControlFlowGraph {
	protected IASTFunctionDefinition func;
	protected CFGNode start;
	protected CFGNode exit;
	
	
	public ControlFlowGraph(){}
	
	public ControlFlowGraph(IASTFunctionDefinition def) {
		ControlFlowGraph cfg = build(def);
		start = cfg.getStart();
		exit = cfg.getExit();
		func = def;
	
	}
	public ControlFlowGraph(CFGNode start, CFGNode exit) {
		this.start = start;
		this.exit = exit;
	}		
	
	
	public void setExit(CFGNode node) {
		exit = node;
	}
	
	public void concat(ControlFlowGraph other) {
		if (start == null) {
			start = other.start;
			exit = other.exit;
		}
		else {
			exit.setNext(other.start);
			exit = other.exit;
		}
	}
	
	public ControlFlowGraph build (IASTFunctionDefinition def) {
		return (new ControlFlowGraphBuilder()).build(def);
	}

	public CFGNode getStart() {
		return start;
	}

	public void setStart(CFGNode start) {
		this.start = start;
	}

	public CFGNode getExit() {
		return exit;
	}
	
	public void DFS() {
		DFSHelper(start);	
	}
	
	private void DFSHelper(CFGNode node) {
		node.setVistited(true);
		//node.printNode();
		ArrayList<CFGNode> adj = node.adjacent();
		for (CFGNode iter : adj) {
			if (iter == null) return;
			if (!iter.isVistited()){
				DFSHelper(iter);
			} 
		}
	}

	/**
	 * Dung de unfold graph
	 * @return
	 */
	public ControlFlowGraph unfold() {
		UnfoldCFG unfoldCfg = new UnfoldCFG(this);
		return new ControlFlowGraph(unfoldCfg.getStart(), unfoldCfg.getExit());
	}
	
	/**
	 * Ham printGraph de in cfg
	 */
	public void printGraph() {
		if (this != null)
			print(start, 0);
	}
	
	public static void printGraph(CFGNode start) {
			print(start, 0);
	}
	
	public void printDebug(){
		printDebug(start);
	}
	
	private static void printSpace(int level) {
		for (int i = 0; i < level; i++) {
			System.out.print(" ");
		}
	}
		
	private static void print(CFGNode start, int level) {
		CFGNode iter = start;
		printSpace(level);
		if (iter == null) {
			return;
		} else if (iter instanceof DecisionNode) {
			iter.printNode();
			printSpace(level);
			System.out.println("Then Clause: ");
			if (((DecisionNode) iter).getThenNode() != null) {
				print(((DecisionNode) iter).getThenNode(), level + 7);
			}
			//printSpace(level);
			System.out.println("Else Clause: ");
			if (((DecisionNode) iter).getElseNode() != null)
				print(((DecisionNode) iter).getElseNode(),  level + 7);		
		} 
		else if (iter instanceof IterationNode) {
			iter.printNode();
			if (iter.getNext() != null) print(iter.getNext(), level);
			else return;
		} else if (iter instanceof EmptyNode) {
			iter.printNode();
			print(iter.getNext(), level);
		} else if (iter instanceof EndConditionNode) {	
			level -= 7;
		} else if (iter instanceof BeginNode)  {
			iter.printNode();
			print(iter.getNext(), level);
			((BeginNode) iter).getEndNode().printNode();
			print(((BeginNode) iter).getEndNode().getNext(),  level);
		} else {
			iter.printNode();
			print(iter.getNext(), level);
		}	
	}
	
	private void printDebug(CFGNode start) {
		CFGNode iter = start;
		if (iter == null) return;	
		else if (iter instanceof DecisionNode) {	
			iter.printNode();
			System.out.print("      ");
			printDebug(((DecisionNode) iter).getThenNode());
			System.out.print("      ");
			printDebug(((DecisionNode) iter).getElseNode());
		} else if (iter instanceof EndConditionNode){
			System.out.print("EndConditionNode");
			return;
		} else {
			if (iter != null){
				iter.printNode();
				printDebug(iter.getNext());
			}
		}		
	
	}

}
