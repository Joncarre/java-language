package main.java.es.ucm.fdi.tp.view;

import java.util.List;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameError;
import main.java.es.ucm.fdi.tp.base.model.GamePlayer;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.mvc.GameEvent;
import main.java.es.ucm.fdi.tp.mvc.GameTable;
import main.java.es.ucm.fdi.tp.view.GUIController.PlayerMode;

public class ConsoleController<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameController {
	
	private List<GamePlayer> players;
	private boolean stopped;
	
	/**
	 * Constructora con parámetros
	 * @param players
	 * @param game
	 */
	public ConsoleController(List<GamePlayer> players, GameTable<S, A> game){
		//super(game);
		
		for(int i = 0; i < players.size(); i++){
			/* ¿Por qué si es distinto de 1? */
			if(players.get(i).getPlayerNumber() != 1)
				throw new GameError("El jugador es incorrecto");
		}
		this.players = players;
		this.stopped = true;
	}
	
	/**
	 * Controla el flujo de ejecución de una partida
	 */
	@SuppressWarnings("unchecked")
	public void playGame(){
		/*startGame();
		while(!game.getState().isFinished() && !this.stopped){
			// request move
			A action = (A) players.get(game.getState().getTurn()).requestAction(game.getState());
			// apply move
			game.execute(action);
			
			if (game.getState().isFinished()) {
				// game over
				String endText = "The game ended: ";
				int winner = game.getState().getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
			}
		}*/
	}
	
	/**
	 * Similar al playGame() de la Práctica 4
	 */
	public void run(){
		this.stopped = false;
		playGame();
		this.stopped = true;
	}

	@Override
	public void makeManualMove(GameAction a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeRandomMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeSmartMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restartGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePlayerMode(PlayerMode playerMode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvent(GameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlayerMode getPlayerMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPlayerId() {
		// TODO Auto-generated method stub
		return 0;
	}
}
