package com.vtse.cfg.build;

import com.vtse.cfg.node.*;
import com.vtse.cfg.utils.ExpressionHelper;
import com.vtse.cfg.utils.MyCloner;
import org.eclipse.cdt.core.dom.ast.IASTExpression;

public class GotoCFG {
    private CFGNode start;
    private CFGNode exit;

    public CFGNode getStart() {
        return start;
    }

    public void setStart(CFGNode start) {
        this.start = start;
    }
    public void setExit(CFGNode exit) {
        this.exit = exit;
    }

    public void generate(ControlFlowGraph otherCfg) {
        try {
            start = iterateNode(otherCfg.getStart());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setExit(findEnd(start));
    }

    protected CFGNode findEnd(CFGNode start) {
        CFGNode iter = start;
        while (iter.getNext() != null) {
            iter = iter.getNext();
        }
        return iter;
    }
    /**
     * return node before end condition node
     * @param label label node
     * @return end function node of this label
     */
    private CFGNode findEndFunctionNode(LabelNode label) {
        CFGNode iter = label;
        while (iter != null && iter.getNext() != null) {
            if (iter.getNext() instanceof EndFunctionNode) {
                if (((EndFunctionNode) iter.getNext()).getFunction().equals(label.getFunction())) {
                    return iter;
                }
            }
            iter = iter.getNext();
        }
        return iter;
    }

    /* Chua xoa Goto va Label Node */
    protected ControlFlowGraph unfoldGoto(GotoNode node) {
        // TODO Auto-generated method stub
        EmptyNode emp = new EmptyNode();
        CFGNode endNode = findEndFunctionNode((LabelNode) ((GotoNode) node).getLabelNode());
        if (endNode == null) return new ControlFlowGraph(emp, emp);
        //copy unfold từ label to endNode

        ControlFlowGraph tmpGraph = null;
        try {
            tmpGraph = new ControlFlowGraph(iterateNode(node.getLabelNode().getNext()), endNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ControlFlowGraph testGraph = MyCloner.clone(tmpGraph);
        testGraph.getExit().setNext(new EmptyNode());
        return testGraph;
    }

    /**
     * Ham duyet graph, vua duyet vua unfold
     * @param node
     * @return
     * @throws Exception
     */
    private CFGNode iterateNode(CFGNode node) throws Exception {
        //if (node != null) node.printNode();
        if (node == null) {
            return null;
        } else if (node instanceof PlainNode || node instanceof FunctionCallNode) {
            node.setNext(iterateNode(node.getNext()));
        } else if (node instanceof EmptyNode
                || node instanceof UndefinedNode || node instanceof BeginFunctionNode) {
            node.setNext(iterateNode(node.getNext()));
        } else if (node instanceof EndConditionNode) {

        } else if (node instanceof BeginWhileNode) {
            DecisionNode condition = ((BeginWhileNode) node).getDecisionNode();
            node.setNext(condition);
            condition.setThenNode(iterateNode(condition.getThenNode()));
            condition.setElseNode(iterateNode(condition.getElseNode()));
            ((BeginWhileNode) node).getEndNode().setNext(iterateNode(((BeginWhileNode) node).getEndNode().getNext()));

        } else if (node instanceof BeginForNode) {
            DecisionNode condition = ((BeginForNode) node).getDecisionNode();
            node.setNext(condition);
            condition.setThenNode(iterateNode(condition.getThenNode()));
            condition.setElseNode(iterateNode(condition.getElseNode()));
            ((BeginForNode) node).getEndNode().setNext(iterateNode(((BeginForNode) node).getEndNode().getNext()));
        }
        else if (node instanceof BeginIfNode) {
            DecisionNode condition = (DecisionNode) node.getNext();
            node.setNext(condition);
            condition.setThenNode(iterateNode(condition.getThenNode()));
            condition.setElseNode(iterateNode(condition.getElseNode()));
            ((BeginIfNode) node).getEndNode().setNext(iterateNode(((BeginIfNode) node).getEndNode().getNext()));
        }
		else if (node instanceof GotoNode) {
			ControlFlowGraph gotoGraph = unfoldGoto((GotoNode)node);
			//CFGNode endNode = node.getNext();
			CFGNode endNode = gotoGraph.getExit().getNext();
			node.setNext(gotoGraph.getStart());
			//node.setNext(new EmptyNode());
		}
        return node;
    }


}
