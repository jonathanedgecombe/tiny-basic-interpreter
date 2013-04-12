package com.jonathanedgecombe.interpreter;

public class CharacterBuffer {
	private String data;
	private int offset;
	
	public CharacterBuffer(String data) {
		this.data = data;
	}
	
	public String getNext() {
		return data.substring(offset, ++offset);
	}
	
	public boolean hasNext() {
		return offset < data.length();
	}
	
	public String peek(int offset) {
		return data.substring(this.offset+offset, this.offset+offset+1);
	}
}
