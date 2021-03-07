package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import bll.Restaurant;

/**
 * It saves the information from the Restaurant class in a file 
 * (named restaurant.ser) using serialization. 
 * It loads the information when the application starts. If it exists.
 * @author BB
 *
 */
public class RestaurantSerializator {
	/**
	 * serialize method for saving data into ".ser" file 
	 * @param restaurant contains the menu items and orders
	 */
	public static void serialize(Restaurant restaurant) {
		try {
			FileOutputStream fileOut = new FileOutputStream("restaurant.ser");
			ObjectOutputStream out   = new ObjectOutputStream(fileOut);
			out.writeObject(restaurant);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in restaurant.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	/**
	 * Reading from the ".ser" file the saved items and orders
	 * @return value is the restaurant containing the saved data before 
	 */
	public static Restaurant deSerialize() {
		Restaurant restaurant = null;
		try {
			FileInputStream fileIn = new FileInputStream("restaurant.ser");
			ObjectInputStream in   = new ObjectInputStream(fileIn);
			restaurant = (Restaurant) in.readObject();
			in.close();
			fileIn.close();
			return restaurant;
		
		} catch (IOException i) {
			System.out.println(i);
			restaurant = new Restaurant();
			serialize(restaurant);
			return restaurant;
		
		} catch (ClassNotFoundException c) {

			System.out.println("ERROR (non-existing restaurant class)");
			c.printStackTrace();
			return restaurant = new Restaurant();
		}
	}
}
