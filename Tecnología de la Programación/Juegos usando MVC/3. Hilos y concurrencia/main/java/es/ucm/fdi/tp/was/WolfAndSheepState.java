package main.java.es.ucm.fdi.tp.was;

import java.awt.Window;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import main.java.es.ucm.fdi.tp.base.model.GameState;

public class WolfAndSheepState extends GameState<WolfAndSheepState, WolfAndSheepAction> {
	
	private static final long serialVersionUID = 5414323895776936939L;
	
	private final int turn;
    private final boolean finished;
    private final int[][] board;
    private final int winner;

    public static final int DIM = 8; // Por el momento el tablero ser� de tama�o fijo

    final static int EMPTY = -1;

    /**
     * Constructora sin par�metros (inicializaci�n)
     * @param dim
     */
	public WolfAndSheepState() {
        super(2);
        board = new int[DIM][];
        for (int i = 0; i < DIM; i++) {
            board[i] = new int[DIM];
            for (int j = 0; j < DIM; j++) 
            	board[i][j] = EMPTY;
        }
        // Metemos al lobo
        board[DIM - 1][0] = 0;
        // Metemos a las ovejas
        for(int i = 1; i < DIM; i = i + 2)
        	board[0][i] = 1;
        this.turn = 0;
        this.winner = -1;
        this.finished = false;
	}
	
	/**
	 * Constructora con parámetros (cambio de estado al realizar una acción)
	 * @param prev
	 * @param board
	 * @param finished
	 * @param winner
	 */
    public WolfAndSheepState(WolfAndSheepState prev, int[][] board, boolean finished, int winner) {
    	super(2);
        this.board = board;
        this.turn = (prev.turn + 1) % 2;
        this.finished = finished;
        this.winner = winner;
    }
    
    /**
     * Nos dice si la acción realizada es válida
     * @param action
     * @return
     */
    public boolean isValid(WolfAndSheepAction action) {
        if (isFinished()) {
            return false;
        }
        boolean validAction = false;
        /* IMPORTANTE: vamos a comprobar que la dirección es correcta. En realida habría que recorrer la lista de acciones con
         * un iterador y ver si la acción que viene por parámetro está en ella. Pero bueno... nos sirve esto. */
        int rowFrom = action.getRowFrom();
        int colFrom = action.getColFrom();
        int rowTo = action.getRowTo();
        int colTo = action.getColTo();
        
        /* IMPORTANTE: es necesario comprobar lo siguiente:
         * 		1.- La dirección es correcta (primeras dos condiciones del if).
         * 		2.- En la casilla origen está el jugador al que le toca mover (tercera condición del if). 
         * 		Si no, un lobo podría mover una oveja, por ejemplo.
         * 		3.- La casilla destino es EMPTY (cuarta condición del if).
         */
        if(action.getPlayerNumber() == 1){ // Si es una oveja...
            if(rowTo - rowFrom == 1 && colTo - colFrom == 1 && at(rowFrom, colFrom) == 1 && at(rowTo, colTo) == EMPTY) /** Si ha pulsado Abajo-Derecha */
            	validAction = true;
            if(rowTo - rowFrom == 1 && colTo - colFrom == -1 && at(rowFrom, colFrom) == 1 && at(rowTo, colTo) == EMPTY) /** Si ha pulsado Abajo-Izquierda */
                validAction = true;
        }else{ // Si es un lobo...
            if(rowTo - rowFrom == 1 && colTo - colFrom == 1 && at(rowFrom, colFrom) == 0 && at(rowTo, colTo) == EMPTY) /** Si ha pulsado Abajo-Derecha */
            	validAction = true;
            if(rowTo - rowFrom == 1 && colTo - colFrom == -1 && at(rowFrom, colFrom) == 0 && at(rowTo, colTo) == EMPTY) /** Si ha pulsado Abajo-Izquierda */
                validAction = true;
            if(rowTo - rowFrom == -1 && colTo - colFrom == 1 && at(rowFrom, colFrom) == 0 && at(rowTo, colTo) == EMPTY) /** Si ha pulsado Arriba-Derecha */
            	validAction = true;
            if(rowTo - rowFrom == -1 && colTo - colFrom == -1 && at(rowFrom, colFrom) == 0 && at(rowTo, colTo) == EMPTY) /** Si ha pulsado Arriba-Izquierda */
            	validAction = true;	
        }
        return validAction;
    }

	@Override
	public List<WolfAndSheepAction> validActions(int playerNumber) {
        ArrayList<WolfAndSheepAction> valid = new ArrayList<>();
        if (finished) {
            return valid;
        }
        for(int i = 0; i < DIM; i++){
        	for(int j = 0; j < DIM; j++){
        		if(at(i, j) == playerNumber){ // Sea lobo u oveja, hay que comprobar las direcciones inferiores
        			if(at(i + 1, j + 1) == EMPTY) /** Dirección 1 (Abajo-Derecha) */
        				valid.add(new WolfAndSheepAction(playerNumber, i, j, i + 1, j + 1));
        			if(at(i + 1,  j - 1) == EMPTY) /** Dirección 2 (Abajo-Izquierda) */
        				valid.add(new WolfAndSheepAction(playerNumber, i, j, i + 1, j - 1));
        			if(playerNumber == 0){ // Si resulta que el jugador es el lobo, hay que comprobar las direcciones superiores
        				if(at(i - 1, j + 1) == EMPTY) /** Dirección 3 (Arriba-Derecha) */
        					valid.add(new WolfAndSheepAction(playerNumber, i, j, i - 1, j + 1));
        				if(at(i - 1, j - 1) == EMPTY) /** Dirección 4 (Arriba-Izquierda) */
        					valid.add(new WolfAndSheepAction(playerNumber, i, j, i - 1, j - 1));
        			}
        		}
        	}
        }
        return valid;
	}
	
	/**
	 * Comprueba si el lobo ha llegado a la fila 0 o si las ovejas no pueden mover
	 * 
	 * @return
	 */
	public boolean wolfWinner(int[][] board){
		boolean won = false;
		// 1. En primer lugar vamos a buscar dónde se encuentra el lobo
        for(int i = 0; i < DIM; i++){
        	for(int j = 0; j < DIM; j++){
        		if(board[i][j] == 0){
        			// 1.1. Después, si la fila es 0, entonces ha ganado (ha llegado arriba del todo)
        			if(i == 0)
        				won = true;
        		}
        	}
        }
        if(!won){ // Si no ha ganado porque haya llegado arriba...
        	won = true; // Hacemos que gane y comprobamos si las ovejas pueden mover
            for(int i = 0; i < DIM; i++){
            	for(int j = 0; j < DIM; j++){
            		if(board[i][j] == 1){
            			if(i+1 >= 0 && i+1 < DIM && j+1 >= 0 && j+1 < DIM && board[i+1][j+1] == -1) /** Dirección 1 (Abajo-Derecha) */
            				won = false;
            			if(i+1 >= 0 && i+1 < DIM && j-1 >= 0 && j-1 < DIM && board[i+1][j-1] == -1) /** Dirección 2 (Abajo-Izquierda) */
            				won = false;
            		}
            	}
            }	
        }
        
        return won;
	}
	/**
	 * Comprueba si el lobo tiene alguna salida
	 * @return
	 */
	public boolean sheepWinner(int[][] board){
		boolean won = true;
		// 1. En primer lugar vamos a buscar dónde se encuentra el lobo
        for(int i = 0; i < DIM; i++){
        	for(int j = 0; j < DIM; j++){
        		if(board[i][j] == 0){ // Una vez encontrado (i, j), miramos si tiene acciones válidas
        			// 1.1. Después, miramos sus cuatro diagonales (no nos sirve 'at', pues no es estático)
        			if(i+1 >= 0 && i+1 < DIM && j+1 >= 0 && j+1 < DIM && board[i+1][j+1] == EMPTY) /** Dirección 1 (Abajo-Derecha) */
        				won = false;
        			if(i+1 >= 0 && i+1 < DIM && j-1 >= 0 && j-1 < DIM && board[i+1][j-1] == EMPTY) /** Dirección 2 (Abajo-Izquierda) */
        				won = false;
        			if(i-1 >= 0 && i-1 < DIM && j+1 >= 0 && j+1 < DIM && board[i-1][j+1] == EMPTY) /** Dirección 3 (Arriba-Derecha) */
        				won = false;
        			if(i-1 >= 0 && i-1 < DIM && j-1 >= 0 && j-1 < DIM && board[i-1][j-1] == EMPTY) /** DDirección 4 (Arriba-Izquierda) */
        				won = false;
        		}
        	}
        }
		return won;
	}
	
	/**
	 * Devuelve si hay un ganador
	 * @param board
	 * @param turn
	 * @return
	 */
	public boolean isWinner(int[][] board, int turn) {
		if(turn == 0)
			return wolfWinner(board);
		else
			return sheepWinner(board);
	}
	
	/**
	 * Devuelve el valor almacenado en una celda del tablero
	 * @param row
	 * @param col
	 * @return
	 */
    public int at(int row, int col) {
    	return row >= 0 && row < DIM && col >= 0 && col < DIM ? board[row][col] : -2;
    }
    
    /**
     * Devuelve el turno
     */
    public int getTurn() {
        return turn;
    }
    
    /**
     * Devuelve si el juego ha finalizado
     */
    public boolean isFinished() {
        return finished;
    }
    
    /**
     * Devuelve si hay un ganador
     */
    public int getWinner() {
        return winner;
    }
	
    /**
     * @return a copy of the board
     */
    public int[][] getBoard() {
        int[][] copy = new int[board.length][];
        for (int i = 0; i < board.length; i++) copy[i] = board[i].clone();
        return copy;
    }
    
    /**
     * Devuelve la dimensión del tablero
     * @return
     */
	public int getDimension() {
		return DIM;
	}
	
	@Override
	public String getGameDescription() {
		return "Wolf and Sheep " + DIM + "x" + DIM;
	}
    
    /**
     * Método toString para imprimir el tablero
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(); // A diferencia de la clase 'String', 'StringBuilder' s� es mutable
        for (int i = 0; i < board.length; i++) {
            sb.append("|");
            for (int j = 0; j < board.length; j++) {
                sb.append(board[i][j] == EMPTY ? " · " : board[i][j] == 0 ? " X " : " O ");
            }
            sb.append("|");
            sb.append("\n");
        }
        return sb.toString();
    }
}
