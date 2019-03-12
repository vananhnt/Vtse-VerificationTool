package cfg.node;

public class EndConditionNode extends EndNode {

    public EndConditionNode() {
    }

    public EndConditionNode(CFGNode node) {
        super(node);
    }

    public void printNode() {
        System.out.println("EndConditionNode");
    }
}
