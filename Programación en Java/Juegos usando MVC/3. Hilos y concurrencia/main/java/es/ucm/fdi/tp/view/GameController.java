package main.java.es.ucm.fdi.tp.view;

import java.util.concurrent.Future;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GamePlayer;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.mvc.GameEvent;
import main.java.es.ucm.fdi.tp.mvc.GameObserver;
import main.java.es.ucm.fdi.tp.mvc.GameTable;
import main.java.es.ucm.fdi.tp.view.GUIController.PlayerMode;

public interface GameController<S extends GameState<S, A>, A extends GameAction<S, A>>{
	
	/** Realiza un movimiento manual */
	public void makeManualMove(A a);
	
	/** Realiza un movimiento random */
	public void makeRandomMove();
	
	/** Realiza un movimiento smart 
	 * @return */
	public Future<?> makeSmartMove();
	
	/** Resetea el juego */
	public void restartGame();
	
	/** Detiene el juego */
	public void stopGame();
	
	/** Cambia el modo de jugador */
	public void changePlayerMode(PlayerMode playerMode);
	
	/** Básicamente actualizará el tablero cuando se crea una notificación en el modelo */ 
	public void handleEvent(GameEvent<S, A> e);
	
	/** Devuelve el modo del jugador */
	public PlayerMode getPlayerMode();
	
	/** Devuelve el id del jugador. Es decir, el de la ventana en cuestión */
	public int getPlayerId();
	
	/** Modifica el número de threads */
	public void smartPlayerConcurrencyLevel(int nThreads);
	
	/**  Modifica el time out */
	public void smartPlayerTimeLimit(int nThreads);
	
	/** Cancela el movimiento smart */
	public void stopSmartPlayer();
	
	/** Añade un jugador smart a la lista de observadores */
	public void addSmartPlayerObserver(SmartMoveObserver o);
}
