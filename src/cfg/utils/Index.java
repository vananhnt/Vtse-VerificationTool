package cfg.utils;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import cfg.build.ASTGenerator;

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
		}
		return node;
	}

	private static IASTNode indexIdExpression(IASTIdExpression node, VariableManager vm) {		
		String name = ExpressionHelper.toString(node);
		Variable var = vm.getVariable(name);	
		IASTName nameId = factory.newName(var.getVariableWithIndex().toCharArray());
		IASTIdExpression newExp = factory.newIdExpression(nameId);	
		return newExp;
	}
	
	private static IASTNode indexVariable(IASTIdExpression node, VariableManager vm) {
		String name = ExpressionHelper.toString(node);
		Variable var = vm.getVariable(name);	
		var.increase();
		IASTName nameId = factory.newName(var.getVariableWithIndex().toCharArray());
		IASTIdExpression newNode = factory.newIdExpression(nameId);	
		return newNode;
	}
	private static IASTNode indexUranyExpression(IASTUnaryExpression node, VariableManager vm) {
		return null;
	}

	/**
	 * Đánh chỉ số cho Binary Ex bao gồm cả so sánh và gán 
	 * @param node
	 * @param vm
	 * @return
	 */
	private static IASTNode indexIASTBinaryExpression(IASTBinaryExpression node, VariableManager vm) {
		boolean isAssignment = (node.getOperator() == IASTBinaryExpression.op_assign);
		if  (isAssignment)  { // nếu là gán tăng chỉ số bên trái 
			IASTExpression right = (IASTExpression) index(node.getOperand2().copy() , vm);
			IASTExpression left = (IASTExpression) indexVariable((IASTIdExpression) node.getOperand1().copy(), vm);
			IASTBinaryExpression newNode = factory.newBinaryExpression(node.getOperator(), left, right);
			return newNode;
		}
		else { //nếu là so sánh chỉ đánh chỉ số cũ 
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

	private static IASTNode indexDeclarationStatement(IASTDeclarationStatement node, VariableManager vm) {
		//Chưa làm
		//Còn cả index DecisionNode cũng chưa làm 
		return null;
	}
}
