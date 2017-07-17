package cfg.node;
// node is 1 incommingNode and 1 outGoingNode;

import java.io.Serializable;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTStatement;

public class PlainNode extends CFGNode implements Serializable {
	private String statement;
	
	public PlainNode(){
		super();		
	}
	
	public PlainNode (String st) {
		statement = st;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	public PlainNode deepCopy(Map<CFGNode, CFGNode> isomorphism) {
		PlainNode copy = (PlainNode) isomorphism.get(this);
		if (copy == null) {
			copy = new PlainNode();
			isomorphism.put(this, copy);
			copy.statement = this.deepCopy(isomorphism).statement;
		}
		return copy;
	}
	
	public void printNode(){
		
		if (statement != null){
			System.out.print("PlainNode: ");
			System.out.println(statement);
		}
		
	}
	
}
