package invariant;

import cfg.build.ASTFactory;
import org.eclipse.cdt.core.dom.ast.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoopMonoWhileTemplate extends LoopTemplate {
    private List<IASTExpressionStatement> consecution;

    public LoopMonoWhileTemplate(){
        super();
    }

    public LoopMonoWhileTemplate(List<IASTName> variables, List<IASTExpressionStatement> initiation, IASTExpression condition, List<IASTExpressionStatement> consecution) {
        super(variables,initiation,condition);
        this.consecution = consecution;
    }

    public List<IASTExpressionStatement> getConsecution() {
        return consecution;
    }

    public void setConsecution(List<IASTExpressionStatement> consecution) {
        this.consecution = consecution;
    }

    public static LoopMonoWhileTemplate getLoopElement(IASTTranslationUnit iastTranslationUnit) {
        return getLoopElement(LoopTemplateUtils.getFunctionBodyElement(iastTranslationUnit));
    }

    //template 1
    private static LoopMonoWhileTemplate getLoopElement(IASTNode[] bodyElement) {
        LoopMonoWhileTemplate loopTemplate = new LoopMonoWhileTemplate();
        List<IASTExpressionStatement> init = new ArrayList<>();
        List<IASTExpressionStatement> statements = new ArrayList<>();
        List<IASTName> var = new ArrayList<>();
        for (IASTNode node : bodyElement) {
            if (node instanceof IASTWhileStatement) {
                IASTWhileStatement whileStatement = (IASTWhileStatement) node;
                IASTExpression condition = whileStatement.getCondition();
                loopTemplate.setLoopCondition((IASTExpression) condition);
                IASTNode[] children = whileStatement.getBody().getChildren();
                for (IASTNode child : children) {
                    if (child instanceof IASTExpressionStatement) {
                        statements.add((IASTExpressionStatement)child);
                    }
                }
                loopTemplate.setConsecution(statements);
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
        System.out.println("-> Consecution: ");
        for (IASTExpressionStatement cons : consecution) {
            System.out.println("\t" + ConsecutionFormat.formatFarkas(cons));
        }
    }

    public static void main(String[] args) throws IOException {
        String benchmark = "benchmark/invgen/template1/count_by_1.c";
        ASTFactory ast = new ASTFactory(benchmark);
        LoopMonoWhileTemplate loopTemplate = LoopMonoWhileTemplate.getLoopElement(ast.getTranslationUnit());
        InvagenRunner.run(benchmark).forEach(s -> {
            System.out.println(s);
        });

    }

}