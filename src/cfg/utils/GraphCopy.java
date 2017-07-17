package cfg.utils;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import cfg.build.ASTGenerator;
import cfg.build.ControlFlowGraph;
import cfg.build.ControlFlowGraphBuilder;
import cfg.node.CFGNode;
import cfg.node.EmptyNode;

public class GraphCopy {
	private ArrayList<CFGNode> nodeList = new ArrayList<>();
	private Map<CFGNode, CFGNode> isomorphism = new IdentityHashMap<>();	
	
	public GraphCopy(ControlFlowGraph cfg) {
		deepCopy(cfg);	
	}

	private void deepCopy(ControlFlowGraph other) {
		traversalCopy(other.getStart());	
	}
	
	private void traversalCopy(CFGNode node) {
		node.setVistited(true);
		nodeList.add(node.deepCopy(isomorphism));
	
		ArrayList<CFGNode> adj = node.adjacent();
		for (CFGNode iter : adj) {
			if (iter == null) return;
			if (!iter.isVistited()){
				traversalCopy(iter);
			} 
		}
	}
	
	public ControlFlowGraph getGraph() {
		int length = nodeList.size();
		return new ControlFlowGraph(nodeList.get(0), nodeList.get(length - 1) );	
	}
	
	public static void main(String[] args) {
		IASTFunctionDefinition func = (new ASTGenerator("./bai1.cpp")).getFunction(0);
		ControlFlowGraph cfg = (new ControlFlowGraphBuilder()).build(func);
		GraphCopy graphCopy = new GraphCopy(cfg);
		//cfg.printGraph();
		System.out.println("==========================");
		
		ControlFlowGraph newGraph = graphCopy.getGraph();
		newGraph.printDebug();
		graphCopy.nodeList.get(0).toString();
	}
}

