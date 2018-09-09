
public class GameEngine {
	private static UI ui;
	private static MoveGenerator mg;
	private static StateAnalyzer sa;
	private static GameEngine ge = null;
	private GameEngine() {
		ui = UI.getUI();
		mg = MoveGenerator.getMoveGenerator();
		sa = StateAnalyzer.getStateAnalyzer();
	}
	public static GameEngine getGameEngine() {
		if(ge == null)
			ge = new GameEngine();
		
		return ge;
	}
	
	private static final int BOARD_DIMENSIONS = 8;
	
	public static void startNewGame() {
		int[] game_params = ui.welcomePrompt();
		boolean computer_first = true;
		if(game_params[0] == 1)
			computer_first = false;
		
		playGame(new Game(generateInitialState(computer_first) , computer_first) , computer_first , game_params[1]);
		
	}
	
	private static void playGame(Game game , boolean computer_move , int time_to_think) {
		boolean finished = false;
		int move = 0;
		ui.printState(game.getCurrentState().getPrintableBoard() , game.getPrintableMoveHistory());
		
		while(!finished) {
			if(game.getCurrentState().checkIfWin()) {
				finished = true;
				ui.endOfGameMessage(true);
				break;
			}
			if(game.getCurrentState().checkIfLoss()) {
				finished = true;
				ui.endOfGameMessage(false);
				break;
			}
			
			if(computer_move) {
				State next_move = null;
				if(move == 0) {
					next_move = mg.getFirstMove(game.getCurrentState());
				}else {
					next_move = mg.getNextMove(game.getCurrentState() , move);
				}
				game.recordMove(next_move , next_move.getComputerPosition());
			}else {
				int[] opponent_move = ui.promptForOpponentMove(sa.listOpponentLiberties(game.getCurrentState()));
				State opp_move = new State(mg.getChildArray(game.getCurrentState().getBoard(), game.getCurrentState().getOpponentPosition()[0] , game.getCurrentState().getOpponentPosition()[1] , opponent_move[0], opponent_move[1], computer_move));
				game.recordMove(opp_move, opponent_move);
			}
			computer_move = !computer_move;
			move++;
			ui.printState(game.getCurrentState().getPrintableBoard() , game.getPrintableMoveHistory());
		}
	}
	
	private static State generateInitialState(boolean computer_first) {
		int[][] board = new int[BOARD_DIMENSIONS][BOARD_DIMENSIONS];
		
		if(computer_first) {
			board[0][0] = 1;
			board[board.length-1][board.length-1] = 2;
		}else {
			board[0][0] = 2;
			board[board.length-1][board.length-1] = 1;
		}
		
		return new State(board);
	}

}
