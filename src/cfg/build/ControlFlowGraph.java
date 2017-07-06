package cfg.build;

import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

import cfg.nodes.CFGNode;
import cfg.nodes.EndNode;

public class ControlFlowGraph {
	private CFGNode start;
	private EndNode exit;
	private Boolean hasLoop;
	
	public ControlFlowGraph(){
		this.start.setPrev(null);
		this.exit.setNext(null);
		this.hasLoop = false;
	}
	
	// build big graph
	public ControlFlowGraph build( IASTFunctionDefinition def){
		// TODO
		
		return null;
	}	
	
	// build subGraph
	public CFGNode createSubGraph( CFGNode prev, IASTStatement body){
		// TODO
		return null;
	}
	
	private CFGNode createIfNode( CFGNode prev, IASTIfStatement body){
		//TODO
		return null;
	}
	
	private CFGNode createForNode( CFGNode prev, IASTForStatement body){
		//TODO 
		return null;
	}

	private CFGNode createWhileNode( CFGNode prev, IASTWhileStatement body){
		//TODO
		return null;
	}

	public CFGNode getStart() {
		return start;
	}


	public void setStart(CFGNode start) {
		this.start = start;
	}


	public EndNode getExit() {
		return exit;
	}


	public void setExit(EndNode exit) {
		this.exit = exit;
	}


	public Boolean getHasLoop() {
		return hasLoop;
	}


	public void setHasLoop(Boolean hasLoop) {
		this.hasLoop = hasLoop;
	}
	
	
	//public 
}
