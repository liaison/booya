package booya;

public class TestBoolExpParser {
	
	public static void main(String[] args){
		
		//String boolExp = "A && B && C";
		String boolExp = " A && B -> (C || D)";
		
		
		BoolExpParser beparser = new BoolExpParser();
		BENode root = beparser.parseBoolExp(boolExp);
		
		System.out.println(boolExp);
		root.printTree();
		root.printAsGraph();
	}
}
