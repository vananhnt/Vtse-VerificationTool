package cfg.node;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTExpression;

public class DecisionNode extends CFGNode {
	private IASTExpression condition;
	private CFGNode thenNode;
	
	// elseNode is next
	
	public DecisionNode(){}

	
	public IASTExpression getCondition() {
		return condition;
	}
	
	public void setCondition(IASTExpression iastExpression) {
		this.condition = iastExpression;
	}
	
//	set THEN NODE with Input is CFGNode  or  IASTStatement		
	public CFGNode getThenNode() {
		return thenNode;
	}	
	public void setThenNode(CFGNode thenNode) {
		this.thenNode = thenNode;
	}
	
// set ELSE NODE with Input is CFGNode  or  IASTStatement	
	public void setElseNode( CFGNode elseNode){
		this.setNext(elseNode);
	}
	
	public CFGNode getElseNode(){
		return this.getNext();
	}

	public ArrayList<CFGNode> adjacent() {
		ArrayList<CFGNode> adj = new ArrayList<>();
		adj.add(this.getElseNode());
		adj.add(thenNode);
		return adj;
	}
	public void printNode(){
		if (condition != null)
		System.out.println("with Condition " + condition.getRawSignature());	
	}
				
}
