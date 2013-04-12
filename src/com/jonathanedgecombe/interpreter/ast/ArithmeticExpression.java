package com.jonathanedgecombe.interpreter.ast;

import com.jonathanedgecombe.interpreter.IntegerValue;
import com.jonathanedgecombe.interpreter.Interpreter;
import com.jonathanedgecombe.interpreter.Value;

public final class ArithmeticExpression extends Expression {
	private final Expression left, right;
	private final OperatorType operator;
	
	public ArithmeticExpression(OperatorType operator, Expression left, Expression right) {
		this.operator = operator;
		this.right = right;
		this.left = left;
	}

	public OperatorType getOperator() {
		return operator;
	}

	public Expression getLeft() {
		return left;
	}

	public Expression getRight() {
		return right;
	}
	
	@Override
	public String toString() {
		return "(" + left + " " + operator + " " + right + ")";
	}

	@Override
	public Value evaluate(Interpreter interpreter) {
		int leftval = left.evaluate(interpreter).getValue();
		int rightval = right.evaluate(interpreter).getValue();
		
		int val;
		switch(operator) {
		case ADD:
			val = leftval+rightval;
			break;
		case SUBTRACT:
			val = leftval-rightval;
			break;
		case MULTIPLY:
			val = leftval*rightval;
			break;
		case DIVIDE:
			val = leftval/rightval;
			break;
		default:
			throw new AssertionError();
		}
		
		return new IntegerValue(val);
	}

}
