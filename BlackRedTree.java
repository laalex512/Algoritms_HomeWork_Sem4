import java.util.ArrayList;
import java.util.List;

public class BlackRedTree {

	private Node root;
	public class Node {
		private int value;
		private Color color;
		private Node leftDaughter;
		private Node rightDaughter;

		@Override
		public String toString() {
			if (color == Color.BLACK) {
				return "B" + value;
			} else {
				return "R" + value;
			}
		}
	}

	public boolean add(int value){
		if (root != null){
			boolean result = addNode(root, value);
			root = rebalance(root);
			root.color = Color.BLACK;
			return result;
		} else {
			root = new Node();
			root.color = Color.BLACK;
			root.value = value;
			return true;
		}
	}
	private boolean addNode(Node node, int value){
		if (node.value == value){
			return false;
		}
		if (node.value > value){
			if (node.leftDaughter != null){
				boolean result = addNode(node.leftDaughter, value);
				node.leftDaughter = rebalance(node.leftDaughter);
				return result;
			} else {
				node.leftDaughter = new Node();
				node.leftDaughter.color = Color.RED;
				node.leftDaughter.value = value;
				return true;
			}
		} else {
			if (node.rightDaughter != null){
				boolean result = addNode(node.rightDaughter, value);
				node.rightDaughter = rebalance(node.rightDaughter);
				return result;
			} else {
				node.rightDaughter = new Node();
				node.rightDaughter.color = Color.RED;
				node.rightDaughter.value = value;
				return true;
			}
		}
	}

	private Node rebalance(Node node){
		Node outputNode = node;
		boolean needRebalance;
		do{
			needRebalance = false;
			if (conditionForRightSwap(outputNode)){
				needRebalance = true;
				outputNode = rightSwap(outputNode);
			}
			if (conditionForLeftSwap(outputNode)){
				needRebalance = true;
				outputNode = leftSwap(outputNode);
			}
			if (conditionForColorSwap(outputNode)){
				needRebalance = true;
				colorSwap(outputNode);
			}
		} while(needRebalance);
		return outputNode;
	}

	private void colorSwap(Node node){
		node.leftDaughter.color = Color.BLACK;
		node.rightDaughter.color = Color.BLACK;
		node.color = Color.RED;
	}

	private Node leftSwap(Node node){
		Node leftDaughter = node.leftDaughter;
		Node betweenDaughter = leftDaughter.rightDaughter;
		leftDaughter.rightDaughter = node;
		node.leftDaughter = betweenDaughter;
		leftDaughter.color = node.color;
		node.color = Color.RED;
		return leftDaughter;
	}

	private Node rightSwap(Node node){
		Node rightDaughter = node.rightDaughter;
		Node betweenDaughter = rightDaughter.leftDaughter;
		rightDaughter.leftDaughter = node;
		node.rightDaughter = betweenDaughter;
		rightDaughter.color = node.color;
		node.color = Color.RED;
		return rightDaughter;
	}

	private boolean conditionForLeftSwap(Node node){
		return node.leftDaughter != null && node.leftDaughter.color == Color.RED &&
			 node.leftDaughter.leftDaughter != null && node.leftDaughter.leftDaughter.color == Color.RED;
	}

	private boolean conditionForRightSwap(Node node){
		return node.rightDaughter != null && node.rightDaughter.color == Color.RED &&
			 (node.leftDaughter == null || node.leftDaughter.color == Color.BLACK);
	}

	private boolean conditionForColorSwap(Node node){
		return node.leftDaughter != null && node.leftDaughter.color == Color.RED &&
			 node.rightDaughter != null && node.rightDaughter.color == Color.RED;
	}

	public void printTree(){
		List<Node> currentLine = new ArrayList();
		currentLine.add(root);
		int countLines = 0;
		while (currentLine.size() > 0){
			countLines++;
			List<Node> nextLine = new ArrayList();
			for (Node node : currentLine){
				if (node.leftDaughter != null){
					nextLine.add(node.leftDaughter);
				}
				if (node.rightDaughter != null) {
					nextLine.add(node.rightDaughter);
				}
			}
			currentLine = nextLine;
		}
		currentLine.clear();
		currentLine.add(root);
		while (currentLine.size() > 0){
			List<Node> nextLine = new ArrayList();
			StringBuilder currentIndent = new StringBuilder();
			for (int i = 0; i < countLines * 3; i++){
				currentIndent.append(" ");
			}
			for (Node node : currentLine){
				System.out.print(currentIndent.toString() + node);
				if (node.leftDaughter != null){
					nextLine.add(node.leftDaughter);
				}
				if (node.rightDaughter != null) {
					nextLine.add(node.rightDaughter);
				}
			}
			System.out.println();
			currentLine = nextLine;
			countLines /= 2;
		}
		System.out.println("----------------------------------------------------");
	}

	private enum Color{
		 RED, BLACK
	}
}
