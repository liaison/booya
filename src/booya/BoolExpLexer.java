package booya;

import java.util.LinkedList;
import booya.tokenizer.*;


/**
 * A lexer to parse the tokens from a boolean expression.
 * @author Lisong Guo <lisong.guo@inria.fr>
 * @date   Nov 03, 2014
 *
 */

public class BoolExpLexer {
	public enum TOKEN_TYPE {
		L_PAREN, R_PAREN, NOT_OP, AND_OP, OR_OP, IMPLY_OP, ID;

		public static TOKEN_TYPE fromOrdinal(int x) {
			switch (x) {
			case 0:
				return L_PAREN;
			case 1:
				return R_PAREN;
			case 2:
				return NOT_OP;
			case 3:
				return AND_OP;
			case 4:
				return OR_OP;
			case 5:
				return IMPLY_OP;
			case 6:
				return ID;
			}
			return null;
		}
	};
	
	private Tokenizer tokenizer = null;
	
	
	public BoolExpLexer(){
		this.tokenizer = new Tokenizer();
		tokenizer.add("\\(", TOKEN_TYPE.L_PAREN.ordinal());
		tokenizer.add("\\)", TOKEN_TYPE.R_PAREN.ordinal());
		tokenizer.add("\\!", TOKEN_TYPE.NOT_OP.ordinal());
		tokenizer.add("\\&\\&", TOKEN_TYPE.AND_OP.ordinal());
		tokenizer.add("\\|\\|", TOKEN_TYPE.OR_OP.ordinal());
		tokenizer.add("\\->",   TOKEN_TYPE.IMPLY_OP.ordinal());
		tokenizer.add("[a-zA-Z0-9_]*", TOKEN_TYPE.ID.ordinal());
	}
	

	
	public LinkedList<Token> parse(String str){
		
		tokenizer.tokenize(str);

		return tokenizer.getTokens();
		
		/**
		for (Tokenizer.Token tok : tokenizer.getTokens()) {
			System.out.println("" + tok.token + " " + tok.sequence);
		}*/
	}
	
}




