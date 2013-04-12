package com.jonathanedgecombe.interpreter;

import java.util.List;

import com.jonathanedgecombe.interpreter.Token.TokenType;
import com.jonathanedgecombe.interpreter.ast.*;

public class Parser {
	private int index;
	private List<Token> tokens;
	private Token token;
	
	public Parser(List<Token> tokens) {
		this.tokens = tokens;
		this.token = tokens.get(0);
	}
	
	public Statement parse() {
		int label = -1;
		
		if (token == null) throw new RuntimeException("PARSING ERROR: Expected NUMBER or KEYWORD, received NONE");
		if (token.getType() == TokenType.NUMBER) {
			label = Integer.parseInt(token.getValue());
			consume();
		}
		
		if (token == null) throw new RuntimeException("PARSING ERROR: Expected KEYWORD, received NONE");
		if (token.getType() != TokenType.KEYWORD) throw new RuntimeException("PARSING ERROR: Expected KEYWORD, received " + token.getType().name());
		
		switch(token.getValue()) {
		
		case "print":
			consume();
			PrintStatement printStatement = new PrintStatement(label);
			
			while (token.getType() == TokenType.SPLITTER || token.getType() == TokenType.VARIABLE || token.getType() == TokenType.NUMBER || token.getType() == TokenType.OPERATOR || token.getType() == TokenType.STRING) {
				if (token.getType() == TokenType.STRING) {
					printStatement.getExpressions().add(new StringExpression(token.getValue()));
					consume();
				} else if (token.getType() != TokenType.SPLITTER) {
					printStatement.getExpressions().add(parseExpression());
				} else {
					consume();
				}
			}
			
			return printStatement;
			
		case "if":
			consume();
			Expression leftExpression = parseExpression();

			if (token.getType() != TokenType.RELOPERATOR) throw new RuntimeException("PARSING ERROR: Expected relational operator");
			OperatorType op = OperatorType.forString(token.getValue());
			
			consume();
			Expression rightExpression = parseExpression();
			
			if (token.getType() != TokenType.KEYWORD || !token.getValue().equals("then")) throw new RuntimeException("PARSING ERROR: Unexpected token, expected THEN");
			
			consume();
			Statement statement = parse();
			
			return new IfStatement(label, leftExpression, rightExpression, op, statement);
			
		case "goto":
			consume();
			if (token.getType() != TokenType.NUMBER) throw new RuntimeException("PARSING ERROR: Goto only supports static labels");
			return new GotoStatement(label, new NumberExpression(Integer.parseInt(token.getValue())));
		
		case "input":
			consume();
			
			InputStatement inputStatement = new InputStatement(label);
			
			while (token.getType() == TokenType.SPLITTER || token.getType() == TokenType.VARIABLE) {
				if (token.getType() == TokenType.VARIABLE) inputStatement.getVariables().add(new VariableExpression(token.getValue()));
				consume();
			}
			
			return inputStatement;
			
		case "let":
			consume();
			if (token.getType() != TokenType.VARIABLE) throw new RuntimeException("PARSING ERROR: Gosub only supports static labels");
			Expression var = new VariableExpression(token.getValue());
			
			consume();
			if (token.getType() != TokenType.RELOPERATOR || !token.getValue().equals("=")) throw new RuntimeException("PARSING ERROR: Unexcpected token, expected '='");
			
			consume();
			return new LetStatement(label, var, parseExpression());
			
		case "gosub":
			consume();
			if (token.getType() != TokenType.NUMBER) throw new RuntimeException("PARSING ERROR: Gosub only supports static labels");
			return new GosubStatement(label, new NumberExpression(Integer.parseInt(token.getValue())));
		
		case "return":
			return new ReturnStatement(label);
			
		case "end":
			return new EndStatement(label);
		}
		
		throw new RuntimeException("PARSING ERROR: Invalid keyword: '" + token.getValue() + "'");
	}
	
	public Expression parseExpression() {
		Expression expr;
		
		if (token.getType() == TokenType.OPERATOR && (token.getValue().equals("+") || token.getValue().equals("-"))) {
			consume();
			expr = parseTerm();
		} else {
			expr = parseTerm();
		}
		
		while (token.getType() == TokenType.OPERATOR && (token.getValue().equals("+") || token.getValue().equals("-"))) {
			OperatorType op = OperatorType.forString(token.getValue());
			consume();
			expr = new ArithmeticExpression(op, expr, parseTerm());
		}
		
		return expr;
	}
	
	public Expression parseTerm() {
		Expression expr = parseFactor();
		
		while (token.getType() == TokenType.OPERATOR && (token.getValue().equals("*") || token.getValue().equals("/"))) {
			OperatorType op = OperatorType.forString(token.getValue());
			consume();
			expr = new ArithmeticExpression(op, expr, parseFactor());
		}
		
		return expr;
	}
	
	public Expression parseFactor() {
		switch(token.getType()) {
		case NUMBER:
			Expression expr = new NumberExpression(Integer.parseInt(token.getValue()));
			consume();
			return expr;
		case VARIABLE:
			expr = new VariableExpression(token.getValue());
			consume();
			return expr;
		case LEFTPAREN:
			consume();
			expr = parseExpression();
			if (token.getType() != TokenType.RIGHTPAREN) throw new RuntimeException("no right bracket");
			consume();
			return expr;
		}
		throw new RuntimeException("COMPLIER ERROR: Invalid factor: '" + token.getValue() + "'");
	}
	
	public void consume() {
		if (++index >= tokens.size())
			token = new Token(TokenType.EOF, "");
		else
			token = tokens.get(index);
	}
}
