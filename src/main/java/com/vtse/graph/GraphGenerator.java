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
    int countIf = 0;
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
    public void printGraph() throws IOException {
        CFGNode start = this.unfoldCFG.getStart();
        this.writeBegin();
        this.print(start);
        this.writeEnd();
    }
    public CFGNode printPlainNode(CFGNode start, CFGNode nextNode) throws IOException {
        this.write("\"" + start.toString() + "\"" + " -> " + "\"" + nextNode.toString() + "\"");
        if(this.debug){
            System.out.println("\"" + start.toString() + "\"" + " -> " + "\"" + nextNode.toString() + "\"");
        }
        this.write(";\n");
        return nextNode;
    }


    public CFGNode printDecisionNode(CFGNode start) throws IOException {
//        CFGNode thenNode = ((DecisionNode)start).getThenNode();
//        CFGNode elseNode = ((DecisionNode)start).getElseNode();
        this.countIf++;

        CFGNode thenNode = ((DecisionNode)start).getThenNode();
        CFGNode elseNode = ((DecisionNode)start).getElseNode();
        Boolean elseNodeEmpty = false;
        if(elseNode instanceof EmptyNode){
            elseNodeEmpty = true;
        }
        thenNode = this.fixNode(thenNode);
        elseNode = this.fixNode(elseNode);
        if(thenNode != null){
            this.write("\"" + start.toString() + "\"" + "->" + "\"" + thenNode.toString() + "\"");
            this.write(";\n");
            if(this.debug){
                System.out.println("\"" + start.toString() + "\"" + "->" + "\"" + thenNode.toString() + "\"");
            }
        }
        if( !elseNodeEmpty && elseNode != null){
            this.write("\"" + start.toString() + "\"" + "->" + "\"" + elseNode.toString() + "\"");
            this.write(";\n");
            if(this.debug){
                System.out.println("\"" + start.toString() + "\"" + "->" + "\"" + elseNode.toString() + "\"");
            }
        }

        int endConditionNodeIndex = this.countIf;
        CFGNode thenNodeEnd = this.print(thenNode);
        CFGNode elseNodeEnd = this.print(elseNode);
        EndConditionNode endConditionNode =(EndConditionNode)thenNodeEnd.getNext();
        endConditionNode.setStatement("EndConditionNode_" + endConditionNodeIndex);
        this.printEndConditionNode(start, thenNodeEnd, elseNodeEnd, endConditionNode, elseNodeEmpty);
        return this.print(endConditionNode);
    }
    public void printEndConditionNode(CFGNode start, CFGNode thenNodeEnd, CFGNode elseNodeEnd, EndConditionNode endConditionNode, Boolean elseNodeEmpty) throws IOException{
        if(thenNodeEnd != null){
            this.write("\"" + thenNodeEnd.toString() + "\"" + "->" + "\"" + endConditionNode.toString() + "\"");
            this.write(";\n");
        }
        if(elseNodeEmpty){
            this.write("\"" + start.toString() + "\"" + "->" + "\"" + endConditionNode.toString() + "\"");
            this.write(";\n");
        } else if(elseNodeEnd != null){
            this.write("\"" + elseNodeEnd.toString() + "\"" + "->" + "\"" + endConditionNode.toString() + "\"");
            this.write(";\n");
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
    public CFGNode print(CFGNode start) throws IOException {
        if(start == null){
            return null;
        }
        CFGNode nextNode = start.getNext();
        if(nextNode instanceof EndConditionNode){
            return start;
        } else if(nextNode != null){
            if(nextNode instanceof BeginIfNode){
                nextNode = nextNode.getNext();
            }
            if(start instanceof  PlainNode){
                nextNode = this.printPlainNode(start, nextNode);
                if(this.debug){
                    System.out.println(nextNode.getClass());
                }
                return this.print(nextNode);

            } else if(start instanceof DecisionNode){

                return this.printDecisionNode(start);

            } else {
                nextNode = this.printPlainNode(start, nextNode);
                if(this.debug){
                    System.out.println(nextNode.getClass());
                }
                return this.print(nextNode);
            }
        }
        return this.print(nextNode);
    }
}
