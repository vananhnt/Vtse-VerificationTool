package cfg.build;

import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

import cfg.nodes.CFGNode;
import cfg.nodes.DecisionNode;
import cfg.nodes.EndNode;
import cfg.nodes.ForBeginNode;
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
		//TODO Testing
		//create beginNode and EndNode
		IfBeginNode ifBegin = new IfBeginNode();	
		EndNode end = new EndNode();
		// create decision 
		DecisionNode dec = new DecisionNode();
		dec.setPrev(ifBegin);
		dec.setCondition(body.getConditionExpression());
			// create branches then/else
		CFGNode then = createSubGraph( body.getThenClause());
		then.setNext(end);
		CFGNode els = createSubGraph(body.getThenClause());
		els.setNext(end);
		dec.setThenNode(then);
		dec.setElseNode(els);		
		//connect
		ifBegin.setNext( dec);
		
		return new CFGNode(ifBegin, end);
	}
	
	private CFGNode createFor( CFGNode prev, IASTForStatement body){
		//TODO 
		// create begin and end Nodes
		ForBeginNode forBegin = new ForBeginNode();
		EndNode end = new EndNode();
		
		
		return new CFGNode(forBegin, end);
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
