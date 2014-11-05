package booya;

public class TestBoolExpParser {
	
	public static void main(String[] args){
		
		String boolExp = "A && B && C";

		BoolExpParser beparser = new BoolExpParser();
		BENode root = beparser.parseBoolExp(boolExp);
		
		root.printTree();
	}
}
