package cfg.nodes;
// node is 1 incommingNode and 1 outGoingNode;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionStatement;

public class PlainNode extends CFGNode{
	
	
	public PlainNode(){
		super();		
	}
	
	public PlainNode(CFGNode prev, CFGNode next){
		super(prev, next);
	}	
}
