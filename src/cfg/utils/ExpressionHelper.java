package cfg.utils;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
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
		return ".";
	}
	
	private static String toStringFunctionCallExpression(IASTFunctionCallExpression node) {
		String expression = node.getFunctionNameExpression().toString() + "_" ;
		IASTNode[] params = node.getChildren();
		//params[0] la ten function
	
		for (int i = 1; i < params.length; i++) {
			if (i > 1) {
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
		if (operatorInt == IASTUnaryExpression.op_postFixDecr) return "--";
		if (operatorInt == IASTUnaryExpression.op_postFixIncr) return "++";
		if (operatorInt == IASTUnaryExpression.op_prefixIncr) return "++";
		if (operatorInt == IASTUnaryExpression.op_prefixDecr) return "--";
		return "@";
	}
	
	
}
