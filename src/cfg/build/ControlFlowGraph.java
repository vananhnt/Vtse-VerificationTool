package cfg.build;
/*
 * nguyen thi thuy
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
	
	public ControlFlowGraph(CFGNode start, CFGNode exit) {
		this.start = start;
		this.exit = exit;
	}		
	
	// build big graph
	public ControlFlowGraph build( IASTFunctionDefinition def){
		// TODO
		
		return null;
	}	
	
	// build subGraph	
	public ControlFlowGraph createSubGraph(IASTStatement body) {
		if (body instanceof IASTCompoundStatement) {
			IASTCompoundStatement comp = (IASTCompoundStatement) body;
			for (IASTStatement statement : comp.getStatements()) {
				// where is connector 
				 
						createSubGraph(statement);
				
				
			}
		} else if (body instanceof IASTIfStatement) {
			return createIf((IASTIfStatement) body);
		} else if (body instanceof IASTForStatement) {
			return createFor((IASTForStatement) body);		
		} else if (body instanceof IASTReturnStatement) {
			System.out.println("return");
		}else if ( body instanceof IASTExpressionStatement ||  body instanceof IASTNullStatement || body instanceof IASTDeclarationStatement){
			PlainNode node = new PlainNode();
			node.setStatement(body);	
			return new ControlFlowGraph(node, node);
		}
		
		
		return null;
	}
/*
 * if statement is "then" create IfBeginNode
 * Input: prev, end and this statement's body
 * Output: CFGNode If with body is built;	
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
			//create body 
		
		
		return new ControlFlowGraph(forBegin, end);
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
	
	
	//public 
}
