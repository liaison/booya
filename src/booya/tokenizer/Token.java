package booya.tokenizer;


/**
 * A data entry to carry the type and the value for a token.
 * @author Lisong Guo <lisong.guo@inria.fr>
 * @date   Nov 03, 2014
 *
 */
public class Token {
	public final int type;
	public final String value;

	public Token(int type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

}
