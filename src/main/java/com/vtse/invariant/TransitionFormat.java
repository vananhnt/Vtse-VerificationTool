package com.vtse.invariant;

import com.vtse.cfg.utils.ExpressionHelper;
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

public class TransitionFormat {

    public static String formatInitiation(IASTNode initStm) {
        String res = "";
        if (initStm instanceof IASTExpressionStatement) {
            return formatInitiation((IASTExpressionStatement) initStm);
        } else if (initStm instanceof IASTBinaryExpression) {
            return formatInitiation((IASTBinaryExpression) initStm);
        }
        return res;
    }
    private static String formatInitiation(IASTExpressionStatement initStm) {
        return formatInitiation(initStm.getExpression());
    }
    private static String formatInitiation(IASTBinaryExpression initStm) {
            IASTBinaryExpression binaryExpression = (IASTBinaryExpression) initStm;
            IASTExpression left = binaryExpression.getOperand1().copy();
            IASTExpression right = binaryExpression.getOperand2().copy();
            int operator = binaryExpression.getOperator();
            if (ExpressionHelper.toString(right).equals("0")) {
                return ExpressionHelper.toString(initStm);
            } else {
                return ExpressionHelper.toString(left) + " - " + ExpressionHelper.toString(right) + " = 0";
            }
    }
    public static String formatFarkas(IASTNode conStm) {
        String res = "";
        if (conStm instanceof IASTExpressionStatement) {
            return formatFarkas((IASTExpressionStatement) conStm);
        } else if (conStm instanceof IASTBinaryExpression) {
            return formatFarkas((IASTBinaryExpression) conStm);
        } else if (conStm instanceof IASTConditionalExpression) {
            return formatFarkas((IASTConditionalExpression) conStm);
        }
        return res;
    }

    private static String formatFarkas(IASTExpressionStatement statement) {
        if (statement.getExpression() instanceof  IASTBinaryExpression) {
            return formatFarkas((IASTBinaryExpression) statement.getExpression());
        }
        return "";
    }

    private static String formatFarkas(IASTBinaryExpression expression) {
        String res = null;
        CPPNodeFactory factory = new CPPNodeFactory();
        int operator = expression.getOperator(); // default operator is =
        //if the binary expression is assignment
        if (operator == IASTBinaryExpression.op_assign) {
            IASTExpression leftHandExp = expression.getOperand1().copy(); //x, y
            IASTExpression leftHand;
            if (leftHandExp instanceof IASTIdExpression) {
                IASTName name = ((IASTIdExpression) leftHandExp).getName();
                IASTName newName = factory.newName(("_" + name.toString()).toCharArray());
                leftHand = factory.newIdExpression(newName);
            } else {
                leftHand = leftHandExp;
            }
            IASTExpression rightHand = expression.getOperand2().copy();
            IASTExpression newRightHand = factory.newBinaryExpression(IASTBinaryExpression.op_minus,
                    rightHand, leftHand);
            //split x + y and constant, just handle const is + in the last position
            IASTLiteralExpression zero = factory.newLiteralExpression(IASTLiteralExpression.lk_integer_constant, "0");
            IASTBinaryExpression resExp = factory.newBinaryExpression(IASTBinaryExpression.op_equals,newRightHand.copy(), zero);
            res = ExpressionHelper.toString(resExp).replaceAll("\\(", "").replaceAll("\\)", "");

        //if binary expression is condition
        } else if (operator == IASTBinaryExpression.op_lessEqual
                || operator == IASTBinaryExpression.op_lessThan) {
            IASTExpression leftHand = expression.getOperand1().copy();
            IASTExpression rightHand = expression.getOperand2().copy();
            IASTExpression newLeftHand = factory.newBinaryExpression(IASTBinaryExpression.op_minus,
                    leftHand, rightHand);
            IASTLiteralExpression zero = factory.newLiteralExpression(IASTLiteralExpression.lk_integer_constant, "0");
            IASTBinaryExpression resExp = factory.newBinaryExpression(operator, newLeftHand.copy(), zero);
            res = ExpressionHelper.toString(resExp).replaceAll("\\(", "")
                    .replaceAll("\\)", "");
        }
        return res;
    }


}
