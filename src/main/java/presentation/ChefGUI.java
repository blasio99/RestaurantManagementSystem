package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import bll.Restaurant;


public class ChefGUI implements ActionListener, Observer{
	
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JTextArea orderArea = new JTextArea("");
	private Restaurant restaurant;
	
	
	public ChefGUI(Restaurant restaurant) {
		setRestaurant(restaurant);
		makeFrame(); //build the frame with style as well
		makePanel(); //builds the styled panel
		
	}
	
	private void makeFrame() {
		frame.setUndecorated(true);
		frame.setSize(700,800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Restaurant");
		frame.add(panel);
	}
	
	private void makePanel() {
		
		Color MY_DARK_GREEN = new Color(52,142,81);
		Color MY_DARKER_GREEN = new Color(2,92,31);
		Color DARKER_GRAY = new Color(55,55,55);
		Border border = BorderFactory.createLineBorder(MY_DARKER_GREEN,4);
		Border labelBorder = BorderFactory.createLineBorder(Color.DARK_GRAY,2);
		Font font = new Font("Roboto", Font.BOLD,20);
		JLabel statusLabel = new JLabel("Dear Chef,\n the orders are displayed here:",JLabel.CENTER);
		JButton back = new JButton("Back");
		
		back.addActionListener(this);
		setBackStyle(back);
		hoverButton(back);
		backButton(back);
		
		statusLabel.setBorder(labelBorder);
		statusLabel.setFont(font);
		statusLabel.setOpaque(true);
		statusLabel.setBackground(Color.DARK_GRAY);
		statusLabel.setForeground(MY_DARK_GREEN);
		statusLabel.setPreferredSize(new Dimension(642,55));
		
		JScrollPane chosenScroll = new JScrollPane(orderArea);
		chosenScroll.setPreferredSize(new Dimension(642, 600));
		chosenScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chosenScroll.setVisible(true);
		chosenScroll.setSize(100,700);
		orderArea.setEditable(false); 
		orderArea.setLineWrap(true);
		orderArea.setWrapStyleWord(true);
		orderArea.setBackground(DARKER_GRAY);
		orderArea.setForeground(Color.LIGHT_GRAY);
		orderArea.setFont(new Font("Roboto", Font.BOLD, 15));
		
		panel.setBackground(Color.DARK_GRAY);
		panel.setBorder(border);
		panel.add(back);
		panel.add(statusLabel);
		panel.add(chosenScroll);
	}
	
	private void backButton(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
				{frame.dispose();}
		});
		
		btn.setBorderPainted(false);
	}

	private void setBackStyle(JButton btn) {
		Color MY_GREEN = new Color(112,202,141);
		Font font = new Font("Roboto", Font.BOLD,25);
		btn.setBorderPainted(false);
		
		btn.setPreferredSize(new Dimension(100,40));
		btn.setBackground(MY_GREEN);
		btn.setForeground(Color.DARK_GRAY);
		btn.setFont(font);
		

	}
	
	private void hoverButton(JButton btn) {
		Color MY_DARK_GREEN = new Color(52,142,81);
		btn.addMouseListener(new MouseAdapter() {
	         Color oldColor = btn.getBackground();
	         public void mouseEntered(MouseEvent me) {
	            oldColor = btn.getBackground();
	            btn.setBackground(MY_DARK_GREEN);
	         }
	         public void mouseExited(MouseEvent me) {
	            btn.setBackground(oldColor);
	         }
	      });
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		frame.setVisible(true);
		JOptionPane.showMessageDialog(null, arg1);
		orderArea.append(String.valueOf(arg1));
		//frame.dispose();
		
	}
	
	public void frameSetVisible(boolean visible) {
		frame.setVisible(visible);
	}
	
	public void doNotShow() {
		frame.dispose();
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
