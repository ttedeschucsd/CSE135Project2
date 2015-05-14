/**
 * 
 */
package helpers;

/**
 * @author Jules Testard
 *
 */
public class ProductWithCategoryName {
	
	private int id;
	
	private String categoryName;
	
	private String name;
	
	private String SKU;
	
	private int price;

	/**
	 * @param id
	 * @param cid
	 * @param name
	 * @param sKU
	 * @param price
	 */
	public ProductWithCategoryName(int id, String categoryName, String name, String sKU, int price) {
		this.id = id;
		this.categoryName = categoryName;
		this.name = name;
		SKU = sKU;
		this.price = price;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the cid
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the sKU
	 */
	public String getSKU() {
		return SKU;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}
	
	
}
