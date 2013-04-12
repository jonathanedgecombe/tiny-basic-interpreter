package com.jonathanedgecombe.interpreter.ast;

import java.util.ArrayList;
import java.util.List;

import com.jonathanedgecombe.interpreter.Interpreter;

public final class PrintStatement extends Statement {
	private final List<Expression> expressions = new ArrayList<>();
	
	public PrintStatement(int label) {
		super(label);
	}

	public List<Expression> getExpressions() {
		return expressions;
	}
	
	public void addExpression(Expression expression) {
		expressions.add(expression);
	}
	
	@Override
	public String toString() {
		String exprs = "";
		for (Expression expr : expressions) {
			exprs += expr + ", ";
		}
		return "PRINT " + exprs;
	}

	@Override
	public void run(Interpreter interpreter) {
		for (Expression expression : expressions) {
			System.out.print(expression.evaluate(interpreter));
		}
		System.out.println();
	}
}
