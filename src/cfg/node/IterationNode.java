package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
/*
 * @va
 */
public class IterationNode extends CFGNode {
	private IASTExpression iterationExpression;
	
	public IterationNode() {}

	public IterationNode(IASTExpression iterExpression) {
		setIterationExpression(iterExpression);
	}
	public IASTExpression getIterationExpression() {
		return iterationExpression;
	}
	public void setIterationExpression(IASTExpression iterationExpression) {
		this.iterationExpression = iterationExpression;
	}
	public void printNode() {
		System.out.println(this.getClass());
		if (iterationExpression != null) {
			System.out.println("\t" + iterationExpression.getRawSignature());
	
		} else {
			System.out.println(iterationExpression);
		}
	}
}
