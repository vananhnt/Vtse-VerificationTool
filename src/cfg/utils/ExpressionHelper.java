package cfg.utils;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTNullStatement;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

public class ExpressionHelper {
	
	public static String toString(IASTNode node) {
		if (node instanceof IASTBinaryExpression) {
			return toStringBinaryExpression((IASTBinaryExpression) node);
		}
		else if (node instanceof IASTIdExpression) {
			IASTIdExpression idExpression = (IASTIdExpression) node;
			return idExpression.getName().toString();
		}
		else if (node instanceof IASTLiteralExpression) {
			return node.toString();
		} 
		else if (node instanceof IASTExpressionStatement) {
			return toString(((IASTExpressionStatement) node).getExpression());
		}
		else if (node instanceof IASTUnaryExpression) {
			return toStringUnaryExpression((IASTUnaryExpression) node); //se chuyen ve dang binary khi index
		}
		else if (node instanceof IASTDeclarationStatement){
			return toStringDeclarationStatement((IASTDeclarationStatement) node);
		}
		else if (node instanceof IASTEqualsInitializer){
			return " = " + toString(node.getChildren()[0]);
		}
		else if (node instanceof IASTReturnStatement){
			return "return " + toString(node.getChildren()[0]);
		}
		else if (node instanceof IASTFunctionCallExpression) {
			return toStringFunctionCallExpression((IASTFunctionCallExpression) node);
		}
		else if (node == null) {
			return "";
		}
		return "@";
	}
	
	private static String toStringFunctionCallExpression(IASTFunctionCallExpression node) {
		String expression = node.getFunctionNameExpression().toString() + "_" ;
		IASTNode[] params = node.getChildren();
		//params[0] la ten function
	
		for (int i = 1; i < params.length; i++) {
			if (i > 0) {
				expression += "_"; 
			}
			expression += toString(params[i]);
		}
		return expression;
	}

	public static String toStringUnaryExpression(IASTUnaryExpression unaryExpression){
		String operand = toString(unaryExpression.getOperand());
		int operator = unaryExpression.getOperator();
		String expression = ""; 
		if (operator == IASTUnaryExpression.op_postFixDecr) {
			expression = String.format("%s %s", operand, "--");
		} else if (operator == IASTUnaryExpression.op_postFixIncr) {
			expression = String.format("%s %s", operand, "++");
		} else if  (operator == IASTUnaryExpression.op_prefixIncr) {
			expression = String.format("%s %s", "++", operand);
		} else if (operator == IASTUnaryExpression.op_prefixDecr) {
			expression = String.format("%s %s", "--", operand);
		} else {
			expression = toString(unaryExpression.getOperand());
		}
		return expression;
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
		switch (operatorInt) {
		case (IASTBinaryExpression.op_assign): return "=";
		case (IASTBinaryExpression.op_plus):  return "+";
		case (IASTBinaryExpression.op_minus): return "-";
		case (IASTBinaryExpression.op_multiply): return "*";
		case (IASTBinaryExpression.op_divide): return "/";
		case (IASTBinaryExpression.op_modulo): return "=";
		case (IASTBinaryExpression.op_equals) : return "=";
		case (IASTBinaryExpression.op_greaterThan): return ">";
		case (IASTBinaryExpression.op_greaterEqual): return ">=";
		case (IASTBinaryExpression.op_lessThan): return "<";
		case (IASTBinaryExpression.op_lessEqual): return "<=";
		case (IASTBinaryExpression.op_logicalAnd): return "and";
		case (IASTBinaryExpression.op_logicalOr): return "or";
		case (IASTBinaryExpression.op_plusAssign): return "+=";
		case (IASTBinaryExpression.op_minusAssign): return "-=";
		case (IASTBinaryExpression.op_multiplyAssign): return "*=";
		case (IASTBinaryExpression.op_divideAssign): return "/=";
		case (IASTBinaryExpression.op_notequals): return "distinct"; 
		default: return "@";
		}
		
	}
	public static boolean checkUnary(int operatorInt) {
		if (operatorInt == IASTBinaryExpression.op_plusAssign
				|| operatorInt == IASTBinaryExpression.op_minusAssign
				|| operatorInt == IASTBinaryExpression.op_multiplyAssign
				|| operatorInt == IASTBinaryExpression.op_divideAssign ) {
			return true;
		}
		return false;
	}

	public static int switchUnaryBinaryOperator(int operatorInt) {
		switch(operatorInt) {
			case (IASTBinaryExpression.op_plusAssign): 
				return IASTBinaryExpression.op_plus;
			case (IASTBinaryExpression.op_minusAssign): 
				return IASTBinaryExpression.op_minus;
			case (IASTBinaryExpression.op_multiplyAssign): 
				return IASTBinaryExpression.op_multiply;
			case (IASTBinaryExpression.op_divideAssign): 
				return IASTBinaryExpression.op_divide;
		}
		return operatorInt;
	}
	public static String getCorrespondUnaryOperator(int operatorInt) {
		if (operatorInt == IASTUnaryExpression.op_plus) return "+";
		if (operatorInt == IASTUnaryExpression.op_minus) return "-";
		if (operatorInt == IASTUnaryExpression.op_not) return "not";
		if (operatorInt == IASTUnaryExpression.op_postFixDecr) return "--";
		if (operatorInt == IASTUnaryExpression.op_postFixIncr) return "++";
		if (operatorInt == IASTUnaryExpression.op_prefixIncr) return "++";
		if (operatorInt == IASTUnaryExpression.op_prefixDecr) return "--";
		return "@";
	}
	
	
}
