package cfg.utils;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;

public class VariableManager {
	private ArrayList<Variable> variableList;

	public ArrayList<Variable> getVariableList() {
		return variableList;
	}

	public void setVariableList(ArrayList<Variable> variableList) {
		this.variableList = variableList;
	}
	
	public Variable getVariable(String name) {
		for (Variable var : variableList) {
			if (var.getName().equals(name)) {
				return var;
			}
		}
		return null;
	} 
	
	public void addVariable(Variable var) {
		variableList.add(var);
	}
	
	public void addVariable(String type, String name, int index) {
		Variable var = new Variable(type, name, index);
		variableList.add(var);
	}
	
	public Variable getVariable(int index) {
		return variableList.get(index);
	}
	
	private ArrayList<Variable> getParameters(IASTFunctionDefinition func) {
		ArrayList<Variable> params = new ArrayList<>();
		IASTNode[] nodes = func.getDeclarator().getChildren();
		
		IASTParameterDeclaration paramDecl = null; 
		for (IASTNode node : nodes) {
			if (node instanceof IASTParameterDeclaration) {
				paramDecl = (IASTParameterDeclaration) node;
			}
		}
		IASTNode[] paramDecls = paramDecl.getChildren();
		for (int i = 0; i < paramDecls.length; i++) {
			if (paramDecls[i] instanceof IASTSimpleDeclSpecifier 
			 && paramDecls[i + 1] instanceof IASTDeclarator) {
				Variable var = new Variable(paramDecls[i].getRawSignature(),
							   				paramDecls[i + 1].getRawSignature(), 0);
				params.add(var);
			}
		}
		return params;
	}
	/**
	 * Node: chi so cua them bien khi bat dau func la 0
	 * Xet params, localVirable, return 
	 * @param func
	 * @return
	 */
	VariableManager build(IASTFunctionDefinition func) {
		VariableManager vm = new VariableManager();
		ArrayList<Variable> varList = new ArrayList<>();
		ArrayList<Variable> params = getParameters(func);
		for (Variable param : params) {
			varList.add(param);
		}
		
		return vm;
	}
}
