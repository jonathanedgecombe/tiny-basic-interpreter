package com.jonathanedgecombe.interpreter.ast;

public enum OperatorType {
	LESSTHAN,
	LESSTHANOREQUAL,
	GREATERTHAN,
	GREATERTHANOREQUAL,
	NOTEQUAL,
	EQUAL,
	ADD,
	SUBTRACT,
	MULTIPLY,
	DIVIDE;
	
	public static OperatorType forString(String data) {
		switch(data) {
		case "<>":
		case "><":
			return NOTEQUAL;
		case "<":
			return LESSTHAN;
		case ">":
			return GREATERTHAN;
		case "<=":
			return LESSTHANOREQUAL;
		case ">=":
			return GREATERTHANOREQUAL;
		case "=":
			return EQUAL;
		case "+":
			return ADD;
		case "-":
			return SUBTRACT;
		case "/":
			return DIVIDE;
		case "*":
			return MULTIPLY;
		default:
			throw new RuntimeException("COMPILER ERROR -  Invalid operator '" + data + "'");
		}
	}
}