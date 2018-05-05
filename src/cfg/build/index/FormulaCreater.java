package cfg.build.index;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

import cfg.build.ASTFactory;
import cfg.build.VtseCFG;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.IterationNode;
import cfg.utils.ExpressionHelper;

/**
 * @author va
 * Chưa test 
 */
public class FormulaCreater {
	public static String LOGIC_AND = "and";
	public static String LOGIC_OR = "or";
	public static String NEGATIVE = "not";
	public static String BINARY_CONNECTIVE = "=>";
	public static String EQUALITY = "=";


	public static String create(CFGNode start, CFGNode exit) {
		String constraint = start.getFormula();
		
		String temp;
		CFGNode node = start.getNext();
		while (node != null) {
			temp = node.getFormula();			
			if (temp != null) {
				if (constraint == null) {
					constraint = temp;
				}
				else {
//					constraint = wrapInfix(LOGIC_AND, temp, constraint);
					constraint = wrapPrefix(LOGIC_AND, constraint, temp);
				}
			}
			if (node == exit) break;
			if (node instanceof DecisionNode) {
				node = ((DecisionNode) node).getEndNode();
			} else {
				node = node.getNext();
			}
			
		}
		
		return constraint;
	}
	public static ArrayList<String> createListConstraint(CFGNode start, CFGNode exit) {
		//String constraint = start.getFormula();
		ArrayList<String> constraintList = new ArrayList<>();
		String temp = null;
		ArrayList<String> dectemps;
		CFGNode node = start.getNext();
		while (node != null) {
			if (node instanceof DecisionNode) {
				dectemps = ((DecisionNode) node).va_getFormula();
				for (String dectemp : dectemps) {
					constraintList.add(dectemp);
				}
			} else temp = node.getFormula();			
			if (temp != null) {
					constraintList.add(temp);
//					constraint = wrapInfix(LOGIC_AND, temp, constraint);
//					constraint = wrapPrefix(LOGIC_AND, constraint, temp);
				
			}
			if (node == exit) break;
			if (node instanceof DecisionNode) {
				node = ((DecisionNode) node).getEndNode();
			} else {
				node = node.getNext();
			}
			
		}
		
		return constraintList;

	}
	
	public static String createInfix(CFGNode start, CFGNode exit) {
		String constraint = start.getInfixFormula();
		
		String temp;
		CFGNode node = start.getNext();
		while (node != null) {
	
			temp = node.getInfixFormula();			
			if (temp != null) {
				if (constraint == null) {
					constraint = temp;
				}
				else {
					constraint = wrapInfix(LOGIC_AND, temp, constraint);
//					constraint = wrapPrefix(LOGIC_AND, constraint, temp);
				}
			}
			if (node == exit) break;
			if (node instanceof DecisionNode) {
				node = ((DecisionNode) node).getEndNode();
			
			} else {
				node = node.getNext();
			}
			
		}
		
		return constraint;
	}
	
	public static String createFormula(IASTNode node) {
		if (node instanceof IASTDeclarationStatement) {
			return prefixDeclarationStatement((IASTDeclarationStatement) node);
		} else if (node instanceof IASTExpressionStatement) { //cau lenh gan va so sanh
			return prefixExpressionStatement((IASTExpressionStatement) node);
		} else if (node instanceof IASTBinaryExpression) { //phep gan va so sanh
			return prefixBinaryExpression((IASTBinaryExpression) node);
		} else if (node instanceof IASTIdExpression) { //bien (khong tinh trong phep khoi tao)
			return ExpressionHelper.toString(node);
		} else if (node instanceof IASTUnaryExpression) {
			return prefixBinaryExpression((IASTBinaryExpression) ((IASTUnaryExpression) node).getOperand());
		} else if (node instanceof IASTLiteralExpression) {
			return ExpressionHelper.toString(node);
//		} else if (node instanceof IASTReturnStatement){
//			return prefixReturnStatement((IASTReturnStatement) node); //da xu ly return o ReturnNode
		}
		return node.getClass().getSimpleName();
	}
	
	public static String createInfixFormula(IASTNode node) {
		if (node instanceof IASTDeclarationStatement) {
			return infixDeclarationStatement((IASTDeclarationStatement) node);
		} else if (node instanceof IASTExpressionStatement) { //cau lenh gan va so sanh
			return infixExpressionStatement((IASTExpressionStatement) node);
		} else if (node instanceof IASTBinaryExpression) { //phep gan va so sanh
			return infixBinaryExpression((IASTBinaryExpression) node);
		} else if (node instanceof IASTIdExpression) { //bien (khong tinh trong phep khoi tao)
			return ExpressionHelper.toString(node);
		} else if (node instanceof IASTLiteralExpression) {
			return ExpressionHelper.toString(node);
		} 
		return null;
	}
	
	private static String prefixDeclarationStatement(IASTDeclarationStatement node) {
		boolean isAssign = false;
		IASTNode[] nodes2 = null; 
		IASTDeclaration declaration = node.getDeclaration();
		IASTNode[] nodes1 = declaration.getChildren();
		String left = null;
		String right = null;
		//String type = null;
		String formula = null;
		for (IASTNode iter1 : nodes1) {
//			if (iter1 instanceof IASTSimpleDeclSpecifier) {
//				type = iter1.toString();
//			}
			if (iter1 instanceof IASTDeclarator) {
				nodes2 = iter1.getChildren();
				//kiem tra co la phep gan?
				for (IASTNode iter2 : nodes2) {
					if (iter2 instanceof IASTEqualsInitializer) {
						isAssign = true;
					}	
				}
				if (isAssign) {
					for (IASTNode iter : nodes2) {
						if (iter instanceof IASTName) {
							left = ((IASTName) iter).toString();
						}
						if (iter instanceof IASTEqualsInitializer) {
							right = createFormula(iter.getChildren()[0]);
						}
					}
					if (formula == null) {
						formula = wrapPrefix("=", left, right);	
					} else {
						formula = wrapPrefix(LOGIC_AND, formula,  wrapPrefix("=", left, right)); 
					}
			}		
		}
		}
	
		return formula;
	}
	
	private static String infixDeclarationStatement(IASTDeclarationStatement node) {
//		node.getDeclaration()
		boolean isAssign = false;
		IASTNode[] nodes2 = null; 
		IASTDeclaration declaration = node.getDeclaration();
		IASTNode[] nodes1 = declaration.getChildren();
		
		for (IASTNode iter1 : nodes1) {
			if (iter1 instanceof IASTDeclarator) {
				nodes2 = iter1.getChildren();
				//kiem tra co la phep gan?
				for (IASTNode iter2 : nodes2) {
					if (iter2 instanceof IASTEqualsInitializer) {
						isAssign = true;
					}	
				}
				
			}
		}
		if (isAssign) {
			String left = null;
			String right = null;
			for (IASTNode iter : nodes2) {
				if (iter instanceof IASTName) {
					left = ((IASTName) iter).toString();
				}
				if (iter instanceof IASTEqualsInitializer) {
					right = createInfixFormula(iter.getChildren()[0]);
				}
			}
			return wrapInfix("=", left, right);
		}
		return null;
		
	}
	
	private static String prefixExpressionStatement(IASTExpressionStatement node) {
		return createFormula(node.getExpression());
	}
	
	private static String prefixBinaryExpression(IASTBinaryExpression node) {
		IASTExpression op1 = node.getOperand1();
		IASTExpression op2 = node.getOperand2();
		String operand = ExpressionHelper.getCorrespondBinaryOperator(node.getOperator());
		String opStr1 = createFormula(op1);
		String opStr2 = createFormula(op2);
		return wrapPrefix(operand, opStr1, opStr2);
	}
	
	private static String infixBinaryExpression(IASTBinaryExpression node) {
		IASTExpression op1 = node.getOperand1();
		IASTExpression op2 = node.getOperand2();
		String operand = ExpressionHelper.getCorrespondBinaryOperator(node.getOperator());
		String opStr1 = createInfixFormula(op1);
		String opStr2 = createInfixFormula(op2);
		return wrapInfix(operand, opStr1, opStr2);
	}
	
	private static String infixExpressionStatement(IASTExpressionStatement node) {
		return createInfixFormula(node.getExpression());
	}

	public static String createFormula(String operator, String operand) {
		return "(" + operator + " " + operand + ")";
	}
	public static String createInfixFormula (String operator, String operand) {
		return "(" + operator + " " + operand + ")";
	}
	public static String wrapInfix(String operand, String left, String right) {
		return "(" + left + " " + operand + " " + right + ")";
	}
	
	public static String wrapPrefix(String operand, String left, String right) {
		return "(" + operand + " " + left + " " + right + ")";
	}
	
	public static void main(String[] args) {
		ASTFactory ast = new ASTFactory("./TestInput.c");
		IASTFunctionDefinition func = ast.getFunction(0);
		VtseCFG cfg = new VtseCFG(func);
		cfg.index();
		cfg.unfold();
		System.out.println(create(cfg.getStart(), cfg.getExit()));
	}
	public static String createFuncCallFormula(IASTFunctionCallExpression funcCall, ASTFactory ast) {
		
		return null;
	}
}
