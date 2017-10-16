package app.utils;

import java.util.Stack;

public class PrefixToInfix {
	
	public static Stack<String> stack;
	
	static {
		stack = new Stack<>();
	}
	
	@SuppressWarnings("unused")
	public static String prefixToInfix(String prefix) {
		String infix = "";
		
		String[] parse = prefix.split(" ");
		
		System.out.println("length: " + parse.length);
		
		for (String s: parse) {
			System.out.print(" " + s);
		}
		System.out.println("");
		
		int currentLength = parse.length;
		
		String temp;
		
//		for(int i = parse.length - 2; i >= 0; i--) {
//			if( !parse[i].equals("") &&  InfixToPrefix.isOperator( parse[i].charAt(0) ) ) {
//				
//			//	String temp = "(" + parse[i+1] + " " + parse[i] + " " + parse[i+2] + ")";
//				if (currentLength - i == 2) {
//					temp = parse[i] + parse[i+1];
//					currentLength -= 1;
//					for (int j = i+1; j < parse.length-1; j++) {
//						parse[j] = parse[j+1];
//					}
//				}
//				else {
//					temp = parse[i+1] + parse[i] + parse[i+2];
//					for (int j = i+1; j < parse.length-2; j++) {
//						parse[j] = parse[j+2];
//					}
//				}
//				
//				
//				parse[i] = temp;
////				for (int j = i+1; j < parse.length-2; j++) {
////					parse[j] = parse[j+2];
////				}
//			}
//		}
//		
//		infix = parse[0];
		String s;
		for (int i = parse.length-1; i >= 0; i--) {
			s = parse[i];
			if (!s.equals("")) {
				if (!InfixToPrefix.isOperator(s.charAt(0))) {
					stack.push(s);
				}
				else {
					if (stack.size() >= 2) {
						temp = stack.pop() + " " + s + " " + stack.pop();
					}
					else {
						temp = s + " " + stack.pop();
					}
					
					stack.push(temp);
				}
			}
		}
		
		return stack.pop();
	}
	
	public static void main(String[] args) {
		String prefix = "- / 437498374983748378473874837483784738478374837847384444444444444444444 15.0";
		
		String infix = PrefixToInfix.prefixToInfix(prefix);
		
		System.out.println("infix: " + infix);
	}
}
