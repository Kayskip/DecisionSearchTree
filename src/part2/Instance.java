package part2;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author karu
 *
 */
public class Instance {
	public int category;
	private List<String> categoryNames;
	public List<Boolean> values;

	/**
	 * @param category
	 * @param scanner
	 * @param categoryNames
	 */
	public Instance(int category, Scanner scanner, List<String> categoryNames) {
		this.setCategoryNames(categoryNames);
		this.setCategory(category);
		this.values = new ArrayList<Boolean>();
		
		while (scanner.hasNextBoolean())
			values.add(scanner.nextBoolean());
	}

	/**
	 * @param index
	 * @return att
	 */
	public boolean gettAttribute(int index) {
		return values.get(index);
	}
	/**
	 * @param category
	 */
	public void setCategory(int category) {
		this.category = category;
	}
	/**
	 * @param categoryNames
	 */
	public void setCategoryNames(List<String> categoryNames) {
		this.categoryNames = categoryNames;
	}
	/**
	 * @return
	 */
	public int getCategory() {
		return category;
	}

	public String toString() {
		StringBuilder ans = new StringBuilder(categoryNames.get(category));
		ans.append(" ");
		for (Boolean val : values)
			ans.append(val ? "true  " : "false ");
		return ans.toString();
	}
}
