package cfg.node;
// node is 1 incommingNode and 1 outGoingNode;

import org.eclipse.cdt.core.dom.ast.IASTStatement;

import cfg.utils.FormulaCreater;
import cfg.utils.Index;
import cfg.utils.VariableManager;

public class PlainNode extends CFGNode {
	private IASTStatement statement;
	
	public PlainNode(){
		super();		
	}
	
	public PlainNode (IASTStatement st) {
		statement = st;
	}

	public IASTStatement getStatement() {
		return statement;
	}

	public void setStatement(IASTStatement statement) {
		this.statement = statement;
	}
	public void index(VariableManager vm) {
		Index.index(statement, vm);
	}
	public String getFormula() {
		return FormulaCreater.createFormula(statement);
		
	}
	public void printNode(){
		
		if (statement != null){
			System.out.print("PlainNode: ");
			System.out.println(statement.getRawSignature());
			
		}
		
	}
	
}
