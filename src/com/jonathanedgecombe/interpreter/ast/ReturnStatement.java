package com.jonathanedgecombe.interpreter.ast;

import com.jonathanedgecombe.interpreter.Interpreter;

public final class ReturnStatement extends Statement {
	public ReturnStatement(int label) {
		super(label);
	}
	
	@Override
	public String toString() {
		return "RETURN";
	}

	@Override
	public void run(Interpreter interpreter) {
		interpreter.setStatement(interpreter.getStack().pop());
	}
}
