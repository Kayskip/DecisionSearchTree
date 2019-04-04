package part2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class DecisionTreeLearning {
    private List<Instance> trainInstances;
    private List<Instance> testInstances;
    
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
	public static void main(String[] args) {
		new DecisionTreeLearning(args[0],args[1]);
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
	    List<Instance> instances = new ArrayList<Instance>();
	    String ln;
	    while (din.hasNext()){ 
	      Scanner line = new Scanner(din.nextLine());
	      instances.add(new Instance(categoryNames.indexOf(line.next()),line));
	    }
	    System.out.println("Read " + instances.size()+" instances");
	    return instances;
	}
	
	/**
	 * @param instances
	 * @param attributes
	 * @return
	 */
	public Node BuildTree(List<Instance> instances, List<String> attributes) {
		String bestAttribute = null;
		double bestWeightedAverageImpurity = Double.MAX_VALUE;
		List<Instance> bestTrueInstances = new ArrayList<Instance>();
		List<Instance> bestFalseInstances = new ArrayList<Instance>();
		
		if(instances.isEmpty()) {
			return baseLinePredictor(instances);
		}
		else if(computePurity(instances) == 0){
			return new Node(this.categoryNames.get(instances.get(0).getCategory()),null,null,true);
		}
		else if(attributes.isEmpty()) {
			return majorityClass(instances);
		}
		else {
			for(String a: attributes) {
				List<Instance> trueInstances = getTrueInstances(instances);
				List<Instance> falseInstances = getFalseInstances(instances);
				
				double truePurity = computePurity(trueInstances);
				double falsePurity = computePurity(falseInstances);
				
				double weightedAverageImpurity = calculatedWeightedImpurity(truePurity, falsePurity,trueInstances,falseInstances);
				
				if(weightedAverageImpurity < bestWeightedAverageImpurity) {
					bestAttribute = a;
					bestWeightedAverageImpurity = weightedAverageImpurity;
					bestTrueInstances = trueInstances;
					bestFalseInstances = falseInstances;
				}
			}
			attributes.remove(bestAttribute);
			Node left = BuildTree(bestTrueInstances, attributes);
			Node right = BuildTree(bestFalseInstances, attributes);
			return new Node (bestAttribute, left,right, false);
		}
	
	}
	private double calculatedWeightedImpurity(double truePurity, double falsePurity, List<Instance> trueInstances,
			List<Instance> falseInstances) {
		// TODO Auto-generated method stub
		return 0;
	}
	private List<Instance> getFalseInstances(List<Instance> instances) {
		// TODO Auto-generated method stub
		return null;
	}
	private List<Instance> getTrueInstances(List<Instance> instances) {
		// TODO Auto-generated method stub
		return null;
	}
	private double computePurity(List<Instance> instances) {
        double m = 0;
        double n = 0;
        for (Instance instance : instances) {
            if (instance.getCategory() == 0) m++;
            if (instance.getCategory() == 1) n++;
        }
        
        return 2*(m/(m+n))*((n/(m+n)));
	}


	private Node majorityClass(List<Instance> instances) {
		 double categoryOne=0;
	        double categoryTwo=0;
	        for(Instance i:instances) {
	            if(i.getCategory()==0)
	                categoryOne++;
	            else
	                categoryTwo++;
	        }
	        if(categoryOne > categoryTwo) {
	            Node n= new Node(categoryNames.get(0),null,null,true);
	            n.setProbability(categoryOne/(categoryOne+categoryTwo));
	            return n;
	        }
	        else {
	            Node n= new Node(categoryNames.get(1),null,null,true);
	            n.setProbability(categoryTwo/(categoryOne+categoryTwo));
	            return n;
	        }
		
	}
	public Boolean allInstancesArePure(Set<Instance> instances) {
		return true;

	}
	public Node baseLinePredictor(List<Instance> instances) {
		double trainingSize = (double) instances.size();
		int categoryZero = 0;
		int categoryOne = 0;
		
		
		for(Instance instance : instances) {
			if(instance.getCategory() == 0) {
				categoryZero++;
			}
			else if(instance.getCategory() == 1) {
				categoryOne++;
			}
		}
		
        if(categoryZero > categoryOne) {
            Node node = new Node(this.categoryNames.get(0),null,null,true);
            node.setProbability(categoryZero/(trainingSize));
            return node;
        }
        else {
            Node node =  new Node(this.categoryNames.get(1),null,null,true);
            node.setProbability(categoryOne/(trainingSize));
            return node;
        }
        
	}

}
