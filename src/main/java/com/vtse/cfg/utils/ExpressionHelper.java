package com.vtse.cfg.utils;

import com.vtse.cfg.node.InvariantNode;
import com.vtse.cfg.node.PlainNode;
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

public class ExpressionHelper {

    public static String toString(IASTNode node) {
        if (node instanceof IASTBinaryExpression) {
            return toStringBinaryExpression((IASTBinaryExpression) node);
        } else if (node instanceof IASTIdExpression) {
            IASTIdExpression idExpression = (IASTIdExpression) node;
            return idExpression.getName().toString();
        } else if (node instanceof IASTLiteralExpression) {
            return node.toString();
        } else if (node instanceof IASTExpressionStatement) {
            return toString(((IASTExpressionStatement) node).getExpression());
        } else if (node instanceof IASTUnaryExpression) {
            return toStringUnaryExpression((IASTUnaryExpression) node); //se chuyen ve dang binary khi index
        } else if (node instanceof IASTDeclarationStatement) {
            return toStringDeclarationStatement((IASTDeclarationStatement) node);
        } else if (node instanceof IASTEqualsInitializer) {
            return " = " + toString(node.getChildren()[0]);
        } else if (node instanceof IASTReturnStatement) {
            return "return " + toString(node.getChildren()[0]);
        } else if (node instanceof IASTFunctionCallExpression) {
            return toStringFunctionCallExpression((IASTFunctionCallExpression) node);
        } else if (node instanceof IASTLabelStatement) {
            return node.getRawSignature();
        } else if (node == null) {
            return "";
        }
        return "@";
    }

    private static String toStringFunctionCallExpression(IASTFunctionCallExpression node) {
        String expression = node.getFunctionNameExpression().toString() + "_";
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

    public static String toStringUnaryExpression(IASTUnaryExpression unaryExpression) {
        String operand = toString(unaryExpression.getOperand());
        int operator = unaryExpression.getOperator();
        String expression = "";
        if (operator == IASTUnaryExpression.op_postFixDecr) {
            expression = String.format("%s %s", operand, "--");
        } else if (operator == IASTUnaryExpression.op_postFixIncr) {
            expression = String.format("%s %s", operand, "++");
        } else if (operator == IASTUnaryExpression.op_prefixIncr) {
            expression = String.format("%s %s", "++", operand);
        } else if (operator == IASTUnaryExpression.op_prefixDecr) {
            expression = String.format("%s %s", "--", operand);
        } else if (operator == IASTUnaryExpression.op_not) {
            expression = String.format("(%s %s)", "not", operand);
        } else if (operator == IASTUnaryExpression.op_minus) {
            expression = String.format("(%s %s)", "-", operand);
        } else {
            expression = toString(unaryExpression.getOperand());
        }
        return expression;
    }

    public static String toStringDeclarationStatement(IASTDeclarationStatement declStatement) {
        String statement = "";
        for (IASTNode run : declStatement.getDeclaration().getChildren()) {
            if (run instanceof IASTDeclarator) {
                statement += ((IASTDeclarator) run).getName() + " ";
                IASTEqualsInitializer init = (IASTEqualsInitializer) ((IASTDeclarator) run).getInitializer();
                statement += toString(init);
            } else {
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
            case (IASTBinaryExpression.op_assign):
                return "=";
            case (IASTBinaryExpression.op_plus):
                return "+";
            case (IASTBinaryExpression.op_minus):
                return "-";
            case (IASTBinaryExpression.op_multiply):
                return "*";
            case (IASTBinaryExpression.op_divide):
                return "/";
            case (IASTBinaryExpression.op_modulo):
                return "=";
            case (IASTBinaryExpression.op_equals):
                return "=";
            case (IASTBinaryExpression.op_greaterThan):
                return ">";
            case (IASTBinaryExpression.op_greaterEqual):
                return ">=";
            case (IASTBinaryExpression.op_lessThan):
                return "<";
            case (IASTBinaryExpression.op_lessEqual):
                return "<=";
            case (IASTBinaryExpression.op_logicalAnd):
                return "and";
            case (IASTBinaryExpression.op_logicalOr):
                return "or";
            case (IASTBinaryExpression.op_plusAssign):
                return "+=";
            case (IASTBinaryExpression.op_minusAssign):
                return "-=";
            case (IASTBinaryExpression.op_multiplyAssign):
                return "*=";
            case (IASTBinaryExpression.op_divideAssign):
                return "/=";
            case (IASTBinaryExpression.op_notequals):
                return "distinct";
            default:
                return "@";
        }

    }

    public static boolean checkUnary(int operatorInt) {
        if (operatorInt == IASTBinaryExpression.op_plusAssign
                || operatorInt == IASTBinaryExpression.op_minusAssign
                || operatorInt == IASTBinaryExpression.op_multiplyAssign
                || operatorInt == IASTBinaryExpression.op_divideAssign) {
            return true;
        }
        return false;
    }

    public static int switchUnaryBinaryOperator(int operatorInt) {
        switch (operatorInt) {
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

    //TODO sua tam la assign == equal
    public static int getNegetive(int operator) { // x < 100 -> x = 100
        if (operator == IASTBinaryExpression.op_lessThan) return IASTBinaryExpression.op_greaterEqual;
        else if (operator == IASTBinaryExpression.op_lessEqual) return IASTBinaryExpression.op_greaterThan;
        else if (operator == IASTBinaryExpression.op_greaterEqual) return IASTBinaryExpression.op_lessThan;
        else if (operator == IASTBinaryExpression.op_greaterThan) return IASTBinaryExpression.op_lessEqual;
        else if (operator == IASTBinaryExpression.op_equals) return IASTBinaryExpression.op_notequals;
        return operator;
    }

    /**
     * Get not condition plain node, regarding loop steps
     * @param condition the condition statement
     * @return a plainNode, only return not condition when the loop may terminate
     */
    public static InvariantNode getNotCondition(IASTExpression condition) {
        CPPNodeFactory factory = new CPPNodeFactory();
        if (condition instanceof IASTBinaryExpression) {
            IASTBinaryExpression con = (IASTBinaryExpression) condition;
            IASTExpression left = con.getOperand1().copy();
            int operator = con.getOperator();
            IASTExpression right = con.getOperand2().copy();
            IASTBinaryExpression newExp = factory.newBinaryExpression(getNegetive(operator), left, right);
            IASTStatement statement = factory.newExpressionStatement(newExp);
            return new InvariantNode(statement);
        }
        return new InvariantNode();
    }

    private static boolean isForwardCondition(IASTBinaryExpression condition) {
        int operator = condition.getOperator();
        if (operator == IASTBinaryExpression.op_lessEqual || operator == IASTBinaryExpression.op_lessThan) {
            return true;
        } else {
           return false;
        }
    }
    /**
     * Check termination, using simple heuristic regarding unwinding steps
     * if condition is not binary expression, auto return false
     * @param whileStatement loop statement
     * @return if the loop will terminate
     */
    public static boolean checkTermination(IASTWhileStatement whileStatement) {
        IASTExpression condition = whileStatement.getCondition();
        String var = null;
        boolean isConditionForward = false;
        if (!(condition instanceof IASTBinaryExpression)) {
            return false;
        }
        isConditionForward = isForwardCondition((IASTBinaryExpression) condition);
        // Get variable in condition
        for (IASTNode child : condition.getChildren()) {
            if (child instanceof IASTIdExpression) {
                var = child.getRawSignature();
            }
        }
        IASTNode[] body = whileStatement.getBody().getChildren();
        for (IASTNode node : body) {
            // Consider assign statements
            if (node instanceof IASTExpressionStatement) {
                IASTExpression expression = ((IASTExpressionStatement) node).getExpression();
                if (expression instanceof IASTBinaryExpression) {

                    //check is assignment or not
                    boolean isAssign = (((IASTBinaryExpression) expression).getOperator() == IASTBinaryExpression.op_assign);
                    if (isAssign) {

                        //check is it the assignment that update loop step
                        IASTExpression left = ((IASTBinaryExpression) expression).getOperand1();
                        IASTExpression right = ((IASTBinaryExpression) expression).getOperand2();
                        if (left.getRawSignature().equals(var)) {

                            //check if step is forward or backward
                            //only consider easy cases like: x = x + a or x = x - a; other cases may produce wrong results
                            if (right instanceof IASTBinaryExpression &&
                                ((IASTBinaryExpression) right).getOperator() == IASTBinaryExpression.op_plus) {
                                //forward step
                                if (isConditionForward) {
                                    return true;
                                }
                            } else if (right instanceof IASTBinaryExpression &&
                                ((IASTBinaryExpression) right).getOperator() == IASTBinaryExpression.op_minus) {
                                //backward
                                if (!isConditionForward) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}
