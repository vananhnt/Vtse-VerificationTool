package invariant;

import cfg.build.ASTFactory;
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LoopIfWhileTemplate extends LoopTemplate {
    private List<List<IASTNode>> consecutions;

    public List<List<IASTNode>> getConsecutions() {
        return consecutions;
    }
    public void setConsecutions(List<List<IASTNode>> consecutions) {
        this.consecutions = consecutions;
    }
    private static LoopIfWhileTemplate getLoopElement(IASTTranslationUnit iastTranslationUnit) {
        return getLoopElement(LoopTemplateUtils.getFunctionBodyElement(iastTranslationUnit));
    }

    private static LoopIfWhileTemplate getLoopElement(IASTNode[] bodyElement) {
        LoopIfWhileTemplate loopTemplate = new LoopIfWhileTemplate();
        List<IASTExpressionStatement> init = new ArrayList<>();
        List<List<IASTNode>> consecutions = new ArrayList<>();
        List<IASTName> var = new ArrayList<>();
        CPPNodeFactory factory = new CPPNodeFactory();
        for (IASTNode node : bodyElement) {
            if (node instanceof IASTWhileStatement) {
                IASTWhileStatement whileStatement = (IASTWhileStatement) node;
                IASTExpression condition = whileStatement.getCondition();
                loopTemplate.setLoopCondition((IASTExpression) condition);
                IASTNode[] children = whileStatement.getBody().getChildren();

                for (IASTNode child : children) {
                    if (child instanceof IASTIfStatement) {
                        IASTIfStatement ifStatement = (IASTIfStatement) child;
                        List<IASTNode> cons = new ArrayList<>();
                        //get consecution of Then Clause
                        IASTExpression trueConditionalExpression = ifStatement.getConditionExpression();
                        cons.add(trueConditionalExpression); //-> true condition
                        for (IASTNode thenNode : ifStatement.getThenClause().getChildren()) {
                            if (thenNode instanceof IASTExpressionStatement) {
                                cons.add((IASTExpressionStatement) thenNode);
                            }
                        }
                        consecutions.add(cons);
                        //get consecution of Else Clause
                        cons = new ArrayList<>();
                        IASTExpression falseConditionalExpression = getNegative(ifStatement.getConditionExpression());

                        for (IASTNode elseNode : ifStatement.getElseClause().getChildren()) {
                            if (elseNode instanceof IASTExpressionStatement) {
                                cons.add((IASTExpressionStatement) elseNode);
                            }
                        }
                        consecutions.add(cons);
                    }
                }
                loopTemplate.setConsecutions(consecutions);
            } else if (node instanceof IASTExpressionStatement) {
                init.add((IASTExpressionStatement) node);
            } else if (node instanceof IASTDeclarationStatement) {
                IASTDeclarationStatement declarationStatement = (IASTDeclarationStatement) node;
                //System.out.println(declarationStatement.getDeclaration().getChildren().length);
                IASTNode[] child_declaration = declarationStatement.getDeclaration().getChildren();
                for (IASTNode childDe : child_declaration) {
                    if (childDe instanceof IASTDeclarator) {
                        var.add(((IASTDeclarator) childDe).getName());
                    }
                }
            }
        }
        loopTemplate.setInitiation(init);
        loopTemplate.setVariables(var);
        return loopTemplate;
    }
    private static IASTBinaryExpression getNegative (IASTExpression pos) {
        if (pos instanceof IASTBinaryExpression) {
            IASTBinaryExpression binaryExpression = (IASTBinaryExpression) pos;
            CPPNodeFactory factory = new CPPNodeFactory();
            int operator = binaryExpression.getOperator();
            IASTExpression leftHand = binaryExpression.getOperand1().copy();
            IASTExpression rightHand = binaryExpression.getOperand2().copy();
            IASTExpression newLeftHand = factory.newBinaryExpression(IASTBinaryExpression.op_minus,
                    leftHand, rightHand);
            IASTLiteralExpression zero = factory.newLiteralExpression(IASTLiteralExpression.lk_integer_constant, "0");
            IASTBinaryExpression resExp = factory.newBinaryExpression(operator, newLeftHand.copy(), zero);
//TO-DO
        }
        return null;
    }
    public void print() {
        System.out.println("-> Variable: ");
        for (IASTName node : super.getVariables()) {
            System.out.println("\t" + node.getRawSignature());
        }
        System.out.println("-> Initiation: ");
        for (IASTExpressionStatement iastExpressionStatement : super.getInitiation()) {
            System.out.println("\t" + iastExpressionStatement.getRawSignature());
        }
        System.out.println("-> Condition: " + super.getLoopCondition().getRawSignature());
        System.out.println("-> Consecutions: ");
        for (List<IASTNode> cons : consecutions) {
            for (IASTNode con : cons) {
                System.out.println("\t" + ConsecutionFormat.formatFarkas(con));
            }
        }
    }
    public static void main(String[] args) throws IOException {
        String benchmark = "benchmark/invgen/template2/loops_crafted/Mono4_1.c";
        ASTFactory ast = new ASTFactory(benchmark);
        LoopIfWhileTemplate loopTemplate = LoopIfWhileTemplate.getLoopElement(ast.getTranslationUnit());
        loopTemplate.print();

    }

}