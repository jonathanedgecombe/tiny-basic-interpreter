package com.jonathanedgecombe.interpreter.ast;

import com.jonathanedgecombe.interpreter.Interpreter;

public final class LetStatement extends Statement {
	private final Expression variable, expression;
	
	public LetStatement(int label, Expression variable, Expression expression) {
		super(label);
		this.variable = variable;
		this.expression = expression;
	}

	public Expression getVariable() {
		return variable;
	}

	public Expression getExpression() {
		return expression;
	}
	
	@Override
	public String toString() {
		return "LET " + variable + " = " + expression;
	}

	@Override
	public void run(Interpreter interpreter) {
		interpreter.setVariable(((VariableExpression) variable).getName(), expression.evaluate(interpreter).getValue());
	}
	
}
