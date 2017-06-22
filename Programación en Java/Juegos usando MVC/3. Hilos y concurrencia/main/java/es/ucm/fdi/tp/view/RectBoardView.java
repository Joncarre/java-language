package main.java.es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Shape;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.extra.jboard.JBoard;
import main.java.es.ucm.fdi.tp.extra.jcolor.ColorChooser;
import main.java.es.ucm.fdi.tp.extra.jcolor.ColorChooserExample;
import main.java.es.ucm.fdi.tp.mvc.GameObserver;
import main.java.es.ucm.fdi.tp.ttt.TttAction;

public abstract class RectBoardView<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected GameController<S, A> gameCtrl;
	protected MessageViewer<S, A> messageViewer;
	protected PlayersInfoViewer<S, A> playersInfoViewer;
	protected S state;
	protected JComponent jboard;
	protected boolean active;
	protected boolean firstClickReceived;
	
	public enum Shape {
		CIRCLE, RECTANGLE
	}
	
	/**
	 * Constructora
	 */
	public RectBoardView(){
		super();
		initGUI();
		this.active = false;
	}
	
	/**
	 * Inicializa todo lo necesario para el 'RectBoardView'
	 */
	private void initGUI(){
		this.setLayout(new BorderLayout());
		this.jboard = new JBoard() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
				RectBoardView.this.mouseClicked(row, col, clickCount, mouseButton);	
			}
			
			@Override
			protected void keyTyped(int keyCode) {
				RectBoardView.this.keyTyped(keyCode);
			}
			
			@Override
			protected Integer getPosition(int row, int col) {
				return RectBoardView.this.getPosition(row, col);
			}
			
			@Override
			protected int getNumRows() {
				return RectBoardView.this.getNumRows();
			}
			
			@Override
			protected int getNumCols() {
				return RectBoardView.this.getNumCols();
			}
			
			@Override
			protected Color getColor(int player) {
				return RectBoardView.this.getColor(player);
			}
			
			@Override
			protected Color getBackground(int row, int col) {
				return RectBoardView.this.getBackground();
			}

			@Override
			protected Shape getShape(int player) {
				//return RectBoardView.this.getShape(player);
				return null;
			}
			
			@Override
			protected int getSepPixels(){
				return RectBoardView.this.getSepPixels();
			}
		};
		this.add(jboard, BorderLayout.CENTER);
		this.jboard.setBorder(new LineBorder(Color.white, 3));
	}
	
	/* ----------------------------------- Métodos abstractos del jBoard ----------------------------------- */

	/** Método que se llama cuando se pulsa sobre la casilla de un tablero */
	protected abstract void mouseClicked(int row, int col, int clickCount, int mouseButton);
	
	/** Esto no sé qué hace */
	protected abstract void keyTyped(int keyCode);
	
	/** Devuelve el valor de una posición del tablero en el estado actual del tablero */
	protected abstract Integer getPosition(int row, int col);
	
	/** Devuelve el número de columnas del tablero */
	protected abstract int getNumCols();
	
	/** Devuelve el número de filas del tablero */
	protected abstract int getNumRows();
	
	/* ----------------------------------- Modificar información de los mensajes y jugadores ----------------------------------- */
	
	@Override
	public void setMessageViewer(MessageViewer<S, A> messageViewer){
		this.messageViewer = messageViewer;
	}
	
	@Override
	public void setPlayersInfoViewer(PlayersInfoViewer<S, A> playersInfoViewer){
		this.playersInfoViewer = playersInfoViewer;
	}
	
	@Override
	public void setGameController(GameController<S,A> gameCtrl){
		this.gameCtrl = gameCtrl;
	}
	
	@Override
	public void colorChanged(int p, Color color) {
		repaint();
	}
	
	protected Color getColor(int player) {
		return playersInfoViewer.getPlayerColor(player);
	}

	protected Shape getShape(int player) {
		return player < state.getPlayerCount() ? Shape.CIRCLE : Shape.RECTANGLE;
	}

	protected Color getBackground(int row, int col) {
		return Color.LIGHT_GRAY;
	}

	protected int getSepPixels() {
		return 1;
	}
	
	/* ----------------------------------- enable, disable & update ----------------------------------- */
	
	@Override
	public void enable(){
		this.active = true;
	}
	
	@Override
	public void disable(){
		this.active = false;
	}
	
	@Override
	public void update(S state){
		this.state = state;
		jboard.repaint();
	}
	
	/**
	 * Devuelve un booleano indicando si el jBoard está o no activo
	 * @return
	 */
	public boolean isActive(){
		return this.active;
	}
}