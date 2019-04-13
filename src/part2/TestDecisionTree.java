package part2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author karu
 *
 */
public class TestDecisionTree {

	private int numCategories;
	private int numAtts;
	private List<String> categoryNames;
	private List<String> attNames;
	private List<Instance> allInstances;

	public TestDecisionTree(String testing, String training) {
		this.readDataFile(testing);
		this.test(training);
	}

	/**
	 * @param training
	 */
	private void test(String training) {
		DecisionTreeLearning d = new DecisionTreeLearning(training);
		double correct = 0;
		for (int i = 0; i < this.allInstances.size(); i++) {
			String output = d.getPredictedCategory(allInstances.get(i));
			System.out.println("\n" + "Predicted category:" + output + " | Actual category: "
					+ categoryNames.get(this.allInstances.get(i).getCategory()));

			if (output.equals(categoryNames.get(this.allInstances.get(i).getCategory())))
				correct++;

		}

		System.out.println("\n" + "Decision Tree Success rate: " + 100 * (correct / allInstances.size()) + "%");
	}

	private void readDataFile(String fname) {
		System.out.println("Reading data from file " + fname);
		try {
			Scanner din = new Scanner(new File(fname));
			categoryNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();)
				categoryNames.add(s.next());
			numCategories = categoryNames.size();
			System.out.println(numCategories + " categories");

			attNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();)
				attNames.add(s.next());
			numAtts = attNames.size();
			System.out.println(numAtts + " attributes");

			allInstances = readInstances(din);
			din.close();
		} catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
	}

	private List<Instance> readInstances(Scanner din) {
		List<Instance> instances = new ArrayList<Instance>();
		String ln;
		while (din.hasNext()) {
			Scanner line = new Scanner(din.nextLine());
			instances.add(new Instance(categoryNames.indexOf(line.next()), line, categoryNames));
		}
		System.out.println("Read " + instances.size() + " instances");
		return instances;
	}

	public static void main(String[] args) {
		new TestDecisionTree(args[0], args[1]);
	}

}
