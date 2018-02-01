package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import cfg.utils.FunctionHelper;

public class EndFunctionNode extends CFGNode {
	IASTFunctionDefinition func;
	public EndFunctionNode(){
	}
	public EndFunctionNode(CFGNode node){
		super(node);
	}
	public EndFunctionNode(IASTFunctionDefinition func) {
		this.func = func;
	}
	public IASTFunctionDefinition getFunction() {
		return func;
	}
	public void printNode() {
		System.out.println("EndFunctionNode: " + FunctionHelper.getFunctionName(func));
	}
}
