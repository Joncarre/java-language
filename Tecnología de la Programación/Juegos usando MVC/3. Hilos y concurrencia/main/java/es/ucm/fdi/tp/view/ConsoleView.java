package main.java.es.ucm.fdi.tp.view;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.mvc.GameEvent;
import main.java.es.ucm.fdi.tp.mvc.GameEvent.EventType;
import main.java.es.ucm.fdi.tp.mvc.GameObservable;
import main.java.es.ucm.fdi.tp.mvc.GameObserver;

public class ConsoleView<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObserver {
	
	/**
	 * Constructora con parámetros
	 * @param gameTable
	 */
	public ConsoleView(GameObservable<S, A> gameTable){
		gameTable.addObserver(this);
	}

	@Override
	public void notifyEvent(GameEvent e) {
		System.out.println(e.toString());
	}	
}
