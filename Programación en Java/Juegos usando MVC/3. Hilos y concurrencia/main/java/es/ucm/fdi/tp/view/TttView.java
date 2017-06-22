package main.java.es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Shape;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.java.es.ucm.fdi.tp.mvc.GameEvent;
import main.java.es.ucm.fdi.tp.ttt.TttAction;
import main.java.es.ucm.fdi.tp.ttt.TttState;
import main.java.es.ucm.fdi.tp.view.GUIController.PlayerMode;
import main.java.es.ucm.fdi.tp.was.WolfAndSheepAction;
import main.java.es.ucm.fdi.tp.was.WolfAndSheepState;

public class TttView extends RectBoardView<TttState, TttAction>{

	private static final long serialVersionUID = 1L;

	@Override
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		if(this.active){
			TttAction action = new TttAction(state.getTurn(), row, col);
			if(state.isValid(action)) // Si la acción es válida...
				gameCtrl.makeManualMove(action);
			else
				JOptionPane.showMessageDialog(new JFrame(),
				"Movimiento inválido");
		}
	}
	
	@Override
	protected void keyTyped(int keyCode) {
		// No hace nada. //
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
