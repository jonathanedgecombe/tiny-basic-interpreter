package com.jonathanedgecombe.interpreter.ast;

import com.jonathanedgecombe.interpreter.Interpreter;
import com.jonathanedgecombe.interpreter.Value;

public abstract class Expression {
	public abstract Value evaluate(Interpreter interpreter);
}
