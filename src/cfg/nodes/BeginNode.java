package cfg.nodes;

import org.eclipse.cdt.core.dom.ast.IASTStatement;

/* node begin the statements if/ for/ while....
 * contain  1 decision 1 loop 1 break and common emplements;
 * init == initialize
 */
public class BeginNode extends CFGNode{
	private PlainNode init;	
	private DecisionNode decision;
	
	public BeginNode(){
		super();
		this.init= null;
		this.decision = null;
	}
	
// get/ set
	public PlainNode getInitialization() {
		return init;
	}
	public void setInitialization(PlainNode initialization) {
		this.init = initialization;
	}
	public void setInit( IASTStatement state){
		init.setData(state);
	}
	public DecisionNode getDecision() {
		return decision;
	}
	public void setDecision(DecisionNode decision) {
		this.decision = decision;
	}
	
	
	
}
