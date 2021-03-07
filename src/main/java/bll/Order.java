package bll;

import java.time.LocalDateTime;

/**
 * This class contains the information about orders
 * It is also serializable, to be able to save the orders as well
 * (I save the orders as well, to ensure that after some electronic problems
 * the stuff can recover the orders amde before)
 * @author BB
 *
 */
@SuppressWarnings("serial")
public class Order implements java.io.Serializable{
	
	private int orderID;
	private LocalDateTime time;
	private int tableNr;
	/**
	 * The constructor for the Order class
	 * @param orderID, the number of order (it is unique)
	 * @param time, the time of creating the order
	 * @param tableNr, the number of the table, where the order was made
	 */
	public Order(int orderID, LocalDateTime time, int tableNr) {
		setOrderID(orderID);
		setTime(time);
		setTableNr(tableNr);
	}
	/**
	 * the hashcode for the order, which are put into HashMap
	 * they differ by the orderID
	 * returns the hashcode
	 */
	@Override
	public int hashCode()
	{
		return getOrderID();
	}
	/**
	 * overrides the equals method which is used by the HashMap
	 * if something differs it returns fals, else returns true
	 */
	@Override
	public boolean equals(Object obj)
	{
		Order o = (Order) obj;
		
		if(obj  == null) return false;
		if(getOrderID() != o.orderID) return false;
		if(getTime()    != o.time)    return false;
		if(getTableNr() != o.tableNr) return false;
		
		return true;
		
	}
	/**
	 * getter for the orderID
	 * @return the ordinal of the order
	 */
	public int getOrderID() {
		return orderID;
	}
	/**
	 * setter for the orderID
	 * @param orderID, the ordinal number of the order
	 */
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	/**
	 * getter for the time value
	 * @return, the time of the order
	 */
	public LocalDateTime getTime() {
		return time;
	}
	/**
	 * setter for the time
	 * @param time, the actual time (in the moment of creating the order)
	 */
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	/**
	 * getter for the ordinal number of the table
	 * @return the number of table
	 */
	public int getTableNr() {
		return tableNr;
	}
	/**
	 * setter for the table number
	 * @param tableNr,the given table number
	 */
	public void setTableNr(int tableNr) {
		this.tableNr = tableNr;
	}
}
