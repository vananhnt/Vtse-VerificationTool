package cfg.build;

import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

import cfg.nodes.CFGNode;
import cfg.nodes.DecisionNode;
import cfg.nodes.EndNode;
import cfg.nodes.IfBeginNode;

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
	private CFGNode createSubGraph(IASTStatement body){
		// TODO
		return null;
	}
/*
 * if statement is "then" create IfBeginNode
 * Input: prev, end and this statement's body
 * Output: CFGNode If with body is built;	
 */
	private CFGNode createIf( IASTIfStatement body){	
		//TODO
		//create beginNode and EndNode
		IfBeginNode ifNode = new IfBeginNode();	
		EndNode end = new EndNode();
		// create decision 
		DecisionNode dec = new DecisionNode();
		dec.setPrev(ifNode);
		dec.setCondition(body.getConditionExpression());
			// create branches then/else
		CFGNode then = createSubGraph( body.getThenClause());
		CFGNode els = createSubGraph(body.getThenClause());
		dec.setThenNode(then);
		dec.setElseNode(els);
		
		
		//connect
		ifNode.setNext( dec);
		
		return null;
	}
	
	private CFGNode createFor( CFGNode prev, IASTForStatement body){
		//TODO 
		return null;
	}

	private CFGNode createWhile( CFGNode prev, IASTWhileStatement body){
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
