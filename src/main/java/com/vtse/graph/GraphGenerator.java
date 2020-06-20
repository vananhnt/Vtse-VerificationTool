package com.vtse.graph;

import com.vtse.cfg.build.UnfoldCFG;
import com.vtse.cfg.index.VariableManager;
import com.vtse.cfg.node.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GraphGenerator {
    UnfoldCFG unfoldCFG;
    FileWriter fileWriter;
    Boolean debug;
    public GraphGenerator(UnfoldCFG unfoldCFG) throws IOException {
        this.unfoldCFG =unfoldCFG;
        FileWriter fileWriter = new FileWriter("graph.dot");
        this.fileWriter = fileWriter;
        this.debug = false;
    }
    public void writeBegin() throws IOException {
        this.fileWriter.write("digraph");
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
        } else {
            label = node.toString();
        }
        this.write("\"" + node.toString() + node.hashCode() + "\"" + " [ label=\"" + label + "\"]");
        this.write(";\n");
    }
    public void writeDirected(String a, String b) throws IOException {
        this.write("\"" + a + "\"" + " -> " + "\"" + b + "\"" );
        this.write(";\n");
    }
    public CFGNode writeTwoNode(CFGNode a, CFGNode b) throws IOException{
        if(a instanceof EmptyNode){
            return null;
        }
        if(a == null){
            return null;
        }
        if(b == null){
            return null;
        }
        while(b instanceof EmptyNode){
            b = b.getNext();
        }
        this.writeDirected(a.toString() + a.hashCode(), b.toString() + b.hashCode());
        this.writeLabel(a);
        this.writeLabel(b);
        return b;
    }
    public void printGraph() throws IOException {
        CFGNode start = this.unfoldCFG.getStart();
        this.writeBegin();
        this.print(start, null);
        this.writeEnd();
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
            this.writeTwoNode(decisionNode, thenNode);
            if(this.debug){
                System.out.println("\"" + decisionNode.toString() + "\"" + "->" + "\"" + thenNode.toString() + "\"");
            }
        }
        if( elseNode != null){
            this.writeTwoNode(decisionNode, elseNode);
            if(this.debug){
                System.out.println("\"" + decisionNode.toString() + "\"" + "->" + "\"" + elseNode.toString() + "\"");
            }
        }
        this.print(thenNode, endOfThen);
        this.print(elseNode, endOfElse);
        this.writeTwoNode(endOfThen, endConditionNode);
        if(endOfElse != null){
            this.writeTwoNode(endOfElse, endConditionNode);
        } else {
            this.writeTwoNode(decisionNode, endConditionNode);
        }
        return endConditionNode;
    }

    public CFGNode printForNode(CFGNode start) throws IOException{
        DecisionNode decisionNode = ((BeginForNode)start).getDecisionNode();
        this.writeTwoNode(start, decisionNode);
        CFGNode endConditionNode = ((BeginForNode) start).getEndNode();
        CFGNode d = decisionNode;
        CFGNode thenNode = ((DecisionNode)d).getThenNode();
        CFGNode iterationNode = ((DecisionNode)d).getEndOfThen();
        this.writeTwoNode(d, thenNode);
        this.writeTwoNode(d, endConditionNode);
        this.print(thenNode, iterationNode);
        this.print(iterationNode, endConditionNode);
        return endConditionNode;
    }

    //print cho vong while
    public CFGNode printWhileNode(CFGNode start) throws IOException{
        DecisionNode decisionNode = ((BeginWhileNode)start).getDecisionNode();
        this.writeTwoNode(start, decisionNode);
        CFGNode endConditionNode = ((BeginWhileNode) start).getEndNode();
        CFGNode d = decisionNode;
        CFGNode thenNode = ((DecisionNode)d).getThenNode();
        CFGNode iterationNode = ((DecisionNode)d).getEndOfThen();

        this.writeTwoNode(d, thenNode);
        this.writeTwoNode(d, endConditionNode);
        this.print(thenNode, iterationNode);
        this.print(iterationNode, endConditionNode);
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
//            System.out.println("----------------");
//            System.out.println(start.getClass());
//            System.out.println(start.toString());
//            System.out.println(nextNode.getClass());
//            System.out.println(nextNode.toString());
//            System.out.println("-----------------");
            nextNode = this.printPlainNode(start, nextNode);
            return this.print(nextNode, end);
        }
    }
}
