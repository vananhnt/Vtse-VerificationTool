package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTExpression;

import cfg.utils.ExpressionHelper;
import cfg.utils.Index;
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
	
	public IASTExpression getIterationExpression() {
		return iterationExpression;
	}
	public void setIterationExpression(IASTExpression iterationExpression) {
		this.iterationExpression = iterationExpression;
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
