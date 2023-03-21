import java.util.ArrayList;
import java.util.List;

public class Tree {
	Node root;
	public class Node {
		int value;
		List<Node> children;
	}

	private Node depthFind(Node node, int value){
		if (node.value == value){
			return node;
		}
		for (Node child : node.children){
			Node result = depthFind(child, value);
			if (result != null){
				return result;
			}
		}
		return null;
	}

	public boolean isExist(int value){
		if (root != null){
			Node result = depthFind(root, value);
			if (result != null){
				return true;
			}
		}
		return false;
	}

	private Node widthFind (int value){
		List<Node> currentLine = new ArrayList<>();
		currentLine.add(root);
		while (currentLine.size() > 0){
			List<Node> nextLine = new ArrayList<>();
			for (Node node : currentLine){
				if (node.value == value){
					return node;
				}
				nextLine.addAll(node.children);
			}
			currentLine = nextLine;
		}
		return null;
	}
}
