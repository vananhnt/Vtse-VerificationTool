package cfg.node;

import java.io.Serializable;

// node exit all of statements
public class EndNode extends CFGNode implements Serializable{
	public EndNode(){}
	
	public EndNode( CFGNode node){
		super(node);
	}
}
