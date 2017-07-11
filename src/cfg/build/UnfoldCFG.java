package cfg.build;

import org.eclipse.cdt.core.dom.ast.IASTConditionalExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTStatement;

import cfg.node.BeginNode;
import cfg.node.BeginWhileNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;
import cfg.node.EndingNode;
import cfg.node.ForBeginningNode;
import cfg.node.IfBeginningNode;
import cfg.node.IterationNode;
import cfg.node.PlainNode;


/**
 * @author va
 * sap dc r 
 */
public class UnfoldCFG {
	private int nLoops = 3;
	
	private CFGNode start;

	
	public UnfoldCFG(ControlFlowGraph other) {
		start = null;
	
	}
	
	public void generate(ControlFlowGraph _cfg) {
		start = iterateNode(_cfg.getStart());
		(new ControlFlowGraphBuilder()).print(start);
	}
	
	private CFGNode unfold(CFGNode node, int loop) {
		if (loop == nLoops) node = null;
		else {
			node.setNext(unfold((subGraph((IterationNode) node)).getStart(), loop++));
		}
		return node;
	}
	 
	/**
	 * @param node
	 * @return
	 * test method
	 */
	private ControlFlowGraph subGraph(IterationNode node) {
		DecisionNode currentDecisionNode = (DecisionNode) node.getNext();
		IASTExpression conditionExpression = currentDecisionNode.getCondition();
		EndConditionNode endNode = new EndConditionNode();
		DecisionNode prevCondition = new DecisionNode();
		prevCondition.setCondition(conditionExpression);
		node.setNext(prevCondition); 
		for (int i = 0; i < nLoops - 1; i++) {	
			DecisionNode condition = new DecisionNode();
			condition.setCondition(conditionExpression);
			
			prevCondition.setNext(condition);
			prevCondition = condition;
		}
		prevCondition.setNext(endNode);
		return new ControlFlowGraph(node.getNext(), endNode);
	}
	
	
	/**
	 * @param node
	 * @return
	 * subGraph thu nghiem / loi
	 */
	private ControlFlowGraph subGraph1(IterationNode node) {
		//ControlFlowGraph tempGraph = new ControlFlowGraph();
		DecisionNode currentDecisionNode = (DecisionNode) node.getNext();
		IASTExpression conditionExpression = currentDecisionNode.getCondition();
		EndConditionNode endNode = new EndConditionNode();
		
		CFGNode thenNode = currentDecisionNode.getThenNode();
		CFGNode elseNode = currentDecisionNode.getElseNode();
		CFGNode lastNode = node;
		
		for (int i = 0; i < nLoops; i++) {
			
			DecisionNode condition = new DecisionNode();

			condition.setCondition(conditionExpression);
			condition.setThenNode(thenNode);
			condition.setElseNode(elseNode);
			
			condition.getThenNode().toString();

			System.out.println("1");
		}
		lastNode = endNode;
		return new ControlFlowGraph(node.getNext(), endNode);
		
	}
	
	private CFGNode iterateNode(CFGNode node) {
		if (node == null) {
			node = null;	
		} else if (node instanceof IterationNode) {
				node = iterateNode(subGraph((IterationNode) node).getStart());
			//	(new ControlFlowGraphBuilder()).print(subGraph((IterationNode) node).getStart());
				
			
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
