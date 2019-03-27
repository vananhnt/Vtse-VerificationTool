package cfg.build;

import cfg.node.*;
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

public class InvariantCFG extends UnfoldCFG {
    public InvariantCFG() {
    }
    public InvariantCFG(ControlFlowGraph g) {
        generate(g);
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
    private int getNegetive(int operator) { // x < 100 -> x = 100
        if (operator == IASTBinaryExpression.op_lessThan) return IASTBinaryExpression.op_equals;
        else if (operator == IASTBinaryExpression.op_lessEqual) return IASTBinaryExpression.op_greaterThan;
        else if (operator == IASTBinaryExpression.op_greaterEqual) return IASTBinaryExpression.op_lessThan;
        else if (operator == IASTBinaryExpression.op_greaterThan) return IASTBinaryExpression.op_equals;
        else if (operator == IASTBinaryExpression.op_equals) return IASTBinaryExpression.op_notequals;
        return operator;
    }

    private PlainNode getNotCondition(IASTExpression condition) {
        CPPNodeFactory factory = new CPPNodeFactory();
        if (condition instanceof IASTBinaryExpression) {
            IASTBinaryExpression con = (IASTBinaryExpression) condition;
            IASTExpression left = con.getOperand1().copy();
            int operator = con.getOperator();
            IASTExpression right = con.getOperand2().copy();
            IASTBinaryExpression newExp = factory.newBinaryExpression(getNegetive(operator), left, right);
            IASTStatement statement = factory.newExpressionStatement(newExp);
            return new PlainNode(statement);
        }
        return new PlainNode();
    }
    private  CFGNode getBeginNode(CFGNode node) throws Exception {
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
        } else if (node instanceof BeginWhileNode || node instanceof BeginForNode) {
            //add Invariant Node
            if (node.getNext() instanceof DecisionNode) {
                if (((DecisionNode) node.getNext()).getThenNode() instanceof InvariantNode) {
                    InvariantNode invariantNode = (InvariantNode)((DecisionNode) node.getNext()).getThenNode();
                    PlainNode notCondition = getNotCondition(((DecisionNode) node.getNext()).getCondition());
                    invariantNode.setNext(notCondition);
                    ControlFlowGraph invariantGraph = new ControlFlowGraph(invariantNode, notCondition);
                    node.setNext(invariantGraph.getStart());
                    CFGNode endNode = ((BeginNode) node).getEndNode();
                    invariantGraph.getExit().setNext(iterateInvariantNode(endNode));
                }
//                InvariantNode invariantNode = new InvariantNode();
//                ControlFlowGraph invariantGraph = new ControlFlowGraph(invariantNode, invariantNode);
//                node.setNext(invariantGraph.getStart());
//                CFGNode endNode = ((BeginNode) node).getEndNode();
//                invariantGraph.getExit().setNext(iterateInvariantNode(endNode));
            }
        } else if (node instanceof PlainNode) {
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
