package cfg.build;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;

public class MultiFunctionCFG {
	private ASTGenerator ast;
	private ArrayList<IASTFunctionDefinition> funcList = new ArrayList<>();
	private ArrayList<VtseCFG> cfgList = new ArrayList<>();
	
	public MultiFunctionCFG() {}

	public MultiFunctionCFG(ASTGenerator ast) {
		this.ast = ast;
		this.funcList = ast.getListFunction();
		this.cfgList = createList();
	}
	
	public ASTGenerator getAst() {
		return ast;
	}

	public void setAst(ASTGenerator ast) {
		this.ast = ast;
	}

	private ArrayList<VtseCFG> createList() {
		ArrayList<VtseCFG> list = new ArrayList<>();
		VtseCFG cfg;
		for (IASTFunctionDefinition func: funcList) {
			cfg = new VtseCFG(func);
			cfg.index();
			list.add(cfg);
		}
		return list;
	}
	
	public void iterateNode(IASTNode node) {
		
	}
	
}
