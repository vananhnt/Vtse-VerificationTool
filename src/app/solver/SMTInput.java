package app.solver;

import cfg.index.Variable;

import java.io.*;
import java.util.List;

public class SMTInput {
    private List<Variable> variableList;
    private String formula;
    private List<String> constraints;

    public SMTInput() {

    }

    public SMTInput(List<Variable> varList, String formula) {
        this.variableList = varList;
        this.formula = formula;
    }

    public List<Variable> getVariableList() {
        return variableList;
    }

    public void setVariableList(List<Variable> variableList) {
        this.variableList = variableList;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public List<String> getConstrain() {
        return constraints;
    }

    public void setConstrainst(List<String> constrainst) {
        this.constraints = constrainst;
    }

    //Unused
    public void printInput() {
        System.err.println("print");
        for (Variable v : variableList) {
            String smtType = getSMTType(v.getType());
//			if (v.hasInitialized()) {

            System.out.println("v: " + v);
            if (v.getIndex() < 0)
                System.out.println("(declare-fun " + v.getVariableWithIndex() + " () " + smtType + ")");
            else {
                int imax = (v.getIndexInvariant() > v.getIndex()) ? v.getIndexInvariant() : v.getIndex();
                for (int i = 0; i <= imax; i++)
                    System.out.println(declare(v.getName(), i, smtType));
            }
        }
//			else if (v.getName().equals("return")) {
//				System.out.println("return return");
//				System.out.println("(declare-fun return () " + smtType + ")");
//			}
//			
//		}

//		for (String s: constraints) {
//			System.out.println("(assert " + s + ")");
//		}

        System.out.println("(assert " + formula + ")");
    }

    public void printInputToOutputStream(OutputStream os)
            throws IOException {
        Writer out = new BufferedWriter(new OutputStreamWriter(os));
        String smtType;
        for (Variable v : variableList) {
            smtType = getSMTType(v.getType());
            // TODO changed

            if (v.hasInitialized()) {

                //System.out.println("v: " + v);
                if (v.getIndex() < 0)
                    out.append("(declare-fun " + v.getVariableWithIndex() + " () " + smtType + ")\n");
                else {
                    int imax = (v.getIndexInvariant() > v.getIndex()) ? v.getIndexInvariant() : v.getIndex();
                    for (int i = -1; i <= imax; i++)
                        out.append(declare(v.getName(), i, smtType) + "\n");
                }
            } else if (v.getName().equals("return")) {
                out.append("(declare-fun return () " + smtType + ")\n");
            }
        }

        out.append("(assert " + formula + ")\n");

        if (constraints != null) {
            for (String s : constraints) {
                out.append("(assert " + s + ")\n");
            }
        }

        out.append("(check-sat)\n");
        out.append("(get-model)");

        out.flush();
        out.close();
    }

    private String getSMTType(String type) {
        String smtType = null;
        if (type.equalsIgnoreCase("bool"))
            smtType = "Bool";
        else if (type.equalsIgnoreCase("int") || type.equalsIgnoreCase("short"))
            smtType = "Int";
        else if (type.equalsIgnoreCase("float") || type.equalsIgnoreCase("double"))
            smtType = "Real";

        return smtType;
    }

    private String declare(String variableName, int index, String type) {
        String value = variableName + "_" + index;
        String declaration = "(declare-fun " + value + " () " + type + ")";
        return declaration;
    }

}
