package invariant;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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