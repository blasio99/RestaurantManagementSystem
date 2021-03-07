package main;

import control.Controller;

public class Main {
	
	public static void main(String[] args) {
		if(args.length > 1) {
			System.out.println("Usage: java -jar PT2020_30424_Benedek_Balazs_Assignment_4.jar");
			System.out.println("   or: java -jar PT2020_30424_Benedek_Balazs_Assignment_4.jar restaurant.ser");
		}
		else if (args.length == 1 && !args[0].contentEquals("restaurant.ser")){
			System.out.println("Usage: java -jar PT2020_30424_Benedek_Balazs_Assignment_4.jar");
			System.out.println("   or: java -jar PT2020_30424_Benedek_Balazs_Assignment_4.jar restaurant.ser");
		}
		else{
			System.out.println("Application started");
			new Controller();
		}
		
	}

}
