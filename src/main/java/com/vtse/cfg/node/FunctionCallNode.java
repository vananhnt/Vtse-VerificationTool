package com.vtse.cfg.node;

import com.vtse.cfg.index.FormulaCreater;
import com.vtse.cfg.utils.ExpressionHelper;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;

public class FunctionCallNode extends CFGNode {
    private IASTFunctionCallExpression funcCall;

    public FunctionCallNode() {
    }

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
