package com.jonathanedgecombe.interpreter.ast;

import com.jonathanedgecombe.interpreter.Interpreter;

public final class IfStatement extends Statement {
	private final Expression leftExpression, rightExpression;
	private final Statement statement;
	private final OperatorType relationalOperator;
	
	public IfStatement(int label, Expression leftExpression, Expression rightExpression, OperatorType relationalOperator, Statement statement) {
		super(label);
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
		this.relationalOperator = relationalOperator;
		this.statement = statement;
	}
	
	public Expression getLeftExpression() {
		return leftExpression;
	}

	public Expression getRightExpression() {
		return rightExpression;
	}

	public OperatorType getRelationalOperator() {
		return relationalOperator;
	}

	public Statement getStatement() {
		return statement;
	}
	
	@Override
	public String toString() {
		return "IF " + leftExpression + " " + relationalOperator + " " + rightExpression + " THEN " + statement + " ";
	}

	@Override
	public void run(Interpreter interpreter) {
		int left = leftExpression.evaluate(interpreter).getValue();
		int right = rightExpression.evaluate(interpreter).getValue();
		
		boolean flag;
		
		switch (relationalOperator) {
		case EQUAL:
			flag = (left == right);
			break;
		case LESSTHAN:
			flag = (left < right);
			break;
		case LESSTHANOREQUAL:
			flag = (left <= right);
			break;
		case GREATERTHAN:
			flag = (left > right);
			break;
		case GREATERTHANOREQUAL:
			flag = (left >= right);
			break;
		case NOTEQUAL:
			flag = (left != right);
		default:
			flag = false;
		}
		
		if (flag) {
			statement.run(interpreter);
		}
	}
}
