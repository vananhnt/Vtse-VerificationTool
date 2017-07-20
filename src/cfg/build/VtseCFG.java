package cfg.build;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import cfg.node.CFGNode;
import cfg.node.PlainNode;
import cfg.utils.FormulaCreater;
import cfg.utils.VariableManager;

public class VtseCFG extends ControlFlowGraph {
	private IASTFunctionDefinition func;
	private VariableManager vm;
	
	public VtseCFG() {
		vm = new VariableManager();
	}
	public VtseCFG(IASTFunctionDefinition func) {
		super(func);
		vm = new VariableManager();
		vm = vm.build(func);
	}
	public VariableManager getVm() {
		return vm;
	}

	public void setVm(VariableManager vm) {
		this.vm = vm;
	}

	public IASTFunctionDefinition getFunc() {
		return func;
	}

	public void setFunc(IASTFunctionDefinition func) {
		this.func = func;
	}
	public void DFS() {
		DFSHelper(super.start);	
	}
	
	public String createFormular() {
		return FormulaCreater.create(start, exit); 
	}
	private void DFSHelper(CFGNode node) {
		node.setVistited(true);
		if (node instanceof PlainNode) {
			System.out.println(((PlainNode) node).getFormula());
		}
		
		ArrayList<CFGNode> adj = node.adjacent();
		for (CFGNode iter : adj) {
			if (iter == null) return;
			if (!iter.isVistited()){
				DFSHelper(iter);
			} 
		}
	}
}
