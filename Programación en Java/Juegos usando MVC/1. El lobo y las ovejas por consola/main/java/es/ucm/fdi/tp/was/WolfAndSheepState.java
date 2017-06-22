package main.java.es.ucm.fdi.tp.was;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import main.java.es.ucm.fdi.tp.base.model.GameState;

public class WolfAndSheepState extends GameState<WolfAndSheepState, WolfAndSheepAction> {
	
	private static final long serialVersionUID = 5414323895776936939L;
	
	private final int turn;
    private final boolean finished;
    private final int[][] board;
    private final int winner;

    private final int DIM = 8; // Por el momento el tablero ser� de tama�o fijo

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
        board[7][0] = 0; 
        // Metemos a las ovejas
        board[0][1] = 1;
        board[0][3] = 1;
        board[0][5] = 1;
        board[0][7] = 1;
        this.turn = 0;
        this.winner = -1;
        this.finished = false;
	}
	
	/**
	 * Constructora con par�metros (cambio de estado al realizar una acci�n)
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
     * Nos dice si la acci�n realizada es v�lida
     * @param action
     * @return
     */
    public boolean isValid(WolfAndSheepAction action) {
        if (isFinished()) {
            return false;
        }
        return at(action.getRowTo(), action.getColTo()) == EMPTY;
    }

	@Override
	public List<WolfAndSheepAction> validActions(int playerNumber) {
        ArrayList<WolfAndSheepAction> valid = new ArrayList<>();
        if (finished) {
            return valid;
        }
        for(int i = 0; i < 8; i++){
        	for(int j = 0; j < 8; j++){
        		if(at(i, j) == playerNumber){ // Sea lobo u oveja, hay que comprobar las direcciones inferiores
        			if(at(i + 1, j + 1) == -1) /** Direcci�n 1 (Abajo-Derecha) */
        				valid.add(new WolfAndSheepAction(playerNumber, i, j, i + 1, j + 1));
        			if(at(i + 1,  j - 1) == -1) /** Direcci�n 2 (Abajo-Izquierda) */
        				valid.add(new WolfAndSheepAction(playerNumber, i, j, i + 1, j - 1));
        			if(playerNumber == 0){ // Si resulta que el jugador es el lobo, hay que comprobar las direcciones superiores
        				if(at(i - 1, j + 1) == -1) /** Direcci�n 3 (Arriba-Derecha) */
        					valid.add(new WolfAndSheepAction(playerNumber, i, j, i - 1, j + 1));
        				if(at(i - 1, j - 1) == -1) /** Direcci�n 4 (Arriba-Izquierda) */
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
	public static boolean wolfWinner(int[][] board){
		boolean won = false;
		// 1. En primer lugar vamos a buscar dónde se encuentra el lobo
        for(int i = 0; i < 8; i++){
        	for(int j = 0; j < 8; j++){
        		if(board[i][j] == 0){
        			// 1.1. Después, si la fila es 0, entonces ha ganado (ha llegado arriba del todo)
        			if(i == 0)
        				won = true;
        		}
        	}
        }
        if(!won){ // Si no ha ganado porque haya llegado arriba...
        	won = true; // Hacemos que gane y comprobamos si las ovejas pueden mover
            for(int i = 0; i < 8; i++){
            	for(int j = 0; j < 8; j++){
            		if(board[i][j] == 1){
            			if(i+1 >= 0 && i+1 < 8 && j+1 >= 0 && j+1 < 8 && board[i+1][j+1] == -1) /** Direcci�n 1 (Abajo-Derecha) */
            				won = false;
            			if(i+1 >= 0 && i+1 < 8 && j-1 >= 0 && j-1 < 8 && board[i+1][j-1] == -1) /** Direcci�n 2 (Abajo-Izquierda) */
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
	public static boolean sheepWinner(int[][] board){
		boolean won = true;
		// 1. En primer lugar vamos a buscar dónde se encuentra el lobo
        for(int i = 0; i < 8; i++){
        	for(int j = 0; j < 8; j++){
        		if(board[i][j] == 0){ // Una vez encontrado (i, j), miramos si tiene acciones válidas
        			// 1.1. Después, miramos sus cuatro diagonales (no nos sirve 'at', pues no es estático)
        			if(i+1 >= 0 && i+1 < 8 && j+1 >= 0 && j+1 < 8 && board[i+1][j+1] == -1) /** Direcci�n 1 (Abajo-Derecha) */
        				won = false;
        			if(i+1 >= 0 && i+1 < 8 && j-1 >= 0 && j-1 < 8 && board[i+1][j-1] == -1) /** Direcci�n 2 (Abajo-Izquierda) */
        				won = false;
        			if(i-1 >= 0 && i-1 < 8 && j+1 >= 0 && j+1 < 8 && board[i-1][j+1] == -1) /** Direcci�n 3 (Arriba-Derecha) */
        				won = false;
        			if(i-1 >= 0 && i-1 < 8 && j-1 >= 0 && j-1 < 8 && board[i-1][j-1] == -1) /** Direcci�n 4 (Arriba-Izquierda) */
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
	public static boolean isWinner(int[][] board, int turn) {
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
     * M�todo toString para imprimir el tablero
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(); // A diferencia de la clase 'String', 'StringBuilder' s� es mutable
        for (int i = 0; i < board.length; i++) {
            sb.append("|");
            for (int j = 0; j < board.length; j++) {
                sb.append(board[i][j] == EMPTY ? " � " : board[i][j] == 0 ? " M " : " O ");
            }
            sb.append("|");
            sb.append("\n");
        }
        return sb.toString();
    }
}
