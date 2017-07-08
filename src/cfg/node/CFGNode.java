package cfg.node;
// abstact ....  ko chua du lieu
import org.eclipse.cdt.core.dom.ast.ASTNodeProperty;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.ExpansionOverlapsBoundaryException;
import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTNodeLocation;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTNode.CopyStyle;
import org.eclipse.cdt.core.parser.IToken;
// Node is parent of all Node in cfg
public abstract class CFGNode {	
	private CFGNode prev;
	private CFGNode next;	
	
	public CFGNode(){		
	}
	
	public CFGNode( CFGNode prev, CFGNode next){
		this.prev = prev;
		this.next = next;		
	}
	
	public CFGNode(CFGNode node){
		this.prev = node.prev;
		this.next = node.next;
	
	}

		public CFGNode getNext() {
		return next;
	}

	public void setNext(CFGNode next) {
		this.next = next;
	}

	public CFGNode getPrev() {
		return prev;
	}

	public void setPrev(CFGNode prev) {
		this.prev = prev;
	}	
// print
		
	public void printNode(){	
		System.out.println(">.< " + this.getClass());
		
	}

}
