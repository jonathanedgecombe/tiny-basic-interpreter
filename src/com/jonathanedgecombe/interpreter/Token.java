package com.jonathanedgecombe.interpreter;

public class Token {
	public enum TokenType {
		KEYWORD,
		VARIABLE,
		NUMBER,
		OPERATOR,
		RELOPERATOR,
		STRING,
		SPLITTER,
		EOF,
		RIGHTPAREN,
		LEFTPAREN;
	}
	
	private TokenType type;
	private String value;
	
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public TokenType getType() {
		return type;
	}
	public void setType(TokenType type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return type.name() + ": '" + value + "'";
	}
}
