package cfg.utils;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;

public class FunctionManager {
	
	private ArrayList<IASTFunctionDefinition> funcList = new ArrayList<>();
	
	public String getFuncName(IASTFunctionDefinition func) {
		return func.getDeclarator().getName().toString();
	}
	
}
