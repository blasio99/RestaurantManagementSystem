package control;

import java.util.HashSet;
import java.util.Iterator;

import bll.MenuItem;
import bll.Restaurant;
import dao.RestaurantSerializator;
import presentation.SelectorGUI;

public class Controller {
	private static Restaurant restaurant;
	private static int idCounter = 1;
	
	public Controller() {
		restaurant = RestaurantSerializator.deSerialize();
		setIdCounter();
		
		new SelectorGUI(restaurant);		
	}
	
	public static MenuItem findByName(String name) {
		Iterator<MenuItem> iterator = restaurant.getMenu().iterator();
		MenuItem foundItem;
		
		while(iterator.hasNext())
		{
			foundItem = iterator.next();
			if(foundItem.getName().compareTo(name) == 0) 
				return foundItem;
		}
		return null;
	}
	
	public static Object[][] records() {
		HashSet<MenuItem> menuList = restaurant.getMenu();
		Object[][] obj = new Object[menuList.size()][2];
		Iterator<MenuItem> iterator = menuList.iterator();
		int i = 0;
		
		while (iterator.hasNext()) {
			MenuItem currentItem = iterator.next();
			obj[i][0] = currentItem.getName();
			obj[i++][1] = currentItem.getPrice();
			
		}
		return obj;
	}
	
	public static void setIdCounter() {
		int max = 0;
		for(bll.Order o: restaurant.getOrderList()) {
			if(o.getOrderID() > max) max = o.getOrderID();
		}
		idCounter = max+1;
	}
	
	public static int createId() {
		return idCounter ++;
	}
}
