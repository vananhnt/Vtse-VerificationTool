package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTStatement;

import cfg.utils.ExpressionHelper;
import cfg.utils.FormulaCreater;
import cfg.utils.Index;
import cfg.utils.VariableManager;

public class PlainNode extends CFGNode {
	private IASTStatement statement;
	
	public PlainNode(){
		super();		
	}
	
	public PlainNode (IASTStatement statement) {
		this.statement = statement;
	}

	public IASTStatement getStatement() {
		return statement;
	}

	public void setStatement(IASTStatement statement) {
		this.statement = statement;
	}

	public void index(VariableManager vm) {
		statement = (IASTStatement) Index.index(statement, vm);
	}
	public String getFormula() {
		return FormulaCreater.createFormula(statement);
	}

	public String toString() {
		return ExpressionHelper.toString(statement);
	}
	public void printNode(){	
		if (statement != null){
			System.out.print("PlainNode: ");
			System.out.println(ExpressionHelper.toString(statement));
		}
		
	}
	
}
