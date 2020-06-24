package com.vtse.cfg.node;

import com.vtse.cfg.utils.FunctionHelper;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

public class EndFunctionNode extends CFGNode {
    IASTFunctionDefinition func;

    public EndFunctionNode() {
    }

    public EndFunctionNode(CFGNode node) {
        super(node);
    }

    public EndFunctionNode(IASTFunctionDefinition func) {
        this.func = func;
    }

    public IASTFunctionDefinition getFunction() {
        return func;
    }

    public void printNode() {
        System.out.println("}  <--" + FunctionHelper.getFunctionName(func));
    }

    public IASTFunctionDefinition getFunc() {
        return func;
    }

    public void setFunc(IASTFunctionDefinition func) {
        this.func = func;
    }
}
