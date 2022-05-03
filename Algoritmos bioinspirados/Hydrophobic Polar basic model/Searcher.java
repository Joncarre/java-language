public class Searcher {
    private final Protein actual;
    private Protein best;
    private int bestScore;
    
    /**
     * Constructora
     * @param chain
     */
    public Searcher(String chain) {
    	actual = new Protein(chain);
        best = (Protein) actual.clone();
        bestScore = Integer.MIN_VALUE;
    }
    
    /**
     * Retorna la mejor proteína (el mejor plegado) posible
     * @return
     */
    public Protein search() {
        search(0, actual.getLength()-1, actual.getLength()-1);
        return best;
    }
    
    /**
     * 
     * @param index
     * @param row
     * @param col
     */
    private void search(int index, int row, int col) {
    	actual.setLocation(index, row, col);
        if(index >= actual.getLength()-1) { // Si hemos colocado el último residuo 
            int score = actual.getScore(); // Calculamos la puntuación de la proteína
            if(score > bestScore) { // Y si es mejor que la que mejor que teníamos
                bestScore = score;
                best = (Protein) actual.clone(); // La clonamos
            }
        } else {
        	// Realizamos una búsqueda recursiva en las cuatro direcciones de la red
            search(index+1, row+1, col);
            search(index+1, row, col+1);
            search(index+1, row-1, col);
            search(index+1, row, col-1);
        }
    }
}