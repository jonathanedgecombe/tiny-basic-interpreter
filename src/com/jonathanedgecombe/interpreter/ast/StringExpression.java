package com.jonathanedgecombe.interpreter.ast;

import com.jonathanedgecombe.interpreter.Interpreter;
import com.jonathanedgecombe.interpreter.StringValue;
import com.jonathanedgecombe.interpreter.Value;

public final class StringExpression extends Expression {
	private final String string;
	
	public StringExpression(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}
	
	@Override
	public String toString() {
		return "\"" + string + "\"";
	}

	@Override
	public Value evaluate(Interpreter interpreter) {
		return new StringValue(string);
	}
}
