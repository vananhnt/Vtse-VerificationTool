package cfg.utils;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import cfg.build.ASTGenerator;
import cfg.node.DecisionNode;

/**
 * @author va
 *
 */
public class Index {
	private static CPPNodeFactory factory = (CPPNodeFactory) (new ASTGenerator()).getTranslationUnit() .getASTNodeFactory(); 
	
	public static IASTNode index(IASTNode node, VariableManager vm) {
		if (node instanceof IASTDeclarationStatement) {
			node = indexDeclarationStatement((IASTDeclarationStatement) node, vm); //cau lenh khoi tao
			
		} else if (node instanceof IASTExpressionStatement) { //cau lenh gan va so sanh
			node = indexExpressionStatement((IASTExpressionStatement) node, vm);
		
		} else if (node instanceof IASTBinaryExpression) { //phep gan va so sanh
			node = indexIASTBinaryExpression(( IASTBinaryExpression) node, vm);
		
		} else if (node instanceof IASTUnaryExpression) { // i++
			node = indexUranyExpression((IASTUnaryExpression) node, vm);
	
		} else if (node instanceof IASTIdExpression) { //bien (khong tinh trong phep khoi tao)
			node = indexIdExpression((IASTIdExpression) node, vm);
		
		} else if ( node instanceof IASTReturnStatement){
			node = indexReturnStatement((IASTReturnStatement) node, vm);
		}
		
		return node;
	}

	private static IASTNode indexIdExpression(IASTIdExpression node, VariableManager vm) {		
		String name = ExpressionHelper.toString(node);
		Variable var = vm.getVariable(name);	
		if (var == null) return node;
		IASTName nameId = factory.newName(var.getVariableWithIndex().toCharArray());
		IASTIdExpression newExp = factory.newIdExpression(nameId);	
		return newExp;
	}
	
	private static IASTNode indexVariable(IASTIdExpression node, VariableManager vm) {
		String name = ExpressionHelper.toString(node);
		Variable var = vm.getVariable(name);
		if (var == null) return node;
		var.increase();
		IASTName nameId = factory.newName(var.getVariableWithIndex().toCharArray());
		IASTIdExpression newNode = factory.newIdExpression(nameId);	
		return newNode;
	}
	
	private static IASTNode indexUranyExpression(IASTUnaryExpression node, VariableManager vm) {
		//TODO
		return null;
	}

	/**
	 * Ä�Ă¡nh chá»‰ sá»‘ cho Binary Ex bao gá»“m cáº£ so sĂ¡nh vĂ  gĂ¡n 
	 * @param node
	 * @param vm
	 * @return
	 */
	private static IASTNode indexIASTBinaryExpression(IASTBinaryExpression node, VariableManager vm) {
		boolean isAssignment = (node.getOperator() == IASTBinaryExpression.op_assign);
		if  (isAssignment)  { // náº¿u lĂ  gĂ¡n tÄƒng chá»‰ sá»‘ bĂªn trĂ¡i 
			IASTExpression right = (IASTExpression) index(node.getOperand2().copy() , vm);
			IASTExpression left = (IASTExpression) indexVariable((IASTIdExpression) node.getOperand1().copy(), vm);
			IASTBinaryExpression newNode = factory.newBinaryExpression(node.getOperator(), left, right);
			return newNode;
		}
		else { //náº¿u lĂ  so sĂ¡nh chá»‰ Ä‘Ă¡nh chá»‰ sá»‘ cÅ© 
			IASTExpression left = node.getOperand1().copy();
			IASTExpression right = node.getOperand2().copy();
			IASTBinaryExpression newNode = factory.newBinaryExpression(node.getOperator(), (IASTExpression) index(left, vm), (IASTExpression) index(right , vm));
			return newNode;	
		}
	}

	private static IASTNode indexExpressionStatement(IASTExpressionStatement node, VariableManager vm) {
		IASTExpression expression = node.getExpression().copy();
		IASTExpressionStatement newNode = factory.newExpressionStatement((IASTExpression) index(expression, vm));		
		return  newNode;
		
	}
/*
 * 
 */
	private static IASTNode indexDeclarationStatement(IASTDeclarationStatement node, VariableManager vm) {
		//ChÆ°a lĂ m			
		String name = "";
		IASTSimpleDeclaration simpleDecl = (IASTSimpleDeclaration) node.getDeclaration().copy();
		for (IASTNode run : simpleDecl.getChildren()){
			if ( run instanceof IASTDeclarator){
				
				int reset = -1;
				IASTEqualsInitializer init = (IASTEqualsInitializer) (((IASTDeclarator) run).getInitializer());
				if (init != null){				
					IASTInitializerClause initClause = (IASTInitializerClause) index(init.getChildren()[0], vm);					
					init.setInitializerClause(initClause);
					reset = 0;
				}
				
				IASTName nameDecl = ((IASTDeclarator) run).getName();
				name = nameDecl.toString();					
				Variable var = vm.getVariable(name);				
				if (var == null) return node;
				var.setIndex(reset);
				IASTName nameId = factory.newName(var.getVariableWithIndex().toCharArray());
				((IASTDeclarator) run).setName(nameId);					
			}
		}		
		IASTDeclarationStatement newNode = factory.newDeclarationStatement(simpleDecl);			
		return newNode;
	}
	
	private static IASTNode indexReturnStatement( IASTReturnStatement returnState, VariableManager vm){			
		IASTExpression exp = (IASTExpression) index(returnState.getChildren()[0], vm);
		IASTReturnStatement newReturn = factory.newReturnStatement(exp);
		//return (IASTStatement)index(returnState.getChildren()[0], vm);
		return newReturn;
	}
}
