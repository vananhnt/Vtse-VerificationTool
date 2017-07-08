package cfg.node;
// node is 1 incommingNode and 1 outGoingNode;

import org.eclipse.cdt.core.dom.ast.IASTStatement;

public class PlainNode extends CFGNode{
	private IASTStatement statement;
	
	public PlainNode(){
		super();		
	}
	
	public PlainNode(CFGNode prev, CFGNode next){
		super(prev, next);
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
	
	public void printNode(){
		
		if (statement != null){
			System.out.println(this.getClass());
			System.out.println( "       ^.^ " + statement.getRawSignature());
		}
		
	}
	
//	public String toString() {
//		
//		return this.getClass() + " -> " + statement.getRawSignature();
//	}
}
