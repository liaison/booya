package booya.tokenizer;


/**
 * The source code is written by a blog in CogitoLearning 
 * 	following the link:  http://cogitolearning.co.uk/?p=525
 * 
 * The implemention was further refined by Lisong Guo
 *  
 * @author Lisong Guo <lisong.guo@inria.fr>
 * @date   Nov 03, 2014
 *
 */

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Tokenizer {
	private class TokenInfo {
		public final Pattern regex;
		public final int id;

		public TokenInfo(Pattern regex, int id) {
			super();
			this.regex = regex;
			this.id = id;
		}
	}

	private LinkedList<TokenInfo> tokenInfos;
	private LinkedList<Token> tokens;

	public Tokenizer() {
		tokenInfos = new LinkedList<TokenInfo>();
		tokens = new LinkedList<Token>();
	}

	public void add(String regex, int token) {
		tokenInfos
				.add(new TokenInfo(Pattern.compile("^(" + regex + ")"), token));
	}

	public void tokenize(String str) {
		String s = str.trim();
		tokens.clear();
		while (!s.equals("")) {
			boolean match = false;
			for (TokenInfo info : tokenInfos) {
				Matcher m = info.regex.matcher(s);
				if (m.find()) {
					match = true;
					String tok = m.group().trim();
					s = m.replaceFirst("").trim();
					tokens.add(new Token(info.id, tok));
					break;
				}
			}
			if (!match)
				throw new ParserException("Unexpected character in input: " + s);
		}
	}

	public LinkedList<Token> getTokens() {
		return tokens;
	}

}
