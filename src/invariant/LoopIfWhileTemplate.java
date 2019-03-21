package invariant;

import cfg.build.ASTFactory;
import main.solver.RedlogRunner;
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoopIfWhileTemplate extends LoopTemplate {
    private List<List<IASTNode>> consecutions;

    public List<List<IASTNode>> getConsecutions() {
        return consecutions;
    }

    public void setConsecutions(List<List<IASTNode>> consecutions) {
        this.consecutions = consecutions;
    }

    public static LoopIfWhileTemplate getLoopElement(IASTTranslationUnit iastTranslationUnit) {
        return getLoopElement(LoopTemplateUtils.getFunctionBodyElement(iastTranslationUnit));
    }

    private static LoopIfWhileTemplate getLoopElement(IASTNode[] bodyElement) {
        LoopIfWhileTemplate loopTemplate = new LoopIfWhileTemplate();
        List<IASTExpressionStatement> init = new ArrayList<>();
        List<List<IASTNode>> consecutions = new ArrayList<>();
        List<IASTName> var = new ArrayList<>();
        CPPNodeFactory factory = new CPPNodeFactory();

        for (IASTNode node : bodyElement) {
            //get While statement
            if (node instanceof IASTWhileStatement) {
                IASTWhileStatement whileStatement = (IASTWhileStatement) node;
                IASTExpression condition = whileStatement.getCondition();
                loopTemplate.setLoopCondition((IASTExpression) condition);
                IASTNode[] children = whileStatement.getBody().getChildren();
                //for each element of if-else in while loop
                for (IASTNode child : children) {
                    //if - else statement
                    if (child instanceof IASTIfStatement) {
                        IASTIfStatement ifStatement = (IASTIfStatement) child;
                        List<IASTNode> cons = new ArrayList<>();
                        //get consecution of Then Clause
                        IASTExpression trueConditionalExpression = ifStatement.getConditionExpression();
                        if (trueConditionalExpression instanceof IASTBinaryExpression)
                            cons.add(trueConditionalExpression); //-> true condition
                        for (IASTNode thenNode : ifStatement.getThenClause().getChildren()) {
                            if (thenNode instanceof IASTExpressionStatement) {
                                cons.add((IASTExpressionStatement) thenNode);
                            }
                        }
                        consecutions.add(cons);
                        //get consecution of Else Clause
                        cons = new ArrayList<>();
                        if (trueConditionalExpression instanceof IASTBinaryExpression) {
                            IASTExpression falseConditionalExpression = getNegative(ifStatement.getConditionExpression());
                            cons.add(falseConditionalExpression);
                        }
                        for (IASTNode elseNode : ifStatement.getElseClause().getChildren()) {
                            if (elseNode instanceof IASTExpressionStatement) {
                                cons.add((IASTExpressionStatement) elseNode);
                            }
                        }
                        if (trueConditionalExpression instanceof IASTBinaryExpression)
                            cons.add(trueConditionalExpression); //-> true condition
                        consecutions.add(cons);
                    //invariant statement
                    } else if (child instanceof IASTLabelStatement) {
                        IASTLabelStatement labelStatement = (IASTLabelStatement) child;
                        if (labelStatement.getName().toString().equals("invariant")) {
                            //Stub - do nothing
                        }
                    }
                }
                loopTemplate.setConsecutions(consecutions);
                //get initiation
            } else if (node instanceof IASTExpressionStatement) {
                init.add((IASTExpressionStatement) node);
            //get variable declaration
            } else if (node instanceof IASTDeclarationStatement) {
                IASTDeclarationStatement declarationStatement = (IASTDeclarationStatement) node;
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

    //-x + 50000 <= 0 -> 50000 < x
    //move to ASTNodeFactory
    private static IASTBinaryExpression getNegative(IASTExpression pos) {
        if (pos instanceof IASTBinaryExpression) {
            IASTBinaryExpression binaryExpression = (IASTBinaryExpression) pos;
            CPPNodeFactory factory = new CPPNodeFactory();
            int operator = binaryExpression.getOperator();
            int newOperator = operator; //temporary
            IASTExpression leftHand = binaryExpression.getOperand1().copy();
            IASTExpression rightHand = binaryExpression.getOperand2().copy();
            IASTBinaryExpression resExp = factory.newBinaryExpression(newOperator, rightHand, leftHand);
            return resExp;
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
                System.out.println("\t" + TransitionFormat.formatFarkas(con));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String benchmark = "benchmark/invgen/template2/loops_crafted/Mono5_1.c";
        ASTFactory ast = new ASTFactory(benchmark);
        LoopIfWhileTemplate loopTemplate = LoopIfWhileTemplate.getLoopElement(ast.getTranslationUnit());

        //print invariant
        List<String> invariants = InvagenRunner.run(benchmark);
        String concat = invariants.get(0);
        for (int i = 1; i < invariants.size(); i++) {
            concat += " and " + invariants.get(i);
        }

        System.out.println(concat);
        System.out.println(RedlogRunner.rlsimpl(concat));
        TextFileModification.modifyFile(benchmark, "invariant:;", "invariant: " + RedlogRunner.rlsimpl(concat) + ";");
        //invariants.forEach(v -> System.out.println(v));

    }

}