package cfg.utils;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import cfg.build.ASTGenerator;

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
	//Lay Vm cua tat ca cac ham
	public static VariableManager getVM(ArrayList<IASTFunctionDefinition> funcList) {
			VariableManager vm = new VariableManager();
			VariableManager subvm = new VariableManager();
			for (IASTFunctionDefinition func: funcList) {
				subvm.build(func);
				vm.concat(subvm);
			}
		return vm;
	}
	
	/**
	 * @param func
	 * Lay tham so cua ham func
	 */
	public static ArrayList<IASTVariable> getParameters(IASTFunctionDefinition func) {
		ArrayList<IASTVariable> params = new ArrayList<>();
		IASTNode[] nodes = func.getDeclarator().getChildren();
		IASTVariable var = null;
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
						typeVar =  (IASTDeclSpecifier) paramDecls[i];
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
	
//	public static ArrayList<IASTIdExpression> getParameters(IASTFunctionDefinition func) {
//		ArrayList<IASTIdExpression> params = new ArrayList<>();
//		IASTNode[] nodes = func.getDeclarator().getChildren();
//		String name;
//		IASTName nameId;
//		IASTIdExpression newIdEx;
//		IASTParameterDeclaration paramDecl = null; 
//		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
//		
//		for (IASTNode node : nodes) {
//			if (node instanceof IASTParameterDeclaration) {
//				paramDecl = (IASTParameterDeclaration) node;
//				
//				IASTNode[] paramDecls = paramDecl.getChildren();
//				for (int i = 0; i < paramDecls.length; i++) {
//					if (paramDecls[i] instanceof IASTDeclarator) {
//						name = paramDecls[i].getRawSignature();
//						nameId = factory.newName(name.toCharArray());
//						newIdEx = factory.newIdExpression(nameId);
//						params.add(newIdEx);
//					}
//				}
//			}
//		}
//		return params;
//	}

}
