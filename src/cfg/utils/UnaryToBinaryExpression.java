package cfg.utils;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.core.dom.ast.INodeFactory;

public class UnaryToBinaryExpression {
	private void properStatement(IASTUnaryExpression unaryExpression) {
		INodeFactory nodeFactory = unaryExpression.getTranslationUnit().getASTNodeFactory();
		
		
		int unaryOperator = unaryExpression.getOperator();
		IASTExpression operand = unaryExpression.getOperand();
		int binaryOperator = unaryToBinaryOperator(unaryOperator);

		System.out.println(operand);
		
//		CtLiteral rightHand = coreFactory.createLiteral().setValue(1);
//	
//		CtExpression assigned = coreFactory.createVariableWrite().setVariable(var.clone());
//		CtExpression leftHand = coreFactory.createVariableRead().setVariable(var.clone());
//		CtBinaryOperator assignment = coreFactory.createBinaryOperator()
//												.setLeftHandOperand(leftHand)
//												.setRightHandOperand(rightHand)
//												.setKind(binaryKind);
//		
//		statement = (CtAssignment) coreFactory.createAssignment()
//					.setAssigned(assigned)
//					.setAssignment(assignment);			
//		
//		else if (statement instanceof CtLocalVariable) {
//			CtLocalVariable lc = (CtLocalVariable) statement;
//			CtExpression assignment = lc.getAssignment();
//			
//			
//			if (assignment == null) {
//				statement = null;
//				return;
//			}
//			
//			CtVariableReference variableReference = lc.getReference();
//			
//			CtVariableWrite variableWrite = coreFactory.createVariableWrite()
//													.setVariable(variableReference);
//			
//			statement = (CtAssignment) coreFactory.createAssignment()
//										.setAssigned(variableWrite)
//										.setAssignment(assignment);
//			
//		}
	}
		
	
	public static int unaryToBinaryOperator(int unaryOperator) 
	{
		if (unaryOperator == IASTUnaryExpression.op_postFixIncr
					|| unaryOperator == IASTUnaryExpression.op_prefixIncr) {
			return IASTBinaryExpression.op_plus;
		}
		else if (unaryOperator == IASTUnaryExpression.op_postFixDecr
					|| unaryOperator == IASTUnaryExpression.op_prefixDecr) {
			return IASTBinaryExpression.op_minus;
		}
		else {
			return -1;
		}
	}
}
