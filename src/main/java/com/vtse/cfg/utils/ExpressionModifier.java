package com.vtse.cfg.utils;

import com.vtse.cfg.build.ASTFactory;
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import java.util.ArrayList;


/**
 * @author va
 * Them ten ham vao bien + Chuan hoa cau lenh
 * static changeName(node, func) : IASTNode
 * Note: muon thay doi IASTNode trong CDT phai tao node moi
 */

public class ExpressionModifier {

    public static IASTNode changeVariableName(IASTNode node, IASTFunctionDefinition func) {
        if (node instanceof IASTBinaryExpression) {
            node = changeBinaryExpression((IASTBinaryExpression) node, func);
        } else if (node instanceof IASTIdExpression) {
            node = changeIdExpression((IASTIdExpression) node, func);
        } else if (node instanceof IASTExpressionStatement) {
            node = changeExpressionStatement((IASTExpressionStatement) node, func);
        } else if (node instanceof IASTUnaryExpression) {
            node = changeUnaryExpression((IASTUnaryExpression) node, func);
        } else if (node instanceof IASTDeclarationStatement) {
            node = changeDeclarationStatement((IASTDeclarationStatement) node, func);
        } else if (node instanceof IASTReturnStatement) {
            node = changeReturnStatement((IASTReturnStatement) node, func);
        } else if (node instanceof IASTFunctionCallExpression) {
            node = changeFunctionCallExpression((IASTFunctionCallExpression) node, func);
        }

        return node;
    }

    /**
     * @param node
     * @param func
     * @return
     */
    public static IASTNode changeFunctionCallExpression(IASTFunctionCallExpression node, IASTFunctionDefinition func) {
        IASTIdExpression newId;
        CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
//		IASTNode[] children = node.getChildren();
//		String currentName = ((IASTIdExpression) children[0]).getName().toString();
//		String params = "";
//		if (children.length > 0) {
//			for (int i = 1; i < children.length; i++) {
//				params += "_" + ExpressionHelper.toString(children[i]);
//			}
//		}
        String currentName = node.getFunctionNameExpression().toString();
        String params = "";
        if (node.getArguments().length > 0) {
            for (IASTNode param : node.getArguments()) {
                params += "_" + param.toString();
            }
        }


        IASTName newName = factory.newName((currentName + params).toCharArray());
        newId = factory.newIdExpression(newName);

        return newId;
    }

    /**
     * @param node, func
     *              sua expression statement
     */
    private static IASTNode changeExpressionStatement(IASTExpressionStatement node, IASTFunctionDefinition func) {
        IASTExpression expression = (IASTExpression) changeVariableName(node.getExpression().copy(), func);
        CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
        IASTStatement newStatement = factory.newExpressionStatement(expression);
        //System.err.println("Test: " + ExpressionHelper.toString(newStatement));
        return newStatement;
    }

    /**
     * @param node, func
     *              sua cau lenh return
     */
    private static IASTNode changeReturnStatement(IASTReturnStatement node, IASTFunctionDefinition func) {
        // Da chuyen het return => binary o PlainNode
        return null;
    }

    /**
     * @param node, func
     *              sua cau lenh khoi tao  (khong con phep gan vi da xu ly o CFGBuilder)
     */
    private static IASTNode changeDeclarationStatement(IASTDeclarationStatement node, IASTFunctionDefinition func) {
        IASTEqualsInitializer init;
        String newNameVar;
        IASTName nameId;
        IASTDeclarationStatement newNode;
        IASTSimpleDeclaration simpleDecl = (IASTSimpleDeclaration) node.getDeclaration().copy();
        CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();

        ASTFactory ast = new ASTFactory(func.getTranslationUnit());
        ArrayList<String> globarVar = ast.getGlobarVarStrList();


        for (IASTNode run : simpleDecl.getChildren()) {
            if (run instanceof IASTDeclSpecifier) {
            }
            if (run instanceof IASTDeclarator) {
                init = (IASTEqualsInitializer) (((IASTDeclarator) run).getInitializer());
                if (init != null) {
                    //Da xu ly o CFGbuilder
                }
                newNameVar = ((IASTDeclarator) run).getName().toString();
                if (!globarVar.contains(newNameVar)) {
                    newNameVar += "_" + FunctionHelper.getShortenName(func);

                }
                //newNameVar += "_" + getFunctionName(func);
                nameId = factory.newName(newNameVar.toCharArray());
                ((IASTDeclarator) run).setName(nameId);
            }
        }
        newNode = factory.newDeclarationStatement(simpleDecl);

        return newNode;
    }

    /**
     * @param node, func
     *              sua Unary
     */
    private static IASTNode changeUnaryExpression(IASTUnaryExpression node, IASTFunctionDefinition func) {
        CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
        IASTUnaryExpression newUnary;
        IASTExpression expression = node.getOperand().copy();
        newUnary = factory.newUnaryExpression(node.getOperator(), (IASTExpression) changeVariableName(expression, func));
        return newUnary;
    }

    /**
     * @param node, func
     *              sua Binary
     */


    private static IASTNode changeBinaryExpression(IASTBinaryExpression node, IASTFunctionDefinition func) {
        IASTExpression left = node.getOperand1().copy();
        IASTExpression right = node.getOperand2().copy();
        CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
        IASTBinaryExpression newNode = null;
        IASTBinaryExpression subBinary;
        int operator = node.getOperator();

        if (ExpressionHelper.checkUnary(operator)) {
            subBinary = factory.newBinaryExpression(ExpressionHelper.switchUnaryBinaryOperator(operator), left, right);
            newNode = factory.newBinaryExpression(IASTBinaryExpression.op_assign, (IASTExpression) changeVariableName(left, func), (IASTExpression) changeVariableName(subBinary, func));
        } else {
            newNode = factory.newBinaryExpression(operator,
                    (IASTExpression) changeVariableName(left, func), (IASTExpression) changeVariableName(right, func));
        }
        return newNode;

    }

    /*
    private static  IASTNode changeBinaryExpression(IASTBinaryExpression node, IASTFunctionDefinition func) {
        IASTExpression left = node.getOperand1().copy();
        IASTExpression right = node.getOperand2().copy();
        CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
        IASTBinaryExpression newNode = factory.newBinaryExpression(node.getOperator(),
                (IASTExpression) changeVariableName(left, func), (IASTExpression) changeVariableName(right, func));
        return newNode;

}*/

    /**
     * @param node, func
     *              sua bien (them ten ham vao sau bien)
     */

    private static IASTNode changeIdExpression(IASTIdExpression node, IASTFunctionDefinition func) {
        // TODO Auto-generated method stub
        String currentName = node.getName().toString();
        ASTFactory ast = new ASTFactory(func.getTranslationUnit());
        ArrayList<String> globarVar = ast.getGlobarVarStrList();
        String newName = null;
        if (globarVar.contains(currentName)) newName = currentName;
        else {
            newName = currentName + "_" + FunctionHelper.getShortenName(func);

        }
        //newName = currentName + "_" + getFunctionName(func);
        CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();

        IASTName nameId = factory.newName(newName.toCharArray());
        IASTIdExpression newExp = factory.newIdExpression(nameId);
        return newExp;
    }
}
