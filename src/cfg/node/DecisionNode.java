package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionStatement;
import org.eclipse.cdt.internal.core.util.ToStringSorter;

public class DecisionNode extends CFGNode{
	private IASTExpression condition;
	
	private CFGNode thenNode;
	private CFGNode elseNode;
	// elseNode is not next
	
	public DecisionNode(){}
	
	public DecisionNode(CFGNode prev){
		this.setPrev(prev);	
	}	
	
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
	
	public void setThenNode(CFGNode other) {
		thenNode = other;
	}
	
// set ELSE NODE with Input is CFGNode  or  IASTStatement	
	public void setElseNode(CFGNode other){
		elseNode = other;
	}
	
	public CFGNode getElseNode(){
		return elseNode;
	}
	
	public void printNode(){
		System.out.println("with Condition: " + condition.getRawSignature());	
		
	}
				
}
