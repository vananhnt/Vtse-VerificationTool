package cfg.node;

import org.eclipse.cdt.core.dom.ast.IASTStatement;

import cfg.utils.ExpressionHelper;

/**
 * @author va
 *
 */
public class ReturnNode extends PlainNode
{
	
	public ReturnNode(IASTStatement statement) {
		super(ExpressionHelper.toString(statement));
	}

}
