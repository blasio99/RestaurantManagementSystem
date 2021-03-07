package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import bll.CompositeProduct;
import bll.MenuItem;
import bll.Restaurant;
import dao.RestaurantSerializator;
/**
 * 
 * @author BB
 *
 */
public class CompositeMenuItemGUI implements ActionListener {

	private static final Color MY_GREEN = new Color(112,202,141);
	private static final Color MY_DARK_GREEN = new Color(52,142,81);
	private static final Color MY_DARKER_GREEN = new Color(2,92,31);
	private static final Color DARKER_GRAY     = new Color(55, 55, 55);
	private static JLabel statusLabel = new JLabel("Create composite item",JLabel.CENTER);
	private JLabel  emptyLabel1 = new JLabel();
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JButton close = new JButton("×");
	private JButton addItem = new JButton("Add item");
	private JButton createComposite = new JButton("Create menu");
	private JComboBox<MenuItem> menuDropDown = new JComboBox<>();
	private JTextArea   chosenItems = new JTextArea("Add items...");
	private JTextArea   menuName = new JTextArea("Give a name...");
	private JScrollPane chosenScroll;
	private Restaurant restaurant;
	private List<MenuItem> compositeItem;
	
	public CompositeMenuItemGUI(Restaurant restaurant) {
		this.restaurant = restaurant;
		fillMenu();
		compositeItem = new ArrayList<>();
		makeFrame(); //build the frame with style as well
		makePanel(); //builds the styled panel
	}
	
	
	private void makeFrame() {
		frame.setUndecorated(true);
		frame.setSize(800,400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Restaurant");
		frame.add(panel);
	}
	
	private void makePanel() {
		style();
		chosenScroll = new JScrollPane(chosenItems);
		chosenScroll.setPreferredSize(new Dimension(440, 100));
		chosenScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chosenScroll.setVisible(true);
		chosenScroll.setSize(100,700);
		chosenItems.setBackground(DARKER_GRAY);
		chosenItems.setEditable(false); // set textArea non-editable
		chosenItems.setLineWrap(true);
		chosenItems.setWrapStyleWord(true);
		
		panel.add(close);
		panel.add(statusLabel);
		
		panel.add(menuDropDown);
		panel.add(menuName);
		panel.add(chosenScroll, BorderLayout.EAST);
		panel.add(emptyLabel1);
		panel.add(addItem);
		panel.add(createComposite);
		
		componentStyle(menuName, new Dimension(270, 35));
		componentStyle(emptyLabel1, new Dimension(670, 35));
		componentStyle(menuDropDown, new Dimension(270, 35));
		componentStyle(chosenItems, new Dimension(650, 100));
		menuName.setBackground(DARKER_GRAY);
		
		hoverButton(close);
		hoverButton(addItem);
		hoverButton(createComposite);
		
		setButtonStyle(close);
		setButtonStyle(addItem);
		setButtonStyle(createComposite);
		
		close.addActionListener(this);
		addItem.addActionListener(this);
		createComposite.addActionListener(this);
	}
	
	public void style() {
		panel.setBackground(Color.DARK_GRAY);
		Border border = BorderFactory.createLineBorder(MY_DARKER_GREEN,4);
		Border labelBorder = BorderFactory.createLineBorder(Color.DARK_GRAY,2);
		Font font = new Font("Roboto", Font.BOLD,20);
		
		panel.setBorder(border);
		
		statusLabel.setBorder(labelBorder);
		statusLabel.setFont(font);
		statusLabel.setOpaque(true);
		statusLabel.setBackground(Color.DARK_GRAY);
		statusLabel.setForeground(MY_DARK_GREEN);
		statusLabel.setPreferredSize(new Dimension(752,55));
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
	
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		if(source == close) {frame.dispose();AdministratorGUI.table();}
		else if(source == addItem) {addItem();}
		else if(source == createComposite) {createComposite();}
		else System.out.println("You are a hacker, man.");
		
		
	}
	
	private void setButtonStyle(JButton btn) {
		Font font = new Font("Roboto", Font.BOLD,25);
		btn.setBorderPainted(false);
		if(btn.equals(close)) btn.setPreferredSize(new Dimension(50,40));
		else btn.setPreferredSize(new Dimension(200,43));
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

	
	public void setFrameVisible(boolean visible) {
		frame.setVisible(visible);
		fillMenu();
		
	}
	
	public void addItem() {
		MenuItem item = (MenuItem) menuDropDown.getSelectedItem();
		if(chosenItems.getText().contentEquals("Add items..."))
			chosenItems.setText("");
		
		chosenItems.append(item.toString() + "\n");
		compositeItem.add(item);
	}
	
	public void createComposite() {
		if(compositeItem == null || compositeItem.isEmpty()) {
			JOptionPane.showMessageDialog(null, "ERROR (There is no selected item)");
			return;
		}
		if(menuName.getText().contentEquals("Give a name...")) {
			JOptionPane.showMessageDialog(null, "ERROR (Give a name to the menu)");
			return;
		}
			
		restaurant.setCompositeItem(compositeItem);
		List<MenuItem> compositeList = restaurant.getCompositeItem();
		String itemName = menuName.getText();
		CompositeProduct cp = new CompositeProduct(itemName, compositeList);
		
		cp.setPrice(cp.computePrice());
		restaurant.createMenuItem(cp);
		RestaurantSerializator.serialize(restaurant);
		chosenItems.setText("");
		JOptionPane.showMessageDialog(null, "Composite menu created successfully!");
	}
	
	public void fillMenu() {
		menuDropDown.removeAllItems();
		Iterator<MenuItem> it = restaurant.getMenu().iterator();

		while (it.hasNext()) {
			MenuItem curentItem = it.next();
			menuDropDown.addItem(curentItem);
		}
	}
}
