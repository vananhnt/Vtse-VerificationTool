package com.vtse.graph;

import com.vtse.cfg.build.UnfoldCFG;
import com.vtse.cfg.build.VtseCFG;
import com.vtse.cfg.index.VariableManager;
import com.vtse.cfg.node.*;
import com.vtse.visualize.PathExecutionVisualize;
import org.antlr.v4.runtime.misc.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphGenerator {
    VtseCFG unfoldCFG;
    FileWriter fileWriter;
    Map<String, String> listParameters;
    Boolean debug;
    Boolean detail;
    Boolean isShowSyncNode;
    Boolean isColored;
    public GraphGenerator(VtseCFG unfoldCFG, Map<String, String> listParameters) throws IOException {
        this.unfoldCFG =unfoldCFG;
        FileWriter fileWriter = new FileWriter("graph.dot");
        this.fileWriter = fileWriter;
        this.listParameters = listParameters;
        this.detail = true;
        this.debug = false;
        this.isShowSyncNode = true;
        this.isColored = false;
    }
    public String getDetail(String label){
        String valueLabel = label;

        Pattern parameterPattern = Pattern.compile("[\\w|\\d|-]+");
        Matcher matcher = parameterPattern.matcher(label);
        Boolean isShorter = false;
        while(matcher.find()){
            String parameter = matcher.group(0);
            String value = this.listParameters.get(parameter);
            if(value != null){
                valueLabel = valueLabel.replaceAll(parameter, value);
                isShorter = true;
            }
        }
        if(isShorter) {
            return label + "\n" + valueLabel;
        } else {
            return label;
        }
    }
    public void writeBegin() throws IOException {
        this.fileWriter.write("strict digraph");
        this.fileWriter.write(" {");
        this.fileWriter.write("\n");
        this.fileWriter.write("splines=ortho;");
        this.fileWriter.write("\n");
    }
    public void writeStartNode() throws IOException{
        CFGNode start = this.unfoldCFG.getStart();
        this.write("Start[label=\"Start\" shape=circle]");
        this.write(";\n");
        this.write("\"" + "Start" + "\"" + " -> " + "\"" + start.toString() + start.hashCode() + "\"");
        this.write(";\n");
        if(this.getColored()){
            this.write("Start[label=\"Start\" shape=circle color=red]");
            this.write(";\n");
            this.write("\"" + "Start" + "\"" + " -> " + "\"" + start.toString() + start.hashCode() + "\"" + " [ color=\"red\"]");
            this.write(";\n");
        }
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
        if(this.detail){
            label = this.getDetail(label);
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
    public void fillColor(List<CFGNode> nodes, Boolean isColored, Boolean isClose) throws IOException {
        this.setColored(isColored);
        this.writeStartNode();
        if(isColored){
            if(!nodes.isEmpty()){
                List<CFGNode> trimmedNodes = new ArrayList<>();
                if(this.isShowSyncNode){
                    trimmedNodes = nodes;
                } else {
                    for (CFGNode node: nodes){
                        if(!(node instanceof EmptyNode) && !(node instanceof SyncNode)){
                            trimmedNodes.add(node);
                        }
                    }
                }
                for (CFGNode node: trimmedNodes){
                    this.write("\"" + node.toString() + node.hashCode() + "\"" + " [ color=\"red\"]");
                    this.write(";\n");
                }
                for(int i=0;i<trimmedNodes.size()-1;i++){
                    CFGNode a = trimmedNodes.get(i);
                    CFGNode b = trimmedNodes.get(i+1);
                    this.write("\"" + a.toString() + a.hashCode() + "\"" + " -> " + "\"" + b.toString() + b.hashCode() + "\"" + " [ color=\"red\"]" );
                    this.write(";\n");
                }
            }
        }
        if(isClose){
            this.writeEnd();
        }
    }
    public void printGraph(Boolean isClose) throws IOException {
        CFGNode start = this.unfoldCFG.getStart();
        this.writeBegin();
        this.writeStartNode();
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
//        System.out.println(formula);


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
        this.print(thenNode, endOfThen);
        this.print(endOfThen, endConditionNode);

        if(this.isShowSyncNode){
            if( elseNode != null){
//        if( elseNode != null && !(elseNode instanceof EmptyNode)){
                this.writeDecisionNode(decisionNode, elseNode, "else");
                if(this.debug){
                    System.out.println("\"" + decisionNode.toString() + "\"" + "->" + "\"" + elseNode.toString() + "\"");
                }
            }
            this.print(elseNode, endOfElse);
            this.print(endOfElse, endConditionNode);
        } else {
            if( elseNode != null && !(elseNode instanceof EmptyNode)) {
                this.writeDecisionNode(decisionNode, elseNode, "else");
                if (this.debug) {
                    System.out.println("\"" + decisionNode.toString() + "\"" + "->" + "\"" + elseNode.toString() + "\"");
                }
            }
            if(elseNode instanceof EmptyNode){
                this.writeDecisionNode(decisionNode, endConditionNode, "else");
            } else {
                this.print(elseNode, endOfElse);
                this.print(endOfElse, endConditionNode);
            }
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

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public Boolean getDetail() {
        return detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public Boolean getShowSyncNode() {
        return isShowSyncNode;
    }

    public void setShowSyncNode(Boolean showSyncNode) {
        isShowSyncNode = showSyncNode;
    }

    public Boolean getColored() {
        return isColored;
    }

    public void setColored(Boolean colored) {
        isColored = colored;
    }
}
