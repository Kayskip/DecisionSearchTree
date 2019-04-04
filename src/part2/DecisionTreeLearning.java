package part2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DecisionTreeLearning {

	private ArrayList<String> categoryNames;
	private int numCategories;
	private ArrayList<String> attNames;
	private int numAtts;
	private List<Instance> allInstances;
	private Node root;

	
	public DecisionTreeLearning(String testing, String training) {
		this.readDataFile(training);
		this.readDataFile(testing);
		
	}
	/**
	 * readDataFile Method taken from the helper-code
	 * @param file name
	 */
	private void readDataFile(String filename) {
		System.out.println("Reading data from file " + filename);
		try {
			Scanner din = new Scanner(new File(filename));
			this.categoryNames = new ArrayList<String>();

			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) {
				this.categoryNames.add(s.next());
			}
			this.numCategories = this.categoryNames.size();

			System.out.println(this.numCategories + " categories");

			this.attNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) {
				this.attNames.add(s.next());
			}
			this.numAtts = this.attNames.size();
			System.out.println(numAtts + " attributes");

			this.allInstances = readInstances(din);
			din.close();
		} catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
	}
	/*
	 * readInstances is parsed the scanner
	 * then iterates through and stores the attributes and the line 
	 * in an instance
	 * @param din scanner
	 * @return list of instances to be stored in the field
	 */
	private List<Instance> readInstances(Scanner din) {
	    List<Instance> instances = new ArrayList<>();
        while (din.hasNext()) {
            Scanner line = new Scanner(din.nextLine());
            instances.add(new Instance(numAtts, line));
        }
        System.out.println("Read " + instances.size() + " instances");
        return instances;
	}

	public static void main(String[] args) {
		new DecisionTreeLearning(args[0],args[1]);
	}
}
