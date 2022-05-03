package main.java.es.ucm.fdi.tp.was;

import main.java.es.ucm.fdi.tp.base.model.GameAction;

public class WolfAndSheepAction implements GameAction<WolfAndSheepState, WolfAndSheepAction> {

	private static final long serialVersionUID = -7358891209285495447L;
	
	private int player;
    private int rowFrom;
    private int colFrom;
    private int rowTo;
    private int colTo;
    
    /**
     * Constructora
     * @param player
     * @param row
     * @param col
     */
    public WolfAndSheepAction(int player, int rowFrom, int colFrom, int rowTo, int colTo) {
        this.player = player;
        this.rowFrom = rowFrom;
        this.colFrom = colFrom;
        this.rowTo = rowTo;
        this.colTo = colTo;
    }
    
	@Override
	public int getPlayerNumber() {
        return player;
	}

	@Override
	public WolfAndSheepState applyTo(WolfAndSheepState state) {
        if (player != state.getTurn()) {
            throw new IllegalArgumentException("Not the turn of this player");
        }
        
        /** Realizar movimiento */
        int[][] board = state.getBoard();
        board[rowTo][colTo] = player; // Desplazamos a la casilla destino
        board[rowFrom][colFrom] = -1; // Y la casilla origen la dejamos "vac�a"

        /** Actualizar estado */
        WolfAndSheepState next;
        if(WolfAndSheepState.isWinner(board, state.getTurn()))
        	next = new WolfAndSheepState(state, board, true, state.getTurn());
        else
        	next = new WolfAndSheepState(state, board, false, -1);
        
        return next;
	}
	/**
	 * Devuelve la fila origen
	 * @return
	 */
    public int getRowFrom() {
        return rowFrom;
    }
    /**
     * Devuelve la columna origen
     * @return
     */
    public int getColFrom() {
        return colFrom;
    }
    /**
     * Devuelve la fila destino
     * @return
     */
    public int getRowTo() {
        return rowTo;
    }
    /**
     * Devuelve la columna destino
     * @return
     */
    public int getColTo() {
        return colTo;
    }
    /**
     * Imprime las opciones disponibles para realizar un movimiento
     */
    public String toString() {
        return "place " + player + " from (" + rowFrom + ", " + colFrom + ") to (" + rowTo + ", " + colTo + ")";
    }
}
