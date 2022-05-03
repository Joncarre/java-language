package main.java.es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop.Action;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GamePlayer;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.base.player.ConcurrentAiPlayer;
import main.java.es.ucm.fdi.tp.base.player.RandomPlayer;
import main.java.es.ucm.fdi.tp.mvc.GameEvent;
import main.java.es.ucm.fdi.tp.mvc.GameEvent.EventType;
import main.java.es.ucm.fdi.tp.mvc.GameObservable;
import main.java.es.ucm.fdi.tp.mvc.GameObserver;
import main.java.es.ucm.fdi.tp.mvc.GameTable;
import main.java.es.ucm.fdi.tp.view.PlayersInfoViewer.PlayersInfoObserver;

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
	
	volatile GamePlayer randPlayer;
	volatile ConcurrentAiPlayer smartPlayer;
	volatile Future<?> smartPlayerFuture;
	volatile PlayerMode playerMode;
	volatile protected List<SmartMoveObserver> observers;
	volatile private GameTable<S, A> game;
	volatile private int playerId;
	volatile private boolean activeGame;
	volatile ExecutorService executor;
	
	/**
	 * Constructora con parámetros
	 * @param executor
	 * @param playerId
	 * @param randPlayer
	 * @param ConcurrentAIPlayer
	 * @param game
	 */
	public GUIController(int playerId, GamePlayer randPlayer, ConcurrentAiPlayer smartPlayer,
			GameTable<S, A> game, ExecutorService executor){
		this.executor = executor;
		this.randPlayer = randPlayer;
		this.smartPlayer = smartPlayer;
		this.playerId = playerId;
		this.game = game;
		this.activeGame = false;
		this.playerMode = PlayerMode.MANUAL; // Todos los jugadores comienzan con el modo manual
		this.observers = new ArrayList<SmartMoveObserver>();
	}
	
	/**
	 * Devuelve 'true' si el jugador que interactúa con la vista es al que le toca jugar
	 * @param p
	 * @return
	 */
	private boolean thisPlayerTurn(int p) {
		return playerId == p;
	}
	
	@Override
	public void handleEvent(final GameEvent<S, A> e){
		S state = e.getState();
		int currTurn = state.getTurn();
		activeGame = !state.isFinished() || e.getType() == EventType.Stop;
		
		/* --------------------- handleEvent de GUIController --------------------- */
		switch (e.getType()) {
			case Start:
			break;
			
			case Info:
			break;
			
			case Change:
				if (activeGame && thisPlayerTurn(currTurn))
					decideMakeAutomaticMove();					
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
		if (gameIsActive() && thisPlayerTurn(game.getState().getTurn()))
			gameExecute(action);
	}

	@Override
	public void makeRandomMove() {
		if (gameIsActive() && thisPlayerTurn(game.getState().getTurn()))
			gameExecute(randPlayer.requestAction(game.getState()));
	}

	@Override
	public Future<?> makeSmartMove(){
		if (gameIsActive() && thisPlayerTurn(game.getState().getTurn())){
			smartPlayerFuture = executor.submit(new Runnable(){ // Creamos una tarea que haga lo que hacía antes 'makeSmartMove'
				@Override
				public void run() {
					for(SmartMoveObserver o: observers)
						o.onStart();
					long time1 = System.currentTimeMillis();
					A action = smartPlayer.requestAction(game.getState());
					long time2 = System.currentTimeMillis();
					if(action != null)
						game.execute(action);
					else // Si la tarea del 'SmartPlayer' no se ejecutó, cambiamos el modo a manual
						changePlayerMode(PlayerMode.MANUAL);
						
					for(SmartMoveObserver o: observers)
						o.onEnd(action != null, time2 - time1, smartPlayer.getEvaluationCount(), smartPlayer.getValue());
				}
			});
		return smartPlayerFuture;
		}else
			return null;
	}

	@Override
	public void restartGame() {
		this.game.restart();
	}
	
	@Override
	public void stopGame(){
		this.game.stop();
	}

	@Override
	public void changePlayerMode(PlayerMode mode) {
		if (mode == playerMode)
			return;
		PlayerMode oldMode = this.playerMode;
		this.playerMode = mode;
		// Si el modo anterior era MANUAL y el nuevo no lo es...
		if (this.playerMode != PlayerMode.MANUAL && oldMode == PlayerMode.MANUAL)
			decideMakeAutomaticMove();
	}

	@Override
	public PlayerMode getPlayerMode() {
		return this.playerMode;
	}
	
	@Override
	public int getPlayerId(){
		return this.playerId;
	}
	
	/* ---------------------------------------- Métodos Práctica 6 ---------------------------------------- */
	
	/**
	 * Llama a game.start() y espera hasta que realmente se ejecute
	 */
	private void gameStart(){
		Future<?> r = executor.submit(new Runnable(){
			@Override
			public void run() {
				game.start();
			}
		});
		try{
			r.get();
		}catch(InterruptedException | ExecutionException e){ }
	}
	
	/**
	 * Llama a game.restart() y espera hasta que realmente se ejecute
	 */
	private void gameRestart(){
		Future<?> r = executor.submit(new Runnable(){
			@Override
			public void run() {
				game.restart();
			}
		});
		try{
			r.get();
		}catch(InterruptedException | ExecutionException e){ }
	}
	
	/**
	 * Llama a game.stop() y espera hasta que realmente se ejecute
	 */
	private void gameStop(){
		Future<?> r = executor.submit(new Runnable(){
			@Override
			public void run() {
				game.stop();
			}
		});
		try{
			r.get();
		}catch(InterruptedException | ExecutionException e){ }
	}
	
	/**
	 * Llama a game.execute() y espera hasta que realmente se ejecute
	 */
	private void gameExecute(final A action){
		Future<?> r = executor.submit(new Runnable(){
			@Override
			public void run() {
				game.execute(action);
			}
		});
		try{
			r.get();
		}catch(InterruptedException | ExecutionException e){ }
	}
	
	/**
	 * Llama a game.isActive() y espera hasta que realmente se ejecute
	 */
	private boolean gameIsActive(){
		Future<?> r = executor.submit(new Callable<Boolean>(){
			@Override
			public Boolean call(){
				return game.isActive();
			}
		});
		try{
			return (boolean) r.get();
		}catch(InterruptedException | ExecutionException e){ 
			return false;
		}
	}
	
	@Override
	public void smartPlayerConcurrencyLevel(int threads){
		smartPlayer.setMaxThreads(threads);
	}
	
	@Override
	public void smartPlayerTimeLimit(int time){
		smartPlayer.setTimeout(time);
	}

	@Override
	public void stopSmartPlayer() {
		smartPlayerFuture.cancel(true);
	}

	@Override
	public void addSmartPlayerObserver(SmartMoveObserver o) {
		observers.add(o);	
	}
	
	/**
	 * Devuelve el nombre del jugador
	 * @return
	 */
	public String getNamePlayer(){
		return smartPlayer.getName();
	}
}
