package bll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import dao.FileWriter;

/**
 * This class is the whole restaurant basically, containing the background 
 * data about its products.
 * This class extends the Observable, giving he notification to the
 * chefGUI, in case a new order is made.
 * 
 * It implements the processing interface of this project, the IRestaurantProcessing interface
 * 
 * It is also serializable, to save the data from it into a ".ser" file
 * @author BB
 *
 */
@SuppressWarnings("serial")
public class Restaurant extends Observable implements IRestaurantProcessing, java.io.Serializable{
	
	private HashSet<MenuItem> menu;
	private List<MenuItem> compositeItem;
	private HashSet<Order> orderList;
	private String compositeName;
	private HashMap<Order, List<MenuItem>> orderMap;
	/**
	 * the constructor for the Restaurant class
	 * it also initializes the gloabal variables
	 */
	public Restaurant() {
		menu = new HashSet<>();
		compositeItem = new ArrayList<>();
		orderList = new HashSet<>();
		orderMap = new HashMap<>();
	}
	/**
	 * This method adds the new order to the list, notifying the chef about it
	 * and passing the order as a String.
	 * @param order, the new order made by the waiter
	 */
	public void addOrderToList(Order order)
	{
		List<MenuItem> list = getOrderMap().get(order);
		orderList.add(order);
		
		StringBuilder sb = new StringBuilder();
		sb.append("--------------------------------------------------\n");
		sb.append("OrderID : " + order.getOrderID() + "\n");
		sb.append("Date    : " + order.getTime()    + "\n");
		sb.append("Table   : " + order.getTableNr() + "\n");
		sb.append("Ordered Items:\n");
		
		Iterator<MenuItem> iterator = list.iterator();
		while (iterator.hasNext()) 
			sb.append(iterator.next().toString() + "\n");
			
		setChanged();
		notifyObservers(sb.toString());
		
	}
	/**
	 * cretaing a new menu item using the Design by Contract method
	 * (which means to create some assertions)
	 */
	@Override
	public void createMenuItem(MenuItem item) {
		assert item != null;
		int sizeBefore = menu.size();
		menu.add(item);
		
		assert sizeBefore+1 == menu.size();
		
	}
	/**
	 * deleting a given, not null menu item
	 */
	@Override
	public void deleteMenuItem(MenuItem item) {
		assert item != null;
		
		int sizeBefore = menu.size();
		menu.remove(item);

		assert sizeBefore-1 == menu.size();
	}
	/**
	 * 
	 * updating a given, not null menu item with the newly computed price
	 */
	@Override
	public void updateMenuItem(MenuItem item) {
		assert item != null;
		double price = 0.0d;
		
		 
		Iterator<MenuItem> iterator = menu.iterator();
		while (iterator.hasNext()) {
			MenuItem currentItem = iterator.next();
			if (currentItem.getName() == item.getName()) {
					
				currentItem.setPrice(item.getPrice());		
				price = currentItem.getPrice();
			}
		}

		iterator = menu.iterator();
		while (iterator.hasNext()) {
			MenuItem currentItem = iterator.next();
			currentItem.setPrice(currentItem.computePrice());
		}
		
		assert price == item.getPrice();
		
	}
	/**
	 * the method for creating an order, made by the waiter, and adding to the list
	 * uses the Design by Contract method (assert)
	 */
	@Override
	public void createOrder(Order order, List<MenuItem> menuItem) {
		assert order != null;
		assert menuItem != null;
		
		Order o = order;
		orderMap.put(order, menuItem);
		
		assert o.equals(order);
	}
	/**
	 * computing the total price of the order
	 * it is iverriden from the IRestaurantProcessing interface
	 */
	@Override
	public double computeOrderPrice(Order order) {
		assert order != null;
		double total = 0.0d;
		Iterator<MenuItem> iterator = orderMap.get(order).iterator();
		
		while(iterator.hasNext())
			total += iterator.next().getPrice();
		
		assert total >= 0.0f;
		return total;
	}
	/**
	 * this overriden method is generating a new text file
	 * whch contains the data, information about the order
	 */
	@Override
	public void generateBill(String str) {
		assert str != null;
		
		new FileWriter(str);
	}
	/**
	 * in case of generate bill itt deletes the order
	 * @pre order is not null
	 * @inv deleting the given order itself
	 * @post the difference between the old size and the new is exactly 1
	 * @param order, the given order to be deleted
	 */
	public void deleteOrder(Order order) {
		assert order != null;
		
		int sizeBefore = orderMap.size();
		orderMap.remove(order, null);

		assert sizeBefore-1 == orderMap.size();
	}
	
	/**
	 * getter for the set of menu items
	 * @return the hashset type menu list
	 */
	public HashSet<MenuItem> getMenu() {
		return menu;
	}
	/**
	 * setter for the menu items
	 * @param menu, hashset menu set
	 */
	public void setMenu(HashSet<MenuItem> menu) {
		this.menu = menu;
	}
	/**
	 * getter for the list of composite items
	 * @return the lsit of the composite products
	 */
	public List<MenuItem> getCompositeItem() {
		return compositeItem;
	}
	/**
	 * setter for the composite item list
	 * @param compositeItem, the list of products
	 */
	public void setCompositeItem(List<MenuItem> compositeItem) {
		this.compositeItem = compositeItem;
	}
	/**
	 * getter for the lsit of orders
	 * @return the order list
	 */
	public HashSet<Order> getOrderList() {
		return orderList;
	}
	/**
	 * setter for the order list
	 * @param orderList, the new given order list
	 */
	public void setOrderList(HashSet<Order> orderList) {
		this.orderList = orderList;
	}
	/**
	 * getter for the composite menu name
	 * @return he name of composite menu
	 */
	public String getCompositeName() {
		return compositeName;
	}
	/**
	 * setter for the composite menu name
	 * @param compositeName, the new name of the composite menu
	 */
	public void setCompositeName(String compositeName) {
		this.compositeName = compositeName;
	}
	/**
	 * getter for the map of orders, which contains the list of ordered meu items
	 * @return the map of orders
	 */
	public HashMap<Order, List<MenuItem>> getOrderMap() {
		return orderMap;
	}
	/**
	 * setter for the map of orders, which contains the list of ordered meu items
	 * @param orderMap, the newly given map of orders
	 */
	public void setOrderMap(HashMap<Order, List<MenuItem>> orderMap) {
		this.orderMap = orderMap;
	}

	
	
}
