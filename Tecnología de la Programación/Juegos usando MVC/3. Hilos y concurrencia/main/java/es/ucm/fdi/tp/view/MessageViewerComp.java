package main.java.es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameState;

public class MessageViewerComp<S extends GameState<S, A>, A extends GameAction<S, A>> extends MessageViewer<S, A>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextArea msgArea;
	
	/**
	 * Constructora
	 */
	public MessageViewerComp(){
		initGUI();
	}
	
	/**
	 * Inicializa lo necesario para el 'MessagesViewer'
	 */
	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Messages Viewer"));
		msgArea = new JTextArea(15, 20);
		msgArea.setEditable(false); // No dejamos al usuario escribir cosas en el 'Messages Viewer'
		JScrollPane statusAreaScroll = new JScrollPane(msgArea); // Barra de desplazamiento
		this.add(statusAreaScroll, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(255, 400));
		this.setMinimumSize (new Dimension (255, 400));
	}
	
	/* ----------------------------------- Métodos de las clases abstractas MessageViewer y GUIView ----------------------------------- */

	@Override
	public void clearMessagesViewer() {
		msgArea.setText("");	
	}

	@Override
	public void addContent(String msg) {
		msgArea.append("* " + msg + "\n");
	}

	@Override
	public void setContent(String msg) {
		msgArea.setText("* " + msg + "\n");	
	}

	@Override
	public String getContent() {
		return msgArea.getText();
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
	public void setPlayersInfoViewer(PlayersInfoViewer<S, A> playersInfoViewer) {
		// No hace nada. //
	}

	@Override
	public void setGameController(GameController<S, A> gameCtrl) {
		// No hace nada. //
	}

	@Override
	public void colorChanged(int p, Color color) {
		// No hace nada. //	
	}
}
