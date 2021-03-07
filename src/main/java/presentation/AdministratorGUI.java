package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import bll.BaseProduct;
import bll.IRestaurantProcessing;
import bll.MenuItem;
import bll.Order;
import bll.Restaurant;
import control.Controller;
import dao.RestaurantSerializator;

/**
 * 
 * @author BB
 *
 */
public class AdministratorGUI implements ActionListener, IRestaurantProcessing{
	private static final Color MY_GREEN        = new Color(112,202,141);
	private static final Color MY_DARK_GREEN   = new Color(52,142,81);
	private static final Color MY_DARKER_GREEN = new Color(2,92,31);
	private static final Color DARKER_GRAY     = new Color(55,55,55);
	public  static JLabel statusLabel          = new JLabel("Dear Admin, just do it.",JLabel.CENTER);
	public  static JLabel compositeLabel       = new JLabel("Create a menu from multiple menu items:",JLabel.CENTER);
	private JFrame  frame = new JFrame();
	private JLabel  labelMenuItem  = new JLabel();
	private JLabel  labelItemPrice = new JLabel();
	private JLabel  emptyLabel1   = new JLabel();
	private JLabel  emptyLabel2  = new JLabel();
	private JPanel  mainPanel   = new JPanel();
	private JPanel  topPanel   = new JPanel();
	private JPanel  fillPanel   = new JPanel();
	private static JPanel  bottomPanel  = new JPanel();
	private JButton back   = new JButton("Back");
	private JButton create = new JButton("Create Item");
	private JButton update = new JButton("Update Item");
	private JButton delete = new JButton("Delete Item");
	private JButton show   = new JButton("Refresh Menu");
	private JButton compositeItem   = new JButton("Create an entire menu"); 
	private JTextArea textMenuItem  = new JTextArea();
	private JTextArea textItemPrice = new JTextArea();
	private static JScrollPane scroll;
	private static Object[][]  data ;
	private static Restaurant  restaurant;
	private CompositeMenuItemGUI compositeMenuItemGUI;
	
	public AdministratorGUI(Restaurant restaurant) {
		AdministratorGUI.restaurant = restaurant;
		compositeMenuItemGUI = new CompositeMenuItemGUI(restaurant);
		makeFrame(); //build the frame with style as well
		makePanel(); //builds the styled panel
		
	}
	
	private void makeFrame() {
		frame.setUndecorated(true);
		frame.setSize(700,800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Restaurant");
		frame.add(mainPanel);
	}
	
	public void frameSetVisible(boolean visible) {
		frame.setVisible(visible);
	}
	
	private void makePanel() {
		
		style();
		
		mainPanel.add(topPanel);
		mainPanel.add(fillPanel);
		mainPanel.add(bottomPanel);
		
		topPanel.add(back);
		topPanel.add(statusLabel);
		
		fillPanel.add(labelMenuItem);
		fillPanel.add(textMenuItem);
		fillPanel.add(labelItemPrice);
		fillPanel.add(textItemPrice);
		
		//bottomPanel.add(emptyLabel1);
		bottomPanel.add(create);
		bottomPanel.add(update);
		bottomPanel.add(delete);
		bottomPanel.add(show);
		//bottomPanel.add(emptyLabel2);
		bottomPanel.add(compositeLabel);
		bottomPanel.add(compositeItem);
		bottomPanel.add(emptyLabel2);
		
		table();
		
		compositeItem.addActionListener(this);
		back.addActionListener(this);
		create.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		show.addActionListener(this);
		
	}
	
	private void style() {
		Border border = BorderFactory.createLineBorder(MY_DARKER_GREEN,4);
		mainPanel.setBorder(border);
		mainPanel.setBackground(Color.DARK_GRAY);
		
		topPanel.setPreferredSize(new Dimension(690, 100));
		topPanel.setBackground(Color.DARK_GRAY);
		
		fillPanel.setLayout(new GridLayout(2,2, 10, 10));
		fillPanel.setBackground(Color.DARK_GRAY);
		
		bottomPanel.setPreferredSize(new Dimension(600,575));
		bottomPanel.setBackground(Color.DARK_GRAY);
		
		componentStyle(statusLabel, new Dimension(642, 30));
		componentStyle(compositeLabel, new Dimension(642, 60));
		componentStyle(emptyLabel1, new Dimension(642, 55));
		componentStyle(emptyLabel2, new Dimension(642, 55));
		componentStyle(labelMenuItem, new Dimension(120, 35));
		componentStyle(textMenuItem, new Dimension(220, 35));
		componentStyle(labelItemPrice, new Dimension(220, 35));
		componentStyle(textItemPrice, new Dimension(120, 45));
		componentStyleCorrection();
		
		setButtonStyle(back);
		setButtonStyle(create);
		setButtonStyle(update);
		setButtonStyle(delete);
		setButtonStyle(show);
		setButtonStyle(compositeItem);
		
		hoverButton(back);
		hoverButton(create);
		hoverButton(update);
		hoverButton(delete);
		hoverButton(show);	
		hoverButton(compositeItem);
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
	
	private void componentStyleCorrection() {
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statusLabel.setForeground(MY_DARK_GREEN);
		compositeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		compositeLabel.setForeground(Color.LIGHT_GRAY);
		
		labelMenuItem.setText("Menu Item:");
		labelMenuItem.setHorizontalAlignment(SwingConstants.CENTER);
		labelItemPrice.setText("Price of it:");
		labelItemPrice.setHorizontalAlignment(SwingConstants.CENTER);
		textMenuItem.setBackground(DARKER_GRAY);
		textItemPrice.setBackground(DARKER_GRAY);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == compositeItem) {compositeMenuItemGUI.setFrameVisible(true);}
		else if(source == create) {createMenuItem(null);}
		else if(source == update) {updateMenuItem(null);}
		else if(source == delete) {deleteMenuItem(null);}
		else if(source == back)   {frame.dispose();}
		else if(source == show)   {table();}
		else System.out.println("You are a hacker, man.");
	}
	
	private void setButtonStyle(JButton btn) {
		Font font = new Font("Roboto", Font.BOLD,25);
		btn.setBorderPainted(false);
		if ( btn.equals(back)) 
			 btn.setPreferredSize(new Dimension(100,40));
		else if(btn.equals(compositeItem))
				btn.setPreferredSize(new Dimension(430,40));
		else btn.setPreferredSize(new Dimension(210,43));
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
	
	public static void table() {
		
		String[] columns = new String[] {"Menu Item", "Price"};
		DefaultTableModel myModel = new DefaultTableModel();
		myModel.setColumnIdentifiers(columns);  
		myModel.fireTableDataChanged();
		
		data = Controller.records() ;
		for(Object[] o: data) myModel.addRow(o);
		
		JTable table = new JTable(myModel);
	    tableStyle(table);
	    
	    scroll = new JScrollPane();
	    scroll.setBackground(Color.DARK_GRAY);
	    scroll.setViewportView(table);
	    scroll.setVisible(true);
	    scroll.revalidate();
        
        bottomPanel.revalidate();
        bottomPanel.add(scroll, BorderLayout.CENTER);

	}
	
	private static void tableStyle(JTable table) {
		if(scroll != null) 	bottomPanel.remove(scroll);
		
		Border labelBorder = BorderFactory.createLineBorder(Color.DARK_GRAY,2);
		Font font = new Font("Roboto", Font.BOLD,20);
		
		tableCellUpdate(table);
		table.setPreferredScrollableViewportSize(new Dimension(450,250));
	    table.getTableHeader().setForeground(DARKER_GRAY);
	    table.getTableHeader().setBackground(MY_GREEN);
	    table.getTableHeader().setBorder(labelBorder);
	    table.getTableHeader().setFont(font);
	    table.setFillsViewportHeight(true);
	    table.setForeground(Color.LIGHT_GRAY);
	    table.setBackground(Color.DARK_GRAY);
	    table.setBounds(30, 40, 600, 300);
	    table.setBorder(labelBorder);
	    table.setRowHeight(30);
	    table.setFont(font);
	    table.revalidate();
	    
        int[] columnWidth = {300, 200};
        for(int i = 0; i < 2; ++i)
        	table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);;
	}
	
		 
        
	private static void tableCellUpdate(JTable table) {
		table.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
		    	  //List<MenuItem> list = 
				String selectedData = "";
				selectedData = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
				int selectedRow = table.getSelectedRow();
				int selectedColumn = table.getSelectedColumn();
				switch(selectedData) {
				case "":JOptionPane.showMessageDialog(null, "Item update ERROR!");
					break;
				default:
					if(selectedColumn == 0) {
						restaurant.updateMenuItem(new BaseProduct(selectedData, Double.valueOf(String.valueOf(table.getValueAt(selectedRow, 1)))));
						RestaurantSerializator.serialize(restaurant);
						JOptionPane.showMessageDialog(null, "Item update finished successfully!");		
					}
					else {
				    	restaurant.updateMenuItem(new BaseProduct(String.valueOf(table.getValueAt(selectedRow, 0)), Double.valueOf(selectedData)));
						RestaurantSerializator.serialize(restaurant);
						JOptionPane.showMessageDialog(null, "Item update finished successfully!");	
					}
						
				}
			}
			
		});
	}
	
	
	@Override
	public void createMenuItem(MenuItem item) {
		String itemName = textMenuItem.getText();
		double itemPrice;
		try {
			 itemPrice = Double.parseDouble(textItemPrice.getText());
			 if(itemPrice>=0.0d) {
				 int oldSize = restaurant.getMenu().size();
				 restaurant.createMenuItem(new BaseProduct(itemName, itemPrice));
				 if(oldSize + 1 == restaurant.getMenu().size()) {
					 RestaurantSerializator.serialize(restaurant);
					 table();
					 JOptionPane.showMessageDialog(null, "Item creation finished successfully!");
					 
				 }
				 else JOptionPane.showMessageDialog(null, "ERROR (item exists)");
			 }
			 else JOptionPane.showMessageDialog(null, "ERROR (creating menu item)");
		}
		catch (Exception e1){
			JOptionPane.showMessageDialog(null, "ERROR (creating menu item)");
		}
	}
	
	@Override
	public void deleteMenuItem(MenuItem item) {
		
		item = Controller.findByName(textMenuItem.getText());
		if(item != null) {
			restaurant.deleteMenuItem(item);
			RestaurantSerializator.serialize(restaurant);
			table();
			JOptionPane.showMessageDialog(null, "Item deleted finished successfully!");	
		}
		else JOptionPane.showMessageDialog(null, "ERROR (deleting menu item)");
		
	}
	
	@Override
	public void updateMenuItem(MenuItem item) {
		
		String itemName = textMenuItem.getText();
		double itemPrice;
		try {
			 itemPrice = Double.parseDouble(textItemPrice.getText());
			 if(itemPrice >= 0.0d) {
				restaurant.updateMenuItem(new BaseProduct(itemName, itemPrice));
				RestaurantSerializator.serialize(restaurant);
				table();
				JOptionPane.showMessageDialog(null, "Item update finished successfully!");
			}
			else JOptionPane.showMessageDialog(null, "Item update ERROR!");
		}
		catch (Exception e1){
			JOptionPane.showMessageDialog(null, "Item update ERROR!");
		}
	}
	
	@Override
	public void createOrder(Order order, List<MenuItem> menuItem) {
		JOptionPane.showMessageDialog(null, "You are not allowed to create order.");	
	}
	
	@Override
	public double computeOrderPrice(Order order) {
		JOptionPane.showMessageDialog(null, "You are not allowed to compute order price.");
		return 0;
	}
	
	@Override
	public void generateBill(String whatToPrint) {
		JOptionPane.showMessageDialog(null, "You are not allowed to generate bill.");
	}
}
