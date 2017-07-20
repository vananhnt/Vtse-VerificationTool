package cfg.utils;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTPointerOperator;

import cfg.node.CFGNode;
import cfg.node.DecisionNode;

/**
 * moi xet truong hop cho co plainNode
 * @author va
 *
 */
public class FormulaCreater {
	public static String LOGIC_AND = "and";
	public static String LOGIC_OR = "or";
	public static String NEGATIVE = "not";
	public static String BINARY_CONNECTIVE = "=>";
	public static String EQUALITY = "=";
	
	public static String create(CFGNode start, CFGNode exit) {
		String constraint = start.getFormula();
		String temp;
		CFGNode node = start.getNext();
		while (node != null && node != exit) {
	
			temp = node.getFormula();
			if (temp != null) {
				if (constraint == null) {
					constraint = temp;
				}
				else {
					constraint = wrapInfix(LOGIC_AND, temp, constraint);
				}
			}
			if (node instanceof DecisionNode) {
				node = ((DecisionNode) node).getEndNode();
			} else {
				node = node.getNext();
			}
			
		}
		return constraint;
	}
	public static String createFormula(IASTNode node) {
		if (node instanceof IASTDeclarationStatement) {
			
		} else if (node instanceof IASTExpressionStatement) { //cau lenh gan va so sanh
			return infixExpressionStatement((IASTExpressionStatement) node);
		} else if (node instanceof IASTBinaryExpression) { //phep gan va so sanh
			return infixBinaryExpression((IASTBinaryExpression) node);
		} else if (node instanceof IASTIdExpression) { //bien (khong tinh trong phep khoi tao)
			return ExpressionHelper.toString(node);
		} else if (node instanceof IASTLiteralExpression) {
			return ExpressionHelper.toString(node);
		}
		return null;
		
	}
	
	private static String infixBinaryExpression(IASTBinaryExpression node) {
		// TODO Auto-generated method stub
		IASTExpression op1 = node.getOperand1();
		IASTExpression op2 = node.getOperand2();
		String operand = ExpressionHelper.getCorrespondBinaryOperator(node.getOperator());
		String opStr1 = createFormula(op1);
		String opStr2 = createFormula(op2);
		return wrapInfix(operand, opStr1, opStr2);
	}
	
	private static String infixExpressionStatement(IASTExpressionStatement node) {
		// TODO Auto-generated method stub
		return createFormula(node.getExpression());
	}
	
	public static String createFormula(String operator, String operand) {
		return "(" + operator + " " + operand + ")";
	}
	
	public static String wrapInfix(String operand, String left, String right) {
		return "(" + left + " " + operand + " " + right + ")";
	}
}
