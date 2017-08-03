package cfg.build;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTBreakStatement;
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
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import cfg.node.BeginForNode;
import cfg.node.BeginIfNode;
import cfg.node.BeginWhileNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;
import cfg.node.IterationNode;
import cfg.node.PlainNode;
import cfg.node.ReturnNode;

/**
 * @author va
 */

public class ControlFlowGraphBuilder {
	
	/** @param def:
	 *  một hàm
	 * @return một cfg chứa 2 node đầu và cuối
	 */
	
	public ControlFlowGraphBuilder(){}
	
	public ControlFlowGraph build(IASTFunctionDefinition def) {
		return createSubGraph(def.getBody());
	}

	/**
	 * @param statement
	 * @return cfg chứa 2 node đầu và cuối Fixed: them kiem tra (subCFG != null)
	 */
	private ControlFlowGraph createSubGraph(IASTStatement statement) {
		ControlFlowGraph cfg = new ControlFlowGraph();
		if (statement instanceof IASTCompoundStatement) {
			IASTCompoundStatement comp = (IASTCompoundStatement) statement;
			for (IASTStatement stmt : comp.getStatements()) {
				ControlFlowGraph subCFG = createSubGraph(stmt);
				if (subCFG != null)
					cfg.concat(subCFG);
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
		} else if (statement instanceof IASTReturnStatement) {
			ReturnNode returnNode = new ReturnNode(statement);
			cfg = new ControlFlowGraph(returnNode, returnNode);	
		} 
		else  {
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
		IterationNode iterationNode = new IterationNode();
		// iterationNode.setIterationExpression(whileStatement.getCondition());

		ControlFlowGraph thenClause = createSubGraph(whileStatement.getBody());
		// decisionNode.setNext(thenClause.getExit());
		decisionNode.setCondition(whileStatement.getCondition().copy());
		beginWhileNode.setNext(decisionNode);

		// then branch
		decisionNode.setThenNode(thenClause.getStart());
		thenClause.getExit().setNext(iterationNode);
			//TODO change
		decisionNode.setEndOfThen(iterationNode);
		// khi in can xet truong hop iterationNode rieng

		// else branch
		decisionNode.setElseNode(new EmptyNode());
		decisionNode.getElseNode().setNext(end);		
		decisionNode.setEndNode(end);
		beginWhileNode.setEndNode(end);
			//TODO change
		decisionNode.setEndOfElse(decisionNode.getElseNode());
		
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
		IterationNode iterationNode = new IterationNode();
		ControlFlowGraph thenClause = createSubGraph(doStatement.getBody());

		decisionNode.setCondition(doStatement.getCondition().copy());
		beginDoNode.setNext(decisionNode);

		// then branch
		decisionNode.setThenNode(thenClause.getStart());
		thenClause.getExit().setNext(iterationNode);
		// iterationNode.setNext(decisionNode);
		// khi in can xet truong hop iterationNode rieng
			//TODO change
		decisionNode.setEndOfThen(iterationNode);
		// else branch
		decisionNode.setElseNode(new EmptyNode());
		decisionNode.getElseNode().setNext(end);
		decisionNode.setEndNode(end);
		beginDoNode.setEndNode(end);
			//TODO change
		decisionNode.setEndOfElse(decisionNode.getElseNode());
		return new ControlFlowGraph(beginDoNode, end);
	}

	/**
	 * @param ifStatement
	 * @return node đầu cuối của khối lệnh If
	 */
	private ControlFlowGraph createIf(IASTIfStatement ifStatement) {
		BeginIfNode beginIfNode = new BeginIfNode();
		DecisionNode decisionNode = new DecisionNode();
		EndConditionNode endNode = new EndConditionNode();

		// creates branches
		ControlFlowGraph thenClause = createSubGraph(ifStatement.getThenClause());
		ControlFlowGraph elseClause = createSubGraph(ifStatement.getElseClause());

		decisionNode.setCondition(ifStatement.getConditionExpression().copy());
		beginIfNode.setNext(decisionNode);

		decisionNode.setThenNode(thenClause.getStart());
		decisionNode.setElseNode(elseClause.getStart());
		decisionNode.setEndNode(endNode);
		
		thenClause.getExit().setNext(endNode);
		elseClause.getExit().setNext(endNode);
		beginIfNode.setEndNode(endNode);
			//TODO change
		decisionNode.setEndOfThen(thenClause.getExit());
		decisionNode.setEndOfElse(elseClause.getExit());
		return new ControlFlowGraph(beginIfNode, endNode);
	}

	/**
	 * @param forStatement
	 * @return node đầu (ForBeginningNode )và cuối (EndConditionNode) của khối
	 *         lệnh For Chưa xử lý lệnh break, continue
	 */
	private ControlFlowGraph createFor(IASTForStatement forStatement) {
		BeginForNode bgForNode = new BeginForNode();
		EndConditionNode endNode = new EndConditionNode();
		DecisionNode decisionNode = new DecisionNode();
		IterationNode iterationNode = new IterationNode(forStatement.getIterationExpression());
		PlainNode init = new PlainNode(forStatement.getInitializerStatement().copy());
		ControlFlowGraph thenClause = createSubGraph(forStatement.getBody());

		bgForNode.setNext(init);
		decisionNode.setCondition(forStatement.getConditionExpression());
		init.setNext(decisionNode);

		// then branch
		decisionNode.setThenNode(thenClause.getStart());
		thenClause.getExit().setNext(iterationNode);
		// iterationNode.setNext(decisionNode);
		// khi in can xet truong hop iterationNode rieng
			//TODO change
		decisionNode.setEndOfThen(iterationNode);
		// else branch
		decisionNode.setElseNode(new EmptyNode());
		decisionNode.getElseNode().setNext(endNode);
		decisionNode.setEndNode(endNode);
		bgForNode.setEndNode(endNode);
			//TODO change
		decisionNode.setEndOfElse(decisionNode.getElseNode());
		return new ControlFlowGraph(bgForNode, endNode);
	}

	/**
	 * @param subCfg
	 * @param statements
	 * @param start
	 * @param end
	 */
	private void linkCfg(ControlFlowGraph subCfg, IASTStatement[] statements, int start, int end) {
		for (int j = start + 1; j < end; j++) {
			if (!(statements[j] instanceof IASTBreakStatement)) {
				subCfg.concat(createSubGraph(statements[j]));
			}
		}
	}

	/**
	 * @param switchSt
	 * @param caseSt
	 * @return
	 */

	private IASTExpression createCondition(IASTSwitchStatement switchSt, IASTCaseStatement caseSt) {
		CPPNodeFactory nodeFactory = (CPPNodeFactory) switchSt.getTranslationUnit().getASTNodeFactory();
		IASTExpression operand1 = switchSt.getControllerExpression().copy();
		IASTExpression operand2 = caseSt.getExpression().copy();
		IASTExpression condition = ((org.eclipse.cdt.core.dom.ast.INodeFactory) nodeFactory)
				.newBinaryExpression(IASTBinaryExpression.op_equals, operand1, operand2);
		return condition;
	}

	/**
	 * @param switchStatement
	 * @return
	 */
	private ControlFlowGraph createSwitch(IASTSwitchStatement switchStatement) {
		BeginIfNode beginSwitchNode = new BeginIfNode();
		EndConditionNode endNode = new EndConditionNode();
		CFGNode lastNode = endNode; // dung de noi cfg
		DecisionNode condition;
		ControlFlowGraph thenClause;
		int endCase = 0;

		if (switchStatement.getBody() instanceof IASTCompoundStatement) {
			IASTStatement[] statements = ((IASTCompoundStatement) switchStatement.getBody()).getStatements();
			for (int i = statements.length - 1; i >= 0; i--) {
				if (statements[i] instanceof IASTDefaultStatement) {
					// Xu ly khoi default
					ControlFlowGraph subCfg = createSubGraph(statements[i + 1]);
					linkCfg(subCfg, statements, i + 1, statements.length);
					subCfg.getExit().setNext(lastNode);
					lastNode = subCfg.getStart();

				} else if (statements[i] instanceof IASTCaseStatement) {
					// Xu ly khoi case
					condition = new DecisionNode();
					condition.setCondition(createCondition(switchStatement, (IASTCaseStatement) statements[i]).copy());

					thenClause = new ControlFlowGraph();
					linkCfg(thenClause, statements, i, endCase);
					thenClause.getExit().setNext(endNode);

					condition.setElseNode(lastNode);
					condition.setThenNode(thenClause.getStart());
					condition.setEndNode(endNode);
					lastNode = condition;
					beginSwitchNode.setNext(condition);
				
				} else if (statements[i] instanceof IASTBreakStatement) {
					endCase = i;
				}
			}
		}
		beginSwitchNode.setEndNode(endNode);
		return new ControlFlowGraph(beginSwitchNode, endNode);
	}
//	private ControlFlowGraph createDeclaration(IASTDeclarationStatement statement) {
//	CPPNodeFactory factory = (CPPNodeFactory) statement.getTranslationUnit().getASTNodeFactory();
//	IASTSimpleDeclSpecifier typeVar = null;
//	IASTDeclaration declaration = statement.getDeclaration();
//	IASTNode[] nodes = declaration.getChildren();
//	ControlFlowGraph cfg = new ControlFlowGraph();
//	IASTDeclarator newDeclarator; 
//	IASTDeclarationStatement newDeclStatement = null;
//	IASTDeclaration newDeclaration;
//	
//	for (IASTNode node : nodes) {
//		if (node instanceof IASTSimpleDeclSpecifier) {
//			typeVar = ((IASTSimpleDeclSpecifier) node).copy();
//		}
//		if (node instanceof IASTDeclarator) {
//			newDeclarator = ((IASTDeclarator) node).copy();
//			newDeclaration = factory.newSimpleDeclaration(typeVar);
//			
//			newDeclStatement = factory.newDeclarationStatement(newDeclaration);
//			System.err.println(ExpressionHelper.toString(newDeclStatement));
//			
//		}
//	}
//	return null;
//}
	
}
