package invariant;

import cfg.build.ASTFactory;
import org.eclipse.cdt.core.dom.ast.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LoopTemplate {
    private List<IASTName> variables;
    private List<IASTExpressionStatement> initiation;
    private IASTExpression condition;
    private List<IASTExpressionStatement> consecution;

    public LoopTemplate(){}

    public LoopTemplate(List<IASTName> variables, List<IASTExpressionStatement> initiation, IASTExpression condition, List<IASTExpressionStatement> consecution) {
        this.variables = variables;
        this.initiation = initiation;
        this.condition = condition;
        this.consecution = consecution;
    }

    public List<IASTName> getVariables() {
        return variables;
    }

    public void setVariables(List<IASTName> variables) {
        this.variables = variables;
    }

    public List<IASTExpressionStatement> getInitiation() {
        return initiation;
    }

    public void setInitiation(List<IASTExpressionStatement> initiation) {
        this.initiation = initiation;
    }

    public IASTExpression getCondition() {
        return condition;
    }

    public void setCondition(IASTExpression condition) {
        this.condition = condition;
    }

    public List<IASTExpressionStatement> getConsecution() {
        return consecution;
    }

    public void setConsecution(List<IASTExpressionStatement> consecution) {
        this.consecution = consecution;
    }

    private static String getBenchmark(String props) throws IOException {
        String path = System.getProperty("user.dir") + "/src/invariant/benchmark.properties";
        FileInputStream is = new FileInputStream(new File(path));
        Properties benchmarkProps = new Properties();
        benchmarkProps.load(is);
        return benchmarkProps.getProperty("invgen");
    }
    public static LoopTemplate getLoopElement(IASTTranslationUnit iastTranslationUnit) {
        return getLoopElement(getFunctionBodyElement(iastTranslationUnit));
    }

    //template 1
    private static LoopTemplate getLoopElement(IASTNode[] bodyElement) {
        LoopTemplate loopTemplate = new LoopTemplate();
        List<IASTExpressionStatement> init = new ArrayList<>();
        List<IASTExpressionStatement> statements = new ArrayList<>();
        List<IASTName> var = new ArrayList<>();
        for (IASTNode node : bodyElement) {
            if (node instanceof IASTWhileStatement) {
                IASTWhileStatement whileStatement = (IASTWhileStatement) node;
                IASTExpression condition = whileStatement.getCondition();
                loopTemplate.setCondition((IASTExpression) condition);
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

    private static IASTNode[] getFunctionBodyElement(IASTTranslationUnit ast) {
        return getFunctionBodyElementUtils(ast.getTranslationUnit());
    }

    private static IASTNode[] getFunctionBodyElementUtils(IASTNode ast) {
        IASTNode[] children = ast.getChildren();

        for (IASTNode iastNode : children) {
            if (iastNode instanceof IASTFunctionDefinition) {
                //function[0] -> compound -> body
                IASTNode[] child_Function = iastNode.getChildren();
                IASTCompoundStatement compoundStatement = null;
                for (IASTNode iChildFunc : child_Function) {
                    if (iChildFunc instanceof IASTCompoundStatement) {
                        compoundStatement = (IASTCompoundStatement) iChildFunc;
                    }
                }
                IASTNode[] child_Compound = compoundStatement.getChildren();
                return child_Compound;
            } else {
                return getFunctionBodyElementUtils(iastNode);
            }
        }
        return null;
    }

    private static void printTree(IASTNode node, int index) {
        IASTNode[] children = node.getChildren();
        for (IASTNode iastNode : children) {
            if (iastNode instanceof IASTFunctionDefinition) {
                //function[0] -> compound -> body
                IASTNode[] child_Function = iastNode.getChildren();
                IASTCompoundStatement compoundStatement = null;
                for (IASTNode iChildFunc : child_Function) {
                    if (iChildFunc instanceof IASTCompoundStatement) {
                        compoundStatement = (IASTCompoundStatement) iChildFunc;
                    }
                }
                IASTNode[] child_Compound = compoundStatement.getChildren();
                for (IASTNode node1 : child_Compound) {
                    System.out.println("-" + node1.getClass().getSimpleName() + " -> " + node1.getRawSignature());
                }
            } else {
                printTree(iastNode, index + 2);
            }
        }
    }

    public void print() {
        System.out.println("-> Variable: ");
        for (IASTName node : variables) {
            System.out.println("\t" + node.getRawSignature());
        }
        System.out.println("-> Initiation: ");
        for (IASTExpressionStatement iastExpressionStatement : initiation) {
            System.out.println("\t" + iastExpressionStatement.getRawSignature());
        }
        System.out.println("-> Condition: " + condition.getRawSignature());
        System.out.println("-> Consecution: ");
        for (IASTExpressionStatement cons : consecution) {
            System.out.println("\t" + cons.getRawSignature());
            ConsecutionFormat.formatFarkas(cons);
        }
    }

    public static void main(String[] args) throws IOException {
        String benchmark = "benchmark/invgen/template1/inv_04.c";
        ASTFactory ast = new ASTFactory(benchmark);
        LoopTemplate loopTemplate = LoopTemplate.getLoopElement(ast.getTranslationUnit());
        //loopTemplate.print();
        InvagenXMLInput.printInputToXMLFarkas(loopTemplate,System.out);
    }

}
