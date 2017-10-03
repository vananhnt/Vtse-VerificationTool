package cfg.utils;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;


/**
 * @author va
 * Them ten ham vao bien + Chuan hoa cau lenh
 * static changeName(node, func) : IASTNode
 * Note: muon thay doi IASTNode trong CDT phai tao node moi
 */

public class ExpressionModifier {

	public static IASTNode changeVariableName(IASTNode node, IASTFunctionDefinition func) {
		if (node instanceof IASTBinaryExpression) {
			node = changeBinaryExpression((IASTBinaryExpression) node, func);
		}
		else if (node instanceof IASTIdExpression) {
			node = changeIdExpression((IASTIdExpression)node, func);
		} 
		else if (node instanceof IASTExpressionStatement) {
			node = changeExpressionStatement((IASTExpressionStatement) node, func);
		}
		else if (node instanceof IASTUnaryExpression) {
			node = changeUnaryExpression((IASTUnaryExpression) node, func);
		}
		else if (node instanceof IASTDeclarationStatement){
			node = changeDeclarationStatement((IASTDeclarationStatement) node, func);
		}
		else if (node instanceof IASTReturnStatement){
			node = changeReturnStatement((IASTReturnStatement) node, func);
		}
		else if (node instanceof IASTFunctionCallExpression) {
			node = changeFunctionCallExpression ((IASTFunctionCallExpression) node, func);
		}
		
		return node;
	}
/**
 * @param node
 * @param func
 * @return
 */
	public static IASTNode changeFunctionCallExpression(IASTFunctionCallExpression node, IASTFunctionDefinition func) {
		IASTIdExpression newId;
		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
//		IASTNode[] children = node.getChildren();
//		String currentName = ((IASTIdExpression) children[0]).getName().toString();
//		String params = "";
//		if (children.length > 0) {
//			for (int i = 1; i < children.length; i++) {
//				params += "_" + ExpressionHelper.toString(children[i]);
//			}
//		}
		String currentName = node.getFunctionNameExpression().toString();
		String params = "";
		
		for (IASTNode param : node.getArguments()) {
			params += "_" + param.toString();
		}
	
		IASTName newName = factory.newName((currentName + params).toCharArray());
		newId = factory.newIdExpression(newName);
		
		return newId;
	}

	/**
	 * @param node, func
	 * sua expression statement
	 */
	private static IASTNode changeExpressionStatement(IASTExpressionStatement node, IASTFunctionDefinition func) {
		IASTExpression expression = (IASTExpression) changeVariableName(node.getExpression().copy(), func);
		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
		IASTStatement newStatement = factory.newExpressionStatement(expression);
		//System.err.println("Test: " + ExpressionHelper.toString(newStatement));
		return newStatement;
	}

	/**
	 * @param node, func
	 * sua cau lenh return
	 */
	private static IASTNode changeReturnStatement(IASTReturnStatement node, IASTFunctionDefinition func) {
		// Da chuyen het return => binary o PlainNode
		return null;
	}

	/**
	 * @param node, func
	 * sua cau lenh khoi tao  (khong con phep gan vi da xu ly o CFGBuilder)
	 */
	private static IASTNode changeDeclarationStatement(IASTDeclarationStatement node, IASTFunctionDefinition func) {
		IASTDeclSpecifier type = null;
		IASTInitializerClause initClause; 
		IASTEqualsInitializer init;
		String newNameVar;
		IASTName nameId;
		IASTDeclarationStatement newNode;
		IASTSimpleDeclaration simpleDecl = (IASTSimpleDeclaration) node.getDeclaration().copy();
		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
		
		for (IASTNode run : simpleDecl.getChildren()){
			if (run instanceof IASTDeclSpecifier) {
				type = (IASTDeclSpecifier) run.copy();
			}
			if (run instanceof IASTDeclarator){
				init = (IASTEqualsInitializer) (((IASTDeclarator) run).getInitializer());
				if (init != null){	
					//Da xu ly o CFGbuilder
				}		
				newNameVar = ((IASTDeclarator) run).getName().toString();
				newNameVar += "_" + getFunctionName(func);
				nameId = factory.newName(newNameVar.toCharArray());
				((IASTDeclarator) run).setName(nameId);					
			}
		}
		newNode = factory.newDeclarationStatement(simpleDecl);		
		
		return newNode;
	}

	/**
	 * @param node, func
	 * sua Unary
	 */
	private static IASTNode changeUnaryExpression(IASTUnaryExpression node, IASTFunctionDefinition func) {
		IASTExpression expression = node.getOperand().copy();
		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
		IASTUnaryExpression newUnary = factory.newUnaryExpression(node.getOperator(), (IASTExpression) changeVariableName(expression, func));
		return newUnary;
	}
	
	/**
	 * @param node, func
	 * sua Binary
	 */
	private static  IASTNode changeBinaryExpression(IASTBinaryExpression node, IASTFunctionDefinition func) {
		IASTExpression left = node.getOperand1().copy();
		IASTExpression right = node.getOperand2().copy();
		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
		IASTBinaryExpression newNode = null;
		IASTBinaryExpression subBinary;
		int operator = node.getOperator();
		
		if (ExpressionHelper.checkUnary(operator)) {
			subBinary = factory.newBinaryExpression(ExpressionHelper.switchUnaryBinaryOperator(operator), left, right);
			newNode = factory.newBinaryExpression(IASTBinaryExpression.op_assign, (IASTExpression) changeVariableName(left, func), (IASTExpression) changeVariableName(subBinary, func));
		} else {
			newNode = factory.newBinaryExpression(operator, 
					(IASTExpression) changeVariableName(left, func), (IASTExpression) changeVariableName(right, func));
		}
		return newNode;	
	
	}
	
	private static String getFunctionName(IASTFunctionDefinition func) {
		String res = func.getDeclarator().getName().toString();
//		for (IASTVariable node: FunctionHelper.getParameters(func)) {
//			res += "_" + node.getName().toString();
//		}
		return res;
	}

	/**
	 * @param node, func
	 * sua bien
	 */
	private static IASTNode changeIdExpression(IASTIdExpression node, IASTFunctionDefinition func) {
		// TODO Auto-generated method stub
		String currentName = node.getName().toString();
		String newName = currentName + "_" + getFunctionName(func);
		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();

		IASTName nameId = factory.newName(newName.toCharArray());
		IASTIdExpression newExp = factory.newIdExpression(nameId);	
		return newExp;
	}
}
