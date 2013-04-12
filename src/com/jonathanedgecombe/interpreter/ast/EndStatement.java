package com.jonathanedgecombe.interpreter.ast;

import com.jonathanedgecombe.interpreter.Interpreter;

public final class EndStatement extends Statement {
	public EndStatement(int label) {
		super(label);
	}
	
	@Override
	public String toString() {
		return "END";
	}

	@Override
	public void run(Interpreter interpreter) {
		System.exit(0);
	}
}
