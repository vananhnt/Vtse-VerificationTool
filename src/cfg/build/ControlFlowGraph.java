package cfg.build;
/*
 * nguyen thi thuy 97
 */

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTContinueStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNullStatement;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

import cfg.nodes.CFGNode;
import cfg.nodes.DecisionNode;
import cfg.nodes.EndingNode;
import cfg.nodes.ForBeginningNode;
import cfg.nodes.IfBeginningNode;
import cfg.nodes.PlainNode;

public class ControlFlowGraph {
	private CFGNode start;
	private CFGNode exit;

	
	public ControlFlowGraph(){		
	}
	public ControlFlowGraph build (IASTFunctionDefinition def) {
		return createSubGraph(def.getBody());
	}
	public ControlFlowGraph(CFGNode start, CFGNode exit) {
		this.start = start;
		this.exit = exit;
	}		
	
	private void concat(ControlFlowGraph other) {
		if (start == null) {
			start = other.start;
			exit = other.exit;
		}
		else {
			exit.setNext(other.start);
			exit = other.exit;
		}
	}
	
	// build big graph
	
	// build subGraph	
	public ControlFlowGraph createSubGraph(IASTStatement statement) {
		ControlFlowGraph cfg = new ControlFlowGraph();
		if (statement instanceof IASTCompoundStatement) {
			IASTCompoundStatement comp = (IASTCompoundStatement) statement;
			for (IASTStatement stmt : comp.getStatements()) {
				ControlFlowGraph subCFG = createSubGraph(stmt);
				cfg.concat(subCFG);
			}
		} else if (statement instanceof IASTIfStatement) {
			 cfg = createIf((IASTIfStatement) statement);
		} else if (statement instanceof IASTForStatement) {
			cfg =  createFor((IASTForStatement) statement);		
		} else if (statement instanceof IASTReturnStatement) {
			System.out.println("return");
		} else {
			PlainNode node = new PlainNode();
			node.setStatement(statement);	
			cfg = new ControlFlowGraph(node, node);
		}
		return cfg;
	}
/*
 * if statement is "then" create IfBeginNode
 * Input: prev, end and this statement's statement
 * Output: CFGNode If with statement is built;	
 */
	public ControlFlowGraph createIf( IASTIfStatement ifStatement){	
		//TODO Testing
		//create beginNode and EndNode
		IfBeginningNode ifBegin = new IfBeginningNode();		
		EndingNode end = new EndingNode();
		// create decision 
		DecisionNode dec = new DecisionNode();
		dec.setPrev(ifBegin);
		dec.setCondition(ifStatement.getConditionExpression());
			// create branches then/else
		ControlFlowGraph thenClause = createSubGraph( ifStatement.getThenClause());
		if ( thenClause != null){ 		
			thenClause.setExit(end);
			dec.setThenNode(thenClause.getStart());
		}
		ControlFlowGraph elseClause = createSubGraph(ifStatement.getElseClause());
		if ( thenClause != null){
			elseClause.setExit(end);
			dec.setElseNode(elseClause.getStart());
		}
		//connect
		ifBegin.setNext( dec);
		
		return new ControlFlowGraph(ifBegin, end);
	}
	
	private ControlFlowGraph createFor( IASTForStatement forStatement){
		//TODO	
		
		// create begin and end Nodes
		ForBeginningNode forBegin = new ForBeginningNode();
		EndingNode end = new EndingNode();
		// create initialize
		//forBegin.setInit( forStatement.getInitializerStatement());
		//create Decision
		DecisionNode dec = new DecisionNode();
		dec.setPrev(forBegin);
		dec.setCondition( forStatement.getConditionExpression());
			//create statement 
		
		
		return new ControlFlowGraph(forBegin, end);
	}

	private CFGNode createWhile( CFGNode prev, IASTWhileStatement statement){
		//TODO
		return null;
	}

	public CFGNode getStart() {
		return start;
	}


	public void setStart(CFGNode start) {
		this.start = start;
	}


	public CFGNode getExit() {
		return exit;
	}


	public void setExit(CFGNode exit) {
		this.exit = exit;
	}


//	public Boolean getHasLoop() {
//		return hasLoop;
//	}
//
//
//	public void setHasLoop(Boolean hasLoop) {
//		this.hasLoop = hasLoop;
//	}
	
	
	public static void  main(String[] args) {
		IASTFunctionDefinition func = (new ASTGenerator("./bai1.cpp")).getFunction(0);
		ControlFlowGraph cfg = (new ControlFlowGraph()).build(func);
		CFGNode start = cfg.getStart();
		if (start == null) {
			System.out.println("null roi");
		}
		else {
			CFGNode next = start;
			while(next != cfg.getExit()) {
				System.out.println(next);
				next = next.getNext();
			}
			
			System.out.println(next);
		}
		
	}
}
