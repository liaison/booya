package booya;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import booya.tokenizer.Token;
import booya.BENode.BENodeType;
import booya.BoolExpLexer.TOKEN_TYPE;

/**
 * 
 * A parser to parse a bool expression into a binary tree. 
 * 
 * Input:  A && (B || !C) -> D 
 * output:   
 *               ->
 *            &&    D
 *         A     ||
 *             B    !
 *                C
 *                
 * @author Lisong Guo <lisong.guo@inria.fr>
 * @date   Nov 04, 2014
 */
public class BoolExpParser {
	private BoolExpLexer belexer = null;

	public BoolExpParser(){
		this.belexer = new BoolExpLexer();
	}
	
	
	public BENode parseBoolExp(String bestr){

		Stack<BENode> expStack = new Stack<BENode>();
		LinkedList<Token> tokens = this.belexer.parse(bestr);
		
		// The parenNode is used as a delimiter.
		BENode parenNode= new BENode();
		parenNode.setNodeType(BENodeType.PARENTHESE);
		
		for(Token t : tokens){
			
			TOKEN_TYPE e = TOKEN_TYPE.fromOrdinal(t.type);
			
			if(e == TOKEN_TYPE.L_PAREN){
				expStack.push(parenNode);
			
			}else if(e == TOKEN_TYPE.AND_OP){
				BENode node = new BENode();
				node.setNodeType(BENodeType.AND);
				expStack.push(node);
			
			}else if(e == TOKEN_TYPE.OR_OP){
				BENode node = new BENode();
				node.setNodeType(BENodeType.OR);
				expStack.push(node);
			
			}else if(e == TOKEN_TYPE.IMPLY_OP){
				BENode node = new BENode();
				node.setNodeType(BENodeType.IMPLY);
				expStack.push(node);
			
			}else if(e == TOKEN_TYPE.NOT_OP){
				BENode node = new BENode();
				node.setNodeType(BENodeType.NOT);
				expStack.push(node);
			
			}else if(e == TOKEN_TYPE.ID){
				
				BENode node = new BENode();
				node.setNodeType(BENodeType.VAR);
				node.setNodeValue(t.value);
				
				expStack.push(node);
				
				// The point to trigger the stack refining
				refineExpStack(expStack);
				
			}else if (e == TOKEN_TYPE.R_PAREN){
				expStack.push(parenNode);
				// Evaluate the subexpression enclosed by the parentheses first.
				evalSubExp(expStack);
				
				// Then trigger the refinement
				refineExpStack(expStack);
			}
			
			System.out.println("type:" + t.type + "\tvalue:" + t.value);
		
		}
		
		BENode root = expStack.pop();
		
		return root;
	}
	

	private static void evalSubExp(Stack<BENode> expStack){
		
		BENode parenNode = expStack.pop();
				
		int parenIndex = expStack.lastIndexOf(parenNode);
		
		//Evaluate the subexpression
		Stack<BENode> subExpStack = new Stack<BENode>();
		subExpStack.addAll(expStack.subList(parenIndex+1, expStack.size()));
		
		//TODO: remove me, Debugging
		System.out.println("p:" + parenIndex + " exp_size:" + expStack.size() + 
										    " subexp_size:" + subExpStack.size());
		//printExpStack(subExpStack);
		
		refineExpStack(subExpStack);
		BENode subExpNode = subExpStack.pop();
		
		//Remove the subexpression in the original stack.
		int count = expStack.size() - parenIndex;
		while( --count >= 0 ){
			expStack.pop();
		}
		
		//Put the root of the subexpression back to the original stack
		expStack.push(subExpNode);
		
		//System.out.println("After evalSubExp:");
		//printExpStack(expStack);
	}
	
	
	/**
	 *  Reduce/refine the expression in the stack recursively.
	 * @param expStack
	 */
	private static void refineExpStack(Stack<BENode> expStack){
		if(expStack.size() <= 1){
			//Nothing to reduce/refine
			return; 
		}
		
		BENode startNode = expStack.pop();
		
		BENode next = expStack.peek();
		BENode.BENodeType nextNodeType = next.getNodeType();
		
		// Binary operator  &&, ||, -> 
		if(nextNodeType == BENode.BENodeType.AND ||
		   nextNodeType == BENode.BENodeType.OR  ||
		   nextNodeType == BENode.BENodeType.IMPLY){
			
			BENode newRoot = expStack.pop();
			BENode leftChild = expStack.pop();
			newRoot.setLeft(leftChild);
			newRoot.setRight(startNode);
			
			expStack.push(newRoot);
			
		//Unary operator  !
		}else if(nextNodeType == BENode.BENodeType.NOT){
			BENode newRoot = expStack.pop();
			newRoot.setLeft(startNode);
			
			expStack.push(newRoot);
			
		}else if(nextNodeType == BENode.BENodeType.PARENTHESE){
			// Keep the left parenthese until reaching the right parenthese.
			expStack.push(startNode);
			return;
		}
		
		// Continue to refine recursively.
		refineExpStack(expStack);
	}


	public static void printExpStack(Stack<BENode> expStack){
		Iterator<BENode> iter = expStack.iterator();
		while(iter.hasNext()){
			BENode node = iter.next();
			System.out.println(node.getNodeType());
		}
	}
	
}

