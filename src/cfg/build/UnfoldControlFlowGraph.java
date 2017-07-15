package cfg.build;


import java.util.concurrent.locks.AbstractQueuedLongSynchronizer.ConditionObject;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import cfg.node.BeginNode;
import cfg.node.CFGNode;
import cfg.node.DecisionNode;
import cfg.node.EmptyNode;
import cfg.node.EndConditionNode;

import cfg.node.ForBeginningNode;
import cfg.node.IfBeginningNode;

import cfg.node.IterationNode;
import cfg.node.PlainNode;

public class UnfoldControlFlowGraph extends ControlFlowGraph{
	private int nLoop;
	public UnfoldControlFlowGraph(){
		super();
	}
	public UnfoldControlFlowGraph( CFGNode start, CFGNode exit){
		super(start, exit);
	}
	
	public UnfoldControlFlowGraph( int nLoop){
		super();
		this.nLoop = nLoop;
	}
	
	public UnfoldControlFlowGraph createUCFG( CFGNode start, CFGNode end){
		UnfoldControlFlowGraph ucfg = new UnfoldControlFlowGraph();
		UnfoldControlFlowGraph subUCFG;
		CFGNode run = start;
		while ( (run != null)  ){
			if (run instanceof ForBeginningNode){
				subUCFG = createForUCFG( (ForBeginningNode)run, ((ForBeginningNode) run).getEndNode());
				run = ((ForBeginningNode) run).getEndNode().getNext();
			} else if ( run instanceof IfBeginningNode){
				//
				subUCFG = createIfUCFG((IfBeginningNode)run, ((IfBeginningNode) run).getEndNode());
				run = ((IfBeginningNode) run).getEndNode().getNext();
			}else {
				subUCFG = new UnfoldControlFlowGraph(run, run);
				run = run.getNext();
			}
			ucfg.concat(subUCFG);
		}		
		return ucfg;
	}

	public UnfoldControlFlowGraph createForUCFG( ForBeginningNode start, CFGNode end){		
		ForBeginningNode run = start;
		// get decision 
		DecisionNode dec;
		if ( run.getNext() instanceof DecisionNode){
			dec = (DecisionNode) run.getNext();
		} else {
			dec = (DecisionNode) (run.getNext().getNext());
		}
		
		// get then/else
		UnfoldControlFlowGraph thenClause = createUCFG(dec.getThenNode(), end);
		UnfoldControlFlowGraph elseClause = createUCFG(dec.getElseNode(), end);
			// loop
		CFGNode beginThen = createLoop(thenClause.getStart(), dec, end);		
		dec.setThenNode(beginThen);
		dec.setElseNode(elseClause.getStart());
		
		return new UnfoldControlFlowGraph(run, end);		
	}
	
	public UnfoldControlFlowGraph createIfUCFG( IfBeginningNode start, CFGNode end){				
		IfBeginningNode run = start;
		DecisionNode dec = (DecisionNode) run.getNext();
		UnfoldControlFlowGraph thenClause = createUCFG(dec.getThenNode(), end);
		UnfoldControlFlowGraph elseClause = createUCFG(dec.getElseNode(), end);
		dec.setThenNode(thenClause.getStart());
		dec.setElseNode(elseClause.getStart());
		return new UnfoldControlFlowGraph(run, end);
	}

/*
 * lap theo yeu cau
 * INPUT start, end
 * return new Node  sau khi lap
 */
	public CFGNode createLoop(CFGNode start, DecisionNode dec, CFGNode end){
		
		CFGNode run = start;		
		int count = 0;		
		while (count < this.nLoop && (run != null) && (run != end)){			
			
			if ( run instanceof BeginNode){				
				run = ((BeginNode) run).getEndNode();
			} else if ( run instanceof DecisionNode){
				run = ((DecisionNode) run).getThenNode();
			} else if( run instanceof IterationNode){
				count ++;	
				
				if ( count == this.nLoop){
					run.setNext(end);
				}else{
					// tao ban sao 
					DecisionNode decision = (DecisionNode) copyGraph(dec, end).getStart();					
					run.setNext(decision);
				}
				run = run.getNext();
			} else{				
				run = run.getNext();
			}
		}
		return start;
	}
	
	/*	TODO
	 * tao ban sao cua nhanh bat ky tu start -> end 
	 */
	public ControlFlowGraph copy( CFGNode start, CFGNode end){
		//TODO
		CFGNode run = start;
		ControlFlowGraph graph = new ControlFlowGraph();
		ControlFlowGraph sub = null;
		//if ( run.equals(end)) return new ControlFlowGraph(end, end);
		while ( (run != null) && (run != end)){			
			if ( run instanceof BeginNode){
				//System.out.println("begin");
				sub = new ControlFlowGraph();
				sub.setStart(run);
				ControlFlowGraph body = copy( run.getNext(), ((BeginNode) run).getEndNode());
				sub.getStart().setNext( body.getStart() );
				sub.setExit(body.getExit());
				run = ((BeginNode) run).getEndNode().getNext();
				
			} else if ( run instanceof DecisionNode){
				//System.out.println("decision");
				DecisionNode dec = new DecisionNode((DecisionNode)run);
				ControlFlowGraph thenClause = copy( ((DecisionNode) run).getThenNode(), end);
				ControlFlowGraph elseClause = copy( ((DecisionNode) run).getElseNode(), end);
				
				dec.setThenNode(thenClause.getStart());
				dec.setElseNode(elseClause.getStart());				
				sub = new ControlFlowGraph(dec, end);
				run = elseClause.getExit().getNext();
				
			} else if ( run instanceof IterationNode){
				//System.out.println("iteration");
				IterationNode iter = new IterationNode( (IterationNode) run);
				
				iter.setNext(end);
				sub = new ControlFlowGraph(iter, iter);	
				break;
				
			} else if (run instanceof EndConditionNode){
				//System.out.println("endCondition");
				sub = new ControlFlowGraph(run, run);
				
			} else {
				//System.out.println("Plain");
				sub = new ControlFlowGraph( run, run);				
				run = run.getNext();
			}
			graph.concat(sub);			
		}		
		return graph;
	}
	
//TODO	
	private ControlFlowGraph copyGraph(CFGNode start, CFGNode exit) {
		CFGNode newStart = start;
		if (start == null) return new ControlFlowGraph(null, null);
		if (start == exit ) {
			//return null;
		} else if (start instanceof ForBeginningNode) {
			newStart = new ForBeginningNode();	
			newStart.setNext(copyGraph(start.getNext(), ((ForBeginningNode) start).getEndNode()).getStart());
			//newStart.setNext(unfold(start).getStart());
		} 
		else if (start instanceof IterationNode) {
			newStart = new IterationNode();
			newStart.setNext( exit);
			
		} else if (start instanceof DecisionNode) {
			newStart = new DecisionNode();
			((DecisionNode) newStart).setCondition(((DecisionNode) start).getCondition());
			ControlFlowGraph thenClause = copyGraph(((DecisionNode) start).getThenNode(), exit);			
			((DecisionNode) newStart).setThenNode(thenClause.getStart());
			
			ControlFlowGraph elseClause = copyGraph(((DecisionNode) start).getElseNode(), exit);
			((DecisionNode) newStart).setElseNode(elseClause.getStart());
			
			
		} else if (start instanceof PlainNode) {
			newStart = new PlainNode(((PlainNode) start).getStatement());
			newStart.setNext(copyGraph(start.getNext(), exit).getStart());	
		
		} else if (start instanceof EndConditionNode) {
			start.printNode();
			newStart = new EndConditionNode( (EndConditionNode) start);
			newStart.setNext(copyGraph(start.getNext(), exit).getStart());
				
		} else if (start instanceof IfBeginningNode) {
			newStart = new IfBeginningNode();
			newStart.setNext(copyGraph(start.getNext(), ((IfBeginningNode) start).getEndNode()).getStart());
		
		}  else {
			newStart = new EmptyNode();
			newStart.setNext(copyGraph(start.getNext(), exit).getStart());	
		}
		return new ControlFlowGraph(newStart, exit);
	}

	
	
// overiding print
	public void print(CFGNode start) {
		CFGNode iter = start;
		if (iter == null) return;
			}
		} else if (iter instanceof DecisionNode) {
			
			iter.printNode();
			System.out.print("      ");
			print(((DecisionNode) iter).getThenNode());
			System.out.print("      ");
			print(((DecisionNode) iter).getElseNode());
		} else if (iter instanceof EndConditionNode){
			System.out.print("      ");
			return;
		} else {
			if (iter != null){
				iter.printNode();
				print(iter.getNext());
			}
		}		
	}
	
	public int getnLoop() {
		return nLoop;
	}

	public void setnLoop(int nLoop) {
		this.nLoop = nLoop;
	}
	
	public static void main(String[] args) {

		int nLoop = 3;
		IASTFunctionDefinition func = (new ASTGenerator("./bai1.cpp")).getFunction(0);
		ControlFlowGraph cfg = (new ControlFlowGraph()).build(func);		
				
		
		UnfoldControlFlowGraph basic = new UnfoldControlFlowGraph( nLoop);
		//cfg.print(cfg.getStart());		
		ControlFlowGraph ucfg = basic.createUCFG(cfg.getStart(), cfg.getExit());
		System.out.println("\n *.* ~ *.*\n ");
		ucfg.print(ucfg.getStart());
		ControlFlowGraph test = basic.copy(cfg.getStart(), cfg.getExit());
		//test.print(test.getStart());
		
	}	
}
