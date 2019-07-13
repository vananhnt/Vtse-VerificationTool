package com.vtse.cfg.utils.astnode;

import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

public class ASTNodeFactory {
    private static CPPNodeFactory factory = new CPPNodeFactory();

    public static IASTDeclarationStatement createDeclarationStatement(IASTName name, IASTDeclSpecifier type) {
        IASTDeclarator newDeclarator;
        IASTDeclarationStatement newDeclStatement;
        IASTSimpleDeclaration newDeclaration = null;
        newDeclarator = factory.newDeclarator(name);
        newDeclaration = factory.newSimpleDeclaration(type);
        newDeclaration.addDeclarator(newDeclarator);
        newDeclStatement = factory.newDeclarationStatement(newDeclaration);
        return newDeclStatement;

    }

}
