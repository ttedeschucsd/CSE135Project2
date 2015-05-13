/**
 * 
 */
package helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jules Testard
 *
 */
public class ShoppingCart {
	
	private List<Integer> quantities;
	
	private List<ProductWithCategoryName> products;
	
	public ShoppingCart() {
		this.products = new ArrayList<ProductWithCategoryName>();
		this.quantities = new ArrayList<Integer>();
	}
	
	public void addToShoppingCart(int quantity, ProductWithCategoryName p) {
		this.products.add(p);
		this.quantities.add(quantity);
	}
	
	public void empty() {
		this.products = new ArrayList<ProductWithCategoryName>();
		this.quantities = new ArrayList<Integer>();
	}

	/**
	 * @return the quantities
	 */
	public List<Integer> getQuantities() {
		return quantities;
	}

	/**
	 * @param quantities the quantities to set
	 */
	public void setQuantities(List<Integer> quantities) {
		this.quantities = quantities;
	}

	/**
	 * @return the products
	 */
	public List<ProductWithCategoryName> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<ProductWithCategoryName> products) {
		this.products = products;
	}

}
