package com.vtse.cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTGotoStatement;
import org.eclipse.cdt.core.dom.ast.IASTName;

public class GotoNode extends CFGNode {

    private IASTGotoStatement gotoStatement;
    private LabelNode labelNode;

    public GotoNode() {
    }

    public GotoNode(IASTGotoStatement st) {
        gotoStatement = st;
    }

    public CFGNode getLabelNode() {
        return labelNode;
    }

    public void setLabelNode(LabelNode label) {
        labelNode = label;
    }

    public IASTName getLabelName() {
        IASTName name = gotoStatement.getName();
        return name;
    }

    public void printNode() {
        System.out.println("GotoNode: " + gotoStatement.getName());
    }

}
