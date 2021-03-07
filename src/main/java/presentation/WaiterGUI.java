package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import bll.IRestaurantProcessing;
import bll.MenuItem;
import bll.Order;
import bll.Restaurant;
import control.Controller;
import dao.RestaurantSerializator;

public class WaiterGUI implements ActionListener, IRestaurantProcessing{
	
	private static final Color MY_GREEN        = new Color(112,202,141);
	private static final Color MY_DARK_GREEN   = new Color(52, 142,81);
	private static final Color MY_DARKER_GREEN = new Color(2,  92, 31);
	private static final Color DARKER_GRAY     = new Color(55, 55, 55);
	private static final int NR_OF_BUTTONS = 4;
	private static final int NR_OF_LABELS  = 6;
	private static final int NR_OF_PANELS  = 4;
	private enum button {back, addItem, createOrder, generateBill}; 
	private enum label  {labelWaiter, labelItems, labelOrder, emptyLabel1, emptyLabel2, statusLabel}; 
	private enum panel  {mainPanel, topPanel, fillPanel, bottomPanel}; 
	private JButton [] buttons = new JButton[NR_OF_BUTTONS];
	private JLabel  [] labels  = new JLabel [NR_OF_LABELS];
	private JPanel  [] panels  = new JPanel [NR_OF_PANELS];
	
	private JFrame frame = new JFrame();
	private JComboBox<MenuItem> menuDropDown = new JComboBox<>();
	private JComboBox<String>  tableDropDown;
	private JTextArea   chosenItems = new JTextArea("Refresh then add items...");
	private JTextArea   textOrderNo = new JTextArea();
	private JScrollPane tableScroll;
	private JScrollPane chosenScroll;
	
	private Restaurant restaurant;
	private List<MenuItem> orderedItems = new ArrayList<MenuItem>();
	private List<Order>    orderList    = new ArrayList<Order>();
	
	public WaiterGUI(Restaurant restaurant) {
		setRestaurant(restaurant);
		initializeComponents();
		makeFrame(); //build the frame with style as well
		makePanel(); 
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		Object  source = e.getSource();
			 if(source == buttons[button.generateBill.ordinal()]) {generateBill(null);}
		else if(source == buttons[button.createOrder.ordinal()]) {createOrder(null, null);table();}
		else if(source == buttons[button.addItem.ordinal()]) {addItem();}
		else if(source == buttons[button.back.ordinal()]) {frame.dispose();}
		else System.out.println("You are a hacker, man, if you reached this part.");
		
		
	}
	
	private void makeFrame() {
		frame.setUndecorated(true);
		frame.setSize(700,800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Restaurant");
		frame.add(panels[panel.mainPanel.ordinal()]);
	}
	
	public void frameSetVisible(boolean visible) {
		fillMenu();
		table();
		frame.setVisible(visible);
	}
	
	private void makePanel() {
		
		style();
		fillMenu();
		
		panels[panel.mainPanel.ordinal()].add(panels[panel.topPanel.ordinal()]);
		panels[panel.mainPanel.ordinal()].add(panels[panel.fillPanel.ordinal()]);
		panels[panel.mainPanel.ordinal()].add(panels[panel.bottomPanel.ordinal()]);
		
		panels[panel.topPanel.ordinal()].add(buttons[button.back.ordinal()]);
		panels[panel.topPanel.ordinal()].add(labels[label.statusLabel.ordinal()]);
		
		panels[panel.fillPanel.ordinal()].add(labels[label.labelWaiter.ordinal()]);
		panels[panel.fillPanel.ordinal()].add(labels[label.labelItems.ordinal()]);
		panels[panel.fillPanel.ordinal()].add(tableDropDown);
		panels[panel.fillPanel.ordinal()].add(menuDropDown);
		panels[panel.fillPanel.ordinal()].add(chosenScroll, BorderLayout.EAST);
		//panels[panel.fillPanel.ordinal()].add(buttons[button.fill.ordinal()]);
		panels[panel.fillPanel.ordinal()].add(buttons[button.addItem.ordinal()]);
		//panels[panel.fillPanel.ordinal()].add(buttons[button.showButton.ordinal()]);
		panels[panel.fillPanel.ordinal()].add(buttons[button.createOrder.ordinal()]);
		panels[panel.fillPanel.ordinal()].add(labels[label.emptyLabel1.ordinal()]);
		panels[panel.fillPanel.ordinal()].add(labels[label.labelOrder.ordinal()]);
		panels[panel.fillPanel.ordinal()].add(textOrderNo);
		panels[panel.fillPanel.ordinal()].add(buttons[button.generateBill.ordinal()]);
		
		panels[panel.bottomPanel.ordinal()].add(labels[label.emptyLabel2.ordinal()]);
		
		buttons[button.back.ordinal()].addActionListener(this);
		//buttons[button.fill.ordinal()].addActionListener(this);
		buttons[button.createOrder.ordinal()].addActionListener(this);
		buttons[button.generateBill.ordinal()].addActionListener(this);
		buttons[button.addItem.ordinal()].addActionListener(this);
		//buttons[button.showButton.ordinal()].addActionListener(this);
		
		table();
		
	}
	
	private void style() {
		Border border = BorderFactory.createLineBorder(MY_DARKER_GREEN,4);
		String[] tableNumbers = {"1", "2", "3", "4", "5", "6", "7", "8"};
		tableDropDown = new JComboBox<String>(tableNumbers);
		
		panels[panel.mainPanel.ordinal()].setBackground(Color.DARK_GRAY);
		panels[panel.mainPanel.ordinal()].setBorder(border);
		
		panels[panel.topPanel.ordinal()].setPreferredSize(new Dimension(690, 110));
		panels[panel.topPanel.ordinal()].setBackground(Color.DARK_GRAY);
		
		panels[panel.fillPanel.ordinal()].setVisible(true);
		panels[panel.fillPanel.ordinal()].setPreferredSize(new Dimension(690, 300));
		panels[panel.fillPanel.ordinal()].setBackground(Color.DARK_GRAY);
		
		panels[panel.bottomPanel.ordinal()].setPreferredSize(new Dimension(650,350));
		panels[panel.bottomPanel.ordinal()].setBackground(Color.DARK_GRAY);
		
		componentStyle(menuDropDown, new Dimension(270, 35));
		componentStyle(chosenItems, new Dimension(650, 100));
		componentStyle(labels[label.statusLabel.ordinal()], new Dimension(642,55));
		componentStyle(tableDropDown, new Dimension(220, 35));
		componentStyle(labels[label.labelWaiter.ordinal()], new Dimension(220, 35));
		componentStyle(labels[label.labelOrder.ordinal()], new Dimension(120, 35));
		componentStyle(labels[label.labelItems.ordinal()], new Dimension(320, 35));
		componentStyle(textOrderNo, new Dimension(120, 35));
		componentStyle(labels[label.emptyLabel1.ordinal()], new Dimension(642, 15));
		componentStyle(labels[label.emptyLabel2.ordinal()], new Dimension(642, 55));
		componentStyleCorrection();
		
		setButtonStyle(buttons[button.back.ordinal()]);
		//setButtonStyle(buttons[button.fill.ordinal()]);
		setButtonStyle(buttons[button.createOrder.ordinal()]);
		setButtonStyle(buttons[button.generateBill.ordinal()]);
		setButtonStyle(buttons[button.addItem.ordinal()]);
		//setButtonStyle(buttons[button.showButton.ordinal()]);
		
		hoverButton(buttons[button.back.ordinal()]);
		//hoverButton(buttons[button.fill.ordinal()]);
		hoverButton(buttons[button.createOrder.ordinal()]);
		hoverButton(buttons[button.generateBill.ordinal()]);
		hoverButton(buttons[button.addItem.ordinal()]);
		//hoverButton(buttons[button.showButton.ordinal()]);
		
	}
	
	private void componentStyleCorrection() {
		chosenScroll = new JScrollPane(chosenItems);
		chosenScroll.setPreferredSize(new Dimension(540, 100));
		chosenScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chosenScroll.setVisible(true);
		chosenScroll.setSize(100,700);
		chosenItems.setBackground(DARKER_GRAY);
		chosenItems.setEditable(false); // set textArea non-editable
		chosenItems.setLineWrap(true);
		chosenItems.setWrapStyleWord(true);
		tableDropDown.setAlignmentX(SwingConstants.CENTER);
		textOrderNo.setBackground(DARKER_GRAY);
		labels[label.labelWaiter.ordinal()].setText("Table No:");
		labels[label.labelWaiter.ordinal()].setHorizontalAlignment(SwingConstants.CENTER);
		labels[label.labelOrder.ordinal ()].setText("Order No:");
		labels[label.labelItems.ordinal ()].setHorizontalAlignment(SwingConstants.CENTER);
	    labels[label.statusLabel.ordinal()].setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	private void componentStyle(JComponent c, Dimension dim) {
		Border labelBorder = BorderFactory.createLineBorder(Color.DARK_GRAY,2);
		Font font = new Font("Roboto", Font.BOLD,20);
		
		c.setBorder(labelBorder);
		c.setOpaque(true);
		c.setBackground(Color.DARK_GRAY);
		c.setPreferredSize(dim);
		c.setForeground(Color.LIGHT_GRAY);
		c.setFont(font);
	}
	
	
	
	private void setButtonStyle(JButton btn) {
		Font font = new Font("Roboto", Font.BOLD,20);
		btn.setBorderPainted(false);
		if ( btn.equals(buttons[button.back.ordinal()])) 
			 btn.setPreferredSize(new Dimension(100,40));
		else if ( btn.equals(buttons[button.generateBill.ordinal()]))
				  btn.setPreferredSize(new Dimension(160,43));
		else btn.setPreferredSize(new Dimension(270,43));
		btn.setBackground(MY_GREEN);
		btn.setForeground(Color.DARK_GRAY);
		btn.setFont(font);
		

	}
	
	private void hoverButton(JButton btn) {
		btn.addMouseListener(new MouseAdapter() {
	         Color oldcolor = btn.getBackground();
	         public void mouseEntered(MouseEvent me) {
	            oldcolor = btn.getBackground();
	            btn.setBackground(MY_DARK_GREEN);
	            
	         }
	         public void mouseExited(MouseEvent me) {
	            btn.setBackground(oldcolor);
	         }
	      });
	}
	
	public void fillMenu() {
		menuDropDown.removeAllItems();
		Iterator<MenuItem> it = restaurant.getMenu().iterator();

		while (it.hasNext()) {
			MenuItem curentItem = it.next();
			menuDropDown.addItem(curentItem);
		}
	}
	
	private void table() {
		
		if(tableScroll != null) panels[panel.bottomPanel.ordinal()].remove(tableScroll);
		
		JTable table;
		Border labelBorder = BorderFactory.createLineBorder(Color.DARK_GRAY,2);
		Font font = new Font("Roboto", Font.BOLD,20);
		Iterator<Order> iterator = restaurant.getOrderList().iterator();
		DefaultTableModel model = new DefaultTableModel();
		
		model.setColumnIdentifiers(new String[] {"No.", "Date", "Table", "Ordered items"});
		model.fireTableDataChanged();
		
		while (iterator.hasNext()) {
			Order o = iterator.next();
			StringBuilder sb = new StringBuilder();
			Iterator<MenuItem> it = restaurant.getOrderMap().get(o).iterator();
			
			while (it.hasNext()) {
				sb.append(it.next().toString());
				if (it.hasNext()) sb.append(" , ");
			}

			Object[] obj = {o.getOrderID(), o.getTime().toString(),
							o.getTableNr(), sb.toString()} ;
			
			model.addRow(obj);
		}
		
	    table = new JTable(model);
	    table.revalidate();
	    table.getTableHeader().setBackground(MY_GREEN);
	    table.getTableHeader().setBorder(labelBorder);
	    table.getTableHeader().setFont(font);
	    table.getTableHeader().setForeground(DARKER_GRAY);
	    table.setPreferredScrollableViewportSize(new Dimension(600,250));
	    table.setBackground(DARKER_GRAY);
	    table.setForeground(Color.LIGHT_GRAY);
	    table.setFillsViewportHeight(true);
	    table.setBounds(30, 40, 650, 200);
	    table.setBorder(labelBorder);
	    table.setRowHeight(30);
	    table.setFont(font);
	    
	    int[] columnWidth = {10, 100, 10, 300};
	    for(int i = 0; i < model.getColumnCount(); ++i)
	    	table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);
	    
	    tableScroll = new JScrollPane();
	    tableScroll.setBackground(Color.DARK_GRAY);
	    tableScroll.setVisible(true);
	    tableScroll.revalidate();
        tableScroll.setViewportView(table);
        
        panels[panel.bottomPanel.ordinal()].revalidate();
        panels[panel.bottomPanel.ordinal()].add(tableScroll, BorderLayout.CENTER);
        
	}
	
	public Order findOrderByID(int id) {
		Iterator<Order> it = orderList.iterator();
		while (it.hasNext()) {
			Order currentOrder = it.next();
			if (currentOrder.getOrderID() == id)
				return currentOrder;
		}
		return null;
	}
	
	@Override
	public void createMenuItem(MenuItem item) {
		JOptionPane.showMessageDialog(null, "You are not allowed to create item.");
	}
	
	@Override
	public void deleteMenuItem(MenuItem item) {
		JOptionPane.showMessageDialog(null, "You are not allowed to delete item.");	
	}
	
	@Override
	public void updateMenuItem(MenuItem item) {
		JOptionPane.showMessageDialog(null, "You are not allowed to update item.");
	}
	
	@Override
	public void createOrder(Order order, List<MenuItem> menuItem) {
		LocalDateTime date = LocalDateTime.now(); // Create a date object
		try {
			int orderID = Controller.createId();
			int table = Integer.parseInt(String.valueOf(tableDropDown.getSelectedItem()));
			
			order = new Order(orderID, date, table);
			menuItem = new ArrayList<>();
			
			Iterator<MenuItem> iterator = orderedItems.iterator();
			while (iterator.hasNext()) 
				menuItem.add(iterator.next());
			
			if(orderedItems.size()>0)
			{
				restaurant.createOrder(order, menuItem);
				orderedItems.removeAll(orderedItems);
				chosenItems.setText("");
				orderList.add(order);
				restaurant.addOrderToList(order);
				RestaurantSerializator.serialize(restaurant);
			}
			else
				JOptionPane.showMessageDialog(null, "The order list is empty.");
			
		}catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Bad input!");
		}
	}
	
	@Override
	public double computeOrderPrice(Order order) {
		return restaurant.computeOrderPrice(order);
	}
	
	@Override
	public void generateBill(String str) {
		int orderID = Integer.parseInt(textOrderNo.getText());
		Order order = findOrderByID(orderID);
		if (order == null)
			JOptionPane.showMessageDialog(null, "Order not found!");
		else {
			Map<Order, List<MenuItem>> map = restaurant.getOrderMap();
			Iterator<MenuItem> iterator = map.get(order).iterator();
			StringBuilder sb = new StringBuilder();
			String[] date = order.getTime().toString().split("T"); 
			
			sb.append(" ____________________________________\n");
			sb.append("|                                    |\n");
			sb.append("|                BILL                |\n");
			sb.append("|                                    |\n");
			sb.append("|         Restaurant Ciumani         |\n");
			sb.append("|____________________________________|\n");
			sb.append("|                                    |\n");
			sb.append("| OrderID: " + orderID);
			for(int i = 0; i<(18 - String.valueOf(orderID).length() - String.valueOf(order.getTableNr()).length()); ++i) sb.append(" ");
			sb.append("Table: " + order.getTableNr() + " |\n"); 
			sb.append("|        " + date[0] + "  " + date[1].substring(0,8));
			for(int i = 0; i< (31 - order.getTime().toString().length()); ++i) sb.append(" ");
			sb.append("|\n");
			sb.append("|____________________________________|\n");
			sb.append("|                                    |\n");
			
			while (iterator.hasNext()) {
				MenuItem mi = iterator.next();
				sb.append("| " + mi.getName() + " "); //+ myOrder.getTableNr() + "\n");
				for(int i = 0; i<(32 - mi.getName().length() - String.valueOf(mi.getPrice()).length() - 4); ++i) sb.append("-");
				sb.append(" " + mi.getPrice() + " RON |\n");
			}
			double total = computeOrderPrice(order);
			
			sb.append("|____________________________________|\n");
			sb.append("|                                    |\n");
			sb.append("| Total price: ");
			for(int i = 0; i<(20 - String.valueOf(total).length() - 4); ++i) sb.append("-");
			sb.append(" " + total + " RON |\n");
			sb.append("|____________________________________|\n");
			sb.append("|         Reaturant Ciumani          |\n");
			sb.append("|  Romania, HR, Ciumani 14, 537050   |\n");
			sb.append("|         Tel: +40753624586          |\n");
			sb.append("|      www.restaurantciumani.ro      |\n");
			sb.append("|====================================|\n");
			sb.append("|                                    |\n");
			sb.append("|                                    |\n");
			sb.append("|        ___________________         |\n");
			sb.append("|         (name, signature)          |\n");
			sb.append("|                                    |\n");
			sb.append("|                                    |\n");
			sb.append("|     Thank you for your visit!      |\n");
			sb.append("|         Have a nice day!           |\n");
			sb.append("|____________________________________|\n");

			restaurant.generateBill(sb.toString());
			restaurant.deleteOrder(order);
			JOptionPane.showMessageDialog(null, "Bill generated successfully !");

		}
		
	}
	
	public void addItem() {
		MenuItem item = (MenuItem) menuDropDown.getSelectedItem();
		if(chosenItems.getText().contentEquals("Refresh then add items..."))
			chosenItems.setText("");
		
		chosenItems.append(item.toString() + "\n");
		orderedItems.add(item);
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	private void initializeComponents() {
		//String[] buttonNames = {"Back", "Refresh List", "Add item",  "Show Orders", "Create Order", "Generate Bill"};
		String[] buttonNames = {"Back", "Add item", "Create Order", "Generate Bill"};
		String[] labelNames  = {"", "Refresh and Select:", "", "", "", "Hello, I'm waiting for orders :)"};
		
		for(int i = 0; i<NR_OF_BUTTONS; ++i) buttons[i] = new JButton(buttonNames[i]);
		for(int i = 0; i<NR_OF_LABELS;  ++i)  labels[i] = new JLabel(labelNames[i]);
		for(int i = 0; i<NR_OF_PANELS;  ++i)  panels[i] = new JPanel();
	}
}
