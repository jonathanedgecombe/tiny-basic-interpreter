package com.jonathanedgecombe.interpreter.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jonathanedgecombe.interpreter.Interpreter;

public final class InputStatement extends Statement {
	private final List<Expression> variables = new ArrayList<>();
	
	public InputStatement(int label) {
		super(label);
	}

	public List<Expression> getVariables() {
		return variables;
	}
	
	public void addVariable(Expression variable) {
		variables.add(variable);
	}
	
	@Override
	public String toString() {
		String vars = "";
		for (Expression var : variables) {
			vars += var + ", ";
		}
		return "INPUT " + vars;
	}

	@Override
	public void run(Interpreter interpreter) {
		Scanner scanner = new Scanner(System.in);
		
		for (Expression var : variables) {
			interpreter.setVariable(((VariableExpression) var).getName(), Integer.parseInt(scanner.next()));
		}
		
		scanner.close();
	}
}
