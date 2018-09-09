import java.util.*;

public class UI {
	private static UI ui = null;
	private UI(){
		kb = new Scanner(System.in);
	}
	public static UI getUI() {
		if(ui == null)
			ui = new UI();
		return ui;
	}
	
	private static Scanner kb;
	private static boolean computer_first;
	
	/**
	 * Prompts user for who will be making the first move and how long the computer has to make a move.
	 * Returns an int array: 
	 * 		-First value = 0 or 1. 
	 * 			-If 0 then computer moves first. 
	 * 			-If 1 then computer waits for player to move first.
	 * 		-Second value = how long computer has to make a move in seconds. 
	 */
	public static int[] welcomePrompt() {
		int[] playerNum_and_timeLimit = new int[2];
		
		System.out.println("Welcome to Isolation!");
		System.out.print("Shall I make the first move? [y/n] ");
		String user_answer = kb.nextLine();
		
		while(user_answer.equalsIgnoreCase("y") && user_answer.equalsIgnoreCase("n")) {
			System.out.print("I didn't catch that. Shall I make the first move? [y/n] ");
			user_answer = kb.nextLine();
		}
		
		if(user_answer.toLowerCase().charAt(0) == 'n') {
			playerNum_and_timeLimit[0] = 1;
			computer_first = false;
		}else {
			playerNum_and_timeLimit[0] = 0;
			computer_first = true;
		}
		
		System.out.println("How many seconds do I have to make my moves? ");
		user_answer = kb.nextLine();
		int time_limit;
		try{
			time_limit = Integer.parseInt(user_answer);
		}
		catch(Exception e){
			time_limit = 0;
		}
		
		while(time_limit < 20 || time_limit > 60) {
			System.out.println("Please try again with a value between 20 and 60.\nHow many seconds do I have to make my moves? ");
			user_answer = kb.nextLine();
			try{
				time_limit = Integer.parseInt(user_answer);
			}
			catch(Exception e){
				time_limit = 0;
			}
		}
		
		playerNum_and_timeLimit[1] = time_limit;
		if(playerNum_and_timeLimit[0] == 0) {
			System.out.println("Alright, I will play first.");
		}else {

			System.out.println("Alright, you will play first.");
		}
		System.out.println("My moves should not take longer than " + playerNum_and_timeLimit[1] + " seconds.");
		System.out.println("Let's play!\n");	
		
		
		
		return playerNum_and_timeLimit;
	}
	
	public static void endOfGameMessage(boolean won) {
		if(won)
			System.out.println("You lost.");
		else
			System.out.println("You won!");
		
	}
	
	public static int[] promptForOpponentMove(List<int[]> possible_moves) {
		int[] chosen_move = new int[2];
		
		System.out.print("Enter Opponent's Move: ");
		String move = kb.nextLine();
		
		boolean valid_move = false;
		
		while(!valid_move) {
		
			while(move.length() < 2 || move.toLowerCase().charAt(0) < 'a' || move.toLowerCase().charAt(0) > 'h' || move.charAt(1) < '1' || move.charAt(1) > '8') {
				System.out.print("Please enter a move in the form \"E5\" \nEnter Opponent's Move: ");
				move = kb.nextLine();
			}
		
			chosen_move[0] = (int)(move.toLowerCase().charAt(0) - 'a');
			chosen_move[1] = (int)(move.charAt(1) - '1');
			
			for(int[] possibility : possible_moves) {
				if(possibility[0] == chosen_move[0] && possibility[1] == chosen_move[1]) {
					valid_move = true;
					break;
				}
			}
			
			if(!valid_move) {
				System.out.print("Invalid move. You are only allowed to move like a chess queen. \nEnter Opponent's Move: ");
				move = kb.nextLine();
			}
			
		}
		
		return chosen_move;
	}
	
	public static void printState(char[][] board , String[] move_history) {
		char board_label = 'A';
		int history = 0;
		if(move_history != null) {
			history = move_history.length;
		}
		for(int row = 0 ; row <= board.length || row <= history ; row++) {
			
			if(row == 0) {
				System.out.print("  ");
				for(int col = 1 ; col <= board.length ; col++) {
					System.out.print(col + " ");
				}
				if(computer_first) {
					System.out.println("\tComputer vs. Opponent");
				}else {
					System.out.println("\tOpponenet vs. Computer");
				}
			}
			
			else if(row <= board.length) {
				System.out.print(board_label + " ");
				for(int col = 0 ; col < board.length ; col++) {
					System.out.print(board[row-1][col] + " ");
				}
				if(row <= history) {
					System.out.println("\t " + row + ". " + move_history[row-1]);
				}else {
					System.out.println();
				}
				board_label++;
			}
			
			else {
				for(int col = 0 ; col <= board.length ; col++) {
					System.out.print("  ");
				}
				System.out.println("\t " + row + ". " + move_history[row-1]);
			}
		}
		System.out.println();
	}

}
