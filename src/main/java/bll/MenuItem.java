package bll;

/**
 * This abstract class is the part of the Composite Design Pattern
 * it is extended by the base product and composite product classes.
 * It is also serializable, to be able to save the data from here
 * @author BB
 *
 */
@SuppressWarnings("serial")
public abstract class MenuItem implements java.io.Serializable {

	private String name;
	private double price;
	/**
	 * the hashcode for the hashSet and hashMap, ow to choose the elements
	 * that can be or not part of the list.
	 * The menu items differ by name AND price (together)
	 */
	@Override
	public int hashCode()
	{
		int  hashcode = 1;
		hashcode += ((name == null) ? 0 : name.hashCode());
		hashcode += price;
		return hashcode;
		
	}
	/**
	 * it overrides the equals to be able to compare menuitems by its correct way
	 * if there is something different, it returns false, else returns true
	 */
	@Override
	public boolean equals(Object obj)
	{
		MenuItem mi = (MenuItem) obj;
		
		if(obj  == null) return false;
		if(!this.getClass().equals(obj.getClass())) return false;
		
		if(!getName().contentEquals( mi.getName()))  return false;
		if(getPrice() != mi.getPrice()) return false;
		return true;
		
	}
	/**
	 * overrides the toString() function to convert menu items into text nicely
	 * @return a string which represents the menu item
	 */
	@Override
	public String toString() {
		return getName() + " " + String.valueOf(getPrice());
	}
	/**
	 * the method inherited by BaseProduct and CompositeProduct classes
	 * @return the total price of a produuct
	 */
	public abstract double computePrice();
	
	/**
	 * getter for the name of menu item
	 * @return the name (String value)
	 */
	public String getName() {
		return name;
	}
	/**
	 * setter for the name of product 
	 * @param name, the title of the product/item
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * getter for the price of menu item
	 * @return the price (int) of the product
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * setter for he price of the product
	 * @param price, how much does it cost
	 */
	public void setPrice(double price) {
		this.price = price;
	}
}
