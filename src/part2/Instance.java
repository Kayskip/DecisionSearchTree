package part2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author karu
 *
 */
public class Instance {

	private int category;
	private List<Boolean> values;

	/**
	 * @param cat
	 * @param s
	 */
	public Instance(int category, Scanner scanner) {
		this.category = category;
		this.values = new ArrayList<Boolean>();

		while (scanner.hasNextBoolean()) {
			this.values.add(scanner.nextBoolean());
		}
	}

	/**
	 * @param index
	 * @return
	 */
	public boolean getAttributes(int index) {
		return this.values.get(index);
	}

	/**
	 * @return category
	 */
	public int getCategory() {
		return this.category;
	}

	public String toString() {
		/*
		 * StringBuilder ans = new StringBuilder(categoryNames.get(category));
		 * ans.append(" "); for (Boolean val : vals) ans.append(val?"true  ":"false ");
		 * return ans.toString();
		 */
		return "";
	}

}
