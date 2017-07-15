package cfg.build;

import org.eclipse.cdt.core.dom.ast.IASTExpression;

import cfg.node.BeginForNode;
import cfg.node.BeginIfNode;
import cfg.node.BeginWhileNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;
import cfg.node.IterationNode;
import cfg.node.PlainNode;


/**
 * @author va
 * sap dc r 
 */
public class UnfoldCFG {
	private int nLoops = 4;
	
	private CFGNode start;
	private CFGNode exit;
	
	public UnfoldCFG(ControlFlowGraph other) {
		start = null;
		exit = null;
	}
	
	public void generate(ControlFlowGraph otherCfg) {
		start = iterateNode(otherCfg.getStart());
	//	(new ControlFlowGraphBuilder()).print(start);
	}	
	private CFGNode findExit(CFGNode node) {
		CFGNode iter = node;
		while (!(iter.getNext() instanceof IterationNode)) {
			iter = iter.getNext();
		}
		return iter;
	}
	private ControlFlowGraph unfold(CFGNode node) {
		DecisionNode currentDecisionNode = (DecisionNode) node.getNext();
		IASTExpression conditionExpression = currentDecisionNode.getCondition().copy();
		
		EndConditionNode endNode = new EndConditionNode();
		BeginWhileNode beginNode = new BeginWhileNode();
		CFGNode thenNode = currentDecisionNode.getThenNode();
		CFGNode elseNode = currentDecisionNode.getElseNode();
		CFGNode exitThenNode = findExit(currentDecisionNode.getThenNode());
		CFGNode lastNode = endNode;
		
		//exitThenNode.printNode();
		ControlFlowGraph thenClause = new ControlFlowGraph();
		thenClause  = copyGraph(thenNode, exitThenNode);
		//thenClause.printGraph();
		ControlFlowGraph newClause = copyGraph(thenNode, exitThenNode);
		//thenClause = (new ControlFlowGraph(new EmptyNode(), new EmptyNode()));
	
		newClause.getExit().setNext(thenClause.getStart());
		newClause.setExit(thenClause.getExit());
		newClause.printGraph();
		
		return new ControlFlowGraph(new EmptyNode(), new EmptyNode());
	}		

	private ControlFlowGraph copyGraph(CFGNode start, CFGNode exit) {
		CFGNode newStart = start;
		if (start == exit) {
			//return null;
		} else if (start instanceof BeginWhileNode) {
			newStart = new BeginWhileNode();	
			newStart.setNext(copyGraph(start.getNext(), exit).getStart());
			//newStart.setNext(unfold(start).getStart());
		} 
		else if (start instanceof IterationNode) {
			newStart = new IterationNode();
			newStart.setNext(unfold(start).getStart());
			
		} else if (start instanceof DecisionNode) {
			newStart = new DecisionNode();
			((DecisionNode) newStart).setCondition(((DecisionNode) start).getCondition());
			((DecisionNode) newStart).setThenNode(copyGraph(((DecisionNode) start)
																.getThenNode(), exit).getStart());
			((DecisionNode) newStart).setElseNode(copyGraph(((DecisionNode) start)
																.getElseNode(), exit).getStart());
		
		} else if (start instanceof PlainNode) {
			newStart = new PlainNode(((PlainNode) start).getStatement());
			newStart.setNext(copyGraph(start.getNext(), exit).getStart());	
		
		} else if (start instanceof EndConditionNode) {
			newStart = new EndConditionNode();
			newStart.setNext(copyGraph(start.getNext(), exit).getStart());
				
		} else if (start instanceof BeginIfNode) {
			newStart = new BeginIfNode();
			newStart.setNext(copyGraph(start.getNext(), exit).getStart());
		
		} else if (start instanceof BeginForNode) {
			newStart = new BeginForNode();
			newStart.setNext(copyGraph(start.getNext(), exit).getStart());
		
		} else {
			newStart = new EmptyNode();
			newStart.setNext(copyGraph(start.getNext(), exit).getStart());	
		}
		return new ControlFlowGraph(newStart, exit);
	}
	
	private ControlFlowGraph unfoldCfg(CFGNode start, CFGNode exit) {
		
		CFGNode newStart = start;
		CFGNode tempNode;
		//ControlFlowGraph cfg = new ControlFlowGraph(start, exit);
		
		if (start == exit) {
			//return null;
		} else if (start instanceof BeginWhileNode) {
			newStart = new BeginWhileNode();	
			newStart.setNext(unfoldCfg(start.getNext(), exit).getStart());
			//newStart.setNext(unfold(start).getStart());
		} 
		else if (start instanceof IterationNode) {
			newStart = new IterationNode();
			newStart.setNext(unfold(start).getStart());
			
		} else if (start instanceof DecisionNode) {
			newStart = new DecisionNode();
			((DecisionNode) newStart).setCondition(((DecisionNode) start).getCondition());
			((DecisionNode) newStart).setThenNode(unfoldCfg(((DecisionNode) start).getThenNode(), exit).getStart());
			((DecisionNode) newStart).setElseNode(unfoldCfg(((DecisionNode) start).getElseNode(), exit).getStart());
			
		} else if (start instanceof PlainNode) {
			newStart = new PlainNode(((PlainNode) start).getStatement());
			newStart.setNext(unfoldCfg(start.getNext(), exit).getStart());	
		
		} else if (start instanceof EndConditionNode) {
			newStart = new EndConditionNode();
			newStart.setNext(unfoldCfg(start.getNext(), exit).getStart());
				
		} else if (start instanceof BeginIfNode) {
			newStart = new BeginIfNode();
			newStart.setNext(unfoldCfg(start.getNext(), exit).getStart());
		
		} else if (start instanceof BeginForNode) {
			newStart = new BeginForNode();
			newStart.setNext(unfoldCfg(start.getNext(), exit).getStart());
		
		} else {
			newStart = new EmptyNode();
			newStart.setNext(unfoldCfg(start.getNext(), exit).getStart());	
		}
		return new ControlFlowGraph(newStart, exit);
	}
	
	private CFGNode iterateNode(CFGNode node) {
		if (node == null) {
			node = null;	
		} else if (node instanceof BeginWhileNode) {
		//	node.setNext(iterateNode(unfold(node).getStart()));
			unfold(node).printGraph();
		} else if (node instanceof IterationNode) {
			//node = iterateNode(unfold(node).getStart());
			node = null;
		} else if (node instanceof DecisionNode) {
			((DecisionNode) node).setThenNode(iterateNode(((DecisionNode) node).getThenNode()));
			((DecisionNode) node).setElseNode(iterateNode(((DecisionNode) node).getElseNode())); 
		}
		 else {
			 node.setNext(iterateNode(node.getNext()));	
		}
		return node;
	}
	

}
