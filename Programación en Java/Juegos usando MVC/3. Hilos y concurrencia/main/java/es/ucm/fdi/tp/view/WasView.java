package main.java.es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.KeyEvent;
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
		if(this.active){
			if(firstClickReceived){
				firstClickReceived = false;
				rowTo = row;
				colTo = col;
				WolfAndSheepAction action = new WolfAndSheepAction(state.getTurn(), rowFrom, colFrom, rowTo, colTo);
				if(state.isValid(action)) // Si la acción es válida...
					gameCtrl.makeManualMove(action);
				else
					JOptionPane.showMessageDialog(new JFrame(),
						"Movimiento inválido");
			}else {
				firstClickReceived = true;
				rowFrom = row;
				colFrom = col;
			}
		}
	}

	@Override
	protected void keyTyped(int keyCode) {
		if (firstClickReceived && keyCode == KeyEvent.VK_ESCAPE){ // 27 es el valor de la tecla ESCAPE
			firstClickReceived = false;
			System.out.print("Ha entrado en el KeyCode");
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
}
