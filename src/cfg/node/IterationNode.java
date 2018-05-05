package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import cfg.build.index.FormulaCreater;
import cfg.build.index.Index;
import cfg.build.index.VariableManager;
import cfg.utils.ExpressionHelper;
import cfg.utils.ExpressionModifier;


public class IterationNode extends CFGNode {
	private IASTExpression iterationExpression;
	
	public IterationNode() {
	}
	public IterationNode(IterationNode other) {
		super(other);
		this.iterationExpression = other.iterationExpression;
	} 
	
	public IterationNode(IASTExpression iterExpression) {
		iterationExpression = iterExpression;
	}
	
	public IterationNode(IASTExpression iterExpression, IASTFunctionDefinition func) {
//		iterationExpression = (IASTExpression) VariableHelper.changeVariableName(iterationExpression, func);
		iterationExpression = (IASTExpression) ExpressionModifier.changeVariableName(
				changeUnarytoBinary((IASTUnaryExpression) iterExpression, func), func);
	} 
	private static IASTExpression changeUnarytoBinary(IASTUnaryExpression node, IASTFunctionDefinition func) {
		CPPNodeFactory factory = (CPPNodeFactory) func.getTranslationUnit().getASTNodeFactory();
		IASTExpression operand = ((IASTUnaryExpression)node).getOperand().copy();		
		int operator = changeOperator(node.getOperator());		
		if (operator == 0){
			return node.getOperand();			
		}
		IASTLiteralExpression number = factory.newLiteralExpression(IASTLiteralExpression.lk_integer_constant, "1");
		IASTExpression right = factory.newBinaryExpression(operator, operand, number);
		IASTExpression expression = factory.newBinaryExpression(IASTBinaryExpression.op_assign, operand, right);
		return expression;		
	}
	
	private static int changeOperator(int unaryOp) {
		int binaryOp = 0;
		if (unaryOp == IASTUnaryExpression.op_postFixDecr || unaryOp ==  IASTUnaryExpression.op_prefixDecr) {
			binaryOp = IASTBinaryExpression.op_minus;
		} else if (unaryOp == IASTUnaryExpression.op_postFixIncr || unaryOp ==  IASTUnaryExpression.op_prefixIncr) {
			binaryOp = IASTBinaryExpression.op_plus;
		}
		return binaryOp;
	}

	public String getFormula() {
		if (iterationExpression != null) {
			return FormulaCreater.createFormula(iterationExpression);
		} 
		return null;
		
	}
	public String getInfixFormula() {
		if (iterationExpression != null) {
		return FormulaCreater.createInfixFormula(iterationExpression);
		} 
		return null;
	}

	public IASTExpression getIterationExpression() {
		return iterationExpression;
	}
	public void setIterationExpression(IASTExpression iterationExpression) {
		this.iterationExpression = iterationExpression;
	}
	
	public void setIterationExpression(IASTExpression iterationExpression, IASTFunctionDefinition func) {
		this.iterationExpression = (IASTExpression) ExpressionModifier.changeVariableName(iterationExpression, func);
	}
	
	public void index(VariableManager vm){
		this.iterationExpression = (IASTExpression) Index.index(iterationExpression, vm);
		//System.out.println( "--" + iterationExpression.);
	}
	public String toString() {
		return ExpressionHelper.toString(iterationExpression);
	}
	public void printNode() {
		System.out.print("IterationNode: ");
		if (iterationExpression != null) {
			System.out.println(ExpressionHelper.toString(iterationExpression));
		} else {
			System.out.println();
		}
	}

}
