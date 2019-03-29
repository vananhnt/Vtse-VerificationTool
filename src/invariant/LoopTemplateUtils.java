package invariant;

import org.eclipse.cdt.core.dom.ast.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class LoopTemplateUtils {

    public static IASTNode[] getFunctionBodyElement(IASTTranslationUnit ast) {
        return getFunctionBodyElementUtils(ast.getTranslationUnit());
    }
    private static String getBenchmark(String props) throws IOException {
        String path = System.getProperty("user.dir") + "/src/invariant/benchmark.properties";
        FileInputStream is = new FileInputStream(new File(path));
        Properties benchmarkProps = new Properties();
        benchmarkProps.load(is);
        return benchmarkProps.getProperty("invgen");
    }

    private static IASTNode[] getFunctionBodyElementUtils(IASTNode ast) {
        IASTNode[] children = ast.getChildren();
        for (IASTNode iastNode : children) {
            //get element in the body
            if (iastNode instanceof IASTFunctionDefinition) {
                IASTNode[] child_Function = iastNode.getChildren();
                IASTCompoundStatement compoundStatement = null;
                for (IASTNode iChildFunc : child_Function) {
                    if (iChildFunc instanceof IASTCompoundStatement) {
                        compoundStatement = (IASTCompoundStatement) iChildFunc;
                    }
                }
                List<IASTNode> child_Compound = new ArrayList<>(Arrays.asList(compoundStatement.getChildren()));
                IASTNode[] params = ((IASTFunctionDefinition) iastNode).getDeclarator().getChildren();
                for (IASTNode param : params) {
                    if (param instanceof IASTParameterDeclaration) {
                        child_Compound.add(param);
                    }
                }
                IASTNode[] result = new IASTNode[child_Compound.size()];
                return child_Compound.toArray(result);
            } else {
                return getFunctionBodyElementUtils(iastNode);
            }
        }
        return null;
    }

    public static void printTree(IASTNode node, int index) {
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
}