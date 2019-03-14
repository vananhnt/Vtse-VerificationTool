package cfg.utils;

import org.eclipse.cdt.core.dom.ast.*;

import java.util.ArrayList;


/**
 * new VariableManager()
 * new VariableManager(IASTFunctionDefinition func)
 * build(IASTFunctionDefinition func): void
 * getVariableList() : ArrayList<Variable>
 * getVariable(int index) : Variable
 * getVariable(String name) : Variable
 * addVariable(Variable var) : void
 * addVariable(String type, String name, String funcName, int index) : void
 *
 * @author va
 */
public class VariableManager {
    private ArrayList<Variable> variableList;

    public VariableManager() {
        this.variableList = new ArrayList<>();
    }

    public VariableManager(IASTFunctionDefinition func) {
        this.variableList = new ArrayList<>();
        build(func);
    }

    public ArrayList<Variable> getVariableList() {
        return variableList;
    }

    public void setVariableList(ArrayList<Variable> variableList) {
        this.variableList = variableList;
    }

    //TODO
    /*
     * note...
     * neu co khoi tao trong ham con
     * va bien thuoc loai declaration
     */
    public void concat(VariableManager otherVM) {
        ArrayList<Variable> otherList = otherVM.getVariableList();
        for (Variable var : otherList) {
            if (!(isHas(var.getName()))) {
                this.getVariableList().add(var);
            } else if (isHas(var.getName()) && (var.getIndex() == -1)) {
                //System.out.println(var.getName());
                var.setIndex(-2);
            }
        }
    }

    public void addVariable(Variable var) {
        variableList.add(var);
    }

    public void addVariable(String type, String name, String funcName, int index) {
        Variable var = new Variable(type, name + "_" + funcName, index);
        variableList.add(var);
    }

    public Variable getVariable(int index) {
        return variableList.get(index);
    }

    public Variable getVariable(String name) {
        for (Variable var : variableList) {
            if (var.getName().equals(name)) {
                return var;
            }
        }
        return null;
    }

    public boolean isHas(String name) {
        if (this.variableList == null) return false;
        for (Variable var : this.variableList) {
            if (name.equals(var.getName())) {
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return this.variableList.size();
    }

    public void printList() {
        if (this.variableList == null) {
            System.out.println("NULL");
        }
        for (Variable var : this.variableList) {
            System.out.println(var.getVariableWithIndex());
        }
    }

    /**
     * Node: chi so cua them bien khi bat dau func la 0
     * Xet params, localVirable, return
     *
     * @param func
     * @return
     */
    void build(IASTFunctionDefinition func) {
        ArrayList<Variable> params = getParameters(func);
        ArrayList<Variable> localVars = new ArrayList<>();

        String funcName = func.getDeclarator().getName().toString();

        localVars = getLocalVar(func, funcName, localVars);
        for (Variable param : params) {
            this.variableList.add(param);
        }
        for (Variable var : localVars) {
            this.variableList.add(var);
        }
        this.variableList.add(getReturn(func));
    }

    private Variable getReturn(IASTFunctionDefinition func) {
        IASTNode typeFunction = func.getDeclSpecifier();
        Variable var = new Variable(typeFunction.getRawSignature(), "return" + "_" + func.getDeclarator().getName().toString());
        return var;
    }

    /*
     * return List parameters  from function
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
                                paramDecls[i + 1].getRawSignature() + "_" +
                                        func.getDeclarator().getName().toString(), 0);
                        params.add(var);
                    }
                }
            }
        }
        return params;
    }

    /*
     * return List local Variables from function
     */
    private ArrayList<Variable> getLocalVar(IASTNode node, String funcName, ArrayList<Variable> list) {
        // find init
        IASTNode[] children = node.getChildren();
        String type;
        String name;
        IASTNode[] body;
        Variable var;

        IASTDeclarator[] declarations;

        if (node instanceof IASTSimpleDeclaration) {
            int init = -1;
            type = ((IASTSimpleDeclaration) node).getDeclSpecifier().getRawSignature();

            declarations = ((IASTSimpleDeclaration) node).getDeclarators();
            name = declarations[0].getName().getRawSignature();

            body = declarations[0].getChildren();
//			for (IASTNode iter : body){
//				if (iter instanceof IASTEqualsInitializer){
//					init = 0;
//				}
//			}
            // add
            var = new Variable(type, name + "_" + funcName, init);
            list.add(var);
        }
        // return

        for (IASTNode run : children) {
            getLocalVar(run, funcName, list);
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


}
