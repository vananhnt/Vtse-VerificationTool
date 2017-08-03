package cfg.build;

import java.io.PrintStream;
import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;

import app.solver.SMTTypeConvertion;
import cfg.node.BeginNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EndConditionNode;
import cfg.node.PlainNode;
import cfg.node.SyncNode;
import cfg.utils.FormulaCreater;
import cfg.utils.Variable;
import cfg.utils.VariableManager;

public class VtseCFG extends ControlFlowGraph {
	private IASTFunctionDefinition func;
	private VariableManager vm;
	private String returnType;
	
	public VtseCFG() {
		vm = new VariableManager();
	}
	public VtseCFG(IASTFunctionDefinition func) {
		super(func);
		this.func = func;
		vm = new VariableManager(func);
		returnType = getReturnType();
	}
	
	
	private String getReturnType() {
		if (func != null) {
			IASTNode type = func.getDeclSpecifier();
			return type.toString();
		}
		return "unIdentify";
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
	public void printFormular(PrintStream ps) {
		ps.print(createFormular());
	}
	
	public String getNameFunction(){
		if (func == null) return null;		
		return this.func.getDeclarator().getName().toString();
	}
	
	public ArrayList<Variable> getInitVariables(){
		if (this.func == null) return null;
		ArrayList<Variable> varList = new ArrayList<>();
		//TODO this.func.get
		for (IASTNode iter : this.func.getDeclarator().getChildren()){
			if (iter instanceof IASTParameterDeclaration){
				String type = ((IASTParameterDeclaration) iter).getDeclSpecifier().toString();
				String name = ((IASTParameterDeclaration) iter).getDeclarator().getName().toString();
				Variable var = new Variable(type, name);
				varList.add(var);
			}
		}
		return varList;		
	}
	public String getTypeFunction(){
		if (func == null) return null;
		return this.func.getDeclSpecifier().toString();
	}
	public void index() {
		CFGNode node = start;
		while (node != null && node != exit) {
			node.index(vm);
			if (node instanceof DecisionNode) {
				node = ((DecisionNode) node).getEndNode();
			} else {
				node = node.getNext();
			}
		}
		if (node == exit){
			node.index(vm);
		}
	}	
	
	
	private void DFSHelper(CFGNode node) {
		node.setVistited(true);
		node.printNode();
		ArrayList<CFGNode> adj = node.adjacent();
		for (CFGNode iter : adj) {
			if (iter == null) return;
			if (!iter.isVistited()){
				DFSHelper(iter);
			} 
		}
	}
	
	public void printMeta() { //Da unfold
		printMeta(System.out, start, exit, " ");
	}
	
	
	public void printSMTFormula(PrintStream printStream) {
		
		int lastIndex;
		// (declare-fun a_0 () Int)
		for (Variable var: vm.getVariableList()) {
			lastIndex = var.getIndex();
			for (int i = 0; i <= lastIndex; i++) {
				printStream.println("(declare-fun " + var.getName() + "_" + i + 
										" () "+ SMTTypeConvertion.getSMTType(var.getType()) +")");
			}
		}
		
//		if (!returnType.equals("void")) {
//			printStream.println("(declare-fun return () " + 
//							SMTTypeConvertion.getSMTType(returnType)+ ")");
//		}
		
//		CFGNode node = start.getNext();
//		String f = null;
//		
//		while (node != exit && node != null) {
//			f = node.getFormula();
//			if (f != null) {
//				printStream.println("(assert " + f + ")");
//			}
//			if (node instanceof DecisionNode) {
//				node = ((DecisionNode) node).getEndNode();
//			} else {
//				node = node.getNext();
//			}
//		
//		}
		String f = FormulaCreater.create(start, exit); 
		if (f != null) {
			printStream.println("(assert " + f + ")");
		}
	
	}
	
	private static void printMeta(PrintStream printStream,CFGNode node, CFGNode end, String nSpaces) {
		if (node == null) {
			return;
		} else if (node == end) {
			if (node.toString() != "") {
				printStream.println(nSpaces + node.toString());	
			}	
		}
		else if (node instanceof PlainNode) {
			if (node.toString() != null) {
				printStream.println(nSpaces + node.toString());	
				//System.err.println("node: " + node);
				//System.err.println("constraint: " + node.toString());
			}		
			printMeta(printStream, node.getNext(), end, nSpaces);	// 4 spaces
		}
		else if (node instanceof SyncNode){
			printStream.println(nSpaces + node.toString());
			printMeta(printStream, node.getNext(), end, nSpaces);
		}
		else if (node instanceof BeginNode) {
			BeginNode begin = (BeginNode) node;
			printMeta(printStream, begin.getNext(), end, nSpaces);
			printMeta(printStream, begin.getEndNode().getNext(), end, nSpaces);	
		}
		else if (node instanceof DecisionNode) {
			DecisionNode cn = (DecisionNode) node;
			printStream.println(nSpaces + "if ( " + cn.toString() + " ) {");
			printMeta(printStream, cn.getThenNode(), cn.getEndNode() , nSpaces + "    ");	// 4 spaces
			printStream.println(nSpaces + "}");
			printStream.println(nSpaces + "else {");
			printMeta(printStream, cn.getElseNode(), cn.getEndNode(), nSpaces + "    ");	// 4 spaces
			printStream.println(nSpaces + "}");
			printMeta(printStream, cn.getEndNode(), end, nSpaces + "");	// 4 spaces
		} else if (node instanceof EndConditionNode) {
			return;
		}
		else {
			String constraint = node.toString();
			if (constraint != null && constraint != "") {
				printStream.println(nSpaces + constraint);	
			}
			printMeta(printStream , node.getNext(), end, nSpaces);
		}
	}
}
