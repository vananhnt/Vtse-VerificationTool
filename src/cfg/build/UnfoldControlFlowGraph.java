package cfg.build;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;
import cfg.node.BeginForNode;
import cfg.node.IterationNode;

public class UnfoldControlFlowGraph extends ControlFlowGraph{
	
/*
 * buoc vao vong for count = 0;
 * moi lan vao node lap cout ++
 * neu count  == nLoop => ngung lap reset interationNode(lap)
 * cac loai node thong thuong chi in ra thoi
 */
	public void ucfg(CFGNode start, int nLoop, int count) {
		CFGNode iter = start;
		if (iter == null) return;
		if (iter instanceof DecisionNode) {	
			iter.printNode();
			ucfg(((DecisionNode) iter).getThenNode(), nLoop, count);
			ucfg(((DecisionNode) iter).getElseNode(), nLoop, count);
		} else if ( iter instanceof BeginForNode){
			iter.printNode();
			ucfg(iter.getNext(), nLoop, 0);		
		} else if (iter instanceof IterationNode){ 
			count ++;
			System.out.println(count);
			if (count % nLoop == 0){
				EndConditionNode end = findClose( iter.getNext());
				iter.setNext(end);
				ucfg(iter.getNext(), nLoop, count);
			
			}
			ucfg(iter.getNext(), nLoop, count);		
		}else {
			iter.printNode();
			ucfg(iter.getNext(), nLoop, count);
		}		
	}	

// chi di vao nhanh else de tim node ket thuc vong lap
	public EndConditionNode findClose( CFGNode forBegin){
		CFGNode run = forBegin;
		while ( run != null){
			if (run instanceof EmptyNode){
				return new EndConditionNode(run.getNext());
			}
			run = run.getNext();
		}
		return null;
	}
	
	public static void main(String[] args) {
		int nLoop = 10;
		IASTFunctionDefinition func = (new ASTGenerator("./bai1.cpp")).getFunction(0);
		ControlFlowGraph cfg = (new ControlFlowGraph()).build(func);		
				
		UnfoldControlFlowGraph ucfg = new UnfoldControlFlowGraph();
		ucfg.ucfg(cfg.getStart(), nLoop, 0);
		//cfg.print(cfg.getStart());
	}
	
}
