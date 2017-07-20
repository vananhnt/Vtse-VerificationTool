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

/**
 * @author va
 *
 */
public class Index {
	public static void index(IASTNode node, VariableManager vm) {
		if (node instanceof IASTDeclarationStatement) {
			indexDeclarationStatement((IASTDeclarationStatement) node, vm); //cau lenh khoi tao
	
		} else if (node instanceof IASTExpressionStatement) { //cau lenh gan va so sanh
			indexExpressionStatement((IASTExpressionStatement) node, vm);
		
		} else if (node instanceof IASTBinaryExpression) { //phep gan va so sanh
			indexIASTBinaryExpression(( IASTBinaryExpression) node, vm);
		
		} else if (node instanceof IASTUnaryExpression) { // i++
			indexUranyExpression((IASTUnaryExpression) node, vm);
	
		} else if (node instanceof IASTIdExpression) { //bien (khong tinh trong phep khoi tao)
			indexIdExpression((IASTIdExpression) node, vm);
		}
		
	}

	private static void indexIdExpression(IASTIdExpression node, VariableManager vm) {
		System.out.println(node.toString());

	}

	private static void indexUranyExpression(IASTUnaryExpression node, VariableManager vm) {
		IASTExpression operand = node.getOperand();
		index(operand, vm);
	}

	private static void indexIASTBinaryExpression(IASTBinaryExpression node, VariableManager vm) {
		System.out.println(node.toString());
		
	}

	private static void indexExpressionStatement(IASTExpressionStatement node, VariableManager vm) {
		// TODO Auto-generated method stub
		index(node.getExpression(), vm);
		System.out.println(node.toString());
		
	}

	private static void indexDeclarationStatement(IASTDeclarationStatement node, VariableManager vm) {
		// TODO Auto-generated method stub
		System.out.println(node.toString());
		
	}
}
