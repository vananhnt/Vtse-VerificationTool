package cfg.utils;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

public class ExpressionHelper {
	
	public static String toString(IASTNode expression) {
		if (expression instanceof IASTBinaryExpression) {
			return toStringBinaryExpression((IASTBinaryExpression) expression);
		}
		if (expression instanceof IASTIdExpression) {
			IASTIdExpression idExpression = (IASTIdExpression) expression;
			return idExpression.getName().toString();
		}
		if (expression instanceof IASTLiteralExpression) {
			return expression.toString();
		}
		
		return null;
	}
	
	public static String toStringBinaryExpression(IASTBinaryExpression binaryExpression) {
		String operand1 = toString(binaryExpression.getOperand1());
		String operand2 = toString(binaryExpression.getOperand2());
		String operator = getCorrespondBinaryOperator(binaryExpression.getOperator());
		
		String expression = String.format("(%s %s %s)", operand1, operator, operand2);
		return expression;
	}
	
	public static String getCorrespondBinaryOperator(int operatorInt) {
		if (operatorInt == IASTBinaryExpression.op_assign) return "=";
		if (operatorInt == IASTBinaryExpression.op_plus) return "+";
		if (operatorInt == IASTBinaryExpression.op_minus) return "-";
		if (operatorInt == IASTBinaryExpression.op_multiply) return "*";
		if (operatorInt == IASTBinaryExpression.op_divide) return "/";
		if (operatorInt == IASTBinaryExpression.op_modulo) return "=";
		if (operatorInt == IASTBinaryExpression.op_equals) return "==";
		if (operatorInt == IASTBinaryExpression.op_greaterThan) return ">";
		if (operatorInt == IASTBinaryExpression.op_greaterEqual) return ">=";
		if (operatorInt == IASTBinaryExpression.op_lessThan) return "<";
		if (operatorInt == IASTBinaryExpression.op_lessEqual) return "<=";
		if (operatorInt == IASTBinaryExpression.op_logicalAnd) return "and";
		if (operatorInt == IASTBinaryExpression.op_logicalOr) return "or";
		
		return "@";
	}
	
	public static String getCorrespondUnaryOperator(int operatorInt) {
		if (operatorInt == IASTUnaryExpression.op_plus) return "+";
		if (operatorInt == IASTUnaryExpression.op_minus) return "-";
		if (operatorInt == IASTUnaryExpression.op_not) return "not";
		
		return "@";
	}
}
