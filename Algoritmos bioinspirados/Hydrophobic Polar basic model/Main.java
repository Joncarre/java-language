import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 
 * @author J. Carrero
 *
 */
public class Main {

	/**
	 * Método que recibe una cadena de aminoácidos como argumento
	 * @param args
	 */
    public static void main(String[] args) {
        String chain = args[0];
        System.out.println("Tu cadena de aminoácidos es: " + chain);
        for (int i = 0; i < chain.length(); i++) { // Comprobar si la cadena de aminoácidos es correcta
            char c = chain.charAt(i);
            if (c != 'H' && c != 'P')
            	throw new IllegalArgumentException("\n Ops! Parece que tu cadena de aminoácidos no es correcta. \n" +
                				   " Por favor, ejecuta el programa con una cadena válida.");
        }
        Protein.run(chain);
    }
}
