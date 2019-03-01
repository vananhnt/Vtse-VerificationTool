package cfg.node;

import cfg.utils.ExpressionHelper;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTStatement;

public class InvariantNode extends PlainNode {

    public InvariantNode(){}
    public InvariantNode(IASTStatement stm) {
        super(stm);
    }
    public InvariantNode(IASTStatement stm, IASTFunctionDefinition func) {
        super(stm, func);
    }
    public void printNode(){
        if (super.getStatement() != null){
            String expresstion_type = super.getStatement().getClass().getSimpleName();
            System.out.print("InvariantNode: ");
            System.out.println(ExpressionHelper.toString(super.getStatement()));
        } else System.out.println(this);

    }
}
