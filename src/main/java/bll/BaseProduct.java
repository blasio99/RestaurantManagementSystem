package bll;

/**
 * BaseProduct class which extends the MenuItem class
 * this kind of product being a type of menu item 
 * @author BB
 *
 */
@SuppressWarnings("serial")
public class BaseProduct extends MenuItem{
	/**
	 * the constructor for a base product 
	 * @param name gives the title of the product
	 * @param price gives how much does the product cost
	 */
	public BaseProduct(String name, double price) {
		//super(name, price);
		setName(name);
		setPrice(price);
	}
	/**
	 * overrides the computePrice() method from the menuItem
	 * @return the price of the required product
	 */
	@Override
	public double computePrice() {
		return getPrice();
	}
	
	

}
