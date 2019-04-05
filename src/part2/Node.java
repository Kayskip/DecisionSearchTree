package part2;

public class Node {

	private String attribute;
	private Node left;
	private Node right;
	private double probability;// probability is set only for leaf nodes
	private boolean isLeaf;

	public Node(String bestAtt, Node l, Node r,boolean isLeaf) {
		this.left = l;
		this.right = r;
		this.setAttribute(bestAtt);
		this.setLeaf(isLeaf);
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


	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

}
