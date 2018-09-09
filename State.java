
public class State {
	private StateAnalyzer sa;
	
	private int heuristic;
	private int[] computer_position;
	private int[] opponent_position;
	private int[][] board;
	
	public State(int[][] board) {
		sa = StateAnalyzer.getStateAnalyzer();
		
		this.board = board;
		findPlayerPositions();
		heuristic = sa.getHeuristic(this);
	}
	
	public int[] getComputerPosition() {
		return computer_position;
	}
	
	public int[] getOpponentPosition() {
		return opponent_position;
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	public int getEvaluation() {
		if(heuristic < 0)
			heuristic = sa.getHeuristic(this);
		return heuristic;
	}
	
	public char[][] getPrintableBoard(){
		char[][] printable_board = new char[board.length][board.length];
		
		for(int row = 0 ; row < board.length ; row++) {
			for(int col = 0 ; col < board.length ; col++) {
				char mark = '-';
				if(board[row][col] == 3) {
					mark = '#';
				}else if(board[row][col] == 1) {
					mark = 'X';
				}else if(board[row][col] == 2) {
					mark = 'O';
				}
				printable_board[row][col] = mark;
			}
		}
		
		return printable_board;
	}
	
	private void findPlayerPositions() {
		computer_position = new int[2];
		opponent_position = new int[2];
		for(int row = 0 ; row < board.length ; row++) {
			for(int col = 0 ; col < board.length ; col++) {
				if(board[row][col] == 1) {
					computer_position[0] = row;
					computer_position[1] = col;
				}else if(board[row][col] == 2) {
					opponent_position[0] = row;
					opponent_position[1] = col;
				}
			}
		}
	}
	
	public boolean checkIfLoss() {
		if(heuristic == -100)
			return true;
		return false;
	}
	
	public boolean checkIfWin() {
		if(heuristic == 100)
			return true;
		return false;
	}
}
