package com.jonathanedgecombe.interpreter.ast;

import com.jonathanedgecombe.interpreter.IntegerValue;
import com.jonathanedgecombe.interpreter.Interpreter;
import com.jonathanedgecombe.interpreter.Value;

public final class NumberExpression extends Expression {
	private final int number;
	
	public NumberExpression(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}
	
	@Override
	public String toString() {
		return Integer.toString(number);
	}

	@Override
	public Value evaluate(Interpreter interpreter) {
		return new IntegerValue(number);
	}
}
