package cfg.build;

import java.util.ArrayList;

import cfg.node.BeginNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.IterationNode;

/**
 *@va 
 * Ban nhap : khong chay duoc
 */
public class UnfoldGraph {
	//public ArrayList<CFGNode> graph = new ArrayList<>();
	
	public ArrayList<CFGNode> generate(CFGNode start) {
		ArrayList<CFGNode> graph = new ArrayList<>();
		iterateNode(graph, start);
		for (CFGNode node : graph){
			if (node instanceof DecisionNode) {
				ArrayList<CFGNode> subGraph = new ArrayList<>();
				subGraph = generate(((DecisionNode) node).getElseNode());
			}	
			//node.printNode();
			//System.out.println(graph.size());
		}
		return graph;
	}
	
	public void print(ArrayList<CFGNode> graph) {
		for (CFGNode node : graph){
			if (node instanceof DecisionNode) {
				System.out.println("++++++++++++++++++++");
				ArrayList<CFGNode> subGraph = new ArrayList<>();
				subGraph = generate(((DecisionNode) node).getElseNode());
			}	
			node.printNode();
			System.out.println(graph.size());
		}
	}
	
	private void iterateNode(ArrayList<CFGNode> graph, CFGNode node) {	
		if (node == null) {
			return ;
		}
		else if (node instanceof DecisionNode) {
			graph.add(node);
			iterateNode(graph, ((DecisionNode) node).getThenNode());
			iterateNode(graph, node.getNext());
//			iterateNode(((DecisionNode) node).getElseNode());
		}
		else if (node instanceof IterationNode) {		
		} else {	
			graph.add(node);
			iterateNode(graph, node.getNext());
			
		}
	}
}
