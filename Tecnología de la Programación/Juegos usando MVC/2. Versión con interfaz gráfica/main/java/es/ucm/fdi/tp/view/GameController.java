package main.java.es.ucm.fdi.tp.view;

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
	
	/** Realiza un movimiento smart */
	public void makeSmartMove();
	
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
}
