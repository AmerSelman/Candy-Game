package game;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Logic class
 * @author Amer
 *
 */
public class Logic{
	private int gridSize = 9;
	private int points = 0;
	private int maxPoint = 2000;
	private int candyPoints = 20;
	private boolean gameOver = false;
	
	private int b = 0;
	
	public int limit = 20;
	
	public int l = 0;
		
	
	private int[][] candyArray = new int[gridSize][gridSize];
	
	/**
	 * this getGridSize method will get the games board size
	 * @return gridSize size of columns and rows
	 */
	public int getGridSize() {
		return gridSize;
	}
	/**
	 * this getLimit method will get the move limit
	 * @return limit max move limit
	 */
	public int getLimit() {
		return limit;
	}
	/**
	 * this getMaxPoints method will get the necessary points to win
	 * @return maxPoint max points
	 */
	public int getMaxPoints() {
		return maxPoint;
	}
	/**
	 * this getBoard method will get the board field specification
	 * @return specification of the game board
	 */
	public int[][] getBoard(){
		check();
		return candyArray;
	}
	/**
	 * this getBoard method will get the currently points
	 * @return currently points
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * this getL method will get the current number of moves made
	 * @return number of moves made
	 */
	public int getL() {
		return l;
	}
	/**
	 * this getStatusOfGame method will get the status of the game (if the games is over)
	 * @return game status
	 */
	public boolean getStatusOfGame() {
		return gameOver;
	}
	
	/**
	 * this createBoard method will generate the game boards values
	 */
	public void createBoard() {
		for(int i = 0;i<gridSize;i++) {
			for(int j=0;j<gridSize;j++) {
				int randomCandy = (int )(Math.random() * 6 + 1);
				candyArray[i][j] = randomCandy;
			}
		}		
	}
	/**
	 * the Logic method default constructor
	 */
	
	public Logic() {
		
		createBoard();
		
	}
	/**
	 * this PlayCandyCrushGame method uses other methods to play the game
	 */
	public void PlayCandyCrushGame() {
		check();
		showGame();
		playGame();
		System.out.println(limit+" " + l);
	}
	/**
	 * this showGame method shows the game board in the console
	 */
	public void showGame() {
		for(int i=0;i<gridSize;i++) {
			for(int j=0;j<gridSize;j++) {
				System.out.print(candyArray[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	/**
	 * this updatePoints method updates the current points
	 * @param point the first integer
	 */
	
	private void updatePoints(int point) {
		points += point;
		if(b == 0) {
			if(points < maxPoint && !isCandyCombinationLeft()) {
				gameOver = true;
				System.out.println(points + "/" + maxPoint);
				System.out.println("You lose!");
			}
			else if(points > maxPoint) {
				gameOver = true;
				System.out.println(points + "/" + maxPoint);
				System.out.println("You win!");
				b++;
			}
			else if (l>limit) {
				gameOver = true;
				System.out.println(points + "/" + maxPoint);
				System.out.println("You lose!");
			}
			else {
				System.out.println(points + "/" + maxPoint);
			}
		}		
	}
	/**
	 * this moveCandy method moves candy(colors) if only used with the console 
	 */
	
	public void moveCandy() {
		int x1,x2,y1,y2,save1,save2;
		Scanner ulaz = new Scanner(System.in);
		x1 = ulaz.nextInt();
		y1 = ulaz.nextInt();
		x2 = ulaz.nextInt();
		y2 = ulaz.nextInt();
		
		while(true) {
			
			if(((x1 == x2 && y1 == y2 + 1) || (x1 == x2 && y1 == y2 - 1) ||
					(x1 == x2 + 1 && y1 == y2) || (x1 == x2 - 1 && y1 == y2)) && validMove(x1,y1,x2,y2)){
								
				break;
			}
			else {
				x1 = ulaz.nextInt();
				y1 = ulaz.nextInt();
				x2 = ulaz.nextInt();
				y2 = ulaz.nextInt();
			}
		}
		l++;
		
		System.out.println(l+". move!");

		//save1 = candyArray[x2][y2];
		//save2 = candyArray[x1][y1];
		//candyArray[x2][y2] = save2;
		//candyArray[x1][y1] = save1;
	}
	/**
	 * this moveCandyOnClick moves candy(colors) for the game
	 * @param bC first arrayList to get the location
	 */
	public void moveCandyOnClick(ArrayList<String> bC) {
		
		
		String bc1 = new String();
		String bc2 = new String();
		
		bc1 = bC.get(0);
		bc2 = bC.get(1);
		
		int nbc1 = Integer.parseInt(bc1);
		int nbc2 = Integer.parseInt(bc2);
		
		int x1,x2,y1,y2;
		
		x1 = nbc1 / 10;
		y1 = nbc1 % 10;
		
		x2 = nbc2 / 10;
		y2 = nbc2 % 10;
		
		System.out.println();
	
		if(((x1 == x2 && y1 == y2 + 1) || (x1 == x2 && y1 == y2 - 1) ||
				(x1 == x2 + 1 && y1 == y2) || (x1 == x2 - 1 && y1 == y2))) {
			if(validMove(x1,y1,x2,y2)) {
				System.out.println("Ispravan potez");
			}
			l++;
		}
		
		else {
			System.out.println("Neispravan potez");
		}
		System.out.println(l);
		check();
		showGame();
	}
	/**
	 * this newGame method starts a new game
	 */
	public void newGame() {
		l = 0;
		points = 0;
		createBoard();
	}
	/**
	 * this check method checks for 5, 4, 3 in row and colors if there is at least 3 colors next to each other in the same row or columns
	 */
	public void check() {
		while(validM()) {
			checkRowForFive();
			checkColForFive();
			checkRowForFour();
			checkColForFour();
			checkRowForThree();
			checkColForThree();
		}
	}
	/**
	 * this validM checks if there is 3 colors next to each other in the same row or columns
	 * @return if there is a combination of 3 
	 */
	public boolean validM() {
		for (int i = 0; i<gridSize;i++) {
			for(int j = 0;j<gridSize - 2;j++) {
				int x = j;
				if(candyArray[i][x++]==candyArray[i][j] && candyArray[i][j] != 0 && 
						candyArray[i][x++] == candyArray[i][j] && 
						candyArray[i][x] == candyArray[i][j]) {
					return true;					
				}
			}
		}
		for(int i = 0;i<gridSize - 2;i++) {
			for(int j = 0;j<gridSize;j++) {
				int x = i;
				if(candyArray[x++][j] == candyArray[i][j] && candyArray[i][j] != 0 && 
						candyArray[x++][j] == candyArray[i][j] &&
						candyArray[x][j] == candyArray[i][j]) {
					return true;
				}
			}
		}
		return false;		
	}
	/**
	 * this valimMove method checks if the move was allowed and moves the colors 
	 * @param x1 first integer row position
	 * @param y1 second integer column position
	 * @param x2 third integer row position
	 * @param y2 fourth integer column position
	 * @return if the move was valid
	 */
	public boolean validMove(int x1, int y1, int x2, int y2) {
		int[][] candyTest = candyArray;
		int X1 = x1;
		int Y1 = y1;
		int X2 = x2;
		int Y2 = y2;
		int s1;
		int s2;
		
		s1 = candyTest[X1][Y1];
		s2 = candyTest[X2][Y2];
		
		candyTest[X1][Y1] = s2;
		candyTest[X2][Y2] = s1;
		
		for (int i = 0; i<gridSize;i++) {
			for(int j = 0;j<gridSize - 2;j++) {
				int x = j;
				if(candyTest[i][x++]==candyTest[i][j] && candyTest[i][j] != 0 && 
						candyTest[i][x++] == candyTest[i][j] && 
						candyTest[i][x] == candyTest[i][j]) {
					candyArray = candyTest;
					return true;					
				}
			}
		}
		for(int i = 0;i<gridSize - 2;i++) {
			for(int j = 0;j<gridSize;j++) {
				int x = i;
				if(candyTest[x++][j] == candyTest[i][j] && candyTest[i][j] != 0 && 
						candyTest[x++][j] ==candyTest[i][j] &&
						candyTest[x][j] == candyTest[i][j]) {
					candyArray = candyTest;
					return true;
				}
			}
		}
		return false;		
	}
	/**
	 * this playGame method plays the game in the console until game over
	 */
	
	public void playGame() {
		while(isCandyCombinationLeft() && !gameOver && l<limit) {
			moveCandy();
			check();
			showGame();
		}
	}
	/**
	 * this moveDownCandies method moves colors down if 0 below
	 */
	public void moveDownCandies() {
		for(int i = gridSize - 2;i>=0;i--) {
			for(int j = 0;j<gridSize;j++) {
				if(candyArray[i+1][j] == 0) {
					int x = candyArray[i][j];
					candyArray[i+1][j] = x;
					candyArray[i][j] = 0;				
				}				
			}
		}
		for(int i = 0;i<1;i++) {
			for(int j=0;j<gridSize;j++) {
				if(candyArray[i][j] == 0) {
					
				int randomCandy = (int )(Math.random() * 6 + 1);
				candyArray[i][j] = randomCandy;
				}
			}
		}
	}
	/**
	 * this mdc method uses moveDownCandies method multiple times
	 */
	public void mdc() {
		for(int k = 0; k<gridSize;k++) {
			moveDownCandies();
		}
	}
	/**
	 * this checkRowForFour method checks row for combination of 4 and if there is an combination puts 0 on that place and adds points
	 */
	public void checkRowForFour() {
		for (int i = 0; i<gridSize;i++) {
			for(int j = 0;j<gridSize - 3;j++) {
				int x = j;
				if(candyArray[i][x++]==candyArray[i][j] && candyArray[i][j] != 0 && 
						candyArray[i][x++] == candyArray[i][j] && 
						candyArray[i][x++] == candyArray[i][j] &&
						candyArray[i][x] == candyArray[i][j]) {
					updatePoints(candyPoints * 4);
					
					candyArray[i][x--] = 0;
					candyArray[i][x--] = 0;
					candyArray[i][x--] = 0;
					candyArray[i][x] = 0;
					
				}
			}
			
		}
		mdc();
	}
	/**
	 * this checkColForFour method checks columns for combination of 4 and if there is an combination puts 0 on that place and adds points
	 */
	
	private void checkColForFour() {
		for(int i = 0;i<gridSize - 3;i++) {
			for(int j = 0;j<gridSize;j++) {
				int x = i;
				if(candyArray[x++][j] == candyArray[i][j] && candyArray[i][j] != 0 && 
						candyArray[x++][j] == candyArray[i][j] &&
						candyArray[x++][j] == candyArray[i][j] &&
						candyArray[x][j] == candyArray[i][j]) {
					updatePoints(candyPoints * 4);
					
					candyArray[x--][j] = 0;
					candyArray[x--][j] = 0;
					candyArray[x--][j] = 0;
					candyArray[x][j] = 0;
				}
			}
		}
		mdc();
	}
	/**
	 * this checkRowForFive method checks row for combination of 5 and if there is an combination puts 0 on that place and adds points
	 */
	public void checkRowForFive() {
		for (int i = 0; i<gridSize;i++) {
			for(int j = 0;j<gridSize - 4;j++) {
				int x = j;
				if(candyArray[i][x++]==candyArray[i][j] && candyArray[i][j] != 0 && 
						candyArray[i][x++] == candyArray[i][j] && 
						candyArray[i][x++] == candyArray[i][j] &&
						candyArray[i][x++] == candyArray[i][j] &&
						candyArray[i][x] == candyArray[i][j]) {
					updatePoints(candyPoints * 5);
					
					candyArray[i][x--] = 0;
					candyArray[i][x--] = 0;
					candyArray[i][x--] = 0;
					candyArray[i][x--] = 0;
					candyArray[i][x] = 0;
					
				}
			}
			
		}
		mdc();
	}
	/**
	 * this checkColForFive method checks columns for combination of 5 and if there is an combination puts 0 on that place and adds points
	 */
	private void checkColForFive() {
		for(int i = 0;i<gridSize - 4;i++) {
			for(int j = 0;j<gridSize;j++) {
				int x = i;
				if(candyArray[x++][j] == candyArray[i][j] && candyArray[i][j] != 0 && 
						candyArray[x++][j] == candyArray[i][j] &&
						candyArray[x++][j] == candyArray[i][j] &&
						candyArray[x++][j] == candyArray[i][j] &&
						candyArray[x][j] == candyArray[i][j]) {
					updatePoints(candyPoints * 5);
					
					candyArray[x--][j] = 0;
					candyArray[x--][j] = 0;
					candyArray[x--][j] = 0;
					candyArray[x--][j] = 0;
					candyArray[x][j] = 0;
				}
			}
		}
		mdc();
	}
	/**
	 * this checkRowForThree method checks row for combination of 3 and if there is an combination puts 0 on that place and adds points
	 */
	public void checkRowForThree() {
		for (int i = 0; i<gridSize;i++) {
			for(int j = 0;j<gridSize - 2;j++) {
				int x = j;
				if(candyArray[i][x++]==candyArray[i][j] && candyArray[i][j] != 0 && 
						candyArray[i][x++] == candyArray[i][j] && 
						candyArray[i][x] == candyArray[i][j]) {
					updatePoints(candyPoints * 3);
					
					candyArray[i][x--] = 0;
					candyArray[i][x--] = 0;
					candyArray[i][x] = 0;
					
				}
			}
			
		}
		mdc();
	}
	/**
	 * this checkColForThree method checks columns for combination of 3 and if there is an combination puts 0 on that place and adds points
	 */
	private void checkColForThree() {
		for(int i = 0;i<gridSize - 2;i++) {
			for(int j = 0;j<gridSize;j++) {
				int x = i;
				if(candyArray[x++][j] == candyArray[i][j] && candyArray[i][j] != 0 && 
						candyArray[x++][j] == candyArray[i][j] &&
						candyArray[x][j] == candyArray[i][j]) {
					updatePoints(candyPoints * 3);
					
					candyArray[x--][j] = 0;
					candyArray[x--][j] = 0;
					candyArray[x][j] = 0;
				}
			}
		}
		mdc();
	}
	/**
	 * this isCandyCombinationLeft method checks if there is a move left that would make a combination
	 * @return if there is a valid move to make
	 */
	public boolean isCandyCombinationLeft() {
		for(int i = 0; i < gridSize;i++) {
			for(int j = 0;j<gridSize-2;j++) {
				int x = j;
				if(candyArray[i][x++] == candyArray[i][j] && candyArray[i][j] != 0 &&
						candyArray[i][x++] == candyArray[i][j]) {
					if(x+1 < gridSize) {
						if(candyArray[i][x+1] == candyArray[i][j]) {
							return true;
						}
					}
					if(i-1 >= 0) {
						if(candyArray[i-1][x] == candyArray[i][j] || candyArray[i-1][x-1] == candyArray[i][j]) {
							return true;
						}
					}
					if(i+1<gridSize) {
						if(candyArray[i+1][x] == candyArray[i][j] || candyArray[i+1][x-1] == candyArray[i][j]) {
							return true;
						}
					}
				}
			}
		}
		for(int i = 0;i<gridSize - 2;i++) {
			for(int j = 0;j<gridSize;j++) {
				int x = i;
				if(candyArray[x++][j] == candyArray[i][j] && candyArray[i][j] != 0 &&
						candyArray[x++][j] == candyArray[i][j]) {
					if(x+1 < gridSize) {
						if(candyArray[x+1][j] == candyArray[i][j]) {
							return true;
						}
					}
					if(j-1 >= 0) {
						if(candyArray[x][j-1] == candyArray[i][j] || candyArray[x-1][j-1] == candyArray[i][j]) {
							return true;
						}
					}
					if(j+1<gridSize){
						if(candyArray[x][j+1] == candyArray[i][j] || candyArray[x-1][j+1] == candyArray[i][j]) {
							return true;
						}
					}
				}
			}
		}
		for(int i = gridSize-1; i>=0;i--) {
			for(int j = gridSize-1;j>=2;j--) {
				int x = j;
				if(candyArray[i][x--] == candyArray[i][j] && candyArray[i][j] != 0 &&
						candyArray[i][x--] == candyArray[i][j]) {
					if(x-1 >= 0) {
						if(candyArray[i][x-1] == candyArray[i][j]) {
							return true;
						}
					}
					if(i+1 < gridSize) {
						if(candyArray[i+1][x] == candyArray[i][j] || candyArray[i+1][x+1] == candyArray[i][j]) {
							return true;
						}
					}
					if(i-1>=0) {
						if(candyArray[i-1][x] == candyArray[i][j] || candyArray[i-1][x+1] == candyArray[i][j]) {
							return true;
						}
					}
				}
			}
		}
		for(int i = gridSize-1; i>=2;i--) {
			for(int j = gridSize-1;i>=0;j--) {
				int x = i;
				if(candyArray[x--][j] == candyArray[i][j] && candyArray[i][j] != 0 &&
						candyArray[x--][j] == candyArray[i][j]) {
					if(x-1 >= 0) {
						if(candyArray[x-1][j] == candyArray[i][j]) {
							return true;
						}
					}
					if(j+1 < gridSize) {
						if(candyArray[x][j+1] == candyArray[i][j] || candyArray[x+1][j+1] == candyArray[i][j]) {
							return true;
						}
					}
					if(j-1>=0){
						if(candyArray[x][j-1] == candyArray[i][j] || candyArray[x+1][j-1] == candyArray[i][j]) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
}
