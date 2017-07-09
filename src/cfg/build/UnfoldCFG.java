package cfg.build;

import org.eclipse.cdt.core.dom.ast.IASTConditionalExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;

import cfg.node.BeginWhileNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;
import cfg.node.ForBeginningNode;
import cfg.node.IfBeginningNode;
import cfg.node.IterationNode;


/**
 * @author va
 * test teo thoi
 */
public class UnfoldCFG {
	private int nLoops = 3;
	
	public void generate(ControlFlowGraph cfg) {
		iterateNode(cfg.getStart());
	}
	
	private ControlFlowGraph unfoldWhile(BeginWhileNode beginWhileNode) {
		DecisionNode decisionNode = (DecisionNode) beginWhileNode.getNext();
		EndConditionNode end = (EndConditionNode) decisionNode.getElseNode().getNext();
		CFGNode lastNode = decisionNode;
		for (int i = 0; i < nLoops; i++) {
			DecisionNode condition = new DecisionNode();
			condition = (DecisionNode) lastNode;
			condition.setElseNode(decisionNode.getElseNode());
			condition.setThenNode(decisionNode.getThenNode());
			condition.getElseNode().setNext(end);
			condition.printNode();
			condition.getThenNode().printNode();
			condition.getElseNode().printNode();
			lastNode = condition.getThenNode().getNext();
			lastNode.printNode();
		}
		return new ControlFlowGraph(beginWhileNode, end);
	}
	
	private void iterateNode(CFGNode node) {
		ControlFlowGraph cfg = new ControlFlowGraph();
		
		if (node == null) {
			return;
		}
		/*
		 * sao khong kiem tra duoc la BeginWhileNode TT_TT
		 */
		else if (node instanceof BeginWhileNode) {
			System.out.println("1");
		}
		else if (node instanceof DecisionNode) {
			
			iterateNode(((DecisionNode) node).getThenNode());
			iterateNode(((DecisionNode) node).getElseNode());
		}
		else if (node instanceof EndConditionNode){
		
		} else {
			iterateNode(node.getNext());
		}
	}
		
}
