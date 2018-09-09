import java.util.*;

public class MoveGenerator {
	private static MoveGenerator mg = null;
	private StateAnalyzer sa;
	private MoveGenerator() {
		sa = StateAnalyzer.getStateAnalyzer();
		depth = 5;
	}
	public static MoveGenerator getMoveGenerator() {
		if(mg == null)
			mg = new MoveGenerator();
		return mg;
	}
	
	private int depth;
	
	/**
	 * Generates computer's move if computer is making first move in the game.
	 */
	public State getFirstMove(State initial) {
		PriorityQueue<State> children = new PriorityQueue<>(
				new Comparator<State>() {
					public int compare(State arg0, State arg1) {
						return arg1.getEvaluation() - arg0.getEvaluation();
					}
				});
		children.addAll(getAllPossibleNextStates(initial , true));
		
		List<State> best = new Stack<>();
		int best_eval = children.peek().getEvaluation();
		while(best_eval - children.peek().getEvaluation() < 4) {
			best.add(children.remove());
		}
		
		
		return best.get((int)(Math.random()*best.size()));
	}
	
	/**
	 * Generates computer's response. 
	 */
	public State getNextMove(State parent , int depth_iterator) {
		if(depth_iterator%5 == 0 && depth < 7)
			depth++;
		State next = alphaBetaSearch(depth , parent);
		return next;
	}
	
	private State alphaBetaSearch(int max_depth , State parent) {
		return maxValue(parent , -1000 , 1000 , max_depth);
	}
	
	private State minValue(State parent , int alpha , int beta , int depth_left) {
		if(parent.checkIfLoss() || parent.checkIfWin() || depth_left < 0)
			return parent;
		
		State best = null;
		int value = 1000;
		PriorityQueue<State> children = new PriorityQueue<>(
				new Comparator<State>() {
					public int compare(State arg0, State arg1) {
						return arg1.getEvaluation() - arg0.getEvaluation();
					}
				});
		children.addAll(getAllPossibleNextStates(parent , false));
		
		for(State child : children) {
			State next = maxValue(child , alpha , beta , depth_left--);
			if(next.getEvaluation() < value) {
				value = next.getEvaluation();
				best = child;
			}
			
			if(value <= alpha)
				return best;
			
			if(value < beta)
				beta = value;
		}
		return best;
	}
	
	private State maxValue(State parent , int alpha , int beta , int depth_left) {
		if(parent.checkIfLoss() || parent.checkIfWin() || depth_left < 0)
			return parent;
		
		State best = null;
		int value = -1000;
		PriorityQueue<State> children = new PriorityQueue<>(
				new Comparator<State>() {
					public int compare(State arg0, State arg1) {
						return arg1.getEvaluation() - arg0.getEvaluation();
					}
				});
		children.addAll(getAllPossibleNextStates(parent , true));
		
		for(State child : children) {
			State next = minValue(child , alpha , beta , depth_left--);
			if(next.getEvaluation() > value) {
				value = next.getEvaluation();
				best = child;
			}
			
			if(value >= beta)
				return best;
			
			if(value > alpha)
				alpha = value;
		}
		return best;
	}
	
	private List<State> getAllPossibleNextStates(State parent , boolean computer_move){
		List<State> children = new Stack<>();
		
		int[][] board = parent.getBoard();
		int parent_row = parent.getComputerPosition()[0], parent_col = parent.getComputerPosition()[1];
		if(!computer_move) {
			parent_row = parent.getOpponentPosition()[0]; parent_col = parent.getOpponentPosition()[1];
		}
			//north
			for(int row = parent_row-1 ; row >= 0 ; row--) {
				if(board[row][parent_col] > 0) 
					break;
				State child = new State(getChildArray(board , parent_row , parent_col , row , parent_col , computer_move));
				children.add(child);
			}
			
			//west
			for(int col = parent_col-1 ; col >= 0 ; col--) {
				if(board[parent_row][col] > 0)
					break;
				State child = new State(getChildArray(board , parent_row , parent_col , parent_row , col , computer_move));
				children.add(child);
			}
			
			//south
			for(int row = parent_row+1 ; row < board.length ; row++) {
				if(board[row][parent_col] > 0)
					break;
				State child = new State(getChildArray(board , parent_row , parent_col , row , parent_col , computer_move));
				children.add(child);
				
			}
			
			//east
			for(int col = parent_col+1 ; col < board.length ; col++) {
				if(board[parent_row][col] > 0) 
					break;
				State child = new State(getChildArray(board ,parent_row , parent_col , parent_row , col , computer_move));
				children.add(child);
			}
			
			//north-west
			for(int row = parent_row-1 ; row >= 0 ; row--) {
				int col = parent_col - (parent_row - row);
				if(col < 0)
					break;
				if(board[row][col] > 0 )
					break;
				State child = new State(getChildArray(board ,parent_row , parent_col, row , col , computer_move));
				children.add(child);
			}
			
			//north-east
			for(int row = parent_row-1 ; row >= 0 ; row--) {
				int col = parent_col + (parent_row - row);
				if(col >= board.length)
					break;
				if(board[row][col] > 0 )
					break;
				State child = new State(getChildArray(board , parent_row , parent_col , row , col , computer_move));
				children.add(child);
			}
			
			//south-west
			for(int row = parent_row+1 ; row < board.length ; row++) {
				int col = parent_col - (row - parent_row);
				if(col < 0)
					break;
				if(board[row][col] > 0 )
					break;
				State child = new State(getChildArray(board , parent_row , parent_col , row , col , computer_move));
				children.add(child);
			}
			
			//south-east
			for(int row = parent_row+1 ; row < board.length ; row++) {
				int col = parent_col + (row - parent_row);
				if(col >= board.length)
					break;
				if(board[row][col] > 0 )
					break;
				State child = new State(getChildArray(board , parent_row , parent_col , row , col , computer_move));
				children.add(child);
			}
		
		return children;
	}
	
	public int[][] getChildArray(int[][] parent_array , int old_pos_row , int old_pos_col , int new_pos_row , int new_pos_col , boolean computer_move){
		int[][] child_array = new int[parent_array.length][parent_array.length];
		
		for(int row = 0 ; row < child_array.length ; row++) {
			for(int col = 0 ; col < child_array.length ; col++) {
				if(row == new_pos_row && col == new_pos_col) {
					if(computer_move)
						child_array[row][col] = 1;
					else
						child_array[row][col] = 2;
				}else if(row == old_pos_row && col == old_pos_col) {
					child_array[row][col] = 3;
				}else {
					child_array[row][col] = parent_array[row][col];
				}
			}
		}
		
		return child_array;
	}

}
