package booya;
/**
 * A node that represents a unit in a boolean expression.
 * 
 * @author Lisong Guo <lisong.guo@inria.fr>
 * @author Jafar Al-Kofahi
 *
 * @date   Nov 02, 2014
 * 
 */
public class BENode {
	public enum BENodeType {AND, OR, NOT, INFER, VAR, PARENTHESE};
	
	private BENode left=null,right=null;
	
	public BENode getLeft() {
		return left;
	}

	public void setLeft(BENode left) {
		this.left = left;
	}

	public BENode getRight() {
		return right;
	}

	public void setRight(BENode right) {
		this.right = right;
	}

	private BENodeType type;
	
	// Only the "VAR" node would carry some string value, as the name of the variable. 
	private String value=null;
	
	public BENode(){		
	}
	
	
	public void setNodeType(BENodeType type){
		this.type = type;
	}
	
	public void setNodeValue(String value){
		this.value = value;
	}
	
	public String getNodeValue(){
		return value;
	}
	
	public BENodeType getNodeType(){
		return this.type;
	}
	
	
	
	@Override
	public String toString()
	{
		if(this.type==BENodeType.VAR)
			return value;
		else
		{
			String buffer="";
			if(this.type==BENodeType.NOT)
				buffer="!("+left.toString()+")";
			else
			{
				String op=(type==BENodeType.AND?" && ":" || ");
				buffer=left.toString()+op+right.toString();
			}
			return buffer;
		}	
	}
	
	private StringBuilder graph;
	private int graphNodeID;
	private void doPrintGraph(BENode node)
	{
		String self=graphNodeID+":"+node.getNodeType().toString();
		graphNodeID++;
		
		if(node.getLeft().getNodeType()==BENodeType.VAR)
			graph.append("\""+self+"\" -> \""+graphNodeID+":"+node.getLeft().getNodeValue() +"\";\r\n");
		else
		{
			String left=graphNodeID+":"+node.getLeft().getNodeType().toString();
			graph.append("\""+self+"\" -> \""+left +"\";\r\n");
			doPrintGraph(node.getLeft());			
		}
		graphNodeID++;
		
		if(node.getRight()==null)
			return;
		if(node.getRight().getNodeType()==BENodeType.VAR)
			graph.append("\""+self+"\" -> \""+graphNodeID+":"+node.getRight().getNodeValue() +"\";\r\n");
		else
		{
			String Right=graphNodeID+":"+node.getRight().getNodeType().toString();
			graph.append("\""+self+"\" -> \""+Right+"\";\r\n");
			doPrintGraph(node.getRight());			
		}
		graphNodeID++;
		
	}
	public void printAsGraph(){
		graphNodeID=1;
		graph=new StringBuilder();
		graph.append("digraph G {\r\n");
		doPrintGraph(this);
		graph.append("}");
		
		System.out.println("==============");
		System.out.println(graph.toString());
	}
	
	
	/**
	 *  Depth First Search to print all the nodes starting from this node.
	 *  Left first, then self, then right.
	 */
	public void printTree(){
		
		System.out.print("(");
		
		if(this.left != null){
			this.left.printTree();
			System.out.print("<--");
		}
		
		System.out.print("|" + this.type.toString() + "|");
		
		if(this.right != null){
			System.out.print("-->");
			this.right.printTree();
		}
	
		System.out.print(")");
	}
	
}









