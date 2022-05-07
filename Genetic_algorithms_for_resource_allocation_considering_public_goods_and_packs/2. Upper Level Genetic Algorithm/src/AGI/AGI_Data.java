package AGI;

/**
 * AGI_Data
 * @author J. Carrero
 *
 */
public class AGI_Data {
	private double fitness;
	private double promising;
    private int[] solution;
	
    /**
     * Constructor
     */
	public AGI_Data() {
		this.fitness = 0;
		this.promising = Double.MAX_VALUE;
		this.solution = new int[AGI_Individual.defaultGeneLength];
	}
	
	/**
	 * Set the fitness
	 * @param fitness
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	/**
	 * Set the promising individual
	 * @param promising
	 */
	public void setPromising(double promising) {
		this.promising = promising;
	}
	
	/**
	 * Set solution
	 * @param _solution
	 */
	public void setSolution(int[] _solution) {
		this.solution = _solution;
	}
	
	/**
	 * Get the fitness
	 * @return
	 */
	public double getFitness() {
		return this.fitness;
	}
	
	/**
	 * Get the promising
	 * @return
	 */
	public double getPromising() {
		return this.promising;	
	}
	
	/**
	 * Get the solution
	 * @return
	 */
	public int[] getSolution() {
		return this.solution;
	}
}
