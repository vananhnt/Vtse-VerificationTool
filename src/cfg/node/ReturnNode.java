 package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

/**
 * @author va
 *
 */

public class ReturnNode extends PlainNode
{
	public ReturnNode(IASTStatement statement) {
		super(changeSyntax((IASTReturnStatement) statement));
	}
	public ReturnNode(IASTStatement statement, IASTFunctionDefinition def) {
		super(changeSyntax((IASTReturnStatement) statement), def);
	}
	private static IASTStatement changeSyntax(IASTReturnStatement statement) {
		CPPNodeFactory factory = (CPPNodeFactory) statement.getTranslationUnit().getASTNodeFactory();
		IASTName name = factory.newName("return".toCharArray());
		IASTIdExpression left = factory.newIdExpression(name).copy();
		
		IASTExpression right = statement.getReturnValue().copy();
		IASTBinaryExpression expression = factory.newBinaryExpression(IASTBinaryExpression.op_assign, left, right);
		IASTStatement binaryStatement = factory.newExpressionStatement(expression);
		return binaryStatement;
	}
	
	
//	private static IASTStatement changeSyntax(IASTReturnStatement statement, IASTFunctionDefinition def) {
//	CPPNodeFactory factory = (CPPNodeFactory) statement.getTranslationUnit().getASTNodeFactory();
//	String newName = def.getDeclarator().getName().toString();
//	
//	ArrayList<IASTVariable> paramList = FunctionHelper.getParameters(def);
//	for (IASTVariable param : paramList) {
//		newName += "_" + param.getName().toString();
//	}
//	
//	IASTName name = factory.newName(newName.toString);
//	IASTIdExpression left = factory.newIdExpression(name).copy();
//	IASTExpression right = statement.getReturnValue().copy();
//	IASTBinaryExpression expression = factory.newBinaryExpression(IASTBinaryExpression.op_assign, left, right);
//	IASTStatement binaryStatement = factory.newExpressionStatement(expression);
//	return binaryStatement;
//}
}
