package gui;

import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;

import java.awt.Color;
import java.awt.Dimension;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.util.ArrayList;


import game.Logic;
/**
 * the GUI class
 * @author Amer
 *
 */
public class GUI extends JFrame{
	
	private int gridSize;
	
	private int points;
	private int maxPoint;
	
	public int row;
	
	public int col;
	
	public int limit;
	
	public int l;
	
	private String space = "                                                                                  ";
	
	public Logic logic;
	
	public String lastClicked;
	
	private Dimension dim = new Dimension(90,90);
	
	public int timesClicked = 0;
	
	private JButton[] buttons = new JButton[81];
	
	private boolean validMoveLeft = true;
	
	
	ArrayList<String> buttonClicked = new ArrayList<String>();
	
	private String info = "The game is played by swiping colours,"
			+ " in any direction,"
			+ " to create sets of 3 or more matching colours."
			+ " When matched, the colours will crush and shift"
			+ " the colours above them, allowing you to accomplish"
			+ " a series of different goals. The player has 20 moves"
			+ "to earn 2000 points and win the game.";
		
	private int[][] candyArray;
	
	private String[] bP = {
			"00","01","02","03","04","05","06","07","08",
			"10","11","12","13","14","15","16","17","18",
			"20","21","22","23","24","25","26","27","28",
			"30","31","32","33","34","35","36","37","38",
			"40","41","42","43","44","45","46","47","48",
			"50","51","52","53","54","55","56","57","58",
			"60","61","62","63","64","65","66","67","68",
			"70","71","72","73","74","75","76","77","78",
			"80","81","82","83","84","85","86","87","88"			
	};
	/**
	 * bAction action on colors to play the game
	 */
	ActionListener bAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			lastClicked = ((JButton) e.getSource()).getName();
			timesClicked++;
			
			playG();
			
		}
	};
	/**
	 * newGameAction action on click to start a new game
	 */
	ActionListener newGameAction = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			newGame();
		}
	};
	/**
	 * gameHelp action on click how to play info
	 */
	ActionListener gameHelp = new ActionListener() {
		public void actionPerformed(ActionEvent j) {
			howToPlay();
		}
	};
	/**
	 * this howToPlay method shows a window with info of how to play the game
	 */
	private void howToPlay() {
		JOptionPane.showMessageDialog(null, info);
	}
	/**
	 * this playG method uses other methods to play the game
	 */
	
	public void playG() {
		
		btClicked(lastClicked);
		
		priBt();
		candyArray = logic.getBoard();
		moveCandy();
		delClickedButtons();
		validMoveLeft = logic.isCandyCombinationLeft();
		checkWin();
	}
	/**
	 * this updatePoints method sets the points and moves as title
	 */
	public void updatePoints() {
		setTitle("Candy Crush!"+space + l+". move out of "+limit+"    " + points+"/"+maxPoint+" Points");
	}
	/**
	 * this getInfo method gets the points and number of moves from Logic class 
	 */
	public void getInfo() {
		points = logic.getPoints();
		l = logic.getL();
	}
	/**
	 * this btCliced method checks the number of clicked colors
	 * @param bName first String for the location of the color clicked
	 */
	
	public void btClicked(String bName) {
		String name = bName;
		buttonClicked.add(name);		
	}
	/**
	 * this priBt method updates the board after the second click
	 */
	public void priBt() {
		if (timesClicked == 2)
			logic.moveCandyOnClick(buttonClicked);
			candyArray = logic.getBoard();
			getInfo();
			updatePoints();
			//System.out.println(buttonClicked);
			
	}
	/**
	 * this checkWin method opens a window do declare win or loss
	 */
	public void checkWin() {
		if(l<=limit && points >= maxPoint) {
			JOptionPane.showMessageDialog(null, "You won!");
		}
		if((l>=limit && points <= maxPoint) || !validMoveLeft){
			JOptionPane.showMessageDialog(null, "You lost!");
		}
	}
	/**
	 * this createButtons method creates buttons
	 */
	
	public void createButtons() {
		int x = 64;
		for(int i=0; i<x;i++) {
			buttons[i] = new JButton();
		}
	}
	/**
	 * this moveCandy method moves colors
	 */
	public void moveCandy() {
		int r = 0;
		for(int i=0;i<gridSize;i++) {
			for(int j=0;j<gridSize;j++) {
				buttons[r].setText(String.valueOf(candyArray[i][j]));
				addColors(i,j,r);
				r++;
			}
		}
		
	}
	/**
	 * this addColors method adds colors for different values
	 * @param i1 first integer row location 
	 * @param j1 second integer column location
	 * @param r1 third integer button location
	 */
	public void addColors(int i1, int j1,int r1) {
		int i = i1;
		int j = j1;
		int r = r1;
		int x = candyArray[i][j];
		if (x == 1) {
			buttons[r].setBackground(Color.BLUE);
			buttons[r].setForeground(Color.BLUE);
		}else if ( x == 2) {
			buttons[r].setBackground(Color.GREEN);
			buttons[r].setForeground(Color.GREEN);
		}else if ( x == 3) {
			buttons[r].setBackground(Color.GRAY);
			buttons[r].setForeground(Color.GRAY);
		}else if ( x == 4) {
			buttons[r].setBackground(Color.magenta);
			buttons[r].setForeground(Color.magenta);
		}else if ( x == 5) {
			buttons[r].setBackground(Color.RED);
			buttons[r].setForeground(Color.RED);
		}else if ( x == 6) {
			buttons[r].setBackground(Color.YELLOW);
			buttons[r].setForeground(Color.YELLOW);
		}
	}
	/**
	 * this delClickedButtons method deletes buttonClicked names after the second click
	 */
	private void delClickedButtons() {
		if (timesClicked == 2) {
			timesClicked = 0;
			buttonClicked.clear();
		}
	}
	
	/**
	 * this makeFields method makes fields, sets the location and action on buttons 
	 */
	
	private void makeFields() {
		createButtons();
		GridBagConstraints c = new GridBagConstraints();
		
		logic.createBoard();
		
		candyArray = logic.getBoard();
		
		int r = 0;
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize;j++) {
				buttons[r] = new JButton(String.valueOf(candyArray[i][j]));
				buttons[r].setPreferredSize(dim);
				buttons[r].setName(bP[r]);
				buttons[r].addActionListener(bAction);
				addColors(i,j,r);
				
				c.gridx=j;
				c.gridy=i;
				add(buttons[r],c);
				r++;
								
			}
		}
	}
	/**
	 * this newGameCandy method gets new colors for the new game
	 */
	private void newGameCandy() {
		int r = 0;
		for(int i=0;i<gridSize;i++) {
			for(int j=0;j<gridSize;j++) {
				buttons[r].setText(String.valueOf(candyArray[i][j]));
				addColors(i,j,r);
				r++;
			}
		}
	}
	/**
	 * this newGame method starts new game
	 */
	private void newGame() {
		logic.newGame();
		logic.createBoard();
		setTitle("Candy Crush!");
		candyArray = logic.getBoard();
		newGameCandy();
		
		
		l = logic.getL();
		points = logic.getPoints();
	}
	/**
	 * this build method creates the JFrame
	 */
	private void build() {
		
		setLayout(new GridBagLayout());
		
		setTitle("Candy Crush!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		makeFields();
		
		addMenu();
		pack();
		setLocationRelativeTo(null);
	}
	/**
	 * this addMenu method adds Menu in the JFrame
	 */
	private void addMenu() {
		JFrame frame = (JFrame)SwingUtilities.getRoot(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);
		
		JMenu Menu = new JMenu("Menu");
		menubar.add(Menu);
		
		JMenuItem newG = new JMenuItem("New Game");
		newG.addActionListener(newGameAction);
		Menu.add(newG);
		
		
		JMenu help = new JMenu("Help");
		menubar.add(help);
		
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(gameHelp);
		help.add(about);
		
		
	}
	/**
	 * the GUI method default constructor
	 */
	public GUI() {
		
		logic = new Logic();
		gridSize = logic.getGridSize();
		maxPoint = logic.getMaxPoints();
		limit = logic.getLimit();
		build();
	}
	/**
	 * this showGame method shows the JFrame
	 */
	public void showGame() {
		setVisible(true);
	}
}
