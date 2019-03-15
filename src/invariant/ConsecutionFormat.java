package invariant;

import cfg.utils.ExpressionHelper;
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

public class ConsecutionFormat {
    public static String formatFarkas(IASTNode conStm) {
        String res = null;
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
            res = ExpressionHelper.toString(resExp).replaceAll("\\(", "")
                    .replaceAll("\\)", "");
            //System.out.println(res);
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
            //return expression.getRawSignature();
        }
        return res;
    }

}
