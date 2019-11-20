import java.io.PrintStream;

public class Protein implements Cloneable {
    private String chain; 
    private int[] row;    
    private int[] col;    

    /**
     * Constructora
     * @param chain
     */
    public Protein(String chain) {
        this.chain = chain;
        this.row = new int[chain.length()];
        this.col = new int[chain.length()];
        for (int i = 0; i < this.col.length; i++) {
            this.row[i] = 0;
            this.col[i] = i;
        }
    }
    
    /**
     * 
     * @param chain
     * @return
     */
    public static int run(String chain) {
         Searcher searcher = new Searcher(chain);
         Protein best = searcher.search(); // Obtenemos la mejor proteína
         int score = best.getScore(); // Y calculamos su puntuación
         System.out.println("Enlaces de tipo H: " + score);
         best.print(chain); 
         return score;
    }
    
    /**
     * Calcula la puntuación de un determinado pliegue
     * @return
     */
    public int getScore() {
        int score = 0;
        for (int i = 1; i < this.chain.length(); i++) {
            int dx = Math.abs(col[i] - col[i-1]);
            int dy = Math.abs(row[i] - row[i-1]);
            if (dx + dy != 1) { // Si las posiciones que ocupan los residuos no son adyacentes
                return Integer.MIN_VALUE; // Esa proteína no nos sirve, así que le asignamos una baja puntuación
            }
        }
        
       for (int i = 0; i < this.chain.length(); i++) {
            for (int j = i+1; j < this.chain.length(); j++) {
                if (row[i] == row[j] && col[i] == col[j]) // Si el enlace entre dos residuos está en la misma coordenada
                    return Integer.MIN_VALUE; // La proteína no nos sirve (pues deben estar en coordenadas adyacentes)
                if (isHydrophobic(i) && isHydrophobic(j)) { // Si el residuo i-ésimo y el i-ésimo+1 son de tipo H
                    if (row[i] == row[j] && (col[i] == col[j]+1 || col[j] == col[i]+1)) // Si están en la misma fila y el i+1 es adyacente al i o el i es adyacente al i+1
                        score++; 
                    else if (col[i] == col[j] && (row[i] == row[j]+1 || row[j] == row[i]+1)) // Si están en la misma columna y el i+1 es adyacente al i o el i es adyacente al i+1
                        score++;
                }
            }
        }
        return score;
    }
    
    /**
     * Muestra por consola el pliegue resultante en forma de red
     * @param out
     */
    public void print(String chain) {
    	int dy_max = 0;
    	int dx_max = 0;
    	for(int i = 0; i < this.chain.length(); i++) {
    		if(this.row[i] > dy_max)
    			dy_max = this.row[i];
    		if(this.col[i] > dx_max)
    			dx_max = this.col[i];
    	}
    
        // Almacenamos la red como una matriz
        char[][] grid = new char[dy_max][dx_max];
        for(int i = 0; i < this.chain.length(); i++) {
        	grid[row[i]-1][col[i]-1] = this.chain.charAt(i);
        }
        
        // Y la mostramos por consola
        for(int i = 0; i < dy_max; i++) {
        	for(int j = 0; j < dx_max; j++) {
        		System.out.print(grid[i][j]);
        	}
        	System.out.print("\n");
        }
    }

    /**
     * Sobreescribe el método 'clone' para crear una copia de la cadena
     */
    public Object clone() {
        try {
            Protein copy = (Protein) super.clone();
            copy.row = new int[this.row.length];
            System.arraycopy(this.row, 0, copy.row, 0, this.row.length);
            copy.col = new int[this.col.length];
            System.arraycopy(this.col, 0, copy.col, 0, this.col.length);
            return copy;
        } catch (CloneNotSupportedException e) {
            return new RuntimeException(e);
        }
    }
    
    /**
     * Retorna el tamaño de la cadena
     * @return
     */
    public int getLength() {
        return this.chain.length();
    }

    /**
     * Retorna 'true' si el aminoácido i-ésimo es de tipo H
     * @param index
     * @return
     */
    public boolean isHydrophobic(int i) {
        return this.chain.charAt(i) == 'H';
    }

    /**
     * Retorna la fila del residuo i-ésimo
     * @param index
     * @return
     */
    public int getRow(int index) {
        return this.row[index];
    }

    /**
     * Retorna la columna del residuo i-ésimo
     * @param index
     * @return
     */
    public int getColumn(int index) {
        return this.col[index];
    }

    /**
     * Establece las coordenadas para el residuo i-ésimo
     * @param index
     * @param row
     * @param col
     */
    public void setLocation(int index, int row, int col) {
        this.row[index] = row;
        this.col[index] = col;
    }
}