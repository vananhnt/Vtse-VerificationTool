package cfg.node;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTExpression;

import cfg.utils.ExpressionHelper;
import cfg.utils.FormulaCreater;
import cfg.utils.Index;
import cfg.utils.VariableManager;

public class DecisionNode extends CFGNode {
	private IASTExpression condition;
	private CFGNode thenNode;
	private CFGNode endNode;
	
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
			formula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, elseFormula);
		}
		else if (elseFormula == null) {
			formula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
		}
		else {
			thenFormula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
			elseFormula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, notConditionStr, elseFormula);
//			thenFormula = FormulaCreater.wrapPrefix(FormulaCreater.LOGIC_AND, conditionStr, thenFormula);
//			elseFormula = FormulaCreater.wrapPrefix(FormulaCreater.LOGIC_AND, notConditionStr, elseFormula);
			
			formula = FormulaCreater.wrapInfix(FormulaCreater.LOGIC_AND, thenFormula, elseFormula);
//			formula = FormulaCreater.wrapPrefix(FormulaCreater.LOGIC_OR, thenFormula, elseFormula);
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


	public CFGNode getEndNode() {
		return endNode;
	}

	public void index(VariableManager vm) {
		Index.index(condition, vm);
	}

	public void setEndNode(CFGNode endNode) {
		this.endNode = endNode;
	}
				
}
