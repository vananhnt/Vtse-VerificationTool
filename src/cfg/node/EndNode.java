package cfg.node;

// node exit all of statements
@SuppressWarnings("serial")
public class EndNode extends CFGNode {
	public EndNode(){}
	
	public EndNode( CFGNode node){
		super(node);
	}
}
