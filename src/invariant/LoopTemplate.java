package invariant;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTName;

import java.util.List;

public class LoopTemplate {
    private List<IASTName> variables;
    private List<IASTExpressionStatement> initiation;
    private IASTExpression loopCondition;

    public LoopTemplate(){}

    public LoopTemplate(List<IASTName> variables, List<IASTExpressionStatement> initiation, IASTExpression loopCondition) {
        this.variables = variables;
        this.initiation = initiation;
        this.loopCondition = loopCondition;
    }

    public List<IASTName> getVariables() {
        return variables;
    }

    public void setVariables(List<IASTName> variables) {
        this.variables = variables;
    }

    public List<IASTExpressionStatement> getInitiation() {
        return initiation;
    }

    public void setInitiation(List<IASTExpressionStatement> initiation) {
        this.initiation = initiation;
    }

    public IASTExpression getLoopCondition() {
        return loopCondition;
    }

    public void setLoopCondition(IASTExpression loopCondition) {
        this.loopCondition = loopCondition;
    }
}