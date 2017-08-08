package cfg.build;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import cfg.node.BeginNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EndConditionNode;
import cfg.node.FunctionCallNode;
import cfg.node.IterationNode;
import cfg.node.PlainNode;
import cfg.utils.FunctionHelper;
import cfg.utils.IASTVariable;
import cfg.utils.VariableHelper;


/**
 * @author va
 * chua xet th goi ham trog condition
 * (new MultiFunctionCFGBuilder(ast)).build(func);
 */

public class MultiFunctionCFGBuilder {
	private ASTGenerator ast;

	public MultiFunctionCFGBuilder() {}
	public MultiFunctionCFGBuilder(ASTGenerator ast) {
		this.ast = ast;

	}
	public ControlFlowGraph build(IASTFunctionDefinition func) {
		ControlFlowGraph prvCfg = getVtseCFG(func);
		CFGNode newStart = iterateNode(prvCfg.getStart(), prvCfg.exit, func);
		return prvCfg;	
	}
	
	private ArrayList<ControlFlowGraph> createList() {
		ArrayList<ControlFlowGraph> list = new ArrayList<>();
		ControlFlowGraph cfg;
		for (IASTFunctionDefinition func: ast.getListFunction()) {
			cfg = new ControlFlowGraph(func);
		
			list.add(cfg);
		}
		return list;
	}
	
	private ControlFlowGraph getVtseCFG(IASTFunctionDefinition func) {
		for (ControlFlowGraph cfg : createList()) {
			if (cfg.getNameFunction().equals(func.getDeclarator().getName().toString())) {
				return cfg;
			}
		}
		return null;
	} 

//	private CFGNode findEnd(CFGNode start) {
//		CFGNode node = start;
//		while (node.getNext() != null) {
//			node = node.getNext();
//		}
//		return node;
//	}
	
	
	/**
	 * @param node, end, func
	 * Ham duyet cfg va xu ly FunctionCallNode
	 */
	private CFGNode iterateNode(CFGNode node, CFGNode end, IASTFunctionDefinition func) {
		if ( node == null) {
			node = null;
		}  else if (node instanceof DecisionNode) {
			((DecisionNode) node).setThenNode(iterateNode(((DecisionNode) node).getThenNode(), end, func));
			((DecisionNode) node).setElseNode(iterateNode(((DecisionNode) node).getElseNode(), end, func));		
		} else if (node instanceof EndConditionNode) {	
			
		} else if (node instanceof BeginNode)  {
			node.setNext(iterateNode(node.getNext(), end, func));
			((BeginNode) node).getEndNode().setNext(iterateNode(((BeginNode) node).getEndNode().getNext(), end, func));
		} else if (node instanceof FunctionCallNode){
			ControlFlowGraph functionGraph = createFuncGraph(((FunctionCallNode) node).getFunctionCall(), func);
			
			if (functionGraph != null) {
				CFGNode pause = node.getNext();
				node = functionGraph.getStart();
				functionGraph.getExit().setNext(iterateNode(pause, end, func));
			}	
		} else {
			node.setNext(iterateNode(node.getNext(), end, func));	
		}	
		
		return node;
	}
	
	//dang test vs 2 ham fp va main
	private ControlFlowGraph createFuncGraph(IASTFunctionCallExpression callExpression, IASTFunctionDefinition currentFunc) {
		
		ControlFlowGraph cfg = new ControlFlowGraph();
		String funcName = callExpression.getFunctionNameExpression().toString();
		//System.out.println(funcName);
		IASTFunctionDefinition func = FunctionHelper.getFunction(ast.getListFunction(), funcName);
		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
		//Cho tham so = params	
			cfg.concat(createArguments(callExpression, currentFunc));
		
		//Noi voi than cua ham duoc goi
			cfg.concat(new ControlFlowGraph(func));
		
		//Tao ra node: ham duoc goi = return	
		IASTIdExpression left = (IASTIdExpression) VariableHelper.changeFunctionCallExpression(callExpression, func);
		
		IASTName nameRight = factory.newName(("return_" + funcName).toCharArray());
		IASTIdExpression right = factory.newIdExpression(nameRight);
		
		IASTBinaryExpression binaryExp = factory.newBinaryExpression(IASTBinaryExpression.op_assign, left, right);
		IASTExpressionStatement statement = factory.newExpressionStatement(binaryExp);
		CFGNode plainNode = new PlainNode(statement); //tao ra plainNode khong co ten ham dang sau
		cfg.concat(new ControlFlowGraph(plainNode, plainNode));
	
		
		return cfg;
	}
	/**
	 * @param callExpression
	 * @param currentFunc
	 * Tra ve cac Node xu ly tham so cua ham
	 */
	private ControlFlowGraph createArguments(IASTFunctionCallExpression callExpression, IASTFunctionDefinition currentFunc) {
		ControlFlowGraph cfg = new ControlFlowGraph();
		String funcName = callExpression.getFunctionNameExpression().toString();
		CFGNode plainNode;
		IASTFunctionDefinition func = FunctionHelper.getFunction(ast.getListFunction(), funcName);
		
		ArrayList<IASTVariable> params = FunctionHelper.getParameters(func);
		IASTInitializerClause[] arguments = callExpression.getArguments();
		IASTBinaryExpression expression;
		IASTExpressionStatement statement;
		IASTExpression right;
		IASTName leftName;
		IASTIdExpression left;
		String leftNameStr;
//		CFGNode declNode;
//		IASTDeclarationStatement declStatement;
//		IASTDeclarator declarator;
//		IASTSimpleDeclaration declaration;

		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
		
		for (int i = 0; i < arguments.length ; i++) {
			leftNameStr = params.get(i).getName().toString();
			leftNameStr += "_" + funcName;
			leftName = factory.newName(leftNameStr.toCharArray());
			left = factory.newIdExpression(leftName);
//			IASTDeclSpecifier type = params.get(i).getType().copy();
//			declarator = factory.newDeclarator(leftName);
//			declaration = factory.newSimpleDeclaration(type);
//			declaration.addDeclarator(declarator);
//			declStatement = factory.newDeclarationStatement(declaration);
//			declNode = new PlainNode(declStatement, func);
//			cfg.concat(new ControlFlowGraph(declNode, declNode));
			
//			IASTName rightName = factory.newName((arguments[i].getRawSignature().toCharArray()));
//			IASTIdExpression right = factory.newIdExpression(rightName);
			
			right = (IASTExpression) VariableHelper.changeVariableName((IASTExpression) arguments[i].copy(), currentFunc);
			expression = factory.newBinaryExpression(IASTBinaryExpression.op_assign, left, right);
			statement = factory.newExpressionStatement(expression);
			plainNode = new PlainNode(statement);
			cfg.concat(new ControlFlowGraph(plainNode, plainNode));
		}
		return cfg;
	}
	
	
	}
