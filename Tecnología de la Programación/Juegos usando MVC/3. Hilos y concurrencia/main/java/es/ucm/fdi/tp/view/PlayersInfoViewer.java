package main.java.es.ucm.fdi.tp.view;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.mvc.GameObserver;

public abstract class PlayersInfoViewer<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	/**
	 * Modifica el contenido de PlayersInfoViewerComp
	 */
	public void setPlayersInfoViewer(PlayersInfoViewer<S, A> playersInfoViewer){
		// Es vacío por defecto. Puede concretarse en alguna de sus subclases. //
	}

	/** Se usa para establecer el número de jugadores */
	abstract public void setNumberOfPlayer(int n);
	
	/** Devuelve el color asociado a un jugador */
	abstract public Color getPlayerColor(int p);
	
	/** Los observadores deben implementar esta interfaz */
	public interface PlayersInfoObserver{
		public void colorChanged(int p, Color color);
	}
	
	/** Declaramos una lista de observadores */
	protected List<PlayersInfoObserver> observers;
	
	/**
	 * Añadir observador a la lista
	 * @param o
	 */
	public void addObserver(PlayersInfoObserver o){
		observers.add(o);
	}
	
	/**
	 * Notificar a los observadores
	 * @param p
	 * @param color
	 */
	protected void notifyObservers(int p, Color color){
		for(PlayersInfoObserver o: observers)
			o.colorChanged(p, color);
	}
}
