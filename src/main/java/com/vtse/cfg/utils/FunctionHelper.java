package com.vtse.cfg.utils;

import com.vtse.cfg.index.IASTVariable;
import com.vtse.cfg.index.VariableManager;
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import java.util.ArrayList;

public class FunctionHelper {

    public static IASTFunctionDefinition getFunction(ArrayList<IASTFunctionDefinition> funcList, String name) {
        String funcName = null;
        for (IASTFunctionDefinition func : funcList) {
            funcName = func.getDeclarator().getName().toString();
            if (name.equals(funcName)) {
                return func;
            }
        }
        return null;
    }

    public static String getFunctionType(IASTFunctionDefinition func) {
        return func.getDeclSpecifier().toString();

    }

    public static String getFunctionName(IASTFunctionDefinition func) {
        return func.getDeclarator().getName().getRawSignature();
    }

    //Lay Vm cua tat ca cac ham
    public static com.vtse.cfg.index.VariableManager getVM(ArrayList<IASTFunctionDefinition> funcList) {
        com.vtse.cfg.index.VariableManager vm = new com.vtse.cfg.index.VariableManager();
        com.vtse.cfg.index.VariableManager subvm = new VariableManager();
        for (IASTFunctionDefinition func : funcList) {
            subvm.build(func);
            vm.concat(subvm);
        }
        return vm;
    }

    /**
     * @param func Lay tham so cua ham func
     */
    public static ArrayList<com.vtse.cfg.index.IASTVariable> getParameters(IASTFunctionDefinition func) {
        ArrayList<com.vtse.cfg.index.IASTVariable> params = new ArrayList<>();
        IASTNode[] nodes = func.getDeclarator().getChildren();
        com.vtse.cfg.index.IASTVariable var = null;
        CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
        IASTParameterDeclaration paramDecl = null;
        IASTDeclSpecifier typeVar;
        IASTName nameVar;
        IASTIdExpression varId;
        for (IASTNode node : nodes) {
            if (node instanceof IASTParameterDeclaration) {
                paramDecl = (IASTParameterDeclaration) node;

                IASTNode[] paramDecls = paramDecl.getChildren();
                for (int i = 0; i < paramDecls.length; i++) {
                    if (paramDecls[i] instanceof IASTSimpleDeclSpecifier
                            && paramDecls[i + 1] instanceof IASTDeclarator) {
                        typeVar = (IASTDeclSpecifier) paramDecls[i];
                        nameVar = ((IASTDeclarator) paramDecls[i + 1]).getName().copy();
                        varId = factory.newIdExpression(nameVar);
                        var = new IASTVariable(typeVar, varId);
                        params.add(var);
                    }
                }
            }
        }
        return params;
    }

    public static ArrayList<IASTNode> getArguments(IASTFunctionCallExpression funcCall) {
        ArrayList<IASTNode> list = new ArrayList<>();
        for (IASTNode arg : funcCall.getArguments()) {
            if (!(arg.getRawSignature().equals("void"))) {
                list.add(arg);
            }
        }
        return list;
    }

}
