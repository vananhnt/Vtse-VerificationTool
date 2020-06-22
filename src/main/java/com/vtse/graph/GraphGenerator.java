package com.vtse.graph;

import com.vtse.cfg.build.UnfoldCFG;
import com.vtse.cfg.build.VtseCFG;
import com.vtse.cfg.index.VariableManager;
import com.vtse.cfg.node.*;
import com.vtse.visualize.PathExecutionVisualize;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphGenerator {
    VtseCFG unfoldCFG;
    FileWriter fileWriter;
    Boolean debug;
    public GraphGenerator(VtseCFG unfoldCFG) throws IOException {
        this.unfoldCFG =unfoldCFG;
        FileWriter fileWriter = new FileWriter("graph.dot");
        this.fileWriter = fileWriter;
        this.debug = false;
    }
    public void writeBegin() throws IOException {
        this.fileWriter.write("strict digraph");
        this.fileWriter.write(" {");
        this.fileWriter.write("\n");
    }
    public void writeEnd() throws IOException{
        this.fileWriter.write("}");
        this.fileWriter.close();
    }
    public void write(String s) throws IOException{
        this.fileWriter.write(s);
    }
    public void writeLabel(CFGNode node) throws IOException{
        String label = "";
        if(node instanceof EndConditionNode){
            label = "EndConditionNode";
        } else if (node instanceof BeginIfNode){
            label = "BeginIfNode";
        } else if (node instanceof BeginForNode){
            label = "BeginForNode";
        } else if (node instanceof BeginWhileNode){
            label = "BeginWhileNode";
        } else if(node instanceof IterationNode){
            if(node.toString().equals("")){
                label = "IterationNode";
            } else {
                label = node.toString();
            }
        } else if (node instanceof EmptyNode){
            label = "EmptyNode";
        } else {
            label = node.toString();
        }
        this.write("\"" + node.toString() + node.hashCode() + "\"" + " [ label=\"" + label + "\" shape=rectangle]");
        this.write(";\n");
        if(node instanceof DecisionNode){
            this.write("\"" + node.toString() + node.hashCode() + "\"" + " [ shape=diamond ]");
            this.write(";\n");
        } else if (node instanceof EndFunctionNode){
            this.write("\"" + node.toString() + node.hashCode() + "\"" + " [ label= \"End\" shape=circle]");
            this.write(";\n");
        }
    }
    public void writeDirected(String a, String b) throws IOException {
        this.write("\"" + a + "\"" + " -> " + "\"" + b + "\"" );
        this.write(";\n");
    }
    public CFGNode writeTwoNode(CFGNode a, CFGNode b) throws IOException{
        if(a == null){
            return null;
        }
        if(b == null){
            return null;
        }
        this.writeDirected(a.toString() + a.hashCode(), b.toString() + b.hashCode());
        this.writeLabel(a);
        this.writeLabel(b);
        return b;
    }
    public void writeDecisionNode(CFGNode decisionNode, CFGNode nextNode, String path) throws IOException {
        this.write("\"" + decisionNode.toString() + decisionNode.hashCode() + "\"" + " -> " + "\"" + nextNode.toString() + nextNode.hashCode() + "\"" );
        this.write("[ label=\"" + path + "\" ]");
        this.write(";\n");
    }
    public void fillColor(List<CFGNode> nodes, Boolean isClose) throws IOException {
        if(!nodes.isEmpty()){
            List<CFGNode> trimmedNodes = new ArrayList<>();
            for (CFGNode node: nodes){
                if(!(node instanceof EmptyNode) && !(node instanceof SyncNode)){
                    trimmedNodes.add(node);
                }
            }
            for (CFGNode node: trimmedNodes){
                if(!(node instanceof EmptyNode) && !(node instanceof SyncNode)) {
                    this.write("\"" + node.toString() + node.hashCode() + "\"" + " [ color=\"red\"]");
                    this.write(";\n");
                }
            }
            for(int i=0;i<trimmedNodes.size()-1;i++){
                CFGNode a = trimmedNodes.get(i);
                CFGNode b = trimmedNodes.get(i+1);
                this.write("\"" + a.toString() + a.hashCode() + "\"" + " -> " + "\"" + b.toString() + b.hashCode() + "\"" + " [ color=\"red\"]" );
                this.write(";\n");
            }
        }
        if(isClose){
            this.writeEnd();
        }
    }
    public void printGraph(Boolean isClose) throws IOException {
        CFGNode start = this.unfoldCFG.getStart();
        this.writeBegin();
        this.print(start, null);
        if(isClose){
            this.writeEnd();
        }
    }
    public CFGNode printPlainNode(CFGNode start, CFGNode nextNode) throws IOException {
        nextNode = this.writeTwoNode(start, nextNode);
        if(this.debug){
            System.out.println("\"" + start.toString() + "\"" + " -> " + "\"" + nextNode.toString() + "\"");
        }
        return nextNode;
    }


    public CFGNode printIfNode(CFGNode startNode) throws IOException {
        DecisionNode decisionNode;
        if (startNode instanceof BeginIfNode){
            decisionNode = ((BeginIfNode)startNode).getDecisionNode();
            this.writeTwoNode(startNode, decisionNode);
        } else {
            decisionNode = (DecisionNode)startNode;
        }

        String formula = decisionNode.getFormula();
        System.out.println(formula);


        CFGNode thenNode = decisionNode.getThenNode();
        CFGNode elseNode = decisionNode.getElseNode();
        CFGNode endConditionNode = decisionNode.getEndNode();
        CFGNode endOfThen = decisionNode.getEndOfThen();
        CFGNode endOfElse = decisionNode.getEndOfElse();

        if(thenNode != null){
            this.writeDecisionNode(decisionNode, thenNode, "then");
            if(this.debug){
                System.out.println("\"" + decisionNode.toString() + "\"" + "->" + "\"" + thenNode.toString() + "\"");
            }
        }
//        if( elseNode != null){
        if( elseNode != null && !(elseNode instanceof EmptyNode)){
            this.writeDecisionNode(decisionNode, elseNode, "else");
            if(this.debug){
                System.out.println("\"" + decisionNode.toString() + "\"" + "->" + "\"" + elseNode.toString() + "\"");
            }
        }
//        this.print(decisionNode, thenNode);
//        this.print(decisionNode, elseNode);
        this.print(thenNode, endOfThen);
        this.print(endOfThen, endConditionNode);
        if(elseNode instanceof EmptyNode){
            this.writeDecisionNode(decisionNode, endConditionNode, "else");
        } else {
            this.print(elseNode, endOfElse);
            this.print(endOfElse, endConditionNode);
        }
        return endConditionNode;
    }

    public CFGNode printForNode(CFGNode start) throws IOException{
        DecisionNode decisionNode = ((BeginForNode)start).getDecisionNode();
        CFGNode nextNode = start.getNext();
        if(nextNode instanceof PlainNode){
            this.writeTwoNode(start, nextNode);
            this.writeTwoNode(nextNode, decisionNode);
        } else {
            this.writeTwoNode(start, decisionNode);
        }
        CFGNode endConditionNode = ((BeginForNode) start).getEndNode();
        CFGNode thenNode = ((DecisionNode)decisionNode).getThenNode();
        CFGNode iterationNode = ((DecisionNode)decisionNode).getEndOfThen();
        this.print(decisionNode, endConditionNode);
        return endConditionNode;
    }

    //print cho vong while
    public CFGNode printWhileNode(CFGNode start) throws IOException{
        DecisionNode decisionNode = ((BeginWhileNode)start).getDecisionNode();
        CFGNode nextNode = start.getNext();
        if(nextNode instanceof PlainNode){
            this.writeTwoNode(start, nextNode);
            this.writeTwoNode(nextNode, decisionNode);
        } else {
            this.writeTwoNode(start, decisionNode);
        }
        CFGNode endConditionNode = ((BeginWhileNode) start).getEndNode();
        CFGNode thenNode = ((DecisionNode)decisionNode).getThenNode();
        CFGNode iterationNode = ((DecisionNode)decisionNode).getEndOfThen();
        this.print(decisionNode, endConditionNode);
        return endConditionNode;
    }
    public CFGNode print(CFGNode start, CFGNode end) throws IOException {
        if(start == null){
            return null;
        }
        if(start == end){
            return start;
        }
        CFGNode nextNode = start.getNext();
        if(nextNode == end){
            this.writeTwoNode(start, end);
            return end;
        }
        if(start instanceof BeginIfNode){
            nextNode= this.printIfNode(start);
            return this.print(nextNode, end);
        } else if(start instanceof BeginForNode){
            nextNode = this.printForNode(start);
            return this.print(nextNode, end);
        }  else if(start instanceof BeginWhileNode){
            nextNode = this.printWhileNode(start);
            return this.print(nextNode, end);
        } else if (start instanceof DecisionNode){
            nextNode = this.printIfNode(start);
            return this.print(nextNode, end);
        }
        else if(start instanceof PlainNode){
            nextNode = this.printPlainNode(start, nextNode);
            return this.print(nextNode, end);
        } else {
            nextNode = this.printPlainNode(start, nextNode);
            return this.print(nextNode, end);
        }
    }
}
