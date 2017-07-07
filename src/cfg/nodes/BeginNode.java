package cfg.nodes;

import java.util.Collection;

import org.eclipse.cdt.core.dom.ast.IASTNode;

/* node begin the statements if/ for/ while....
 * contain  1 decision 1 loop 1 break and common emplements;
 * 
 */
public class BeginNode extends CFGNode{
	private PlainNode initialization;
	private DecisionNode decision;
	
	public BeginNode(){
		super();
		this.initialization = null;
		this.decision = null;
	}
	
// get/ set
	public PlainNode getInitialization() {
		return initialization;
	}
	public void setInitialization(PlainNode initialization) {
		this.initialization = initialization;
	}
	public DecisionNode getDecision() {
		return decision;
	}
	public void setDecision(DecisionNode decision) {
		this.decision = decision;
	}
	
	
	
}
