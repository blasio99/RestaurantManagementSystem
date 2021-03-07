package bll;

import java.util.List;
/**
 * The restaurant interface which is implemented by the restaurant
 * and the GUI classes as well. It contains the main operations that
 * can be executed by the above mentioned classes
 * @author BB
 *
 */
public interface IRestaurantProcessing {
	/**
	 * creates a menu item, choosing the right type (composite or base)
	 * @pre the given item is not null
	 * @inv the menu item itself, putting in the set
	 * @post the difference between the old size and the new size is exactly 1
	 * @param item, the new item which is about to be added to the menu 
	 */
	public void   createMenuItem(MenuItem item);
	
	/**
	 * deletes a menu item, choosing the right type (composite or base)
	 * @pre the given item is not null
	 * @inv the menu item itself, putting in the set
	 * @post the difference between the old size and the new size is exactly minus1
	 * @param item, the selected item which is about to be deleted from the menu
	 */
	public void   deleteMenuItem(MenuItem item);
	
	/**
	 * updates a menu item, choosing the right type (composite or base)
	 * @pre the menu item is not null
	 * @inv the update method, itself
	 * @post the new price and the given item price are equal
	 * @param item, the selected item which is about to be updated in the menu
	 */
	public void   updateMenuItem(MenuItem item);
	
	/**
	 * creates a new order, required by the waiter, notifying the chef
	 * @pre the given order is not null
	 * @inv the order itself, putting in the map
	 * @post the given order and the created order are equals
	 * @param order, the information about the order
	 * @param menuItem, the list of menu items, ordered by the clients
	 */
	public void   createOrder(Order order, List<MenuItem> menuItem);
	
	/**
	 * computes the total price of an ordered, which has to be paid by the client
	 * @pre the given item and order are not null
	 * @inv the menu item itself, putting in the set
	 * @post the difference between the old size and the new size is exactly 1
	 * @param order, the information about the given order
	 * @return the total price of the order
	 */
	public double computeOrderPrice(Order order);
	
	/**
	 * generates the bill in .txt file
	 * @pre the given bill content (String) is not null
	 * @inv creating the bill.txt file
	 * @param content, the content printed in the text file
	 */
	public void   generateBill(String content);
}
