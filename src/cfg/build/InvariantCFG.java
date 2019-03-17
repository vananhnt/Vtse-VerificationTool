package cfg.build;

import cfg.node.*;

public class InvariantCFG extends UnfoldCFG {
    int numberOfInvariant = 0;
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

    private CFGNode iterateInvariantNode(CFGNode node) throws Exception {
        //if (node != null) node.printNode();
        if (node == null) {
            return null;
        } else if (node instanceof BeginWhileNode || node instanceof BeginForNode) {
            //add Invariant Node
            if (node.getNext() instanceof DecisionNode) {
                if (((DecisionNode) node.getNext()).getThenNode() instanceof InvariantNode) {
                    InvariantNode invariantNode = (InvariantNode) ((DecisionNode) node.getNext()).getThenNode();
                    ControlFlowGraph invariantGraph = new ControlFlowGraph(invariantNode, invariantNode);
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
