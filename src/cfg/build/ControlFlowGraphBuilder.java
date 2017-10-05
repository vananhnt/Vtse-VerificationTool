package cfg.build;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTBreakStatement;
import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTGotoStatement;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTLabelStatement;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import cfg.node.BeginForNode;
import cfg.node.BeginIfNode;
import cfg.node.BeginWhileNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;
import cfg.node.FunctionCallNode;
import cfg.node.GotoNode;
import cfg.node.IterationNode;
import cfg.node.LabelNode;
import cfg.node.PlainNode;
import cfg.node.ReturnNode;
import cfg.node.UndefinedNode;
import cfg.utils.FunctionHelper;
import cfg.utils.astnode.ASTNodeFactory;

/**
 * @author va
 + build(def) : ControlFlowGraph 
 //Them tham so func de them ten ham vao bien
 - createSubGraph(statement, /func) :ControlFlowGraph
 - createIf(ifStatement, /func) : ControlFlowGraph
 - createWhile(whileStatement, /func) : ControlFlowGraph
 - createDo(doStatement, /func) : ControlFlowGraph
 - createFor(forStatement, /func) : ControlFlowGraph
 - createSwitch(switchStatement, /func) : ControlFlowGraph
 - createDeclarationStatement(declStatement, /func) : ControlFlowGraph
 */

public class ControlFlowGraphBuilder {
	private ArrayList<GotoNode> gotoList = new ArrayList<>();
	
	public ControlFlowGraphBuilder() {}
	
	public ControlFlowGraph build(IASTFunctionDefinition def) {
//		return createSubGraph(def.getBody());
		return createSubGraph(def.getBody(), def);
	}

	/**
	 * @param statement
	 * @return cfg chua 2 not dau va cuoi
	 */
	ControlFlowGraph createSubGraph(IASTStatement statement) {
		ControlFlowGraph cfg = new ControlFlowGraph();
		ControlFlowGraph subCFG;
		if (statement instanceof IASTCompoundStatement) {
			IASTCompoundStatement comp = (IASTCompoundStatement) statement;
			//Xet truong hop compound rong
			IASTStatement[] stmts = comp.getStatements();
			if (stmts.length == 0) {
				EmptyNode empty = new EmptyNode();
				cfg = new ControlFlowGraph(empty, empty);
			} else {
				for (IASTStatement stmt : comp.getStatements()) {
					subCFG = createSubGraph(stmt);
					if (subCFG != null && cfg != null)
						cfg.concat(subCFG);
				}	
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
		} else if (statement instanceof IASTDeclarationStatement) {
			cfg = createDeclaration((IASTDeclarationStatement) statement);
		} else {
			PlainNode plainNode = new PlainNode(statement);
			cfg = new ControlFlowGraph(plainNode, plainNode);
		}
		return cfg;
	}
	
	ControlFlowGraph createSubGraph(IASTStatement statement, IASTFunctionDefinition def) {
		ControlFlowGraph cfg = new ControlFlowGraph();
		ControlFlowGraph subCFG;
		if (statement instanceof IASTCompoundStatement) {
			IASTCompoundStatement comp = (IASTCompoundStatement) statement;
			//Xet truong hop compound rong
			IASTStatement[] stmts = comp.getStatements();
			if (stmts.length == 0) {
				EmptyNode empty = new EmptyNode();
				cfg = new ControlFlowGraph(empty, empty);
			} else {
				for (IASTStatement stmt : comp.getStatements()) {
					subCFG = createSubGraph(stmt, def);
					if (subCFG != null && cfg != null)
						cfg.concat(subCFG);
				}	
			}
		} else if (statement instanceof IASTIfStatement) {
			cfg = createIf((IASTIfStatement) statement, def);
		} else if (statement instanceof IASTForStatement) {
			cfg = createFor((IASTForStatement) statement, def);
		} else if (statement instanceof IASTDoStatement) {
			cfg = creatDo((IASTDoStatement) statement, def);
		} else if (statement instanceof IASTWhileStatement) {
			cfg = createWhile((IASTWhileStatement) statement, def);
		} else if (statement instanceof IASTSwitchStatement) {
			cfg = createSwitch((IASTSwitchStatement) statement, def);
		} else if (statement instanceof IASTReturnStatement) {
			//Neu ham void -> khong co return statement -> khong can tao ReturnNode
			//Hien tai dang de la EmptyNode neu return statement la void
			if (!FunctionHelper.getFunctionType(def).equals("void")) {
				cfg = createReturnStatement((IASTReturnStatement) statement, def);
			} else {
				EmptyNode emptyNode = new EmptyNode();
				cfg = new ControlFlowGraph(emptyNode, emptyNode);
			}
		} else if (statement instanceof IASTDeclarationStatement) {
			cfg = createDeclaration((IASTDeclarationStatement) statement, def);
		} else if (statement instanceof IASTExpressionStatement) {
			cfg = createExpressionStatement((IASTExpressionStatement) statement, def);
		} else if (statement instanceof IASTGotoStatement) {
			GotoNode newNode = new GotoNode((IASTGotoStatement) statement);
			cfg = new ControlFlowGraph(newNode, newNode);
			gotoList.add(newNode);
		} else if (statement instanceof IASTLabelStatement) {
			cfg = createGotoGraph((IASTLabelStatement) statement, def);
		} else {
			UndefinedNode undefined = new UndefinedNode(statement);
			cfg = new ControlFlowGraph(undefined, undefined);
		}
		return cfg;
	}
	
	private ControlFlowGraph createGotoGraph(IASTLabelStatement statement, IASTFunctionDefinition def) {
		ControlFlowGraph cfg = null;
		LabelNode labelNode = new LabelNode(statement);
		for (GotoNode go : gotoList) {
			if (go.getLabelName().toString().equals(statement.getName().toString())) {
				go.setNext(labelNode);
				gotoList.remove(go);
				break;
			}
		}
		IASTStatement nested = statement.getNestedStatement();
		ControlFlowGraph sub = createSubGraph(nested, def);
		if (sub != null) {
			labelNode.setNext(sub.getStart());
			cfg = new ControlFlowGraph(labelNode, sub.getExit());
		} else {
			cfg = new ControlFlowGraph(labelNode, labelNode);
		}
		return cfg;
	}

	private ControlFlowGraph createReturnStatement(IASTReturnStatement statement, IASTFunctionDefinition def) {
		CFGNode returnNode;
		ControlFlowGraph cfg = null;
		//IASTNode[] nodes = statement.getChildren();
		IASTStatement newStatement;
		
		if (!hasCallExpression(statement)) {
			returnNode = new ReturnNode(statement, def);
			cfg = new ControlFlowGraph(returnNode, returnNode);
		} else {
			//Cac truong hop return; (Da xu ly o tren -> khong can thiet)
			if (statement.getReturnArgument() == null) {
				EmptyNode emptyNode = new EmptyNode();
				cfg = new ControlFlowGraph(emptyNode, emptyNode);
			}	
			else {
			//Cac truong hop co gia tri return	
				cfg = createFuncCallGraph(statement, def);
				returnNode = new ReturnNode(statement, def);
				cfg.concat(new ControlFlowGraph(returnNode, returnNode));
			}
		}
		return cfg;
		
	}

	/**
	 * Kiem tra co loi goi ham trong cau lenh? Loi goi ham la void hay co gia tri tra ve 
	 * @param statement
	 * @return
	 */
//	private boolean isVoidCall(IASTNode statement) {
//		boolean result = false;
//		IASTNode[] nodes = statement.getChildren();
//		for (IASTNode node : nodes) {
//			if (node instanceof IASTFunctionCallExpression) {
//				
//			} else {
//				result = isVoidCall(node);
//			}
//		}
//	return result;
//	}
	
	private boolean hasCallExpression(IASTNode statement) {
		boolean result = false;
		IASTNode[] nodes = statement.getChildren();
		for (IASTNode node : nodes) {
			if (node instanceof IASTFunctionCallExpression) {
				result = true;
				return result;
			} else {
				result = hasCallExpression(node);
			}
		}
	return result;
	}	
	
	/**
	 * Voi loi goi ham co gia tri tra ve -> chuyen loi goi ham thanh 1 bien 
	 * Voi loi goi ham void -> noi luon
	 * @param: expressionStatement, functionDef
	 */
	private ControlFlowGraph createExpressionStatement(IASTExpressionStatement statement, IASTFunctionDefinition def) {
		CFGNode plainNode;
		
		ControlFlowGraph cfg = null;
		//IASTNode[] nodes = statement.getChildren();
		IASTStatement newStatement;
		
		if (!hasCallExpression(statement)) {
			plainNode = new PlainNode(statement, def);
			cfg = new ControlFlowGraph(plainNode, plainNode);
		} else {
			//Tao ra node moi co chua loi goi ham la 1 bien
			cfg = createFuncCallGraph(statement, def);
			//Neu khong la ham void, tao ra node moi co
			
				plainNode = new PlainNode(statement, def);
				cfg.concat(new ControlFlowGraph(plainNode, plainNode));
				
		}
		return cfg;
	}
	
	/**
	 * @param node, def
	 * Tao ra subGraph chua loi goi ham
	 */
	private ControlFlowGraph createFuncCallGraph(IASTNode node, IASTFunctionDefinition def) {
		ControlFlowGraph cfg = new ControlFlowGraph();
		if (node instanceof IASTExpressionStatement) {
			cfg = createFuncCallGraph(((IASTExpressionStatement) node).getExpression(), def);
		} else if (node instanceof IASTDeclarationStatement) {
			cfg = createFuncCallDecl((IASTDeclarationStatement) node, def);
		} else if (node instanceof IASTReturnStatement) {
			cfg = createFuncCallGraph(((IASTReturnStatement) node).getReturnValue(), def);
		} else if (node instanceof IASTBinaryExpression) {	
			cfg = createFuncCallBinary((IASTBinaryExpression) node, def); 
		} else if (node instanceof IASTUnaryExpression) {
			cfg = createFuncCallGraph(((IASTUnaryExpression) node).getOperand(), def);
		} else if (node instanceof IASTFunctionCallExpression) {
			FunctionCallNode callNode = new FunctionCallNode();
			callNode.setFunctionCall((IASTFunctionCallExpression) node);
			cfg = new ControlFlowGraph(callNode, callNode);
		}
		return cfg;
	}
	
	private ControlFlowGraph createFuncCallDecl(IASTDeclarationStatement node, IASTFunctionDefinition def) {
		//Da xu ly Declaration -> khong can?
		return null;
	}

	private ControlFlowGraph createFuncCallBinary(IASTBinaryExpression node, IASTFunctionDefinition def) {
		ControlFlowGraph cfg_left = new ControlFlowGraph();
		ControlFlowGraph cfg_right = new ControlFlowGraph();
		
		cfg_left = createFuncCallGraph(node.getOperand1(), def);
		cfg_right = createFuncCallGraph(node.getOperand2(), def);
		if (cfg_left == null) {
			System.err.println("null cfg");
			return cfg_right;
		} else if (cfg_right == null) {
			System.err.println("null cfg");
			return cfg_left;
		} else {
			cfg_left.concat(cfg_right);
			return cfg_left;
		}
	}
	/**
	 * @param statement, func
	 * @return
	 * Tach ra lenh khoi tao thanh cac node noi nhau
	 * Vd: int sum = 0, a = b + c;
	 * => int sum; sum = 0; int a; a = b + c;
	 */
	private ControlFlowGraph createDeclaration(IASTDeclarationStatement statement, IASTFunctionDefinition func) {
		ControlFlowGraph cfg = new ControlFlowGraph();
		ControlFlowGraph declCfg = new ControlFlowGraph();
		IASTEqualsInitializer init;
		IASTName nameVar;
		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
		IASTSimpleDeclaration simpleDecl = (IASTSimpleDeclaration) statement.getDeclaration().copy();
		
		IASTDeclSpecifier type = simpleDecl.getDeclSpecifier().copy();
		IASTDeclarator[] declarators = simpleDecl.getDeclarators();
		IASTBinaryExpression binaryExpression;
		IASTExpression newExpression;
		
		IASTDeclarator newDeclarator;
		IASTDeclarationStatement newDeclStatement;
		IASTExpressionStatement newExprStatement;
		IASTInitializerClause newInit;
		IASTExpression rightInitClause;
		IASTIdExpression newId;
		IASTSimpleDeclaration newDeclaration = null;
		CFGNode node;
		
		for (IASTDeclarator decl : declarators) {
			nameVar = decl.getName().copy();
			init = (IASTEqualsInitializer) decl.getInitializer();
			/*
			newDeclarator = factory.newDeclarator(nameVar);
			newDeclaration = factory.newSimpleDeclaration(type);
			newDeclaration.addDeclarator(newDeclarator);
			newDeclStatement = factory.newDeclarationStatement(newDeclaration);
			*/
			newDeclStatement = ASTNodeFactory.createDeclarationStatement(nameVar, type);
			node = new PlainNode(newDeclStatement, func);
			cfg.concat(new ControlFlowGraph(node, node));
			//Neu nhu co dang: int b = 0; int b = f(x) + f(y);
			if (init != null) {
				newDeclarator = factory.newDeclarator(nameVar);
				rightInitClause = (IASTExpression) init.getChildren()[0].copy();
				newId = factory.newIdExpression(nameVar).copy();
				newExpression = factory.newBinaryExpression(IASTBinaryExpression.op_assign, newId, rightInitClause);
				newExprStatement = (IASTExpressionStatement) factory.newExpressionStatement(newExpression);
				
				cfg.concat(createSubGraph(newExprStatement, func));
//				node = new PlainNode(newExprStatement, func);
//				cfg.concat(new ControlFlowGraph(node, node));
//			
			} else { //dang: int b;
				//newDeclarator = factory.newDeclarator(nameVar);
				//newId = factory.newIdExpression(nameVar).copy();
				//rightInitClause = factory.newLiteralExpression(IASTLiteralExpression.lk_float_constant, "0");
				//newExpression = factory.newBinaryExpression(IASTBinaryExpression.op_assign, newId, rightInitClause);
				//newExprStatement = (IASTExpressionStatement) factory.newExpressionStatement(newExpression);
				//node = new PlainNode(newExprStatement, func);
				//cfg.concat(new ControlFlowGraph(node, node));
			}
			
		}
		return cfg;
	}
	
	private ControlFlowGraph createDeclaration(IASTDeclarationStatement statement) {
		ControlFlowGraph cfg = new ControlFlowGraph();
		ControlFlowGraph declCfg = new ControlFlowGraph();
		IASTEqualsInitializer init;
		IASTName nameVar;
		CPPNodeFactory factory = new CPPNodeFactory();
		IASTSimpleDeclaration simpleDecl = (IASTSimpleDeclaration) statement.getDeclaration().copy();
		
		IASTDeclSpecifier type = simpleDecl.getDeclSpecifier().copy();
		IASTDeclarator[] declarators = simpleDecl.getDeclarators();
		IASTBinaryExpression binaryExpression;
		IASTExpression newExpression;
		
		IASTDeclarator newDeclarator;
		IASTDeclarationStatement newDeclStatement;
		IASTExpressionStatement newExprStatement;
		IASTInitializerClause newInit;
		IASTExpression rightInitClause;
		IASTIdExpression newId;
		IASTSimpleDeclaration newDeclaration = null;
		CFGNode node;
		
		for (IASTDeclarator decl : declarators) {
			nameVar = decl.getName().copy();
			init = (IASTEqualsInitializer) decl.getInitializer();
			/*
			newDeclarator = factory.newDeclarator(nameVar);
			newDeclaration = factory.newSimpleDeclaration(type);
			newDeclaration.addDeclarator(newDeclarator);
			newDeclStatement = factory.newDeclarationStatement(newDeclaration);
			*/
			newDeclStatement = ASTNodeFactory.createDeclarationStatement(nameVar, type);
			node = new PlainNode(newDeclStatement);
			cfg.concat(new ControlFlowGraph(node, node));
			//Neu nhu co dang: int b = 0; int b = f(x) + f(y);
			if (init != null) {
				newDeclarator = factory.newDeclarator(nameVar);
				rightInitClause = (IASTExpression) init.getChildren()[0].copy();
				newId = factory.newIdExpression(nameVar).copy();
				newExpression = factory.newBinaryExpression(IASTBinaryExpression.op_assign, newId, rightInitClause);
				newExprStatement = (IASTExpressionStatement) factory.newExpressionStatement(newExpression);
				
				cfg.concat(createSubGraph(newExprStatement));
//				node = new PlainNode(newExprStatement, func);
//				cfg.concat(new ControlFlowGraph(node, node));
//			
			} else { //dang: int b;
				//newDeclarator = factory.newDeclarator(nameVar);
				//newId = factory.newIdExpression(nameVar).copy();
				//rightInitClause = factory.newLiteralExpression(IASTLiteralExpression.lk_float_constant, "0");
				//newExpression = factory.newBinaryExpression(IASTBinaryExpression.op_assign, newId, rightInitClause);
				//newExprStatement = (IASTExpressionStatement) factory.newExpressionStatement(newExpression);
				//node = new PlainNode(newExprStatement, func);
				//cfg.concat(new ControlFlowGraph(node, node));
			}
			
		}
		return cfg;
	}

	/**
	 * @param whileStatement
	 * @return node đầu và cuối của khối lệnh while
	 */
	@Deprecated
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
		// TODO change
		decisionNode.setEndOfThen(iterationNode);
		// khi in can xet truong hop iterationNode rieng

		// else branch
		decisionNode.setElseNode(new EmptyNode());
		decisionNode.getElseNode().setNext(end);
		decisionNode.setEndNode(end);
		beginWhileNode.setEndNode(end);
		// TODO change
		decisionNode.setEndOfElse(decisionNode.getElseNode());

		return new ControlFlowGraph(beginWhileNode, end);
	}

	private ControlFlowGraph createWhile(IASTWhileStatement whileStatement, IASTFunctionDefinition func) {
		BeginWhileNode beginWhileNode = new BeginWhileNode();
		DecisionNode decisionNode = new DecisionNode();
		EndConditionNode end = new EndConditionNode();
		IterationNode iterationNode = new IterationNode();
		// iterationNode.setIterationExpression(whileStatement.getCondition());

		ControlFlowGraph thenClause = createSubGraph(whileStatement.getBody(), func);
		// decisionNode.setNext(thenClause.getExit());
		decisionNode.setCondition(whileStatement.getCondition().copy(), func);
		beginWhileNode.setNext(decisionNode);

		// then branch
		decisionNode.setThenNode(thenClause.getStart());
		thenClause.getExit().setNext(iterationNode);
		// TODO change
		decisionNode.setEndOfThen(iterationNode);
		// khi in can xet truong hop iterationNode rieng

		// else branch
		decisionNode.setElseNode(new EmptyNode());
		decisionNode.getElseNode().setNext(end);
		decisionNode.setEndNode(end);
		beginWhileNode.setEndNode(end);
		// TODO change
		decisionNode.setEndOfElse(decisionNode.getElseNode());

		return new ControlFlowGraph(beginWhileNode, end);
	}

	/**
	 * @param doStatement;
	 * @return node dau va cuoi cua Ham Do - While
	 */
	@Deprecated
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
		// TODO change
		decisionNode.setEndOfThen(iterationNode);
		// else branch
		decisionNode.setElseNode(new EmptyNode());
		decisionNode.getElseNode().setNext(end);
		decisionNode.setEndNode(end);
		beginDoNode.setEndNode(end);
		// TODO change
		decisionNode.setEndOfElse(decisionNode.getElseNode());
		return new ControlFlowGraph(beginDoNode, end);
	}

	private ControlFlowGraph creatDo(IASTDoStatement doStatement, IASTFunctionDefinition func) {
		BeginWhileNode beginDoNode = new BeginWhileNode();
		DecisionNode decisionNode = new DecisionNode();

		EndConditionNode end = new EndConditionNode();
		IterationNode iterationNode = new IterationNode();
		ControlFlowGraph thenClause = createSubGraph(doStatement.getBody(), func);

		decisionNode.setCondition(doStatement.getCondition().copy(), func);
		beginDoNode.setNext(decisionNode);

		// then branch
		decisionNode.setThenNode(thenClause.getStart());
		thenClause.getExit().setNext(iterationNode);
		// iterationNode.setNext(decisionNode);
		// khi in can xet truong hop iterationNode rieng
		// TODO change
		decisionNode.setEndOfThen(iterationNode);
		// else branch
		decisionNode.setElseNode(new EmptyNode());
		decisionNode.getElseNode().setNext(end);
		decisionNode.setEndNode(end);
		beginDoNode.setEndNode(end);
		// TODO change
		decisionNode.setEndOfElse(decisionNode.getElseNode());
		return new ControlFlowGraph(beginDoNode, end);
	}

	/**
	 * @param ifStatement
	 * @return node đầu cuối của khối lệnh If
	 */
	@Deprecated
	private ControlFlowGraph createIf(IASTIfStatement ifStatement) {
		BeginIfNode beginIfNode = new BeginIfNode();
		DecisionNode decisionNode = new DecisionNode();
		EndConditionNode endNode = new EndConditionNode();
		ControlFlowGraph elseClause;
		ControlFlowGraph thenClause;
		CFGNode elseNode = null;
		// creates branches
		thenClause = createSubGraph(ifStatement.getThenClause());
		if (ifStatement.getElseClause() != null) {
			elseClause = createSubGraph(ifStatement.getElseClause());
		} else {
			elseNode = new EmptyNode();
			elseClause = new ControlFlowGraph(elseNode, elseNode);
		}

		decisionNode.setCondition(ifStatement.getConditionExpression().copy());
		beginIfNode.setNext(decisionNode);

		decisionNode.setThenNode(thenClause.getStart());
		decisionNode.setElseNode(elseClause.getStart());
		decisionNode.setEndNode(endNode);

		thenClause.getExit().setNext(endNode);
		elseClause.getExit().setNext(endNode);
		beginIfNode.setEndNode(endNode);
		// TODO change
		decisionNode.setEndOfThen(thenClause.getExit());
		decisionNode.setEndOfElse(elseClause.getExit());
		return new ControlFlowGraph(beginIfNode, endNode);
	}

	private ControlFlowGraph createIf(IASTIfStatement ifStatement, IASTFunctionDefinition func) {
		BeginIfNode beginIfNode = new BeginIfNode();
		DecisionNode decisionNode = new DecisionNode();
		EndConditionNode endNode = new EndConditionNode();
		ControlFlowGraph elseClause;
		ControlFlowGraph thenClause;
		CFGNode elseNode = null;
		// creates branches
		thenClause = createSubGraph(ifStatement.getThenClause(), func);
		if (ifStatement.getElseClause() != null) {
			elseClause = createSubGraph(ifStatement.getElseClause(), func);
		} else {
			elseNode = new EmptyNode();
			elseClause = new ControlFlowGraph(elseNode, elseNode);
		}
		
		decisionNode.setCondition(ifStatement.getConditionExpression().copy(), func);
		beginIfNode.setNext(decisionNode);

		decisionNode.setThenNode(thenClause.getStart());
		decisionNode.setElseNode(elseClause.getStart());
		decisionNode.setEndNode(endNode);
		if (thenClause != null) {
			thenClause.getExit().setNext(endNode);	
		}
		if (elseClause != null) {
			elseClause.getExit().setNext(endNode);
			}
		
		beginIfNode.setEndNode(endNode);
		// TODO change
		decisionNode.setEndOfThen(thenClause.getExit());
		decisionNode.setEndOfElse(elseClause.getExit());
		return new ControlFlowGraph(beginIfNode, endNode);
	}

	/**
	 * @param forStatement
	 * @return node đầu (ForBeginningNode )và cuối (EndConditionNode) của khối
	 *         lệnh For Chưa xử lý lệnh break, continue
	 */
	@Deprecated
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
		// TODO change
		decisionNode.setEndOfThen(iterationNode);
		// else branch
		decisionNode.setElseNode(new EmptyNode());
		decisionNode.getElseNode().setNext(endNode);
		decisionNode.setEndNode(endNode);
		bgForNode.setEndNode(endNode);
		// TODO change
		decisionNode.setEndOfElse(decisionNode.getElseNode());
		return new ControlFlowGraph(bgForNode, endNode);
	}

	private ControlFlowGraph createFor(IASTForStatement forStatement, IASTFunctionDefinition func) {
		BeginForNode bgForNode = new BeginForNode();
		EndConditionNode endNode = new EndConditionNode();
		DecisionNode decisionNode = new DecisionNode();
		IterationNode iterationNode = new IterationNode(forStatement.getIterationExpression(), func);
		PlainNode init = new PlainNode(forStatement.getInitializerStatement().copy(), func);
		ControlFlowGraph thenClause = createSubGraph(forStatement.getBody(), func);

		bgForNode.setNext(init);
		decisionNode.setCondition(forStatement.getConditionExpression(), func);
		init.setNext(decisionNode);

		// then branch
		decisionNode.setThenNode(thenClause.getStart());
		thenClause.getExit().setNext(iterationNode);
		// iterationNode.setNext(decisionNode);
		// khi in can xet truong hop iterationNode rieng
		// TODO change
		decisionNode.setEndOfThen(iterationNode);
		// else branch
		decisionNode.setElseNode(new EmptyNode());
		decisionNode.getElseNode().setNext(endNode);
		decisionNode.setEndNode(endNode);
		bgForNode.setEndNode(endNode);
		// TODO change
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

	private void linkCfg(ControlFlowGraph subCfg, IASTStatement[] statements, int start, int end,
			IASTFunctionDefinition func) {
		for (int j = start + 1; j < end; j++) {
			if (!(statements[j] instanceof IASTBreakStatement)) {
				subCfg.concat(createSubGraph(statements[j], func));
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
	@Deprecated
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

	private ControlFlowGraph createSwitch(IASTSwitchStatement switchStatement, IASTFunctionDefinition func) {
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
					ControlFlowGraph subCfg = createSubGraph(statements[i + 1], func);
					linkCfg(subCfg, statements, i + 1, statements.length);
					subCfg.getExit().setNext(lastNode);
					lastNode = subCfg.getStart();

				} else if (statements[i] instanceof IASTCaseStatement) {
					// Xu ly khoi case
					condition = new DecisionNode();
					condition.setCondition(createCondition(switchStatement, (IASTCaseStatement) statements[i]).copy(),
							func);

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

}
