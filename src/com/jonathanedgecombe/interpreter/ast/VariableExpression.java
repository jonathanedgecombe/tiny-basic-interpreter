package com.jonathanedgecombe.interpreter.ast;

import com.jonathanedgecombe.interpreter.IntegerValue;
import com.jonathanedgecombe.interpreter.Interpreter;
import com.jonathanedgecombe.interpreter.Value;

public final class VariableExpression extends Expression {
	private final String name;
	
	public VariableExpression(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public Value evaluate(Interpreter interpreter) {
		return new IntegerValue(interpreter.getVariable(name));
	}
}
