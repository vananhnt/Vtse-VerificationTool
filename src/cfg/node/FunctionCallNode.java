package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;

import cfg.utils.ExpressionHelper;
import cfg.utils.FormulaCreater;

public class FunctionCallNode extends CFGNode {
	private IASTFunctionCallExpression funcCall;
	
	public FunctionCallNode() {}
	
	public IASTFunctionCallExpression getFunctionCall() {
		return funcCall;
	}
	
	public void setFunctionCall(IASTFunctionCallExpression funcCall) {
		this.funcCall = funcCall;
	}
	
	public void printNode() {
		System.out.println("FuncCallNode: " + ExpressionHelper.toString(funcCall));
//		System.out.println("  - " + ExpressionHelper.toString(funcCall.getFunctionNameExpression()));
//		System.out.println("  - " + funcCall.getExpressionType().toString());
//		for (IASTInitializerClause node: funcCall.getArguments()) {
//			System.out.println("  - " + node.getRawSignature());
//		}
	}
	
	public String getFormula() {
		return FormulaCreater.createFormula(funcCall);
	}
}
