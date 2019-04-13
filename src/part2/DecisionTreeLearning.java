package part2;
import java.util.*;
import java.io.*;

/**
 * @author karu
 *
 */
public class DecisionTreeLearning {
	private List<String> categoryNames;
	private List<String> attributeNames;
	private List<Instance> allInstances;
	private Node root;
	private Node baseLine;

	public DecisionTreeLearning(String fileName) {
		readDataFile(fileName);
		Set<Instance> setOfInstances = new HashSet<>(allInstances);
		List<String> attributeList = new ArrayList<>(attributeNames);
		root = buildTree(setOfInstances, attributeList);
		this.leafReport("", root);
		this.baseLine = this.getBaseLinePredictor();
		System.out.println("\n BaseLine accuracry " + this.baseLine.getProbability() * 100 + "%");

	}

	/**
	 * nonLeafReport from the hand out. simply traverses through every path starting
	 * from root.
	 * 
	 * @param indent
	 * @param n
	 */
	private void leafReport(String indent, Node n) {
		if (n.getLeftNode() != null) {
			System.out.format("%s%s = True:\n", indent, n.getAttribute());
			leafReport(indent + " ", n.getLeftNode());
		}
		if (n.getRightNode() != null) {
			System.out.format("%s%s = False:\n", indent, n.getAttribute());
			leafReport(indent + " ", n.getRightNode());
		}
		if (n.getLeftNode() == null && n.getRightNode() == null) {
			System.out.format("%sClass %s, prob = " + n.getProbability() * 100 + "%%\n", indent, n.getAttribute());
		}
	}

	/**
	 * @param instances
	 * @param attributes
	 * @return root
	 */
	private Node buildTree(Set<Instance> instances, List<String> attributes) {
		String bestAttribute = "";
		Set<Instance> bestTrueInstances = new HashSet<Instance>();
		Set<Instance> bestFalseInstances = new HashSet<Instance>();

		if (instances.isEmpty())
			return getBaseLinePredictor();

		if (categoryCheck(instances))
			return leafNodeContainingNameOfClass(instances);

		else if (attributes.isEmpty())// no more attributes to traverse threw
			return leafNodeOfMajorityClass(instances);
		else {
			double best = 1000;
			bestAttribute = "";
			for (String s : attributes) {
				Set<Instance> trueInstances = getTrueInstances(instances, s);
				Set<Instance> falseInstances = getFalseInstances(instances, s);
				double purity = computePurityOfAttribute(s, trueInstances, falseInstances);

				if (purity < best) {
					bestAttribute = s;
					best = purity;
					bestTrueInstances = trueInstances;
					bestFalseInstances = falseInstances;
				}
			}
		}
		attributes.remove(bestAttribute);// must be removed so that iteration is not performed on an attribute twice
		Node l = buildTree(bestTrueInstances, attributes);
		Node r = buildTree(bestFalseInstances, attributes);
		return new Node(bestAttribute, l, r);
	}

	/**
	 * @param instances
	 * @return true or false dependent on if category is pure
	 */
	private boolean categoryCheck(Set<Instance> instances) {
		int categoryZero = 0;
		int categoryOne = 0;

		for (Instance i : instances) {
			if (i.category == 0)
				categoryZero++;
			else
				categoryOne++;
		}
		if (categoryZero == 0 || categoryOne == 0)
			return true;

		return false;
	}

	private float computePurityOfAttribute(String s, Set<Instance> trueInstances, Set<Instance> falseInstances) {
		float tru = 0;
		float fal = 0;
		float catOneOnFalse = 0;
		float catTwoOnFalse = 0;
		float catOneOnTrue = 0;
		float catTwoOnTrue = 0;
		float aImpurity = 0;
		float bImpurity = 0;

		int l;
		for (l = 0; l < this.attributeNames.size(); l++) {
			if (this.attributeNames.get(l).equals(s))
				break;
		}

		for (Instance i : trueInstances) {
			if (i.gettAttribute(l)) {
				tru++;
				if (i.getCategory() == 0)
					catOneOnTrue++;
				else
					catTwoOnTrue++;
			}
		}
		for (Instance i : falseInstances) {
			if (!i.gettAttribute(l)) {
				fal++;
				if (i.getCategory() == 0)
					catOneOnFalse++;
				else
					catTwoOnFalse++;
			}
		}

		float probA = tru / (tru + fal);
		float probB = fal / (tru + fal);

		if (catOneOnTrue + catTwoOnTrue != 0) {
			aImpurity = 2 * (catOneOnTrue / (catOneOnTrue + catTwoOnTrue))
					* (catTwoOnTrue / (catOneOnTrue + catTwoOnTrue));
		}
		if (catTwoOnFalse + catOneOnFalse != 0)
			bImpurity = 2 * (catOneOnFalse / (catOneOnFalse + catTwoOnFalse))
					* (catTwoOnFalse / (catOneOnFalse + catTwoOnFalse));

		float d = ((probA * aImpurity) + (bImpurity * probB));

		return d;
	}

	/**
	 * Works the same as true instances and returns a new list of instances
	 * containing false instances and vice versa for the true instances method
	 * 
	 * @param instances
	 * @param attribute
	 * @return list of false instances
	 */
	private Set<Instance> getFalseInstances(Set<Instance> instances, String s) {
		Set<Instance> falseSet = new HashSet<>();
		int l;
		for (l = 0; l < this.attributeNames.size(); l++) {
			if (this.attributeNames.get(l).equals(s))
				break;
		}
		for (Instance i : instances) {
			if (!i.gettAttribute(l)) {
				falseSet.add(i);
			}
		}
		return falseSet;
	}

	/**
	 * @param instances
	 * @param attribute
	 * @return list of true instances
	 */
	private Set<Instance> getTrueInstances(Set<Instance> instances, String s) {
		Set<Instance> trueSet = new HashSet<>();
		int l;
		for (l = 0; l < attributeNames.size(); l++) {
			if (attributeNames.get(l).equals(s)) {
				break;
			}
		}
		for (Instance i : instances) {
			if (i.gettAttribute(l)) {
				trueSet.add(i);
			}
		}
		return trueSet;
	}

	/**
	 * returns a leaf node with the category that occurred the most in the given set
	 * of instances
	 * 
	 * @param instances
	 * @return
	 */
	private Node leafNodeOfMajorityClass(Set<Instance> instances) {
		double categoryZero = 0;
		double categoryOne = 0;
		for (Instance i : instances) {
			if (i.getCategory() == 0)
				categoryZero++;
			else
				categoryOne++;
		}
		if (categoryZero > categoryOne) {
			Node n = new Node(categoryNames.get(0), null, null);
			n.setProbability(categoryZero / (categoryZero + categoryOne));
			return n;
		} else {
			Node node = new Node(categoryNames.get(1), null, null);
			node.setProbability(categoryZero / (categoryZero + categoryOne));
			return node;
		}
	}

	/**
	 * this returns the pure classification and sets probability to 1.
	 * 
	 * @param instances
	 * @return
	 */
	private Node leafNodeContainingNameOfClass(Set<Instance> instances) {
		Instance inst = null;
		for (Instance i : instances)
			inst = i;

		Node node = new Node(categoryNames.get(inst.getCategory()), null, null);
		node.setProbability(1);
		return node;
	}

	private Node getBaseLinePredictor() {
		double categoryZero = 0;
		double categoryOne = 0;
		for (Instance i : this.allInstances) {

			if (i.getCategory() == 1)
				categoryOne++;
			else
				categoryZero++;

		}
		if (categoryZero >= categoryOne) {
			Node node = new Node(this.categoryNames.get(0), null, null);
			node.setProbability(categoryZero / (categoryZero + categoryOne));
			return node;
		} else {
			Node node = new Node(this.categoryNames.get(1), null, null);
			node.setProbability(categoryZero / (categoryZero + categoryOne));
			return node;
		}
	}

	/**
	 * Main method for determining a given instances class, traverses down the DT
	 * starting at the root node appropriately
	 * 
	 * @param instance
	 * @return
	 */
	public String getPredictedCategory(Instance instance) {
		Node n = root;
		while (!(n.getLeftNode() == null & n.getRightNode() == null)) {
			String s = n.getAttribute();
			int i;
			for (i = 0; i < allInstances.size(); i++) {
				if (attributeNames.get(i).equals(s))
					break;
			}
			if (instance.gettAttribute(i))
				n = n.getLeftNode();
			else if (!instance.gettAttribute(i))
				n = n.getRightNode();
		}
		return n.getAttribute();
	}

	private void readDataFile(String fname) {
		System.out.println("Reading data from file " + fname);
		try {
			Scanner din = new Scanner(new File(fname));
			categoryNames = new ArrayList<>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();)
				categoryNames.add(s.next());
			int numCategories = categoryNames.size();
			System.out.println(numCategories + " categories");

			attributeNames = new ArrayList<>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();)
				attributeNames.add(s.next());
			int numAtts = attributeNames.size();
			System.out.println(numAtts + " attributes");

			allInstances = readInstances(din);
			din.close();
		} catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
	}

	private List<Instance> readInstances(Scanner din) {
		List<Instance> instances = new ArrayList<>();
		while (din.hasNext()) {
			Scanner line = new Scanner(din.nextLine());
			instances.add(new Instance(categoryNames.indexOf(line.next()), line, this.categoryNames));
		}
		System.out.println("Read " + instances.size() + " instances");
		return instances;
	}
}