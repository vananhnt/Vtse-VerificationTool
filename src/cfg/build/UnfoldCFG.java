package cfg.build;

import org.eclipse.cdt.core.dom.ast.IASTExpression;

import cfg.node.BeginWhileNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;
import cfg.node.BeginForNode;
import cfg.node.BeginIfNode;
import cfg.node.IterationNode;
import cfg.node.PlainNode;


/**
 * @author va
 * sap dc r 
 */
public class UnfoldCFG {
	private int nLoops = 4;
	
	private CFGNode start;
	
	public UnfoldCFG(ControlFlowGraph other) {
		start = null;
	}
	
	public void generate(ControlFlowGraph otherCfg) {
		copyGraph(otherCfg.getStart(), otherCfg.getExit()).printGraph();		
	}	
	
	private ControlFlowGraph unfold(CFGNode node) {
		DecisionNode currentDecisionNode = (DecisionNode) node.getNext();
		IASTExpression conditionExpression = currentDecisionNode.getCondition();
		
		EndConditionNode endNode = new EndConditionNode();
		CFGNode beginNode = new BeginIfNode();
		CFGNode lastNode = endNode;
		CFGNode elseNode = new EmptyNode();
		
		return new ControlFlowGraph(lastNode, endNode);
	}		
	
	private ControlFlowGraph copyGraph(CFGNode start, CFGNode exit) {
		CFGNode newStart = start;
		CFGNode newExit = exit;
		CFGNode tempNode;
		if (start == exit || start == null) {
			return null;
	
		} else if (start instanceof BeginWhileNode) {
			newStart = new BeginWhileNode();
			newStart.setNext(iterateNode(start.getNext()));
		} 
		
		else if (start instanceof IterationNode) {
			newStart = new IterationNode();
			
		} else if (start instanceof DecisionNode) {
			//newStart = new DecisionNode();
			//tempNode = (DecisionNode) start;
			newStart = new EmptyNode();
			newStart.setNext(iterateNode(start.getNext()));
		} else if (start instanceof PlainNode) {
			newStart = new PlainNode(((PlainNode) start).getStatement());
			newStart.setNext(iterateNode(start.getNext()));
		
		} else if (start instanceof EndConditionNode) {
			newStart = new EndConditionNode();
			newStart.setNext(iterateNode(start.getNext()));
		
		} else if (start instanceof BeginIfNode) {
			newStart = new BeginIfNode();
			newStart.setNext(iterateNode(start.getNext()));
		
		} else if (start instanceof BeginForNode) {
			newStart = new BeginForNode();
			newStart.setNext(iterateNode(start.getNext()));
		
		} else {
			newStart = new EmptyNode();
			newStart.setNext(iterateNode(start.getNext()));
		}
		return new ControlFlowGraph(newStart, newExit);
	}
	
	
	private CFGNode iterateNode(CFGNode node) {
		CFGNode newNode = null;
		if (node == null) {
			return null;
		} else if (node instanceof BeginWhileNode) {
			newNode = new BeginWhileNode();
			newNode.setNext(iterateNode(node.getNext()));
		} 
		else if (node instanceof IterationNode) {
		//	node.setNext(iterateNode(subGraph(node).getStart()));			
		} else if (node instanceof DecisionNode) {
			newNode = new DecisionNode();
			((DecisionNode) newNode).setCondition(((DecisionNode) node).getCondition());
			((DecisionNode) newNode).setThenNode(iterateNode(((DecisionNode) node).getThenNode()));
			((DecisionNode) newNode).setElseNode(iterateNode(((DecisionNode) node).getElseNode())); 
		
		} else if (node instanceof PlainNode) {
			 newNode = new PlainNode(((PlainNode) node).getStatement());
			 newNode.setNext(iterateNode(node.getNext()));
		
		} else if (node instanceof EndConditionNode) {
			 newNode = new EndConditionNode();
			 newNode.setNext(iterateNode(node.getNext()));
		
		} else if (node instanceof BeginIfNode) {
			 newNode = new BeginIfNode();
			 newNode.setNext(iterateNode(node.getNext()));
		
		} else {
			newNode = new EmptyNode();
			newNode.setNext(iterateNode(node.getNext()));
		}
		
		return newNode;
	}
	

}
