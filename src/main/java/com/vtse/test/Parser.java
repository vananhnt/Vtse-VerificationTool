package com.vtse.test;

import com.vtse.cfg.build.ASTFactory;
import com.vtse.cfg.build.VtseCFG;
import org.eclipse.cdt.core.dom.ast.ExpansionOverlapsBoundaryException;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTranslationUnit;

public class Parser {
    public static void main(String[] args) throws Exception {
        String fileLocation = "./benchmark/float-cdfpl-func/newton_1_1_true_unreach_call.cc";
        ASTFactory ast = new ASTFactory(fileLocation);
        IASTFunctionDefinition func = ast.getFunction(0);
        VtseCFG cfg = new VtseCFG(func);
        cfg.unfold();
        cfg.index();

        //cfg.printMeta();

        //printTree(func, 1);
    }

    @SuppressWarnings("unused")
    private static void printTree(IASTNode node, int index) {
        IASTNode[] children = node.getChildren();

        boolean printContents = true;

        if ((node instanceof CPPASTTranslationUnit)) {
            printContents = false;
        }

        String offset = "";
        try {
            offset = node.getSyntax() != null ? " (offset: " + node.getFileLocation().getNodeOffset() + "," + node.getFileLocation().getNodeLength() + ")" : "";
            printContents = node.getFileLocation().getNodeLength() < 30;
        } catch (ExpansionOverlapsBoundaryException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            offset = "UnsupportedOperationException";
        }

        System.out.println(String.format(new StringBuilder("%1$").append(index * 2).append("s").toString(), new Object[]{"-"}) + node.getClass().getSimpleName() + offset + " -> " + (printContents ? node.getRawSignature().replaceAll("\n", " \\ ") : node.getRawSignature().subSequence(0, 5)));

        for (IASTNode iastNode : children)
            printTree(iastNode, index + 2);
    }

}

