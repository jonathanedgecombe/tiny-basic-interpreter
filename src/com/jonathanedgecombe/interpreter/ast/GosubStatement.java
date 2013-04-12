package com.jonathanedgecombe.interpreter.ast;

import com.jonathanedgecombe.interpreter.Interpreter;

public final class GosubStatement extends Statement {
	private final Expression number;
	
	public GosubStatement(int label, Expression number) {
		super(label);
		this.number = number;
	}

	public Expression getNumber() {
		return number;
	}
	
	@Override
	public String toString() {
		return "GOSUB " + number;
	}

	@Override
	public void run(Interpreter interpreter) {
		interpreter.getStack().push(this.getNextStatement().getLabel());
		interpreter.setStatement(((NumberExpression) number).getNumber());
	}
}
