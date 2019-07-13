package com.vtse.cfg.node;

import com.vtse.cfg.index.VariableManager;

import java.io.PrintStream;
import java.util.ArrayList;

public abstract class CFGNode {
    protected CFGNode next;
    private boolean vistited;

    public CFGNode() {
    }

    public CFGNode(CFGNode next) {
        this.vistited = false;
        this.next = next;
    }

    public CFGNode getNext() {
        return next;
    }

    public void setNext(CFGNode next) {
        this.next = next;
    }

    public ArrayList<CFGNode> adjacent() {
        ArrayList<CFGNode> adj = new ArrayList<>();
        adj.add(next);
        return adj;
    }

    public void printNode() {
        if (this != null) System.out.println(this.getClass());
    }

    public String toString() {
        return "";
    }

    public void index(VariableManager vm) {
    }

    public boolean isVistited() {
        return vistited;
    }

    public void setVistited(boolean vistited) {
        this.vistited = vistited;
    }

    public String getFormula() {
        return null;
    }

    public String getInfixFormula() {
        return null;
    }

    public void printFormular(PrintStream ps) {
        ps.println(getFormula());
    }

    public void printInfixFormular(PrintStream ps) {
        ps.println(getInfixFormula());
    }

}
