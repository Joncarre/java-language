package main.java.es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Shape;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.java.es.ucm.fdi.tp.mvc.GameEvent;
import main.java.es.ucm.fdi.tp.ttt.TttAction;
import main.java.es.ucm.fdi.tp.view.GUIController.PlayerMode;
import main.java.es.ucm.fdi.tp.was.WolfAndSheepAction;
import main.java.es.ucm.fdi.tp.was.WolfAndSheepState;

public class WasView extends RectBoardView<WolfAndSheepState, WolfAndSheepAction>{

	private static final long serialVersionUID = 1L;
    private int rowFrom;
    private int colFrom;
    private int rowTo;
    private int colTo;

	@Override
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		if(firstClickReceived){
			firstClickReceived = false;
			rowTo = row;
			colTo = col;
			/*  La comprobación del turno no se hace aquí */
			if(state.getTurn() == gameCtrl.getPlayerId() && gameCtrl.getPlayerMode() == PlayerMode.MANUAL){
				WolfAndSheepAction action = new WolfAndSheepAction(state.getTurn(), rowFrom, colFrom, rowTo, colTo);
				if(state.isValid(action)) // Si la acción es válida...
					gameCtrl.makeManualMove(action);
				else
					JOptionPane.showMessageDialog(new JFrame(),
					"Movimiento inválido");
			}
		}else {
			firstClickReceived = true;
			rowFrom = row;
			colFrom = col;
		}
	}

	@Override
	protected void keyTyped(int keyCode) {
		if (firstClickReceived && keyCode == 27){ // 27 es el valor de la tecla ESCAPE
			firstClickReceived = false;
			System.out.print("Ha entrado en el KeyCode");
			gameCtrl.stopGame();
			this.messageViewer.addContent("Movimiento cancelado.");
			this.messageViewer.addContent("Elige un nuevo movimiento.");
		}
	}

	@Override
	protected Integer getPosition(int row, int col) {
		if (state != null && state.at(row, col) != -1)
			return state.at(row, col);
		else
			return null;
	}

	@Override
	protected int getNumCols() {
		if(state != null)
			return state.getDimension();
		else
			return -1;
	}

	@Override
	protected int getNumRows(){
		if(state != null)
			return state.getDimension();
		else
			return -1;
	}

	@Override
	protected Color getColor(int player) {
		if(player == 1){
			return Color.blue;
		}else{
			return Color.white;
		}
	}

	@Override
	protected Color getBackground(int row, int col) {
		if(row % 2 == 0 && col % 2 == 0 || row % 2 == 1 && col % 2 == 1)
			return Color.lightGray;
		else
			return Color.gray;
	}

	@Override
	protected Shape getShape(int player) {
		if(player == 1 || player == 0)
			return Shape.CIRCLE;
		else
			return Shape.RECTANGLE;
	}
	
	@Override
	protected int getSepPixels(){
		return 0;
	}
}
