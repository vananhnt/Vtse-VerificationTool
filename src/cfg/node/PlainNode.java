package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTStatement;

import cfg.utils.ExpressionHelper;
import cfg.utils.FormulaCreater;
import cfg.utils.Index;
import cfg.utils.Variable;
import cfg.utils.VariableHelper;
import cfg.utils.VariableManager;

public class PlainNode extends CFGNode {
	private IASTStatement statement;
	private IASTFunctionDefinition func;
	
	public PlainNode(){
		super();		
	}
	
	public PlainNode (IASTStatement statement) {
		this.statement = statement;
	}
	public PlainNode (IASTStatement statement, IASTFunctionDefinition func) {
		this.statement = changeName(statement, func);
		
		this.func = func;
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
	public IASTStatement changeName(IASTStatement statement, IASTFunctionDefinition func) {
	
		return (IASTStatement) VariableHelper.changeName(statement, func);
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
