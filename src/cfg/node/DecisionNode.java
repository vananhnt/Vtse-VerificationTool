package cfg.node;
// build endOfThen  endOofElse =.=

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTExpression;

import cfg.utils.Cloner;
import cfg.utils.ExpressionHelper;
import cfg.utils.FormulaCreater;
import cfg.utils.Index;
import cfg.utils.Variable;
import cfg.utils.VariableManager;

public class DecisionNode extends CFGNode {
	private IASTExpression condition;
	private CFGNode thenNode;
	private CFGNode endNode;
	
	private CFGNode endOfThen;
	private CFGNode endOfElse;
	private VariableManager thenVM;
	private VariableManager elseVM;
	// elseNode is next
	
	public DecisionNode(){}

	
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
	
	public String getFormula() {
		String conditionStr = FormulaCreater.createFormula(condition);
		String notConditionStr = FormulaCreater.createFormula(FormulaCreater.NEGATIVE, conditionStr);
		
		String thenFormula = FormulaCreater.create(thenNode, endNode);
		String elseFormula = FormulaCreater.create(next, endNode);
		
		String formula = "";
		
		if (thenFormula == null && elseFormula == null) {
			return null;
		}	
		else if (thenFormula == null) {
			formula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, elseFormula);
		}
		else if (elseFormula == null) {
			formula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
		}
		else {
//			thenFormula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
//			elseFormula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, notConditionStr, elseFormula);
			thenFormula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
			elseFormula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, notConditionStr, elseFormula);
			
//			formula = FormulaCreater.wrapInfix(FormulaCreater.LOGIC_AND, thenFormula, elseFormula);
			formula = FormulaCreater.wrapPrefix(FormulaCreater.LOGIC_AND, thenFormula, elseFormula);
		}
		return formula;
		
	}

	public ArrayList<CFGNode> adjacent() {
		ArrayList<CFGNode> adj = new ArrayList<>();
		adj.add(thenNode);
		adj.add(this.getElseNode());
		return adj;
	}

	public void printNode(){
		if (condition != null)
		System.out.println("with Condition " + ExpressionHelper.toString(condition));	
	}

	public String toString() {
		return ExpressionHelper.toString(condition);
	}
	
	public CFGNode getEndNode() {
		return endNode;
	}
	
	public void setEndNode(CFGNode endNode) {
		this.endNode = endNode;
	}			

	public void index(VariableManager vm) {
		condition = (IASTExpression) Index.index(condition, vm);
		endNode.printNode();
		
		// then clause
		thenVM = Cloner.clone(vm);
		CFGNode run = this.getThenNode();
		while ( (run != null) && (run != this.endNode)){
			run.index(thenVM);
			if (run instanceof DecisionNode){
				run = ((DecisionNode) run).getEndNode();
			} else{
				run = run.getNext();
			}
		}
		
		// else clause
		elseVM = Cloner.clone(vm);
		run = this.getElseNode();
		while ( (run != null) && (run != this.endNode)){
			run.index(elseVM);
			if (run instanceof DecisionNode){
				run = ((DecisionNode) run).getEndNode();
			} else{
				run = run.getNext();
			}
		}

		// sync
		vm.setVariableList(sync().getVariableList());
		
	}
	
	private VariableManager sync(){
		int size = thenVM.getSize();
		Variable thenVar;
		Variable elseVar;
		String leftHand;
		String rightHand;
		SyncNode syncNode;
		
		for (int i = 0; i < size; i++) {
			thenVar = thenVM.getVariable(i);
			elseVar = elseVM.getVariable(i);	
			
			if (thenVar.getIndex() < elseVar.getIndex()) {
				rightHand = thenVar.getVariableWithIndex();
				thenVar.setIndex(elseVar.getIndex());
				leftHand = thenVar.getVariableWithIndex();				
				syncNode = new SyncNode(leftHand, rightHand);				
				
				this.endOfThen.setNext(syncNode);
				syncNode.setNext(endNode);
				setEndOfThen(syncNode);				
			}
			else if (elseVar.getIndex() < thenVar.getIndex()) {
				rightHand = elseVar.getVariableWithIndex();
				elseVar.setIndex(thenVar.getIndex());
				leftHand = elseVar.getVariableWithIndex();
				syncNode = new SyncNode(leftHand, rightHand);
				
				this.endOfElse.setNext(syncNode);
				syncNode.setNext(endNode);
				setEndOfElse(syncNode);
			}
		}
		
		// set VM
		return elseVM;
	}

	public CFGNode getEndOfElse() {
		return endOfElse;
	}

	public void setEndOfElse(CFGNode endOfElse) {
		this.endOfElse = endOfElse;
	}

	public CFGNode getEndOfThen() {
		return endOfThen;
	}

	public void setEndOfThen(CFGNode endOfThen) {
		this.endOfThen = endOfThen;
	}
	
	public static void main(String[] args) {
		
	}
}
