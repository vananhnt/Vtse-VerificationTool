package app.verification.userassertion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.utils.InfixToPrefix;
import cfg.utils.Variable;



public class UserInput {
	String input;
	String assertion;
	List<Variable> parameters;
	String[] mathElements;
	
	public UserInput() {
		
	}
	
	public UserInput(String input) {
		this.input = input;
	}
	
	public UserInput setInput(String input) {
		this.input = input;
		return this;
	}
	
	public String createUserAssertion(String input) {
		
		this.input = input;
		
		preProcess();
		
		String prefix = InfixToPrefix.infixToPrefix(this.input);
		
		mathElements = prefix.split(" ");
		
		for (String str: mathElements) {
			System.out.println("str: " + str);
		}
		
		addParenthesis();
		
		addIndexForParameter();
		
		postReplace();
		
        return assertion;
	}
	public String createUserAssertion(String input, String funcName) {
		
		this.input = input;
		
		preProcess();
		
		String prefix = InfixToPrefix.infixToPrefix(this.input);
		
		mathElements = prefix.split(" ");
		
		for (String str: mathElements) {
			System.out.println("str: " + str);
		}
		
		addParenthesis();
		
		addIndexForParameter(funcName);
		
		postReplace();
		
        return assertion;
	}

	
	public UserInput setParameter(List<Variable> parameters) {
		this.parameters = parameters;
		return this;
	}
	
	private void addIndexForParameter() {
		if (mathElements == null || parameters == null)
			return;
		int length = mathElements.length;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < parameters.size(); j++) {
				if (parameters.get(j).getName().equals(mathElements[i]) &&
						!mathElements[i].equalsIgnoreCase("return") ) {
					mathElements[i] = mathElements[i] + "_0";
				}
			}
			
		}
	
		String old;
		String replacement;
		for (Variable v: parameters) {
			//if (!v.getName().equals("return")) {
				old = " " + v.getName() + " ";
				replacement = " " + v.getName() + "_0" + " ";
				assertion = assertion.replaceAll(old, replacement);
				
				old = String.format(" %s\\)", v.getName());
				replacement = String.format(" %s_0\\)", v.getName());
				assertion = assertion.replaceAll(old, replacement);
			//}
		}
	}
	private void addIndexForParameter(String funcName) {
		if (mathElements == null || parameters == null)
			return;
		int length = mathElements.length;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < parameters.size(); j++) {
				if (parameters.get(j).getName().equals(mathElements[i]) &&
						!mathElements[i].equalsIgnoreCase("return") ) {
					mathElements[i] = mathElements[i] + "_" + funcName + "_0";
				}
			}
			
		}
	
		String old;
		String replacement;
		for (Variable v: parameters) {
			//if (!v.getName().equals("return")) {
				old = " " + v.getName() + " ";
				replacement = " " + v.getName() + "_" + funcName+ "_0" + " ";
				assertion = assertion.replaceAll(old, replacement);
				
				old = String.format(" %s\\)", v.getName());
				replacement = String.format(" %s_0\\)", v.getName()+ "_" + funcName);
				assertion = assertion.replaceAll(old, replacement);
			//}
		}
	}
	
	/**
	 * Tiá»�n xá»­ lÃ½ input trÆ°á»›c khi chuyá»ƒn Ä‘á»•i
	 * thay tháº¿ cÃ¡c phÃ©p toÃ¡n cÃ³ 2 kÃ­ tá»±
	 */
	private void preProcess() {
	
		input = input.replaceAll(" ", "")
					.replace("==", "=")
					.replaceAll(">=", "@")
					.replaceAll("<=", "~")
					.replaceAll("&&", "&")
					.replaceAll("\\^", "&")
					.replaceAll("!=", "?");
		
	}
	
	/**
	 * xá»­ lÃ½ input sau khi chuyá»ƒn Ä‘á»•i (tráº£ láº¡i tÃªn cho em)
	 * thay tháº¿ láº¡i cÃ¡c phÃ©p toÃ¡n Ä‘Ã£ Ä‘Æ°á»£c thay tháº¿ trong preProcess
	 */
	private void postReplace() {
		assertion = assertion.replaceAll("@", ">=")
						.replaceAll("~", "<=")
						.replaceAll("&", "and")
						.replaceAll("!", "not")
						.replaceAll("\\?", "distinct");
	}
	
	private void addParenthesis() {
		boolean isInteger = true;
		String type;
		for(int i = mathElements.length - 2; i >= 0; i--) {
			if( InfixToPrefix.isOperator(mathElements[i].charAt(0)) ) {
				
				if ( isInteger ) {
					type = getType(mathElements[i+1]);
					System.out.println("type 1: " + type);
					if (type.equals("double") || type.equals("float")) {
						System.out.println("hellow");
						isInteger = false;
						System.out.println("is integer 1 : " + isInteger);
					}
					type = getType(mathElements[i+2]);
					System.out.println("type 2: " + type);
					if (type.equals("double") || type.equals("float")) {
						isInteger = false;
						System.out.println("is integer 2: " + isInteger);
					}
				}
				
				if (mathElements[i].equals("/") && isInteger) {
					System.err.println("is integer: " + isInteger);
					mathElements[i] = "div";
				}
				
				String temp = "(" + mathElements[i] + " " + mathElements[i+1] + " " + mathElements[i+2] + ")";
				
				mathElements[i] = temp;
				for (int j = i+1; j < mathElements.length-2; j++) {
					mathElements[j] = mathElements[j+2];
				}
			}
		}
		
		assertion = mathElements[0];
		System.out.println("assertion: " + assertion);
	}
	
	private String getType(String operand) {
		if (parameters == null)
			return "";
		
		String type = "";
		System.out.println("operand: " + operand);
		
		for (int i = 0; i < parameters.size(); i++) {
			if ( parameters.get(i).getName().equals(operand) ) {
				return parameters.get(i).getType();
			}
		}
		
		return type;
	}
	
	public static void main(String[] args) throws IOException {
        String input = "-1+2*4/5-7+3/6";
        input = "a*-a+ (2*3-1)";
        input = "(x - 100) * (y < x)";
        input = "return <= 0";
        input = "return = n*(n+1) / 2";
     //   input = "return * return > 1";
        input = "(a>= 0)^(a<=1)";
    //    input = "return * return > (a+b)/2";
        input = "a != b";
        
//        input = "-a * b";	// error
//        
//        input = "!(a < b)";	// error
        input = "return < (a + b)";
        
        List<Variable> parameters = new ArrayList<>();
        parameters.add(new Variable("double", "return"));
        parameters.add(new Variable("double", "a"));
        parameters.add(new Variable("double", "b"));
        
        if (input.contains("<=")) {
        	System.out.println("contain <= ");
        }
        else {
        	System.out.println("not contain <= ");
        }	
   
        System.out.println("input: " + input);
        String output;
        UserInput theTrans = new UserInput(input);
        theTrans.setParameter(parameters);
        output = theTrans.createUserAssertion(input, "main"); 
        System.out.println("prefix is " + output + '\n');
    }
	
}
