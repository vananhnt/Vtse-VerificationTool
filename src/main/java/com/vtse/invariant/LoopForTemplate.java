package com.vtse.invariant;

import com.vtse.cfg.utils.UnaryToBinaryExpression;

import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import java.util.ArrayList;
import java.util.List;

public class LoopForTemplate extends LoopTemplate {
    private List<IASTExpressionStatement> consecution = new ArrayList<>();

    public List<IASTExpressionStatement> getConsecution() {
        return consecution;
    }

    public void setConsecution(List<IASTExpressionStatement> consecutions) {
        this.consecution = consecutions;
    }

    public static LoopForTemplate getLoopElement(IASTTranslationUnit iastTranslationUnit) {
        return getLoopElement(LoopTemplateUtils.getFunctionBodyElement(iastTranslationUnit));
    }

    private static LoopForTemplate getLoopElement(IASTNode[] bodyElement) {
        LoopForTemplate loopTemplate = new LoopForTemplate();
        List<IASTExpressionStatement> init = new ArrayList<>();
        List<IASTExpressionStatement> consecutions = new ArrayList<>();
        List<IASTName> var = new ArrayList<>();

        if (bodyElement == null) return loopTemplate;

        for (IASTNode node : bodyElement) {
            //get For statement
            if (node instanceof IASTForStatement) {
                IASTForStatement forStatement = (IASTForStatement)node;
                //get initiation
                if (forStatement.getInitializerStatement() instanceof IASTExpressionStatement)
                init.add((IASTExpressionStatement)forStatement.getInitializerStatement());
                IASTExpression condition = forStatement.getConditionExpression();
                loopTemplate.setLoopCondition(condition);
                //get consecution, body first, i++ later
                IASTNode[] forBodyElements = forStatement.getBody().getChildren();
                for (IASTNode ex : forBodyElements) {
                    if (ex instanceof IASTExpressionStatement) {
                        consecutions.add((IASTExpressionStatement) ex);
                    }
                }
                //add iteration into consecution
                consecutions.add(getIterationStatement(forStatement));
                loopTemplate.setConsecution(consecutions);
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
            } else if (node instanceof IASTParameterDeclaration) {
                IASTParameterDeclaration param = (IASTParameterDeclaration) node;
                IASTDeclarator declarator = param.getDeclarator();
                var.add(declarator.getName());
            }
        }
        loopTemplate.setInitiation(init);
        loopTemplate.setVariables(var);
        return loopTemplate;
    }
    private static IASTExpressionStatement getIterationStatement(IASTForStatement forStatement) {
        CPPNodeFactory factory = new CPPNodeFactory();
        IASTExpression iteration = forStatement.getIterationExpression();
        IASTExpressionStatement statement = null;
        if (iteration instanceof IASTBinaryExpression) {
            statement = factory.newExpressionStatement(iteration);
        } else if (iteration instanceof IASTUnaryExpression) {
            IASTBinaryExpression newExpression = UnaryToBinaryExpression.unaryToBinary((IASTUnaryExpression) iteration);
            statement = factory.newExpressionStatement(newExpression);
        }
        return statement;
    }
    public void print() {
        super.print();
        System.out.println("-> Consecution: ");
        for (IASTExpressionStatement cons : consecution) {
            System.out.println("\t" + TransitionFormat.formatFarkas(cons));
        }
    }
}
