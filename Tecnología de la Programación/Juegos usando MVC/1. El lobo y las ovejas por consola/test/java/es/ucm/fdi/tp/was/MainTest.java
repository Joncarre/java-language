package test.java.es.ucm.fdi.tp.was;

import static org.junit.Assert.assertEquals;
import main.java.es.ucm.fdi.tp.launcher.Main;

import org.junit.Test;

public class MainTest {
	
    @Test
    /**
     * Comprueba que el número de argumentos es correcto
     */
	public void tooArguments(){
    	Main main = new Main();
    	String[] args1 = {"param1", "param2", "param3", "param4"};
    	String[] args2 = {"param1", "param2"};
        assertEquals("El número de parámetros debe ser 3", false, main.argsNumber(args1));
        assertEquals("El número de parámetros debe ser 3", false, main.argsNumber(args2));
	}
	
    @Test
    /**
     * Comprueba que el juego es correcto
     */
    public void correctGame(){
    	Main main = new Main();
        assertEquals("El juego debe ser -ttt- o -was-", null, main.createInitialState("chess")); 
    }
}
