package main.java.es.ucm.fdi.tp.view;

import java.util.List;
import java.util.concurrent.Future;

import org.junit.runner.notification.StoppedByUserException;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameError;
import main.java.es.ucm.fdi.tp.base.model.GamePlayer;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.mvc.GameEvent;
import main.java.es.ucm.fdi.tp.mvc.GameTable;
import main.java.es.ucm.fdi.tp.mvc.GameEvent.EventType;
import main.java.es.ucm.fdi.tp.view.GUIController.PlayerMode;

public class ConsoleController<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameController<S, A> {
	
	private List<GamePlayer> players;
	private GameTable<S, A> game;
	private boolean stopped;
	
	/**
	 * Constructora con parámetros
	 * @param players
	 * @param game
	 */
	public ConsoleController(List<GamePlayer> players, GameTable<S, A> game){
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).getPlayerNumber() == -1)
				throw new GameError("El jugador es incorrecto");
		}
		this.players = players;
		this.game = game;
		this.stopped = true;
	}
	
	/**
	 * Controla el flujo de ejecución de una partida
	 */
	@SuppressWarnings("unchecked")
	public void playGame(){
		game.start();
        System.out.println("\n" + game.getState()); // Mostramos el estado inicial
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
            System.out.println("\n" + game.getState());
		}
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
	public void handleEvent(final GameEvent<S, A> e) {
		// No hace nada. //
	}

	@Override
	public void makeManualMove(A action) {
		// No hace nada. //
	}

	@Override
	public void makeRandomMove() {
		// No hace nada. //
	}

	@Override
	public Future<?> makeSmartMove() {
		// No hace nada. //
		return null;
	}

	@Override
	public void restartGame() {
		this.game.restart();
	}

	@Override
	public void stopGame() {
		game.stop();
		this.stopped = true;	
	}

	@Override
	public void changePlayerMode(PlayerMode mode) {
		// No hace nada. //
	}

	@Override
	public PlayerMode getPlayerMode() {
		// No hace nada. //
		return null;
	}

	@Override
	public int getPlayerId() {
		// No hace nada. //
		return 0;
	}

	@Override
	public void smartPlayerConcurrencyLevel(int nThreads) {
		// No hace nada. //
	}

	@Override
	public void smartPlayerTimeLimit(int nThreads) {
		// No hace nada. //	
	}

	@Override
	public void stopSmartPlayer() {
		// No hace nada. //		
	}

	@Override
	public void addSmartPlayerObserver(SmartMoveObserver o) {
		// No hace nada. //			
	}
}
