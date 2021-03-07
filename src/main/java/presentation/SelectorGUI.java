package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import bll.Restaurant;
/**
 * 
 * @author BB
 *
 */
public class SelectorGUI implements ActionListener{
	private static final Color MY_GREEN = new Color(112,202,141);
	private static final Color MY_DARK_GREEN = new Color(52,142,81);
	private static final Color MY_DARKER_GREEN = new Color(2,92,31);
	private static JLabel statusLabel = new JLabel("Choose your interface",JLabel.CENTER);
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JButton close = new JButton("×");
	private JButton admin = new JButton("Admin");
	private JButton waiter = new JButton("Waiter");
	private JButton chef = new JButton("Chef");
	
	private Restaurant restaurant;
	private WaiterGUI waiterGUI;
	private AdministratorGUI adminGUI;
	private ChefGUI chefGUI;
	
	public SelectorGUI(Restaurant restaurant) {
		this.restaurant = restaurant;
		chefGUI = new ChefGUI(restaurant);
		adminGUI = new AdministratorGUI(restaurant);
		waiterGUI = new WaiterGUI(restaurant);
		this.restaurant.addObserver(chefGUI);
		
		makeFrame(); //build the frame with style as well
		makePanel(); //builds the styled panel
	}
	
	private void makeFrame() {
		frame.setUndecorated(true);
		frame.setSize(700,200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTitle("Restaurant");
		frame.add(panel);
	}
	
	private void makePanel() {
		style();
		
		panel.add(close);
		panel.add(statusLabel);
		panel.add(admin);
		panel.add(waiter);
		panel.add(chef);
		
		hoverButton(close);
		hoverButton(admin);
		hoverButton(waiter);
		hoverButton(chef);
		
		setButtonStyle(close);
		setButtonStyle(admin);
		setButtonStyle(waiter);
		setButtonStyle(chef);
		
		close.addActionListener(this);
		admin.addActionListener(this);
		waiter.addActionListener(this);
		chef.addActionListener(this);
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
		statusLabel.setPreferredSize(new Dimension(642,55));
	}
	
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		if(source == admin) {adminGUI.frameSetVisible(true);}
		else if(source == waiter) {waiterGUI.frameSetVisible(true);}
		else if(source == chef) {chefGUI.frameSetVisible(true);}
		else if(source == close) {System.exit(0);}
		else System.out.println("You are a hacker, man.");
		
		
	}
	
	private void setButtonStyle(JButton btn) {
		Font font = new Font("Roboto", Font.BOLD,25);
		btn.setBorderPainted(false);
		if(btn.equals(close)) btn.setPreferredSize(new Dimension(50,40));
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
}
