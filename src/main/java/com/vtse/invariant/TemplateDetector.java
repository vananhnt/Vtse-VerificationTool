package com.vtse.invariant;

import com.vtse.cfg.build.ASTFactory;
import org.eclipse.cdt.core.dom.ast.*;

public class TemplateDetector {

    public static int detect(IASTTranslationUnit iastTranslationUnit) {
        IASTNode[] nodes = LoopTemplateUtils.getFunctionBodyElement(iastTranslationUnit);
        if (nodes == null) return -1;
        for (IASTNode node : nodes) {
            if (node instanceof IASTWhileStatement) {
                IASTWhileStatement whileStatement = (IASTWhileStatement) node;
                IASTNode[] bodyElements = whileStatement.getBody().getChildren();
                for (IASTNode bodyElement : bodyElements) {
                    if (bodyElement instanceof IASTIfStatement) {
                        return LoopTemplate.IFELSE_WHILE_TEMPLATE;
                    } else if (bodyElement instanceof IASTWhileStatement) {
                        return -1;
                    }
                }
                return LoopTemplate.MONO_WHILE_TEMPLATE;
            } else if (node instanceof IASTForStatement) {
                return LoopTemplate.FOR_TEMPLATE;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String benchmark = "benchmark/invgen/template2/loops_crafted/Mono5_1.c";
        ASTFactory ast = new ASTFactory(benchmark);
        System.out.println(TemplateDetector.detect(ast.getTranslationUnit()));
    }
}
