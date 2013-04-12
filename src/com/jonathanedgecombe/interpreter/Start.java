package com.jonathanedgecombe.interpreter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jonathanedgecombe.interpreter.ast.*;

public class Start {
	
	public static void main(String[] args) throws IOException {
		if (args.length != 1) throw new RuntimeException("Invalid number of arguments. Use as: filename");
		
		run(args[0]);
	}
	
	public static void run(String path) throws IOException {
		Scanner scanner = new Scanner(new File(path));
		
		int lastLabel = 0;
		List<Statement> lines = new ArrayList<>();
		List<Integer> usedLabels = new ArrayList<>();
		
		Statement lastStatement = null;
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			
			Tokenizer tokenizer = new Tokenizer();
			List<Token> tokens = tokenizer.tokenize(line);
			
			Parser parser = new Parser(tokens);
			Statement statement = parser.parse();
			
			lines.add(statement);
			usedLabels.add(statement.getLabel());
			
			if (lastStatement != null) lastStatement.setNextStatement(statement);
			lastStatement = statement;
			
			//System.out.println(lastStatement);
		}
		
		scanner.close();
		
		Interpreter interpreter = new Interpreter();
		
		/* Assign labels to lines without labels */
		for (Statement statement : lines) {
			if (statement.getLabel() == -1) {
				while (usedLabels.contains(lastLabel)) {
					lastLabel++;
				}
				
				statement.setLabel(lastLabel);				
				
				lastLabel++;
			}
			interpreter.addStatement(statement.getLabel(), statement);
		}
		
		interpreter.setInitialStatement();
		while (true) interpreter.run();
	}
	
}
