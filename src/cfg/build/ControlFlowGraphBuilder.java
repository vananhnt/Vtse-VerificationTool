package cfg.build;

import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

import cfg.node.BeginWhileNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;
import cfg.node.ForBeginningNode;
import cfg.node.IfBeginningNode;
import cfg.node.IterationNode;
import cfg.node.PlainNode;

/**
 * @author va
 *
 */

public class ControlFlowGraphBuilder {
	
	/**
	 * @param def: một hàm
	 * @return một cfg chứa 2 node đầu và cuối
	 */
	public ControlFlowGraph build (IASTFunctionDefinition def) {
		return createSubGraph(def.getBody());
	}	
	
	
	/**
	 * @param statement
	 * @return cfg chứa 2 node đầu và cuối 
	 * Fixed: them kiem tra (subCFG != null)
	 */
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
		} else if (statement instanceof IASTSwitchStatement) {
			cfg = createSwitch((IASTSwitchStatement) statement);
		} else if (statement instanceof IASTCaseStatement) {
			cfg = createCase((IASTCaseStatement) statement);
		} else if (statement instanceof IASTReturnStatement) {
			System.out.println("return");		
		} else {
			PlainNode plainNode = new PlainNode(statement);
			cfg = new ControlFlowGraph(plainNode, plainNode);
		}
		return cfg;
	}
	
	
	

	/**
	 * @param whileStatement
	 * @return node đầu và cuối của khối lệnh while 
	 */
	private ControlFlowGraph createWhile(IASTWhileStatement whileStatement) {
		BeginWhileNode beginWhileNode = new BeginWhileNode();
		DecisionNode decisionNode = new DecisionNode();
		EndConditionNode end = new EndConditionNode();
		IterationNode iterationNode = new IterationNode ();
		ControlFlowGraph thenClause = createSubGraph(whileStatement.getBody());
		
		decisionNode.setCondition(whileStatement.getCondition());
		beginWhileNode.setNext(decisionNode);	
		
		//then branch	
		decisionNode.setThenNode(thenClause.getStart());
		thenClause.getExit().setNext(iterationNode);
		iterationNode.setNext(decisionNode);
		//khi in can xet truong hop iterationNode rieng

		//else branch
		decisionNode.setElseNode(new EmptyNode());
		decisionNode.getElseNode().setNext(end);
		
		return new ControlFlowGraph(beginWhileNode, end);
	}

	
	/**
	 * @param doStatement;
	 * @return node đầu và cuối của khối lệnh Do - While 
	 */
	private ControlFlowGraph creatDo(IASTDoStatement doStatement) {
		BeginWhileNode beginDoNode = new BeginWhileNode();
		DecisionNode decisionNode = new DecisionNode();
		EndConditionNode end = new EndConditionNode();
		IterationNode iterationNode = new IterationNode ();
		ControlFlowGraph thenClause = createSubGraph(doStatement.getBody());
		
		decisionNode.setCondition(doStatement.getCondition());
		beginDoNode.setNext(decisionNode);
		
		//then branch	
		decisionNode.setThenNode(thenClause.getStart());
		thenClause.getExit().setNext(iterationNode);
		iterationNode.setNext(decisionNode);
		//khi in can xet truong hop iterationNode rieng
		
		//else branch
		decisionNode.setElseNode(new EmptyNode());
		decisionNode.getElseNode().setNext(end);
		
		return new ControlFlowGraph(beginDoNode, end);
	}

	
	/**
	 * @param ifStatement
	 * @return node đầu cuối của khối lệnh If 
	 */
	private ControlFlowGraph createIf(IASTIfStatement ifStatement) {
		IfBeginningNode beginIfNode = new IfBeginningNode();
		DecisionNode decisionNode = new DecisionNode();
		EndConditionNode endNode = new EndConditionNode();
		
		//creates branches
		ControlFlowGraph thenClause = createSubGraph(ifStatement.getThenClause());
		ControlFlowGraph elseClause = createSubGraph(ifStatement.getElseClause());
		
		decisionNode.setCondition(ifStatement.getConditionExpression());
		beginIfNode.setNext(decisionNode);
		
		decisionNode.setThenNode(thenClause.getStart());
		decisionNode.setElseNode(elseClause.getStart());
		//System.out.println("1"); thenClause.getExit().printNode();
		thenClause.getExit().setNext(endNode);
		elseClause.getExit().setNext(endNode);
		
		return new ControlFlowGraph(beginIfNode, endNode);
	}

	
	/**
	 * @param forStatement
	 * @return node đầu (ForBeginningNode )và cuối (EndConditionNode) 
	 * của khối lệnh For 
	 * Chưa xử lý lệnh break, continue 
	 */
	private ControlFlowGraph createFor(IASTForStatement forStatement) {
		ForBeginningNode bgForNode = new ForBeginningNode();
		EndConditionNode endNode = new EndConditionNode();
		DecisionNode decisionNode = new DecisionNode();
		IterationNode iterationNode = new IterationNode (forStatement.getIterationExpression());
		PlainNode init = new PlainNode(forStatement.getInitializerStatement());
		ControlFlowGraph thenClause = createSubGraph(forStatement.getBody());
		
		bgForNode.setNext(init);	
		decisionNode.setCondition(forStatement.getConditionExpression());
		init.setNext(decisionNode);
		
		//then branch
		decisionNode.setThenNode(thenClause.getStart());
		thenClause.getExit().setNext(iterationNode);
		iterationNode.setNext(decisionNode);
		//khi in can xet truong hop iterationNode rieng
		
		//else branch
		decisionNode.setElseNode(new EmptyNode());
		decisionNode.getElseNode().setNext(endNode);
		
		return new ControlFlowGraph(bgForNode, endNode);
	}
	
	private ControlFlowGraph createCase(IASTCaseStatement statement) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param switchStatement
	 * @return
	 */
	private ControlFlowGraph createSwitch(IASTSwitchStatement switchStatement) {
		IfBeginningNode beginSwitchNode = new IfBeginningNode();
		EndConditionNode endNode = new EndConditionNode();
		CFGNode lastNode = endNode; //dung de noi cua cfg
		
		ControlFlowGraph caseStatements = createSubGraph(switchStatement.getBody());
		if (switchStatement.getBody() instanceof IASTCompoundStatement) {
			IASTStatement[] statements = ((IASTCompoundStatement) switchStatement.getBody()).getStatements();
			for (int i = statements.length - 1; i >= 0; i--) {
				if (statements[i] instanceof IASTDefaultStatement) {
					ControlFlowGraph subCfg = createSubGraph(statements[i]);
					subCfg.getExit().setNext(lastNode);
					lastNode = subCfg.getStart();
				} else {
					ControlFlowGraph thenClause = createSubGraph(statements[i]);
					thenClause.getExit().setNext(endNode);	
					//chua setCondition
					if (thenClause != null) {
						DecisionNode condition = new DecisionNode();
					
						condition.setElseNode(lastNode);
						condition.setThenNode(thenClause.getStart());
						lastNode = condition;
						beginSwitchNode.setNext(condition);
					}
				}
				
			}
		}
		return new ControlFlowGraph(beginSwitchNode, endNode);
	}
	
	/**
	 * @param start: node đầu của cfg
	 *	In ra ControlFlowGraph 
	 */
	public static void print(CFGNode start) {
		CFGNode iter = start;
		if (iter == null) {
			return;
		}
		if (iter instanceof DecisionNode) {
			if (((DecisionNode) iter).getCondition() == null) {
				System.out.println("decision is not set yet");
			} else {
				iter.printNode();
				print(((DecisionNode) iter).getThenNode());
				print(((DecisionNode) iter).getElseNode());
	
			}
		} 
		else if (iter instanceof IterationNode) {
			return;
		}
		else {
				iter.printNode();
				print(iter.getNext());	
			
		}
		
	}
	
	
	/**
	 * @param args
	 * In cfg
	 */
	public static void  main(String[] args) {
		IASTFunctionDefinition func = (new ASTGenerator("./test.c")).getFunction(0);
		ControlFlowGraph cfg = (new ControlFlowGraphBuilder()).build(func);
		print(cfg.getStart());
		
	}
}
