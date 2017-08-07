package cfg.build;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import cfg.node.BeginNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EndConditionNode;
import cfg.node.FunctionCallNode;
import cfg.node.IterationNode;
import cfg.node.PlainNode;
import cfg.utils.FunctionHelper;
import cfg.utils.VariableHelper;


/**
 * @author va
 * chua xet th goi ham trog return va condition
 */
public class MultiFunctionCFG {
	private static ASTGenerator ast;
	private static ArrayList<IASTFunctionDefinition> funcList = new ArrayList<>();
	private static ArrayList<VtseCFG> cfgList = new ArrayList<>();
	
	public MultiFunctionCFG() {}

	public MultiFunctionCFG(ASTGenerator ast) {
		MultiFunctionCFG.ast = ast;
		MultiFunctionCFG.funcList = ast.getListFunction();
		MultiFunctionCFG.cfgList = createList();
	}
	
	private ArrayList<VtseCFG> createList() {
		ArrayList<VtseCFG> list = new ArrayList<>();
		VtseCFG cfg;
		for (IASTFunctionDefinition func: funcList) {
			cfg = new VtseCFG(func);
		
			list.add(cfg);
		}
		return list;
	}
	
	public ASTGenerator getAst() {
		return ast;
	}

	public void setAst(ASTGenerator ast) {
		this.ast = ast;
	}
	
	private VtseCFG getVtseCFG(IASTFunctionDefinition func) {
		for (VtseCFG cfg : cfgList) {
			if (cfg.getNameFunction().equals(func.getDeclarator().getName().toString())) {
				return cfg;
			}
		}
		return null;
	} 
	private CFGNode findEnd(CFGNode start) {
		CFGNode node = start;
		while (node.getNext() != null) {
			node = node.getNext();
		}
		return node;
	}
	
	public VtseCFG build(IASTFunctionDefinition func) {
		VtseCFG prvCfg = getVtseCFG(func);
		CFGNode newStart = iterateNode(prvCfg.getStart(), prvCfg.exit);
		return prvCfg;	
	}
	
	private CFGNode iterateNode(CFGNode node, CFGNode end) {
		if ( node == null) {
			node = null;
		}  else if (node instanceof DecisionNode) {
			node.setNext(iterateNode(((DecisionNode) node).getThenNode(), end));
			node.setNext(iterateNode(((DecisionNode) node).getElseNode(), end));		
		}  else if (node instanceof IterationNode) {
		} else if (node instanceof EndConditionNode) {	
			
		} else if (node instanceof BeginNode)  {
			node.setNext(iterateNode(node.getNext(), end));
			((BeginNode) node).getEndNode().setNext(iterateNode(((BeginNode) node).getEndNode().getNext(), end));
		} else if (node instanceof FunctionCallNode){
			ControlFlowGraph functionGraph = createFuncGraph(((FunctionCallNode) node).getFunctionCall());

			if (functionGraph != null) {
				CFGNode pause = node.getNext();
				node = functionGraph.getStart();
				functionGraph.getExit().setNext(iterateNode(pause, end));
			}	
		} else {
			node.setNext(iterateNode(node.getNext(), end));	
		}	
		
		return node;
	}
	
	//dang test vs 2 ham fp va main
	private ControlFlowGraph createFuncGraph(IASTFunctionCallExpression callExpression) {
		
		ControlFlowGraph cfg = new ControlFlowGraph();
		IASTFunctionDefinition func = FunctionHelper.getFunction(funcList, "fp");
		String funcName = callExpression.getFunctionNameExpression().toString();
		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
		//Cho tham so = params	
			cfg.concat(createArguments(callExpression));
		//Noi voi than cua ham duoc goi
			cfg.concat(new ControlFlowGraph(func));
		//Tao ra node: ham duoc goi = return	
		IASTIdExpression left = (IASTIdExpression) VariableHelper.changeFunctionCallExpression(callExpression, func);
		
		IASTName nameRight = factory.newName(("return_" + funcName).toCharArray());
		IASTIdExpression right = factory.newIdExpression(nameRight);
		
		IASTBinaryExpression binaryExp = factory.newBinaryExpression(IASTBinaryExpression.op_assign, left, right);
		IASTExpressionStatement statement = factory.newExpressionStatement(binaryExp);
		CFGNode plainNode = new PlainNode(statement); //tao ra plainNode khong co ten ham dang sau
		cfg.concat(new ControlFlowGraph(plainNode, plainNode));
	
		
		return cfg;
	}
	
	private ControlFlowGraph createArguments(IASTFunctionCallExpression callExpression) {
		ControlFlowGraph cfg = new ControlFlowGraph();
		
		CFGNode plainNode;
		IASTFunctionDefinition func = FunctionHelper.getFunction(funcList, "fp");
		ArrayList<IASTIdExpression> params = FunctionHelper.getParameters(func);
		IASTInitializerClause[] arguments = callExpression.getArguments();
		IASTBinaryExpression expression;
		IASTExpressionStatement statement;
		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
		
		for (int i = 0; i < arguments.length ; i++) {
			IASTName leftName = params.get(i).getName();
			IASTIdExpression left = factory.newIdExpression(leftName);
			
			IASTName rightName = factory.newName((arguments[i].getRawSignature().toCharArray()));
			IASTIdExpression right = factory.newIdExpression(rightName);
			
			expression = factory.newBinaryExpression(IASTBinaryExpression.op_assign, left, right);
			statement = factory.newExpressionStatement(expression);
			plainNode = new PlainNode(statement);
			cfg.concat(new ControlFlowGraph(plainNode, plainNode));
		}
		return cfg;
	}
	
	public static void main(String[] args) {
		ASTGenerator ast = new ASTGenerator("./TestInput.c");
		MultiFunctionCFG multicfg = new MultiFunctionCFG(ast);
		IASTFunctionDefinition func = FunctionHelper.getFunction(funcList, "main");
		VtseCFG total = multicfg.build(func);
		total.unfold();
		total.index();
		total.printMeta();
	}
	}
