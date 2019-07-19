package com.vtse.cfg.build;

import com.vtse.cfg.node.*;
import com.vtse.cfg.utils.ExpressionHelper;

public class    InvariantCFG extends UnfoldCFG {
    public InvariantCFG() {
    }

    public void generate(ControlFlowGraph otherCfg) {
        try {
            super.setStart(iterateInvariantNode(otherCfg.getStart()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setExit(super.findEnd(super.getStart()));
    }

    private CFGNode getBeginNode(CFGNode node) throws Exception {
        while (!(node instanceof BeginWhileNode || node instanceof BeginForNode)) {
            node = node.getNext();
        }
        return node;
    }
    //iterate in while loop
    private CFGNode iterateInvariantNode(CFGNode node) throws Exception {
        //if (node != null) node.printNode();
        if (node == null) {
            return null;
        } else if (node instanceof BeginWhileNode) {
            //add Invariant Node
            if (node.getNext() instanceof DecisionNode) {

                if (((DecisionNode) node.getNext()).getThenNode() instanceof InvariantNode) {
                    InvariantNode invariantNode = (InvariantNode)((DecisionNode) node.getNext()).getThenNode();
                    PlainNode notCondition = ExpressionHelper.getNotCondition(((DecisionNode) node.getNext()).getCondition());
                    ((DecisionNode) node.getNext()).getEndOfThen().setNext(notCondition);
                    //invariantNode.setNext(notCondition);
                    ControlFlowGraph invariantGraph = new ControlFlowGraph(invariantNode, notCondition);
                    CFGNode endNode = ((BeginNode) node).getEndNode();
                    invariantGraph.getExit().setNext(iterateInvariantNode(endNode));
                }
//                InvariantNode invariantNode = new InvariantNode();
//                ControlFlowGraph invariantGraph = new ControlFlowGraph(invariantNode, invariantNode);
//                node.setNext(invariantGraph.getStart());
//                CFGNode endNode = ((BeginNode) node).getEndNode();
//                invariantGraph.getExit().setNext(iterateInvariantNode(endNode));
            }
        } else if (node instanceof BeginForNode) {
            if (node.getNext().getNext() instanceof DecisionNode) {
                if (((DecisionNode) node.getNext().getNext()).getThenNode() instanceof InvariantNode) {
                    DecisionNode decisionNode = (DecisionNode) node.getNext().getNext();
                    PlainNode initNode = null;
                    if (decisionNode.getThenNode() instanceof PlainNode) {
                        initNode = (PlainNode) decisionNode.getThenNode();
                    }
                    InvariantNode invariantNode = (InvariantNode) decisionNode.getThenNode();
                    initNode.setNext(invariantNode);
                    PlainNode notCondition = ExpressionHelper.getNotCondition(decisionNode.getCondition());
                    invariantNode.setNext(notCondition);
                    ControlFlowGraph invariantGraph = new ControlFlowGraph(initNode, notCondition);
                    CFGNode endNode = ((BeginNode) node).getEndNode();
                    invariantGraph.getExit().setNext(iterateInvariantNode(endNode));
                }
            }
        }
        else if (node instanceof PlainNode) {
            node.setNext(iterateInvariantNode(node.getNext()));
        } else if (node instanceof BeginIfNode) {
            DecisionNode condition = (DecisionNode) node.getNext();
            node.setNext(condition);
            condition.setThenNode(iterateInvariantNode(condition.getThenNode()));
            condition.setElseNode(iterateInvariantNode(condition.getElseNode()));
            ((BeginIfNode) node).getEndNode().setNext(iterateInvariantNode(((BeginIfNode) node).getEndNode().getNext()));
        } else if (node instanceof EmptyNode || node instanceof LabelNode
                || node instanceof UndefinedNode) {
            node.setNext(iterateInvariantNode(node.getNext()));
        } else if (node instanceof EndConditionNode) {
            //node.setNext(iterateNode(node.getNext()));
        } else if (node instanceof GotoNode) {
            ControlFlowGraph gotoGraph = unfoldGoto((GotoNode) node);
            CFGNode endNode = node.getNext();
            node.setNext(gotoGraph.getStart());
            gotoGraph.getExit().setNext(iterateInvariantNode(endNode));
        }
        return node;
    }

}
