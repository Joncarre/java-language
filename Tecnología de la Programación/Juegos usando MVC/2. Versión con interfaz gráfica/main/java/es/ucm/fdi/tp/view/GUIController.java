package main.java.es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GamePlayer;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.base.player.RandomPlayer;
import main.java.es.ucm.fdi.tp.mvc.GameEvent;
import main.java.es.ucm.fdi.tp.mvc.GameEvent.EventType;
import main.java.es.ucm.fdi.tp.mvc.GameObservable;
import main.java.es.ucm.fdi.tp.mvc.GameObserver;
import main.java.es.ucm.fdi.tp.mvc.GameTable;

public class GUIController<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameController<S, A>{

	private static final long serialVersionUID = 1L;
	
	enum PlayerMode{ // Los tipos enumerados en Java son como clases; pueden tener constructora
		MANUAL("Manual"),
		RANDOM("Random"),
		AI("Smart");
		
		private String name;
		
		/**
		 * Constructora con parámetros
		 * @param name
		 */
		private PlayerMode(String name){
			this.name = name;
		}
		
		/**
		 * Devuelve el nombre
		 */
		public String toString(){
			return this.name;
		}
	}
	
	GamePlayer randPlayer;
	GamePlayer smartPlayer;
	PlayerMode playerMode;
	private GameTable<S, A> game;
	private int playerId;
	
	/**
	 * Constructora con parámetros
	 * @param playerId
	 * @param randPlayer
	 * @param smartPlayer
	 * @param guiView
	 * @param gameCtrl
	 * @param game
	 */
	public GUIController(int playerId, GamePlayer randPlayer, GamePlayer smartPlayer, GameTable<S, A> game){
		this.randPlayer = randPlayer;
		this.smartPlayer = smartPlayer;
		this.playerId = playerId;
		this.game = game;
		this.playerMode = PlayerMode.MANUAL; // Todos los jugadores comienzan con el modo manual
	}
	
	@Override
	public void handleEvent(final GameEvent<S, A> e){
		
		/* --------------------- handleEvent de GUIController --------------------- */
		switch (e.getType()) {
			case Start:
				
			break;
			case Info:

			break;
			case Change:

			break;
			case Stop:

			break;
			case Error:

			break;
			
			default:
			break;
		}
	}
	
	/**
	 * Decide de forma automática el movimiento a realizar
	 */
	@SuppressWarnings("unused")
	private void decideMakeAutomaticMove() {
		PlayerMode mode = getPlayerMode();
		switch (mode) {
			case MANUAL:
				// No hace nada. //
			break;
			case RANDOM:
				makeRandomMove();
			break;
			case AI:
				makeSmartMove();
			break;
			default:
			break;
		}
	}
	
	@Override
	public void makeManualMove(A action) {
		game.execute(action);
	}

	@Override
	public void makeRandomMove() {
		//randPlayer.requestAction(state);
	}

	@Override
	public void makeSmartMove() {
		//smartPlayer.requestAction(state);	
	}

	@Override
	public void restartGame() {
		this.game.restart();
	}

	@Override
	public void stopGame() {
		this.game.stop();
	}

	@Override
	public void changePlayerMode(PlayerMode playerMode) {
		this.playerMode = playerMode;
	}

	@Override
	public PlayerMode getPlayerMode() {
		return this.playerMode;
	}
	
	@Override
	public int getPlayerId(){
		return this.playerId;
	}
}
