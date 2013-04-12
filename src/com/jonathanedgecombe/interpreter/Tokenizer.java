package com.jonathanedgecombe.interpreter;

import java.util.ArrayList;
import java.util.List;

import com.jonathanedgecombe.interpreter.Token.TokenType;

public class Tokenizer {
	
	private static List<String> whitespace = new ArrayList<>();
	private static List<String> keywords = new ArrayList<>();
	private static List<String> digits = new ArrayList<>();
	private static List<String> characters = new ArrayList<>();
	private static List<String> operators = new ArrayList<>();
	private static List<String> relationalOperators = new ArrayList<>();
	
	
	public List<Token> tokenize(String line) {
		List<Token> list = new ArrayList<>();
		
		CharacterBuffer buffer = new CharacterBuffer(line);
		
		while (buffer.hasNext()) {
			Token token = nextToken(buffer);
			list.add(token);
		}
		
		return list;
	}
	
	private enum CharacterType {
		CHARACTER,
		DIGIT,
		OPERATOR,
		TOPERATOR,
		RELOPERATOR,
		QUOTE,
		SPLITTER,
		RIGHTPAREN,
		LEFTPAREN;
		
		public static CharacterType getType(String token) {
			if (characters.contains(token.toLowerCase())) {
				return CHARACTER;
			} else if (digits.contains(token)) {
				return DIGIT;
			} else if (operators.contains(token)) {
				return OPERATOR;
			} else if (relationalOperators.contains(token)) {
				return RELOPERATOR;
			} else if (token.equals(",")) {
				return SPLITTER;
			} else if (token.equals("\"")) {
				return QUOTE;
			} else if (token.equals("(")) {
				return LEFTPAREN;
			} else if (token.equals(")")) {
				return RIGHTPAREN;
			}
			throw new RuntimeException("TOKENIZER ERROR: Invalid token: '" + token + "'");
		}
	}
	
	
	public Token nextToken(CharacterBuffer buffer) {
		String token = buffer.getNext();
		
		token = readWhitespace(token, buffer);

		CharacterType type = CharacterType.getType(token);
		
		switch(type) {
		
		case SPLITTER:
			return new Token(TokenType.SPLITTER, token);
		
		case CHARACTER:
			if (!buffer.hasNext() || (buffer.hasNext() && !characters.contains(buffer.peek(0).toLowerCase()))) {
				//Variable
				return new Token(TokenType.VARIABLE, token);
			} else {
				//Keyword
				return readKeyword(token, buffer);
			}
		
		case DIGIT:
			//Number
			return readNumber(token, buffer);
		
		case OPERATOR:
			//Operator
			return new Token(TokenType.OPERATOR, token);
		
		case RELOPERATOR:
			//Relational Operator
			return readRelationalOperator(token, buffer);
		
		case QUOTE:
			//String Literal
			return readString(buffer);
			
		case LEFTPAREN:
			return new Token(TokenType.LEFTPAREN, "(");
			
		case RIGHTPAREN:
			return new Token(TokenType.RIGHTPAREN, ")");
			
		default:
			throw new RuntimeException("TOKENIZER ERROR - Invalid token: '" + token + "'");

		}
	}
	
	
	
	public String readWhitespace(String token, CharacterBuffer buffer) {
		if (whitespace.contains(token)) {
			//Skip whitespace
			while (buffer.hasNext() && whitespace.contains(buffer.peek(0))) {
				buffer.getNext();
			}
			
			return buffer.getNext();
		} else {
			return token;
		}
	}
	
	public Token readKeyword(String token, CharacterBuffer buffer) {
		//Read remaining characters
		while (buffer.hasNext() && characters.contains(buffer.peek(0).toLowerCase())) {
			token += buffer.getNext();
		}
		
		//Check if valid keyword
		if (keywords.contains(token.toLowerCase())) {
			return new Token(TokenType.KEYWORD, token.toLowerCase());
		} else {
			throw new RuntimeException("TOKENIZER ERROR - Invalid keyword: " + token);
		}
	}
	
	public Token readNumber(String token, CharacterBuffer buffer) {
		while (buffer.hasNext() && digits.contains(buffer.peek(0))) {
			token += buffer.getNext();
		}
		return new Token(TokenType.NUMBER, token);
	}
	
	public Token readString(CharacterBuffer buffer) {
		String token = ""; //Ignore first quotation mark.
		while (buffer.hasNext() && !(buffer.peek(0).equals("\"") && !(buffer.peek(-1).equals("\\")))) {
			token += buffer.getNext();
		}
		buffer.getNext(); //Ignore ending quotation mark.
		return new Token(TokenType.STRING, token);
	}
	
	public Token readRelationalOperator(String token, CharacterBuffer buffer) {
		//Relational Operator
		if (!token.equals("=") && buffer.hasNext() && relationalOperators.contains(buffer.peek(0))) {
			return new Token(TokenType.RELOPERATOR, token+buffer.getNext());
		} else {
			return new Token(TokenType.RELOPERATOR, token);
		}
	}
	
	
	
	static {
		whitespace.add(" ");
		whitespace.add("	");
		
		keywords.add("print");
		keywords.add("if");
		keywords.add("then");
		keywords.add("goto");
		keywords.add("input");
		keywords.add("let");
		keywords.add("gosub");
		keywords.add("return");
		keywords.add("clear");
		keywords.add("end");
		
		String digits = "0123456789";
		for (String digit : digits.split("")) {
			Tokenizer.digits.add(digit);
		}
		
		String variables = "abcdefghijklmnopqrstuvwxyz";
		for (String variable : variables.split("")) {
			characters.add(variable);
		}
		
		operators.add("*");
		operators.add("+");
		operators.add("-");
		operators.add("/");
		
		relationalOperators.add(">");
		relationalOperators.add("<");
		relationalOperators.add("=");
	}
}
