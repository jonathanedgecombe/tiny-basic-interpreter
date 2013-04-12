package com.jonathanedgecombe.interpreter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

import com.jonathanedgecombe.interpreter.ast.*;

public class Interpreter {
	private final Map<Integer, Statement> statements = new LinkedHashMap<>();
	private final Map<String, Integer> variables = new HashMap<>();
	private final Stack<Integer> stack = new Stack<>();
	
	public Interpreter() {
		String[] vnames = "abcdefghijklmnopqrstuvwxyz".split("");
		for (String vname : vnames) {
			variables.put(vname, 0);
		}
	}
	
	public void addStatement(int label, Statement statement) {
		statements.put(label, statement);
	}
	
	private Statement statement;
	public void setInitialStatement() {
		statement = statements.entrySet().iterator().next().getValue();
		//System.out.println(statement);
	}
	public void setStatement(int label) {
		statement = statements.get(label);
	}
	
	public void run() {
		Statement current = statement;
		if (current == null) throw new RuntimeException("RUNTIME ERROR: Missing END");
		statement = statement.getNextStatement();
		current.run(this);
	}

	public Stack<Integer> getStack() {
		return stack;
	}

	public void setVariable(String name, int value) {
		variables.put(name.toLowerCase(), value);
	}
	
	public int getVariable(String name) {
		return variables.get(name.toLowerCase());
	}
}
