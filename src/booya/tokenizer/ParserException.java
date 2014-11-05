package booya.tokenizer;


/**
 * The source code is written by a blog in CogitoLearning following the link:
 * http://cogitolearning.co.uk/?p=525
 * The implemention was further refined by Lisong Guo
 *
 * @author Lisong Guo <lisong.guo@inria.fr>
 * @date Nov 03, 2014
 * 
 */

public class ParserException extends RuntimeException {
	public ParserException(String msg) {
		super(msg);
	}
}