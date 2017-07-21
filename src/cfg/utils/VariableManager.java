package cfg.utils;

import java.util.ArrayList;


import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;

public class VariableManager {
	private ArrayList<Variable> variableList;

	public VariableManager(){
		this.variableList = new ArrayList<>();
	}
	
	public VariableManager( IASTFunctionDefinition func){
		this.variableList = new ArrayList<>();
		build(func);
	}
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
	
	public boolean isHas(String name){
		if ( this.variableList == null)	return false;
		for (Variable var : this.variableList){
			if(name.equals(var.getName())){
				return true;
			}
		}
		return false;
	}
/*
 * return List parameters  form function	
 */
	private ArrayList<Variable> getParameters(IASTFunctionDefinition func) {
		ArrayList<Variable> params = new ArrayList<>();
		IASTNode[] nodes = func.getDeclarator().getChildren();
		
		IASTParameterDeclaration paramDecl = null; 
		for (IASTNode node : nodes) {
			if (node instanceof IASTParameterDeclaration) {
				paramDecl = (IASTParameterDeclaration) node;
				
				IASTNode[] paramDecls = paramDecl.getChildren();
				for (int i = 0; i < paramDecls.length; i++) {
					if (paramDecls[i] instanceof IASTSimpleDeclSpecifier 
					 && paramDecls[i + 1] instanceof IASTDeclarator) {
						Variable var = new Variable(paramDecls[i].getRawSignature(),
									   				paramDecls[i + 1].getRawSignature(), 0);
						params.add(var);
					}
				}

			}
		}
		return params;
	}

/*
 * return List local Variables form function
 */
	public ArrayList<Variable> getLocalVar( IASTNode node, ArrayList<Variable> list){
		// find init
		IASTNode[] children = node.getChildren();		
		if ( node instanceof IASTSimpleDeclaration ){
			String type = ((IASTSimpleDeclaration) node).getDeclSpecifier().getRawSignature();
			IASTDeclarator[] declarations = ((IASTSimpleDeclaration) node).getDeclarators();
			String name = declarations[0].getName().getRawSignature();
				// add
			Variable var = new Variable(type, name);			
			list.add(var);						
		}
		// return
		if( node instanceof IASTReturnStatement){
			IASTExpression result = ((IASTReturnStatement) node).getReturnValue();
			Variable var = new Variable("return", result.getRawSignature());
			list.add(var);
		}
		for ( IASTNode run : children){
			getLocalVar(run, list);
		}		
		return list;
	}
/*
 * add bien toan cuc 
 */
	
//	public void getGloble( IASTNode node){
//		IASTNode[] children = node.getChildren();
//		if (node instanceof IASTIdExpression && !(isHas(node.getRawSignature()))){
//			String name = node.getRawSignature();
//			Variable var = new Variable();
//			this.variableList.add(var);
//		}
//		
//		for ( IASTNode run : children){
//			getGloble(run);
//		}
//	}
	
	public void printList(){
		if ( this.variableList == null){
			System.out.println("NULL");
		} 
		for (Variable var : this.variableList){
			System.out.println( var.getVariableWithIndex());
		}
	}
	/**
	 * Node: chi so cua them bien khi bat dau func la 0
	 * Xet params, localVirable, return 
	 * @param func
	 * @return
	 */
	public void build(IASTFunctionDefinition func) {		
		ArrayList<Variable> params = getParameters(func);
		ArrayList<Variable> localVars = new ArrayList<>();
		localVars = getLocalVar(func, localVars);
		for (Variable param : params) {
			this.variableList.add(param);
		}
		for ( Variable var : localVars){
			this.variableList.add(var);
		}		
		
	}
}
