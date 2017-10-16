package cfg.utils.astnode;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
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
