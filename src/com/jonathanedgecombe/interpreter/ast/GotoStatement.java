package com.jonathanedgecombe.interpreter.ast;

import com.jonathanedgecombe.interpreter.Interpreter;

public final class GotoStatement extends Statement {
	private final Expression number;
	
	public GotoStatement(int label, Expression number) {
		super(label);
		this.number = number;
	}

	public Expression getNumber() {
		return number;
	}
	
	@Override
	public String toString() {
		return "GOTO " + number;
	}
	
	@Override
	public void run(Interpreter interpreter) {
		interpreter.setStatement(((NumberExpression) number).getNumber());
	}
}
