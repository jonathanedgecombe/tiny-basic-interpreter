package com.jonathanedgecombe.interpreter;

public class IntegerValue extends Value {
	private int value;
	
	public IntegerValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	@Override
	public int getValue() {
		return value;
	}
}
