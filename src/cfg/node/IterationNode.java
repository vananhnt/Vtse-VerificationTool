package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
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

	public void printNode() {
		System.out.print("IterationNode: ");
		if (iterationExpression != null) {
			System.out.println(iterationExpression.getRawSignature());
		} else {
			System.out.println();
		}
	}

}
