package bll;

import java.util.Iterator;
import java.util.List;

/**
 * This class creates composite menu items which basically
 * contains more base products, being able to create a menu
 * (e. g. soup, second plate, dessert, coke)
 * @author BB
 *
 */
@SuppressWarnings("serial")
public class CompositeProduct extends MenuItem{
	
	private List<MenuItem> compositeProduct; 
	
	/**
	 * the constructor for the composite product 
	 * @param name the title of the menu
	 * @param compProd the list of base products that contains the required menu
	 */
	public CompositeProduct(String name, List<MenuItem> compProd) {
		setName(name);
		setCompositeProduct(compProd);
	}
	
	/**
	 * ovverides the compute price method from the MenuItem
	 * to find how much does the menu cost exactly
	 * @return total the resulting total price of menu
	 */
	@Override
	public double computePrice() {
		double total = 0.0d;
		System.out.print("size " + this.getCompositeProduct().size() + " | ");
		Iterator<MenuItem> iterator = compositeProduct.iterator();
		while(iterator.hasNext()) 
			total += iterator.next().getPrice();
		System.out.println("price: " + total);
		return total;
	}
	
	/**
	 *  getter for compositeProduct
	 * @return compositeProduct, the list of products, the menu
	 */
	public List<MenuItem> getCompositeProduct() {
		return compositeProduct;
	}
	/**
	 * setter for compositeProduct
	 * @param compositeProduct, the list of products from the menu
	 */
	public void setCompositeProduct(List<MenuItem> compositeProduct) {
		this.compositeProduct = compositeProduct;
	}

}
