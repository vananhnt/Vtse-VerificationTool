package cfg.node;

import java.io.Serializable;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
/*
 * @va
 */
public class IterationNode extends CFGNode implements Serializable {
	private String iterationExpression;
	
	public IterationNode() {
	}
	public IterationNode(IterationNode other) {
		super(other);
		this.iterationExpression = other.iterationExpression;
	} 
	
	public IterationNode(IASTExpression iterExpression) {
		iterationExpression = iterExpression.getRawSignature();
	}
	
	public String getIterationExpression() {
		return iterationExpression;
	}
	public void setIterationExpression(IASTExpression iterationExpression) {
		this.iterationExpression = iterationExpression.getRawSignature();
	}
	public IterationNode deepCopy(Map<CFGNode, CFGNode> isomorphism) {
		IterationNode copy = (IterationNode) isomorphism.get(this);
		if (copy == null) {
			copy = new IterationNode();
			isomorphism.put(this, copy);
			copy.setNext(this.deepCopy(isomorphism).getNext());
		}
		return copy;
	}
	public void printNode() {
		System.out.print("IterationNode: ");
		if (iterationExpression != null) {
			System.out.println(iterationExpression);
		} else {
			System.out.println();
		}
	}

}
