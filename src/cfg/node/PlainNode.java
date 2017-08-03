package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTStatement;

import cfg.utils.ExpressionHelper;
import cfg.utils.FormulaCreater;
import cfg.utils.Index;
import cfg.utils.VariableManager;

public class PlainNode extends CFGNode {
	protected IASTStatement statement;
	protected boolean isFunctionCall;
	
	public PlainNode(){
		super();		
	}
	
	public PlainNode (IASTStatement statement) {
		this.statement = statement;
		this.isFunctionCall = hasCallExpression(statement);
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
	
	private boolean hasCallExpression(IASTNode statement) {
		boolean result = false;
		IASTNode[] nodes = statement.getChildren();
		for (IASTNode node : nodes) {
			if (node instanceof IASTFunctionCallExpression) {
				result = true;
				return result;
			} else {
				result = hasCallExpression(node);
			}
		}
	return result;
	}
	
	public String toString() {
		return ExpressionHelper.toString(statement);
	}
	public void printNode(){	
		if (statement != null){
			if (isFunctionCall) {
				System.out.print("<isFunctionCall> ");
			}
			System.out.print("PlainNode: ");
			System.out.println(ExpressionHelper.toString(statement));
		}
		
	}
	
}
