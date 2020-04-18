package com.vtse.graph;

import com.vtse.cfg.build.UnfoldCFG;
import com.vtse.cfg.node.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GraphGenerator {
    UnfoldCFG unfoldCFG;
    FileWriter fileWriter;
    Boolean debug;
    int countIf;
    int countFor;
    public GraphGenerator(UnfoldCFG unfoldCFG) throws IOException {
        this.unfoldCFG =unfoldCFG;
        FileWriter fileWriter = new FileWriter("graph.dot");
        this.fileWriter = fileWriter;
        this.debug = false;
        this.countIf = 0;
        this.countFor = 0;
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
    public void writeTwoNode(CFGNode a, CFGNode b) throws IOException{
        if(a instanceof EmptyNode){
            return;
        }
        while(b instanceof EmptyNode){
            b = b.getNext();
        }
        this.writeDirected(a.toString() + a.hashCode(), b.toString() + b.hashCode());
        this.writeLabel(a);
        this.writeLabel(b);
    }
    public void printGraph() throws IOException {
        CFGNode start = this.unfoldCFG.getStart();
        this.writeBegin();
        this.print(start, null);
        this.writeEnd();
    }
    public CFGNode printPlainNode(CFGNode start, CFGNode nextNode) throws IOException {
        this.writeTwoNode(start, nextNode);
        if(this.debug){
            System.out.println("\"" + start.toString() + "\"" + " -> " + "\"" + nextNode.toString() + "\"");
        }
        return nextNode;
    }


    public CFGNode printIfNode(CFGNode beginIfNode) throws IOException {
        CFGNode decisionNode = ((BeginIfNode)beginIfNode).getDecisionNode();
        this.writeTwoNode(beginIfNode, decisionNode);

        CFGNode thenNode = ((DecisionNode)decisionNode).getThenNode();
        CFGNode elseNode = ((DecisionNode)decisionNode).getElseNode();
        CFGNode endConditionNode = ((DecisionNode)decisionNode).getEndNode();

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

        this.print(thenNode, endConditionNode);
        this.print(elseNode, endConditionNode);
        CFGNode thenNodeEnd = ((DecisionNode)decisionNode).getEndOfThen();
        CFGNode elseNodeEnd = ((DecisionNode)decisionNode).getEndOfElse();
        this.writeTwoNode(thenNodeEnd, endConditionNode);
        this.writeTwoNode(elseNodeEnd, endConditionNode);
        return endConditionNode;
    }

    public CFGNode printForNode(CFGNode start) throws IOException{
        CFGNode decisionNode = ((BeginForNode)start).getDecisionNode();
        this.countFor++;
        CFGNode endNode = ((BeginForNode) start).getEndNode();
        ((EndConditionNode)endNode).setStatement("EndCondition_For_1");
        System.out.println(((DecisionNode)decisionNode).getElseNode().getClass());
        System.out.println(((DecisionNode)decisionNode).getElseNode().toString());
        CFGNode d = decisionNode;
//        for(int i=0;i<4;i++){
//            d = ((DecisionNode) d).getEndOfThen();
//            d = d.getNext();
//        }

        // TODO decisionNode has no thenNode
        while(true){
            System.out.println("iiiiiiiiiiiiiiiiiii");
            CFGNode thenNode = ((DecisionNode)d).getThenNode();
            CFGNode iterationNode = ((DecisionNode)d).getEndOfThen();
            this.print(thenNode, iterationNode);
            d = iterationNode.getNext();
            this.printPlainNode(d, endNode);
            if(!(d instanceof DecisionNode)){
                this.printPlainNode(iterationNode, endNode);
                break;
            }
        }
        return start;
    }
    public void printEndConditionNode(CFGNode start, CFGNode thenNodeEnd, CFGNode elseNodeEnd, EndConditionNode endConditionNode, Boolean elseNodeEmpty) throws IOException{
        if(thenNodeEnd != null){
            this.writeTwoNode(thenNodeEnd, endConditionNode);
        }
        if(elseNodeEmpty){
            this.writeTwoNode(start, endConditionNode);
        } else if(elseNodeEnd != null){
            this.writeTwoNode(elseNodeEnd, endConditionNode);
        }
    }
    public CFGNode fixNode(CFGNode node){
        while(true){
            if(node instanceof BeginIfNode){
                node = node.getNext();
            }  else {
                break;
            }
        }
        return node;
    }
    public CFGNode print(CFGNode start, CFGNode end) throws IOException {
        if(start == null){
            return null;
        }
        CFGNode nextNode = start.getNext();
        if(nextNode == end){
            return start;
        }
        if(start instanceof BeginIfNode){
            nextNode= this.printIfNode(start);
            return this.print(nextNode, end);
        } else if(start instanceof BeginForNode){
            nextNode = this.printForNode(start);
            return this.print(nextNode, end);
        }  else if(start instanceof PlainNode){
            nextNode = this.printPlainNode(start, nextNode);
            return this.print(nextNode, end);
        } else {
            nextNode = this.printPlainNode(start, nextNode);
            return this.print(nextNode, end);
        }
    }
}
