package com.jonathanedgecombe.interpreter.ast;

import com.jonathanedgecombe.interpreter.Interpreter;

public abstract class Statement {
	private int label;
	private Statement nextStatement;
	
	public Statement(int label) {
		this.label = label;
	}

	public int getLabel() {
		return label;
	}
	
	public void setLabel(int label) {
		this.label = label;
	}

	public Statement getNextStatement() {
		return nextStatement;
	}

	public void setNextStatement(Statement nextStatement) {
		this.nextStatement = nextStatement;
	}
	
	public abstract void run(Interpreter interpreter);
}
