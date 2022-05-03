package test.java.es.ucm.fdi.tp.was;

import static org.junit.Assert.*;
import main.java.es.ucm.fdi.tp.was.WolfAndSheepState;

import org.junit.Test;

public class WolfAndSheepStateTest {
	
    @Test
    /**
     * Comprueba que cuando el lobo llega a la fila 0 gana
     */
    public void WolfWinner(){
    	WolfAndSheepState state = new WolfAndSheepState();
        int[][] board;
        for(int j = 1; j < state.getBoard().length; j = j + 2){
            board = state.getBoard();
            board[0][j] = 0;
            WolfAndSheepState newstate = new WolfAndSheepState(state, board, false, -1);
            assertEquals("El ganador debe ser el lobo", true, newstate.wolfWinner(board)); 
        }
    }
    
    @Test
    /**
     * Comprueba que cuando el lobo queda atrapado, las ovejas ganan
     */
    public void SheepWinner(){
    	WolfAndSheepState state = new WolfAndSheepState();
        int[][] board;
        board = state.getBoard();
    	// Debido a la constructora de 'WolfAndSheepState', hay que poner a -1 las posiciones de lobo y ovejas
        // Metemos al lobo
        board[WolfAndSheepState.DIM - 1][0] = -1;
        // Metemos a las ovejas
        for(int i = 1; i < WolfAndSheepState.DIM; i = i + 2)
        	board[0][i] = -1;
        
    	int i = 1;
    	int j = 0;
    	while(i < state.getBoard().length){
    		if(i % 2 == 0){ // Filas 0, 2, 4 y 6
            	while(j < state.getBoard().length){
            		board[i][j] = 0; // Metemos al lobo
            		board = meterOvejas(board, i, j); // Y le rodeamos de ovejas
                    WolfAndSheepState newstate = new WolfAndSheepState(state, board, false, -1);
                    assertEquals("El ganador deben ser las ovejas", true, newstate.sheepWinner(board)); 
            		board[i][j] = -1; // Quitamos al lobo
            		j = j + 2;
            	}
            	j = 0;
    		}else{ // Filas 1, 3, 5, 7
            	while(j < state.getBoard().length){
            		board[i][j] = 0; // Metemos al lobo
            		board = meterOvejas(board, i, j); // Y le rodeamos de ovejas
                    WolfAndSheepState newstate = new WolfAndSheepState(state, board, false, -1);
                    assertEquals("El ganador deben ser las ovejas", true, newstate.sheepWinner(board)); 
            		board[i][j] = -1; // Quitamos al lobo
            		j = j + 2;
            	}
            	j = 1;
    		}
    		i++;
    	}
    }
    
    /**
     * Método auxiliar para meter las ovejas en el método 'SheepWinner'
     * @param board
     * @return
     */
    public int[][] meterOvejas(int[][] board, int i, int j){ // row = i, col = j
    	// (Abajo-Derecha)
    	if((i + 1 >= 0 && i + 1 < board.length && j + 1 >= 0 && j + 1 < board.length) &&
    		board[i + 1][j + 1] == -1){
    		board[i + 1][j + 1] = 1;
    	}
    	// (Abajo-Izquierda)
    	if((i + 1 >= 0 && i + 1 < board.length && j - 1 >= 0 && j - 1 < board.length) &&
        		board[i + 1][j - 1] == -1){
        		board[i + 1][j - 1] = 1;
        	}
    	// (Arriba-Derecha)
    	if((i - 1 >= 0 && i - 1 < board.length && j + 1 >= 0 && j + 1 < board.length) &&
        		board[i - 1][j + 1] == -1){
        		board[i - 1][j + 1] = 1;
        	}
    	// (Arriba-Izquierda)
    	if((i - 1 >= 0 && i - 1 < board.length && j - 1 >= 0 && j - 1 < board.length) &&
        		board[i - 1][j - 1] == -1){
        		board[i - 1][j - 1] = 1;
        	}
    	
		return board;
    }
    
    @Test
    /**
     * Comprueba los movimientos del lobo en su posición inicial y tras su primer movimiento
     */
    public void initialWolf(){
    	WolfAndSheepState state = new WolfAndSheepState();
        int[][] board = state.getBoard();
        // Sólo tiene 1 movimiento al comenzar
    	assertEquals("El lobo sólo tiene una acción", 1, state.validActions(0).size());
    	board[state.getBoard().length - 1][0] = -1;
    	board[state.getBoard().length - 2][1] = 0;
    	state = new WolfAndSheepState(state, board, false, -1);
    	// Tiene 4 movimientos al realizar su primer movimiento
    	assertEquals("El lobo tiene 4 acciones al realizar su primer movimiento", 4, state.validActions(0).size()); 
    } 
    
    @Test
    /**
	 * Comprueba los movimientos de las ovejas en su posición inicial
     */
    public void initialSheep(){
    	WolfAndSheepState state = new WolfAndSheepState();
    	int[][] board;
    	board = state.getBoard();
    	// Debido a la constructora de 'WolfAndSheepState', hay que poner a -1 las posiciones de las ovejas antes de empezar
        for(int i = 1; i < WolfAndSheepState.DIM; i = i + 2)
        	board[0][i] = -1;
        
        for(int j = 1; j < state.getBoard().length; j = j + 2){
        	board[0][j] = 1; // Metemos la oveja
    		if (j == state.getBoard().length - 1){ // Si está en un lateral...
                WolfAndSheepState newstate = new WolfAndSheepState(state, board, false, -1);
                assertEquals("Cada oveja sólo tiene una acción", 1, newstate.validActions(1).size());
    		}else{ // Si no, entonces está en el centro del tablero
                WolfAndSheepState newstate = new WolfAndSheepState(state, board, false, -1);
                assertEquals("Cada oveja sólo tiene dos acciones", 2, newstate.validActions(1).size());
    		}	
        	board[0][j] = -1; // Sacamos la oveja
        }
    }
    
    @Test
    /**
	 * Comprueba los movimientos de las ovejas cuando están en un lateral
     */
    public void sheepEdge(){
    	WolfAndSheepState state = new WolfAndSheepState();
    	int[][] board;
    	board = state.getBoard();
    	// Debido a la constructora de 'WolfAndSheepState', hay que poner a -1 las posiciones de las ovejas antes de empezar
        for(int i = 1; i < WolfAndSheepState.DIM; i = i + 2)
        	board[0][i] = -1;
        
        for(int i = 1; i < state.getBoard().length - 1; i++){
        	if(i % 2 == 0){ // Si es el lateral derecho...
            	board[i][state.getBoard().length - 1] = 1; // Metemos la oveja
                WolfAndSheepState newstate = new WolfAndSheepState(state, board, false, -1);
                assertEquals("Cada oveja sólo tiene una acción", 1, newstate.validActions(1).size());
            	board[i][state.getBoard().length - 1] = -1; // Sacamos la oveja
        	}else{ // Si es el lateral izquierdo...
            	board[i][0] = 1; // Metemos la oveja
                WolfAndSheepState newstate = new WolfAndSheepState(state, board, false, -1);
                assertEquals("Cada oveja sólo tiene una acción", 1, newstate.validActions(1).size());
            	board[i][0] = -1; // Sacamos la oveja
        	}
        }
    }
}
