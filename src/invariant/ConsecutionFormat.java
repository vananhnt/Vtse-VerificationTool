package invariant;

import cfg.utils.ExpressionHelper;
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

public class ConsecutionFormat {
    public static String formatFarkas(IASTExpressionStatement consecution) {
        String res = null;
        CPPNodeFactory factory = new CPPNodeFactory();
        IASTBinaryExpression expression = (IASTBinaryExpression) consecution.getExpression();
        int operator = expression.getOperator(); // default operator is =
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
        return res;
    }

}
