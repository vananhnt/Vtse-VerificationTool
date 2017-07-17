package cfg.build;

import java.util.LinkedList;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import cfg.node.BeginForNode;
import cfg.node.BeginIfNode;
import cfg.node.BeginWhileNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;
import cfg.node.IterationNode;
import cfg.node.PlainNode;
import cfg.utils.ObjectCloner;


/**
 * @author va
 * sap dc r 
 */
public class UnfoldCFG {
	private int nLoops = 2;
	
	private CFGNode start;
	
	public UnfoldCFG() {}
	
	public UnfoldCFG(ControlFlowGraph cfg) {
		generate(cfg);
	}
	
	public void generate(ControlFlowGraph otherCfg) {
		try {
			start = iterateNode(otherCfg.getStart());
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}	
	
	/**
	 * @param start
	 * @return
	 */
	private CFGNode findExit(CFGNode start) {
		CFGNode iter = start;
		while (!(iter instanceof IterationNode)) {
			iter = iter.getNext();
		}
		return iter;
	}
	
	
	private ControlFlowGraph unfoldWhile(CFGNode start, CFGNode exit) throws Exception {
		DecisionNode currentCondition = (DecisionNode) start.getNext();
		String conditionExpression = currentCondition.getCondition();
		EndConditionNode endNode = (EndConditionNode) ((BeginWhileNode) start).getEndNode();
		CFGNode lastNode = endNode;
		ControlFlowGraph thenClause = new ControlFlowGraph(currentCondition.getThenNode(), 
					findExit(currentCondition.getThenNode())); 
		ControlFlowGraph copyThen;
		
		thenClause.setStart(iterateNode(thenClause.getStart()));
		//thenClause.printGraph();
		
		for (int i = 0; i < nLoops; i++) {
			DecisionNode condition = new DecisionNode();
			condition.setCondition(conditionExpression);
			condition.setElseNode(new EmptyNode());
			condition.getElseNode().setNext(endNode);
			
			copyThen = new ControlFlowGraph();
			copyThen = (ControlFlowGraph) ObjectCloner.deepCopy(thenClause);
			
			condition.setThenNode(copyThen.getStart());
			copyThen.getExit().setNext(lastNode);
			lastNode = condition;
		}
		CFGNode beginNode = new BeginWhileNode();
		beginNode.setNext(lastNode);
	 	//new ControlFlowGraph(beginNode.getNext(), endNode).printGraph();
		return new ControlFlowGraph(beginNode.getNext(), endNode);
	}
	
	private ControlFlowGraph unfoldFor(CFGNode start, CFGNode exit) throws Exception {
		PlainNode initNode = (PlainNode) start.getNext();
	
		DecisionNode currentCondition = (DecisionNode) initNode.getNext();
		String conditionExpression = currentCondition.getCondition();
		EndConditionNode endNode = (EndConditionNode) ((BeginForNode) start).getEndNode();
		CFGNode lastNode = endNode;
		ControlFlowGraph thenClause = new ControlFlowGraph(currentCondition.getThenNode(), 
					findExit(currentCondition.getThenNode())); 
		ControlFlowGraph copyThen;
		
		thenClause.setStart(iterateNode(thenClause.getStart()));
		//thenClause.printGraph();
		
		for (int i = 0; i < nLoops; i++) {
			DecisionNode condition = new DecisionNode();
			condition.setCondition(conditionExpression);
			condition.setElseNode(new EmptyNode());
			condition.getElseNode().setNext(endNode);
			
			copyThen = new ControlFlowGraph();
			copyThen = (ControlFlowGraph) ObjectCloner.deepCopy(thenClause);
			
			condition.setThenNode(copyThen.getStart());
			copyThen.getExit().setNext(lastNode);
			lastNode = condition;
		}
		CFGNode beginNode = new BeginForNode();
		initNode.setNext(lastNode);
		beginNode.setNext(initNode);
	 	//new ControlFlowGraph(beginNode.getNext(), endNode).printGraph();
		return new ControlFlowGraph(beginNode.getNext(), endNode);
	} 
	
	private CFGNode iterateNode(CFGNode node) throws Exception {
		if (node == null) {
			return null;
		
		} else if (node instanceof BeginWhileNode) {
			ControlFlowGraph whileGraph = unfoldWhile(node, ((BeginWhileNode) node).getEndNode());
			node.setNext(whileGraph.getStart());
			whileGraph.getExit().setNext(iterateNode(((BeginWhileNode) node).getEndNode().getNext()));
			
		} else if (node instanceof PlainNode) {
			node.setNext(iterateNode(node.getNext()));	
	
		} else if (node instanceof BeginIfNode) {
			node.setNext(iterateNode(node.getNext()));	
			
		} else if (node instanceof BeginForNode) {
			ControlFlowGraph forGraph = unfoldFor(node, ((BeginForNode) node).getEndNode());
			node.setNext(forGraph.getStart());
			forGraph.getExit().setNext(iterateNode(((BeginForNode) node).getEndNode().getNext()));
	
		} else if (node instanceof EmptyNode) {
			node.setNext(iterateNode(node.getNext()));				
		
		} else if (node instanceof EndConditionNode) {
			node.setNext(iterateNode(node.getNext()));		
		}
		return node;
}

	public static void  main(String[] args) {
		IASTFunctionDefinition func = (new ASTGenerator("./test.c")).getFunction(0);
		ControlFlowGraph cfg = (new ControlFlowGraphBuilder()).build(func);
		//cfg.printGraph();
		UnfoldCFG newCfg = new UnfoldCFG();
		newCfg.generate(cfg);
		
 	}
	

}

