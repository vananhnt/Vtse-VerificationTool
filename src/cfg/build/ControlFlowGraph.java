package cfg.build;
/*
 * nguyen thi thuy 97
 * @vananh: Note to Thuy: Da them ham addNode va sua printGraph
 * 						  De in cfg chi can: cfg.printGraph()
 */

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTContinueStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNullStatement;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;
import cfg.node.EndNode;
import cfg.node.BeginForNode;
import cfg.node.BeginIfNode;
import cfg.node.IterationNode;
import cfg.node.PlainNode;

public class ControlFlowGraph {
	private CFGNode start;
	private CFGNode exit;

	public ControlFlowGraph(){}
	
	public ControlFlowGraph(CFGNode start, CFGNode exit) {
		this.start = start;
		this.exit = exit;
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
	
	public void addNode(CFGNode node) {
		if (start == null) {
			start = node;
			exit = node;
		}
		else {
			exit.setNext(node);
			exit = node;
		}
	}
	public void setExit(CFGNode node) {
		exit = node;
	}
	public ControlFlowGraph copy() {
		return this;
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

	
	public void printGraph() {
		if (this != null)
			print(start, exit, 0);
	}
	
	
	private void printSpace(int level) {
		for (int i = 0; i < level; i++) {
			System.out.print(" ");
		}
	}
	
	private void print(CFGNode start, CFGNode exit, int level) {
		CFGNode iter = start;
		printSpace(level);
		if (iter == exit) {
			iter.printNode();
			return;
		} else if (iter == null) {
			return;
		} else if (iter instanceof DecisionNode) {
			iter.printNode();
			printSpace(level);
			System.out.println("Then Clause: ");
			if (((DecisionNode) iter).getThenNode() != null) {
				print(((DecisionNode) iter).getThenNode(), exit, level + 10);
			}
			printSpace(level);
			System.out.println("Else Clause: ");
			if (((DecisionNode) iter).getElseNode() != null)
				print(((DecisionNode) iter).getElseNode(), exit,  level + 10);		
		} 
		else if (iter instanceof IterationNode) {
			iter.printNode();
			return;
		} else if (iter instanceof EmptyNode) {
			iter.printNode();
			print(iter.getNext(), exit, level);
		} else if (iter instanceof EndConditionNode) {	
			iter.printNode();
			print(iter.getNext(), exit, level - 10);
		}
		else {
			iter.printNode();
			print(iter.getNext(), exit, level);
		}	
	}
}
