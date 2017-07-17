package cfg.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionStatement;
import org.eclipse.cdt.internal.core.util.ToStringSorter;

import cfg.utils.ExpressionHelper;

public class DecisionNode extends CFGNode implements Serializable{
	private String condition;
	private CFGNode thenNode;
	
	// elseNode is next
	
	public DecisionNode(){}

	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String iastExpression) {
		this.condition = iastExpression;
	}
	public void setCondition(IASTExpression iastExpression) {
		this.condition = iastExpression.getRawSignature();
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

	public DecisionNode deepCopy(Map<CFGNode, CFGNode> isomorphism) {
		DecisionNode copy = (DecisionNode) isomorphism.get(this);
		if (copy == null) {
			copy = new DecisionNode();
			isomorphism.put(this, copy);
			copy.condition = this.deepCopy(isomorphism).condition;
			copy.thenNode = this.deepCopy(isomorphism).getThenNode();
			copy.setElseNode(this.deepCopy(isomorphism).getElseNode());
		}
		return copy;
	}
	public ArrayList<CFGNode> adjacent() {
		ArrayList<CFGNode> adj = new ArrayList<>();
		adj.add(this.getElseNode());
		adj.add(thenNode);
		return adj;
	}
	public void printNode(){
		if (condition != null)
		System.out.println("with Condition " + condition);	
	
	}
				
}
