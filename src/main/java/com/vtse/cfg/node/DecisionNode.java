package com.vtse.cfg.node;
// build endOfThen  endOofElse =.=

import com.vtse.cfg.index.FormulaCreater;
import com.vtse.cfg.index.Index;
import com.vtse.cfg.index.Variable;
import com.vtse.cfg.index.VariableManager;
import com.vtse.cfg.utils.MyCloner;
import com.vtse.cfg.utils.ExpressionHelper;
import com.vtse.cfg.utils.ExpressionModifier;
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

import java.util.ArrayList;

public class DecisionNode extends CFGNode {
    private IASTExpression condition;
    private CFGNode thenNode;
    private CFGNode endNode;

    private CFGNode endOfThen;
    private CFGNode endOfElse;
    private VariableManager thenVM;
    private VariableManager elseVM;
    // elseNode is next

    public DecisionNode() {
    }


    public IASTExpression getCondition() {
        return condition;
    }

    public void setCondition(IASTExpression iastExpression) {
        this.condition = iastExpression;
    }

    public void setCondition(IASTExpression iastExpression, IASTFunctionDefinition func) {
        CPPNodeFactory factory = new CPPNodeFactory();
        if (iastExpression instanceof IASTIdExpression) {
            //System.err.println("err");
            IASTLiteralExpression number = factory.newLiteralExpression(IASTLiteralExpression.lk_float_constant, "0");
            IASTBinaryExpression binaryExpression = factory.newBinaryExpression(IASTBinaryExpression.op_notequals, iastExpression, number);
            this.condition = binaryExpression;
            changeName(binaryExpression, func);
        } else {
            this.condition = iastExpression;
            changeName(iastExpression, func);
        }


    }

    private void changeName(IASTExpression expression, IASTFunctionDefinition func) {
        condition = (IASTExpression) ExpressionModifier.changeVariableName(expression, func);
    }

    //	set THEN NODE with Input is CFGNode  or  IASTStatement
    public CFGNode getThenNode() {
        return thenNode;
    }

    public void setThenNode(CFGNode thenNode) {
        this.thenNode = thenNode;
    }

    public CFGNode getElseNode() {
        return this.getNext();
    }

    // set ELSE NODE with Input is CFGNode  or  IASTStatement
    public void setElseNode(CFGNode elseNode) {
        this.setNext(elseNode);
    }

    public String getInfixFormula() {
        String conditionStr = FormulaCreater.createInfixFormula(condition);
        String notConditionStr = FormulaCreater.createInfixFormula(FormulaCreater.NEGATIVE, conditionStr);

        String thenFormula = FormulaCreater.createInfix(thenNode, endNode);
        String elseFormula = FormulaCreater.createInfix(next, endNode);

        String formula = "";

        if (thenFormula == null && elseFormula == null) {
            return null;
        } else if (thenFormula == null) {
            formula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, elseFormula);
        } else if (elseFormula == null) {
            formula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
        } else {
            thenFormula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
            elseFormula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, notConditionStr, elseFormula);
//			thenFormula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
//			elseFormula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, notConditionStr, elseFormula);
//			
            formula = FormulaCreater.wrapInfix(FormulaCreater.LOGIC_AND, thenFormula, elseFormula);
        }
        return formula;

    }

    public String getFormula() {
        String conditionStr = FormulaCreater.createFormula(condition);
        String notConditionStr = FormulaCreater.createFormula(FormulaCreater.NEGATIVE, conditionStr);

        String thenFormula = FormulaCreater.create(thenNode, endNode);
        String elseFormula = FormulaCreater.create(next, endNode);

        String formula = "";

        if (thenFormula == null && elseFormula == null) {
            return null;
        } else if (thenFormula == null) {
            formula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, elseFormula);
        } else if (elseFormula == null) {
            formula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
        } else {
//			thenFormula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
//			elseFormula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, notConditionStr, elseFormula);
            thenFormula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
            elseFormula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, notConditionStr, elseFormula);

//			formula = FormulaCreater.wrapInfix(FormulaCreater.LOGIC_AND, thenFormula, elseFormula);
            formula = FormulaCreater.wrapPrefix(FormulaCreater.LOGIC_AND, thenFormula, elseFormula);
        }
        return formula;

    }

    public ArrayList<String> va_getFormula() {
        ArrayList<String> formulaList = new ArrayList<>();
        String conditionStr = FormulaCreater.createFormula(condition);
        String notConditionStr = FormulaCreater.createFormula(FormulaCreater.NEGATIVE, conditionStr);

        String thenFormula = FormulaCreater.create(thenNode, endNode);
        String elseFormula = FormulaCreater.create(next, endNode);

        String formula = "";

        if (thenFormula == null && elseFormula == null) {
            return null;
        } else if (thenFormula == null) {
            formula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, elseFormula);
            formulaList.add(formula);
        } else if (elseFormula == null) {
            formula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
            formulaList.add(formula);
        } else {
//			thenFormula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
//			elseFormula = FormulaCreater.wrapInfix(FormulaCreater.BINARY_CONNECTIVE, notConditionStr, elseFormula);
            thenFormula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
            elseFormula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, notConditionStr, elseFormula);
            formulaList.add(elseFormula);
            formulaList.add(thenFormula);
//			formula = FormulaCreater.wrapInfix(FormulaCreater.LOGIC_AND, thenFormula, elseFormula);
            //formula = FormulaCreater.wrapPrefix(FormulaCreater.LOGIC_AND, thenFormula, elseFormula);
        }

        return formulaList;

    }

    public ArrayList<CFGNode> adjacent() {
        ArrayList<CFGNode> adj = new ArrayList<>();
        adj.add(thenNode);
        adj.add(this.getElseNode());
        return adj;
    }

    public void printNode() {
        String type = condition.getClass().getSimpleName();
        if (condition != null)
            System.out.println("with Condition " + ExpressionHelper.toString(condition));
    }

    public String toString() {
        return ExpressionHelper.toString(condition);
    }

    public CFGNode getEndNode() {
        return endNode;
    }

    public void setEndNode(CFGNode endNode) {
        this.endNode = endNode;
    }

    public void index(VariableManager vm) {
        condition = (IASTExpression) Index.index(condition, vm);

        // then clause
        thenVM = MyCloner.clone(vm);
        CFGNode run = this.getThenNode();
        while ((run != null) && (run != this.endNode)) {
            run.index(thenVM);
            if (run instanceof DecisionNode) {
                run = ((DecisionNode) run).getEndNode();
            } else {
                run = run.getNext();
            }
        }

        // else clause
        elseVM = MyCloner.clone(vm);
        run = this.getElseNode();
        while ((run != null) && (run != this.endNode)) {
            run.index(elseVM);
            if (run instanceof DecisionNode) {
                run = ((DecisionNode) run).getEndNode();
            } else {
                run = run.getNext();
            }
        }
        // sync

        vm.setVariableList(sync(vm).getVariableList());

    }

    /*
     * flag : = true neu can them syncNode va nguoc lai
     */
    private VariableManager sync(VariableManager vm) {
        int size = thenVM.getSize();
        Variable thenVar;
        Variable elseVar;
        String leftHand;
        String rightHand;
        SyncNode syncNode;
		/*
		for (int i = 0; i < size; i++) {
			boolean flag = true;
			int indexVar = vm.getVariable(i).getIndex();
			thenVar = thenVM.getVariable(i);
			elseVar = elseVM.getVariable(i);
			if (thenVar.getIndex() == indexVar || 
				elseVar.getIndex() == indexVar){
				flag = false;
			}
			
			if (thenVar.getIndex() < elseVar.getIndex() ) {
				rightHand = thenVar.getVariableWithIndex();
				thenVar.setIndex(elseVar.getIndex());
				leftHand = thenVar.getVariableWithIndex();
				
				if (flag){
					syncNode = new SyncNode(leftHand, rightHand);				
					this.endOfThen.setNext(syncNode);
					syncNode.setNext(endNode);
					setEndOfThen(syncNode);	
				}
			}
			else if (elseVar.getIndex() < thenVar.getIndex()) {
				rightHand = elseVar.getVariableWithIndex();
				elseVar.setIndex(thenVar.getIndex());
				leftHand = elseVar.getVariableWithIndex();
				
				if (flag){
					syncNode = new SyncNode(leftHand, rightHand);				
					this.endOfElse.setNext(syncNode);
					syncNode.setNext(endNode);
					setEndOfElse(syncNode);
				}
			}
		}
		*/
        for (int i = 0; i < size; i++) {
            thenVar = thenVM.getVariable(i);
            elseVar = elseVM.getVariable(i);

            if (thenVar.getIndex() < elseVar.getIndex()) {
                rightHand = thenVar.getVariableWithIndex();
                thenVar.setIndex(elseVar.getIndex());
                leftHand = thenVar.getVariableWithIndex();
                syncNode = new SyncNode(leftHand, rightHand);

                this.endOfThen.setNext(syncNode);
                syncNode.setNext(endNode);
                setEndOfThen(syncNode);
            } else if (elseVar.getIndex() < thenVar.getIndex()) {
                rightHand = elseVar.getVariableWithIndex();
                elseVar.setIndex(thenVar.getIndex());
                leftHand = elseVar.getVariableWithIndex();
                syncNode = new SyncNode(leftHand, rightHand);

                this.endOfElse.setNext(syncNode);
                syncNode.setNext(endNode);
                setEndOfElse(syncNode);
            }
        }
        // set VM
        return thenVM;
    }

    public CFGNode getEndOfElse() {
        return endOfElse;
    }

    public void setEndOfElse(CFGNode endOfElse) {
        this.endOfElse = endOfElse;
    }

    public CFGNode getEndOfThen() {
        return endOfThen;
    }

    public void setEndOfThen(CFGNode endOfThen) {
        this.endOfThen = endOfThen;
    }

}
