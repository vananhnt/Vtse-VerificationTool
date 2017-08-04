package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import cfg.utils.ExpressionHelper;
import cfg.utils.Index;
import cfg.utils.VariableHelper;
import cfg.utils.VariableManager;
/*
 * @va
 */


public class IterationNode extends CFGNode {
	private IASTExpression iterationExpression;
	
	public IterationNode() {
	}
	public IterationNode(IterationNode other) {
		super(other);
		this.iterationExpression = other.iterationExpression;
	} 
	
	public IterationNode(IASTExpression iterExpression) {
		iterationExpression = iterExpression;
	}
	
	public IterationNode(IASTExpression iterExpression, IASTFunctionDefinition func) {
		iterationExpression = (IASTExpression) VariableHelper.changeName(iterationExpression, func);
	} 
	
	public IASTExpression getIterationExpression() {
		return iterationExpression;
	}
	public void setIterationExpression(IASTExpression iterationExpression) {
		this.iterationExpression = iterationExpression;
	}
	
	public void setIterationExpression(IASTExpression iterationExpression, IASTFunctionDefinition func) {
		this.iterationExpression = (IASTExpression) VariableHelper.changeName(iterationExpression, func);
	}
	
	public void index( VariableManager vm){
		this.iterationExpression = (IASTExpression) Index.index(iterationExpression, vm);
		//System.out.println( "--" + iterationExpression.);
	}
	public String toString() {
		return ExpressionHelper.toString(iterationExpression);
	}
	public void printNode() {
		System.out.print("IterationNode: ");
		if (iterationExpression != null) {
			System.out.println(ExpressionHelper.toString(iterationExpression));
		} else {
			System.out.println();
		}
	}

}
