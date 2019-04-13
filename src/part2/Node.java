package part2;

/**
 * @author karu
 *
 */
public class Node {
	private String attribute;
	private Node left;
	private Node right;
	private int category;
	private double probability;

	/**
	 * @param attribute
	 * @param left
	 * @param right
	 */
	public Node(String attribute, Node left, Node right) {
		this.setLeftNode(left);
		this.setRightNode(right);
		this.setAttribute(attribute);
	}

	/**
	 * @param node
	 */
	public void setLeftNode(Node n) {
		this.left = n;
	}

	/**
	 * @param node
	 */
	public void setRightNode(Node n) {
		this.right = n;
	}

	/**
	 * @return left node
	 */
	public Node getLeftNode() {
		return left;
	}

	/**
	 * @return right node
	 */
	public Node getRightNode() {
		return right;
	}

	/**
	 * @return attribute
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * Sets attribute safely
	 * @param attribute
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * Sets category safely
	 * @param category
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * @return probability
	 */
	public double getProbability() {
		return probability;
	}

	/**
	 * @param probability
	 */
	public void setProbability(double probability) {
		this.probability = probability;
	}

}
