

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.html.ImageView;
import javax.swing.*;  
import java.awt.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

public class ticTac{
	private int Rows = 3;
	private int Column = 3;
	private Seed board [][] = new Seed [Rows][Column];
	Player pp;
	Seed player = pp.User();
	Computer cc;
	Seed computer = cc.Com();
	Seed currentPlayer;
	Random random = new Random();
	Scanner scanner = new Scanner(System.in);
	List <Cell> getPointsAvailable;
	
	
	
	public static void main(String[] args){
		ticTac tt = new ticTac();
		
		tt.startGame();
		tt.displayBoard();
		tt.firstMove();
		tt.gamePlaying();
		tt.checkStatus();
				
	}
		
	
	
	public enum GameState{
		Playing, Cross_Won, Nought_Won, Draw;
	}
	
	GameState currentState;
	
	public enum Seed{
		Cross, Nought, Empty;
	}
	
	//gamePlaying
		public void gamePlaying() {
		
			for(int i = 0; i < Rows; i++) {
				for(int j = 0; j < Column; j++) {
					if(Playing()) {
					System.out.println("Please enter a row and a column (only values between 0 and 2):");
					Cell user = new Cell(scanner.nextInt(Rows), scanner.nextInt(Column));
					makeMove(user, player);
					displayBoard();
					
				
					makeMove(findBestMove(), computer);
					displayBoard();
					
					}else if(!Playing()) {
						break;
					}
			   }
			}
		
		}
		
	//Playing
		public boolean Playing() {
			boolean playing = false;
		
			return playing = checkWinner(player) || 
				   checkWinner(computer) || 
				   getAvailableMoves().isEmpty() ? false : true;
		
		}
	
	//startGame
		public void startGame() {
			
			for(int i = 0; i < Rows; i++) {
				for(int j = 0; j < Column; j++) {
					board[i][j] = Seed.Empty;
				}
			}
			
			currentState = GameState.Playing;
			currentPlayer = (player == Seed.Cross) ? player : computer;
			String playerName = " ";
			System.out.println("What is your full name? ");
			playerName = scanner.nextLine();
			
			System.out.println(playerName + " will have piece " + player);
			
		}
	
	
	//checkWinner
		public boolean checkWinner(Seed piece) {
			boolean gameOver = false;
			
			//Rows
			if(piece == board[0][0] && piece == board[0][1] && piece == board[0][2] || 
			   piece == board[1][0] && piece == board[1][1] && piece == board[1][2] ||
			   piece == board[2][0] && piece == board[2][1] && piece == board[2][2]) {
				gameOver = true;
			}
			//Columns
			if(piece == board[0][0] && piece == board[1][0] && piece == board[2][0] || 
				piece == board[0][1] && piece == board[1][1] && piece == board[2][1] ||
				piece == board[0][2] && piece == board[1][2] && piece == board[2][2]) {
				gameOver = true;
			}
			//Diagonals
			if(piece == board[0][0] && piece == board[1][1] && piece == board[2][2] || 
				piece == board[0][2] && piece == board[1][1] && piece == board[2][0] ) {
				gameOver = true;
			}
			return gameOver;
		}
	
	
	//checkStatus
		public void checkStatus(){
	
			if(checkWinner(player)) {
				System.out.println("The player has WON BOIIIII");
			}else if(checkWinner(computer)) {
				System.out.println("Dang the computer won bro!");
			}else {
				System.out.println("It's a draw");
			}
		}
	
	
	//makeMove
	public void makeMove(Cell user, Seed currentPlayer) {
	
			if(board[user.getX()][user.getY()] == Seed.Empty) {
				board[user.getX()][user.getY()] = currentPlayer;
			}else if(board[user.getX()][user.getY()] != Seed.Empty){
				System.out.println("Please enter a new value, the row " + user.getX() + " and the column " + user.getY() + 
									" you entered is already taken");
				if(currentPlayer == player) {
					gamePlaying();
				}else {

					
					makeMove(findBestMove(), computer);
					displayBoard();
				}
			}
	}
	
	
	//firstMove
		public Seed firstMove() {
			Seed firstMove;
			if(computer == Seed.Cross) {
				firstMove = computer;
				player = Seed.Nought;
				Cell com = new Cell(random.nextInt(Rows), random.nextInt(Column));
				makeMove(com, computer);
				displayBoard();
				
			}else {
				player = Seed.Cross;
				firstMove = player;
				computer = Seed.Nought;
			}
			return firstMove; 
		}
	
	
	//displayBoard
		public void displayBoard() {
			System.out.println();
			
			for(int i = 0; i < Rows; i++) {
				System.out.println("-------");
				for(int j = 0; j < Column; j++) {
					System.out.println("   | ");
					System.out.println(board[i][j] + " ");
				}
			}
		}
		
	//getAvailableMoves
	public List <Cell> getAvailableMoves(){
		getPointsAvailable = new ArrayList<>();
		
		for(int i = 0; i < Rows; i++) {
			for(int j = 0; j < Column; j++) {
				if(board[i][j] == Seed.Empty) {
					getPointsAvailable.add(new Cell(i, j));
				}
			}
		}
		return getPointsAvailable;
	}
	
	
	//Minimax method
		//1.
	Cell computerMoves;
	public int minimax(int depth, Seed currentPlayer) {
		
		if(checkWinner(computer)) {
			return 1;
		}if(checkWinner(player)) {
			return -1;
		}
		
		int min = 1, max = -1;
		List<Cell> pointsAvailable = getAvailableMoves();
		
		if(pointsAvailable.isEmpty()) {
			return 0;
		}else {
			int i = 0;
			for(Cell moves : pointsAvailable) {
				moves = pointsAvailable.get(i);
				
				if(currentPlayer == computer) {
					makeMove(moves, computer);
					int currentScore = minimax(depth + 1, player);
					max = Math.max(max, currentScore);
					if(currentScore >= 0 || i == pointsAvailable.size() -1 && max < 0) {
						if(depth == 0) {
							computerMoves = moves;
						}if(currentScore >= 1) {
							board[moves.x][moves.y] = Seed.Empty; break;
						}
					}
				}else if(currentPlayer == player){
					makeMove(moves, player);
					int currentScore = minimax(depth + 1, computer);
					min = Math.min(min, currentScore);
				}
				board[moves.x][moves.y] = Seed.Empty;
				i++;
			}
			
			
		}
		
		
		return currentPlayer == computer ? max : min;
		
	}
	
	//Find the best move for the computer
	//Figure out how to return the best move
	
	public Cell findBestMove() {
		minimax(0, computer);
		
		int x = (computerMoves.x) * 3 + computerMoves.y;
		int y = (computerMoves.x) * 3 + computerMoves.y;
		
		return new Cell(computerMoves.x, computerMoves.y);
		
	}

	

	public class Cell{
		
		int x, y;
		public Cell(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
	
	}

 }


