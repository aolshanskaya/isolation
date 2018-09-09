import java.util.List;
import java.util.Stack;

public class StateAnalyzer {
	private static StateAnalyzer sa = null;
	private StateAnalyzer() {}
	public static StateAnalyzer getStateAnalyzer() {
		if(sa == null)
			sa = new StateAnalyzer();
		return sa;
	}
	
	/**
	 * if killer state (opponent has no liberties left) then returns 100;
	 * else returns player - opponent liberties. 
	 */
	public int getHeuristic(State state) {
		//check terminal - win
		int opponent_liberties = getNumLiberties(state.getOpponentPosition() , state.getBoard()).size();
		if(opponent_liberties == 0)
			return 100;
		//check terminal - loss
		int player_liberties = getNumLiberties(state.getComputerPosition() , state.getBoard()).size();
		if(player_liberties == 0)
			return -100;
		//return difference of liberties
		return player_liberties - opponent_liberties;
	}
	
	/**
	 * Returns a list of possible next moves for the opponent to check the user's input against.
	 */
	public List<int[]> listOpponentLiberties(State state){
		return getNumLiberties(state.getOpponentPosition() , state.getBoard());
	}
	
	/**
	 * Returns the total number of liberties for the player at the given position.
	 */
	private List<int[]> getNumLiberties(int[] player_position , int[][] board) {
		List<int[]> liberties = new Stack<>();
		int num_liberties = 0;
		
		//north
		int lib = 0;
		for(int row = player_position[0]-1 ; row >= 0 ; row--) {
			if(board[row][player_position[1]] > 0) 
				break;
			int[] to_add = {row , player_position[1]};
			liberties.add(to_add);
			num_liberties++;
			lib++;
		}
		
		//west
		lib = 0;
		for(int col = player_position[1]-1 ; col >= 0 ; col--) {
			if(board[player_position[0]][col] > 0)
				break;
			int[] to_add = {player_position[0] , col};
			liberties.add(to_add);
			num_liberties++;
			lib++;
		}
		
		//south
		lib = 0;
		for(int row = player_position[0]+1 ; row < board.length ; row++) {
			if(board[row][player_position[1]] > 0)
				break;
			int[] to_add = {row , player_position[1]};
			liberties.add(to_add);
			num_liberties++;
			lib++;
		}
		
		//east
		lib = 0;
		for(int col = player_position[1]+1 ; col < board.length ; col++) {
			if(board[player_position[0]][col] > 0) 
				break;
			int[] to_add = {player_position[0] , col};
			liberties.add(to_add);
			num_liberties++;
			lib++;
		}
		
		//north-west
		lib = 0;
		for(int row = player_position[0]-1 ; row >= 0 ; row--) {
			int col = player_position[1] - (player_position[0] - row);
			if(col < 0)
				break;
			if(board[row][col] > 0 )
				break;
			int[] to_add = {row , col};
			liberties.add(to_add);
			num_liberties++;
			lib++;
		}
		
		//north-east
		lib = 0;
		for(int row = player_position[0]-1 ; row >= 0 ; row--) {
			int col = player_position[1] + (player_position[0] - row);
			if(col >= board.length)
				break;
			if(board[row][col] > 0 )
				break;
			int[] to_add = {row , col};
			liberties.add(to_add);
			num_liberties++;
			lib++;
		}
		
		//south-west
		lib = 0;
		for(int row = player_position[0]+1 ; row < board.length ; row++) {
			int col = player_position[1] - (row - player_position[0]);
			if(col < 0)
				break;
			if(board[row][col] > 0 )
				break;
			int[] to_add = {row , col};
			liberties.add(to_add);
			num_liberties++;
			lib++;
		}
		
		//south-east
		lib = 0;
		for(int row = player_position[0]+1 ; row < board.length ; row++) {
			int col = player_position[1] + (row - player_position[0]);
			if(col >= board.length)
				break;
			if(board[row][col] > 0 )
				break;
			int[] to_add = {row , col};
			liberties.add(to_add);
			num_liberties++;
			lib++;
		}
		return liberties;
	}

}
