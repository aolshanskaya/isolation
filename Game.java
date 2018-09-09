import java.util.*;

public class Game {
	private List<State> states;
	private List<int[]> moves;
	private boolean first_to_move;
	private boolean computer_move;
	
	public Game(State initial_state , boolean first) {
		states = new Stack<>();
		moves = new Stack<>();
		
		first_to_move = first;
		computer_move = true;
		if(!first_to_move)
			computer_move = false;
		states.add(initial_state);
	}
	
	public void recordMove(State state , int[] move) {
		states.add(state);
		moves.add(move);
		computer_move = !computer_move;
	}
	
	public boolean amIPlayer1() {
		return first_to_move;
	}
	
	public State getCurrentState() {
		if(states.isEmpty())
			return null;
		return states.get(states.size()-1);
	}
	
	public String[] getPrintableMoveHistory(){
		String[] printable_moves = new String[(moves.size()+1)/2];
		
		for(int i = 0 ; i < printable_moves.length ; i++) {
			StringBuilder sb = new StringBuilder();
			
			sb.append((char) ('A' + moves.get(i*2)[0]));
			sb.append(moves.get(i*2)[1]+1);
			
			if(i != printable_moves.length-1 || moves.size()%2 == 0) {
				sb.append("\t");
				sb.append((char) ('A' + moves.get(i*2+1)[0]));
				sb.append(moves.get(i*2+1)[1]+1);
			}
			printable_moves[i] = sb.toString();
		}
		return printable_moves;
	}

}
