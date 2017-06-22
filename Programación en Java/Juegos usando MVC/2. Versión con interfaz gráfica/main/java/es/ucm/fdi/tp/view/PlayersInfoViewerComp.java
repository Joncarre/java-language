package main.java.es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameState;

public class PlayersInfoViewerComp<S extends GameState<S, A>, A extends GameAction<S, A>> extends PlayersInfoViewer<S, A> {

	private static final long serialVersionUID = 1L;
	
	private int numPlayers;
	
	public PlayersInfoViewerComp(int numPlayers) {
		this.numPlayers = numPlayers;
		initGUI();
	}
	
	/**
	 * Inicializa lo necesario para el 'PlayersInfoViewer'
	 */
	public void initGUI(){
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Player Information"));
		this.setPreferredSize(new Dimension(150, 150));
		this.setMinimumSize (new Dimension (150, 150));
	}
	
	/* ----------------------------------- Métodos de las clases abstractas PlayersInfoViewer y GUIView ----------------------------------- */

	@Override
	public void setNumberOfPlayer(int n) {
		this.numPlayers = n;
	}

	@Override
	public Color getPlayerColor(int p) {
		return Color.red;
	}

	@Override
	public void enable() {
		// No hace nada. //
	}

	@Override
	public void disable() {
		// No hace nada. //
	}
	
	@Override
	public void update(S state) {
		// No hace nada. //
	}

	@Override
	public void setMessageViewer(MessageViewer<S, A> messageInfoViewer) {
		// No hace nada. //
	}

	@Override
	public void setGameController(GameController<S, A> gameCtrl) {
		// No hace nada. //
	}
}
