package app.solver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import cfg.utils.Variable;

public class SMTInput {
	public SMTInput() {
		
	}
	
	public SMTInput(List<String> formula, List<Variable> listVariables) {
		this.formula = formula;
		this.listVariables = listVariables;
	}
	
	public List<String> getConstraints() {
		return constraints;
	}
	
	public List<Variable> getListVariables() {
		return listVariables;
	}
	
	public List<String> getFormula() {
		return formula;
	}
	
	public void setConstraints(List<String> constraints) {
		this.constraints = constraints;
	}
	
	public void setListVariables(List<Variable> list) {
		this.listVariables = list;
	}
	
	public void setFormula(List<String> formula) {
		this.formula = formula;
	}
	
	public void printInput() {
		System.err.println("print");
		for (Variable v: listVariables) {
			String smtType = getSMTType(v.getType());
			if (v.hasInitialized()) {
				
				System.out.println("v: " + v);
				if ( v.getIndex() < 0)
					System.out.println("(declare-fun " + v.getVariableWithIndex() + " () " + smtType + ")");
				else {
					for (int i = 0; i <= v.getIndex(); i++)
						System.out.println(declare(v.getName(), i, smtType));
				}
			}
			else if (v.getName().equals("return")) {
				System.out.println("return return");
				System.out.println("(declare-fun return () " + smtType + ")");
			}
			
		}
		for (String s: constraints) {
			System.out.println("(assert " + s + ")");
		}
			
		for (String s: formula) {
			System.out.println("(assert " + s + ")");
		}
	}
	
	public void printInputToOutputStream(OutputStream os) 
						throws IOException {
		Writer out = new BufferedWriter(new OutputStreamWriter(os));
		String smtType;
		for (Variable v: listVariables) {
			smtType = getSMTType(v.getType());
			if (v.hasInitialized()) {
				
				System.out.println("v: " + v);
				if ( v.getIndex() < 0)
					out.append("(declare-fun " + v.getVariableWithIndex() + " () " + smtType + ")\n");
				else {
					for (int i = 0; i <= v.getIndex(); i++)
						out.append(declare(v.getName(), i, smtType) + "\n");
				}
			}
			else if (v.getName().equals("return")) {
				out.append("(declare-fun return () " + smtType + ")\n");
			}
		}
		
		
		for (String s: formula) {
			out.append("(assert " + s + ")\n");
		}
		
		for (String s: constraints) {
			out.append("(assert " + s + ")\n");
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
	
	public List<String> formula;
	public List<Variable> listVariables;
	public List<String> constraints;
}