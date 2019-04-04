package part2;

public class Node {

	private String name;
	private Node left;
	private Node right;
	private double probability;// probability is set only for leaf nodes

	public Node(String bestAtt, Node l, Node r) {
		this.left = l;
		this.right = r;
		this.name = bestAtt;
	}

	/**
	 * @param n
	 */
	public void setLeftNode(Node n) {
		this.left = n;
	}

	/**
	 * @param n
	 */
	public void setRightNode(Node n) {
		this.right = n;
	}

	/**
	 * @return
	 */
	public Node getLeftNode() {
		return left;
	}

	/**
	 * @return
	 */
	public Node getRightNode() {
		return right;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
}
