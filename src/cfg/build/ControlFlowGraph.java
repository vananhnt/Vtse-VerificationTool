package cfg.build;
/*
 * nguyen thi thuy 97
 */

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTContinueStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNullStatement;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;
import cfg.node.EndingNode;
import cfg.node.ForBeginningNode;
import cfg.node.IfBeginningNode;
import cfg.node.IterationNode;
import cfg.node.PlainNode;

public class ControlFlowGraph {
	private CFGNode start;
	private CFGNode exit;

	
	public ControlFlowGraph(){		
	}
	
	public ControlFlowGraph(CFGNode start, CFGNode exit) {
		this.start = start;
		this.exit = exit;
	}		
	
	public void concat(ControlFlowGraph other) {
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
	public ControlFlowGraph build (IASTFunctionDefinition def) {
		return createSubGraph(def.getBody());
	}
	// build subGraph	
	private ControlFlowGraph createSubGraph(IASTStatement statement) {
		ControlFlowGraph cfg = new ControlFlowGraph();
		if (statement instanceof IASTCompoundStatement) {
			IASTCompoundStatement comp = (IASTCompoundStatement) statement;
			for (IASTStatement stmt : comp.getStatements()) {
				ControlFlowGraph subCFG = createSubGraph(stmt);
				if (subCFG != null) cfg.concat(subCFG);
			}
		} else if (statement instanceof IASTIfStatement) {
			cfg = createIf((IASTIfStatement) statement);
		} else if (statement instanceof IASTForStatement) {
			cfg = createFor((IASTForStatement) statement);
		} else if (statement instanceof IASTDoStatement) {
			cfg = creatDo((IASTDoStatement) statement);
		} else if (statement instanceof IASTWhileStatement) {
			cfg = createWhile((IASTWhileStatement) statement);
		} else if (statement instanceof IASTReturnStatement) {
			System.out.println("return");		
		} else {
			PlainNode plainNode = new PlainNode(statement);
			cfg = new ControlFlowGraph(plainNode, plainNode);
		}
		return cfg;
	}

	private ControlFlowGraph createWhile(IASTWhileStatement whileStatement) {
		ForBeginningNode beginWhileNode = new ForBeginningNode();
		DecisionNode decisionNode = new DecisionNode();
		decisionNode.setCondition(whileStatement.getCondition());
		beginWhileNode.setNext(decisionNode);	
		
		//then branch	
		ControlFlowGraph thenClause = createSubGraph(whileStatement.getBody());
		decisionNode.setThenNode(thenClause.getStart());
		IterationNode iterationNode = new IterationNode ();
		thenClause.getExit().setNext(iterationNode);
				iterationNode.setNext(decisionNode);
		//		khi in can xet truong hop iterationNode rieng

		//else branch
		decisionNode.setElseNode(new EmptyNode());
		EndConditionNode end = new EndConditionNode();
		decisionNode.getElseNode().setNext(end);
		beginWhileNode.setEndNode(end);
		return new ControlFlowGraph(beginWhileNode, end);
	}

	private ControlFlowGraph creatDo(IASTDoStatement doStatement) {
		ForBeginningNode beginDoNode = new ForBeginningNode();
		DecisionNode decisionNode = new DecisionNode();
		decisionNode.setCondition(doStatement.getCondition());
		beginDoNode.setNext(decisionNode);
		
		//then branch	
		ControlFlowGraph thenClause = createSubGraph(doStatement.getBody());
		decisionNode.setThenNode(thenClause.getStart());
		IterationNode iterationNode = new IterationNode ();
		thenClause.getExit().setNext(iterationNode);
				iterationNode.setNext(decisionNode);
		//		khi in can xet truong hop iterationNode rieng
		
		//else branch
		decisionNode.setElseNode(new EmptyNode());
		EndConditionNode end = new EndConditionNode();
		decisionNode.getElseNode().setNext(end);
		beginDoNode.setEndNode(end);
		return new ControlFlowGraph(beginDoNode, end);
	}

	private ControlFlowGraph createIf(IASTIfStatement ifStatement) {
		IfBeginningNode beginIfNode = new IfBeginningNode();
		DecisionNode decisionNode = new DecisionNode();
		EndConditionNode end = new EndConditionNode();
		decisionNode.setCondition(ifStatement.getConditionExpression());
		beginIfNode.setNext(decisionNode);
		
		//creates branches
		ControlFlowGraph thenClause = createSubGraph(ifStatement.getThenClause());
		ControlFlowGraph elseClause = createSubGraph(ifStatement.getElseClause());
		
		decisionNode.setThenNode(thenClause.getStart());
		decisionNode.setElseNode(elseClause.getStart());
		
		thenClause.getExit().setNext(end);
		elseClause.getExit().setNext(end);
		
		return new ControlFlowGraph(beginIfNode, end);
	}

	private ControlFlowGraph createFor(IASTForStatement forStatement) {
		ForBeginningNode bgForNode = new ForBeginningNode();
		PlainNode init = new PlainNode(forStatement.getInitializerStatement());
		bgForNode.setNext(init);
		
		DecisionNode decisionNode = new DecisionNode();
		decisionNode.setCondition(forStatement.getConditionExpression());
		init.setNext(decisionNode);
		
		//then branch
		ControlFlowGraph thenClause = createSubGraph(forStatement.getBody());
		decisionNode.setThenNode(thenClause.getStart());
		IterationNode iterationNode = new IterationNode (forStatement.getIterationExpression());
		thenClause.getExit().setNext(iterationNode);
				iterationNode.setNext(decisionNode);
		//		khi in can xet truong hop iterationNode rieng
		
		//else branch
		decisionNode.setElseNode(new EmptyNode());
		EndConditionNode end = new EndConditionNode();
		decisionNode.getElseNode().setNext(end);
		bgForNode.setEndNode(end);
		return new ControlFlowGraph(bgForNode, end);
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

	
	/*
	 * main: print the graph
	 */
	public static void print(CFGNode start) {
		CFGNode iter = start;
		if (iter == null) return;
		if (iter instanceof DecisionNode) {
			iter.printNode();
			print(((DecisionNode) iter).getThenNode());
			print(((DecisionNode) iter).getElseNode());
		} 
		else if (iter instanceof IterationNode) {
			iter.printNode();
			return;
		}
		else {
			iter.printNode();
			print(iter.getNext());
		}
		
	}
	public static void  main(String[] args) {
		IASTFunctionDefinition func = (new ASTGenerator("./bai1.cpp")).getFunction(0);
		ControlFlowGraph cfg = (new ControlFlowGraph()).build(func);
		CFGNode start = cfg.getStart();
		cfg.print(start);		
	}
}
