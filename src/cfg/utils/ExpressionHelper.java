package cfg.utils;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

public class ExpressionHelper {
	
	public static String toString(IASTNode node) {
		if (node instanceof IASTBinaryExpression) {
			return toStringBinaryExpression((IASTBinaryExpression) node);
		}
		if (node instanceof IASTIdExpression) {
			IASTIdExpression idExpression = (IASTIdExpression) node;
			return idExpression.getName().toString();
		}
		if (node instanceof IASTLiteralExpression) {
			return node.toString();
		} 
		if (node instanceof IASTExpressionStatement) {
			return toString(((IASTExpressionStatement) node).getExpression());
		}
		if (node instanceof IASTDeclarationStatement){
			return toStringDeclarationStatement((IASTDeclarationStatement) node);
		}
		if (node instanceof IASTEqualsInitializer){
			return " = " + toString(node.getChildren()[0]);
		}
		if (node instanceof IASTReturnStatement){
			return "return " + toString( node.getChildren()[0]);
		}
		return ".";
	}
	
	public static String toStringDeclarationStatement( IASTDeclarationStatement declStatement){
		String statement= "";
		for (IASTNode run : declStatement.getDeclaration().getChildren()){
			if (run instanceof IASTDeclarator){
				statement += ((IASTDeclarator) run).getName() + " ";
				IASTEqualsInitializer init = (IASTEqualsInitializer) ((IASTDeclarator) run).getInitializer();
				statement += toString(init);
			} else{
				statement += run.toString() + " ";
			}
		}
		return statement;		
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
