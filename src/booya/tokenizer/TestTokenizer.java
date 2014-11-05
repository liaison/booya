package booya.tokenizer;


import java.util.LinkedList;

import booya.BoolExpLexer;

/**
 * The source code is written by a blog in CogitoLearning following the link:
 * http://cogitolearning.co.uk/?p=525
 
 * The implemention was further refined by Lisong Guo
 *  
 * @author Lisong Guo <lisong.guo@inria.fr>
 * @date Nov 03, 2014
 * 
 */

public class TestTokenizer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		math_exp_lexer();
		bool_exp_lexer();
	}
	
	/**
	 * Lexer for arithematic mathematic expressions.
	 */
	public static void math_exp_lexer(){		
		Tokenizer tokenizer = new Tokenizer();
		tokenizer.add("sin|cos|exp|ln|sqrt", 1);
		tokenizer.add("\\(", 2);
		tokenizer.add("\\)", 3);
		tokenizer.add("\\+|-", 4);
		tokenizer.add("\\*|/", 5);
		tokenizer.add("[0-9]+", 6);
		tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", 7);

		try {
			String str = " sin(x) * (1 - var_12) ";
			System.out.println("Math Exp:" + str);
			tokenizer.tokenize(str);

			for (Token tok : tokenizer.getTokens()) {
				System.out.println("" + tok.type + " " + tok.value);
			}
		} catch (ParserException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Lexer for boolean expressions.
	 */
	public static void bool_exp_lexer(){
		BoolExpLexer belexer = new BoolExpLexer();
		
		try {
			//String str = 
			//		"(CONFIG_WATCHDOG && CONFIG_FOOTBRIDGE) && !CONFIG_21285_WATCHDOG && CONFIG_MODULES";
			//String str = 
			//		"CONFIG_9P_FSCACHE (CONFIG_NETWORK_FILESYSTEMS && CONFIG_EXPERIMENTAL && ((CONFIG_9P_FS_MODULE && (CONFIG_FSCACHE_MODULE || CONFIG_FSCACHE)) || (CONFIG_9P_FS && CONFIG_FSCACHE)))";
			String str = "(CONFIG_ACPI && CONFIG_X86) -> CONFIG_ACPI_BATTERY";
			System.out.println("Bool Exp:" + str);
			
			LinkedList<Token> tokens = belexer.parse(str);
			
			for (Token tok : tokens) {
				System.out.println("" + tok.type + " " + tok.value);
			}
		} catch (ParserException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
}









