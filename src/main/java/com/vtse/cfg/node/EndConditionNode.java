package com.vtse.cfg.node;

public class EndConditionNode extends EndNode {
    public String statement;
    public EndConditionNode() {
        this.statement = "";
    }
    public void setStatement(String statement){
        this.statement = statement;
    }
    public String getStatement(){
        return this.statement;
    }
    public EndConditionNode(CFGNode node) {
        super(node);
    }

    public void printNode() {
        System.out.println("EndConditionNode");
    }

    public String toString(){
        return this.statement;
    }
}
