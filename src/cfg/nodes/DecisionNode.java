package cfg.nodes;

import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionStatement;
import org.eclipse.cdt.internal.core.util.ToStringSorter;

public class DecisionNode extends CFGNode{
	private CPPASTExpressionStatement condition;
	private CFGNode thenNode;
	private EndConditionNode endNode;
	// elseNode is next
	
	public DecisionNode(){
		super();
		this.condition = null;
		this.thenNode = null;
		this.endNode = null;
	}
	
	public DecisionNode( CFGNode prev, EndConditionNode next){
		this.setPrev(prev);
		this.endNode = next;
	}
	
	
	
	public CPPASTExpressionStatement getCondition() {
		return condition;
	}
	public void setCondition(CPPASTExpressionStatement condition) {
		this.condition = condition;
	}
	public CFGNode getThenNode() {
		return thenNode;
	}
	public void setThenNode(CFGNode thenNode) {
		this.thenNode = thenNode;
	}
	public EndConditionNode getEndNode() {
		return endNode;
	}
	public void setEndNode(EndConditionNode endNode) {
		this.endNode = endNode;
	}
	
	public String toString(){
		
		 // TODO
		return null;
	}
		
}
