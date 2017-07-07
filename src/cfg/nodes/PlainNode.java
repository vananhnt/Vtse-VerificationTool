package cfg.nodes;
// node is 1 incommingNode and 1 outGoingNode;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionStatement;

public class PlainNode extends CFGNode{
	private CPPASTExpressionStatement expression;
	
	public PlainNode(){
		super();
		this.setExpression(null);
	}
	
	public PlainNode(CFGNode prev, CFGNode next){
		super(prev, next);
	}
	
	public PlainNode(CFGNode prev, CFGNode next, CPPASTExpressionStatement exp){
		super(prev, next);
		this.setExpression(exp);
	}

	public CPPASTExpressionStatement getExpression() {
		return expression;
	}

	public void setExpression(CPPASTExpressionStatement expression) {
		this.expression = expression;
	}
	
	
	public String toString(){		
		return this.expression.getRawSignature();
	}
}
