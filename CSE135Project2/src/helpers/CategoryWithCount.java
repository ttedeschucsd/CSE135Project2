package helpers;

public class CategoryWithCount {
	
	private int id;
	
	private String name;
	
	private String description;
	
	private int count;

	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param count
	 */
	public CategoryWithCount(int id, String name, String description, int count) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.count = count;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
}
