package main;

import java.awt.Image;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.TransferHandler;
import javax.xml.parsers.DocumentBuilder;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.omg.PortableServer.THREAD_POLICY_ID;
import data.CustomReadFile;
import data.CustomWriteFile;

public class FitnessCalc {
	
	private static Vector<Vector<Double>> M_preferences;
	private static Vector<Vector<Double>> O_preferences; 
	private static Vector<Double> Temporal_preferences; 
	private static Vector<Integer> changedResources;
	static int numUsers;
	private int maxValuePreference;
	private Vector<Individual> indivs_Results;
	private double[][] M_results;
	private double[][] O_results;
	private double[][] F_results;
	private double max_O_result;
	private Vector<Individual> M_population;
	private CustomReadFile readFile;
	private CustomWriteFile writeFile;
	Scanner sc;
	
	/**
	 * Constructor
	 * @param numUser
	 * @param maxValuePreference
	 */
	public FitnessCalc(int numUser, int maxIterations) {
		this.numUsers = numUser;
		this.maxValuePreference = 99;
		this.O_results = new double[maxIterations][numUser];
		this.max_O_result = 24.47;
		M_preferences = new Vector<Vector<Double>>(this.numUsers);
		O_preferences = new Vector<Vector<Double>>(this.numUsers);
		changedResources = new Vector<Integer>(Individual.defaultGeneLength);
	}
	
	/**
	 * Read preference for each user
	 * @param index
	 * @param newPreference
	 * @throws IOException 
	 */
    public void readPreferences() throws IOException {
        // Set a user preferences for each resource
        for (int i = 0; i < numUsers; i++) {
    		this.readFile = new CustomReadFile("user_R_" + i + ".txt");
        	this.sc = new Scanner(this.readFile);
        	Vector<Double> newPreference = this.readFile.readVector(sc);
        	M_preferences.add(i, newPreference);
        }
    }
    
    /* Unificar este método con el de arriba */
    public void readPreferences2() throws IOException {
        // Set a user preferences for each resource
        for (int i = 0; i < numUsers; i++) {
    		this.readFile = new CustomReadFile("user_R_" + i + ".txt");
        	this.sc = new Scanner(this.readFile);
        	Vector<Double> newPreference = this.readFile.readVector(sc);
        	O_preferences.add(i, newPreference);
        }
    }
    
    /**
     * Write preference for each user
     * @throws IOException
     */
    public void writePreferences() throws IOException {
		for(int i = 0; i < numUsers; i++) {
			String text = "";
			this.writeFile = new CustomWriteFile("user_R_" + i + ".txt");
			for(int j = 0; j < Individual.defaultGeneLength; j++)
				text += M_preferences.get(i).get(j) + " ";
			text += -1.0;
			this.writeFile.writeVector(this.writeFile, text);
			this.writeFile.closeWriteFile(this.writeFile);
		}
    }
    
    /**
     * Generate random preference for each user
     */
    public void randomPreferences() {
        for (int i = 0; i < numUsers; i++) {
        	Vector<Double> newPreference = new Vector<Double>(Individual.defaultGeneLength);
        	for(int j = 0; j < Individual.defaultGeneLength; j++)
        		newPreference.add(j, ((Math.random() * this.maxValuePreference) + 1));
         	M_preferences.add(i, formalizePreferences(newPreference));
        }
    }

    /**
     * Calculate individuals fitness
     * @param individual
     * @return
     */
    public static int getFitness(Individual individual) {
    	// The assignments for each resource is a vector of numUsers positions
    	int[] assignments = new int[numUsers];
        // Loop through our individuals genes
        for (int i = 0; i < Individual.defaultGeneLength; i++)
        	assignments[individual.getGene(i)] += M_preferences.get(individual.getGene(i)).get(i);
        	
        return getMinValue(assignments); 
    }
    
    /**
     * Return of minimum value
     * @param vector
     * @return
     */
    public static int getMinValue(int[] vector) {
    	int min = vector[0];
    	int max = vector[0];
    	for(int i = 0; i < vector.length; i++) {
    		if(vector[i] < min)
    			min = vector[i];
    		if(vector[i] > max)
    			max = vector[i];
    	}
    	return max-min;
    }
    
    /**
     * Get number of users
     * @return
     */
    public int getNumUsers() {
    	return numUsers;
    }
    
    /**
     * resetValues
     */
    public void resetValues(int rows, int cols) {
    	this.M_results = new double[rows][cols];
    	this.F_results = new double[rows][cols];
    	this.indivs_Results = new Vector<Individual>(rows);
    }
    
    /**
     * Reset matrix of populations
     * @param cols
     */
    public void resetPopulations(int cols) {
    	this.M_population = new Vector<Individual>(cols);
    }
    
    /**
     * Calculates the result with the mutated preferences
     * @param individual
     * @param iteration
     */
    public void calculateMutationPopulation(Individual individual, int preference, int iteration){
    	if(preference == 0) { // For the first preference, we calculate original results
    		for(int i = 0; i < O_preferences.get(0).size(); i++)
        		this.O_results[iteration][individual.getGene(i)] += O_preferences.get(individual.getGene(i)).get(i);
    	}
    	M_population.add(individual);
    }
    
    /**
     * Calculates the result with the original references
     * @param iteration
     */
    public void calculateMutationDistribution(int iteration){
    	for(int i = 0; i < O_preferences.get(0).size(); i++)
        	this.M_results[iteration][this.M_population.get(iteration).getGene(i)] += O_preferences.get(this.M_population.get(iteration).getGene(i)).get(i);	
    }
    
    /**
     * Write to file all results
     * @param numIterations
     * @param preference
     * @throws 
     */
    public void writeDistribution(Individual individual, int averageIterations, int preferenceIteration) throws IOException {
    	String text = ""; String textAverage = "";
		this.writeFile = new CustomWriteFile("e1_prueba_" + preferenceIteration + ".txt");
	    // First row is the original values
    	
    	Individual best = new Individual();
    	best = getBestIndividual();
    	double fitness = best.getOnlyFitness();
        fitness = this.max_O_result - fitness;
        
        textAverage += fitness;
		this.writeFile.writeVector(this.writeFile, text.replace(".", ","));
		this.writeFile.closeWriteFile(this.writeFile);
		// Print average
		System.out.println(textAverage.replace(".", ","));
    }
    
    /**
     * This method formalizes the preferences by making them all add a number n (in our case it will be 100)
     */
    public Vector<Double> formalizePreferences(Vector<Double> preference) {
    	double aux = 0;
    	for(int i = 0; i < preference.size(); i++)
    		aux += preference.get(i);
    	aux = 100/aux;
    	for(int j = 0; j < preference.size(); j++)
    		preference.set(j, preference.get(j)*aux);
    	return preference;
    }
    
    /**
     * 
     */
    public void formalizeMutationPreferences() {
    	M_preferences.set(0, formalizePreferences(M_preferences.get(0)));
    }
    
    /**
     * 
     * @param resources
     * @return
     */
    public boolean distributionVariationAlza() {

    	double totalVariation = 0.0;
    	// Acumulamos la distribución 
    	for(int k = 0; k < M_preferences.get(0).size(); k++) {
    		if(!changedResources.contains(k)) // Sólo se acumula la distribución de los n-m recursos
    			totalVariation += M_preferences.get(0).get(k) - Temporal_preferences.get(k);
    	}
    	
    	// Calculamos cuánto vale la suma de los recursos a disminuir
    	double sumaTotal = 0.0;
    	for(int r = 0; r < changedResources.size(); r++) {
    		sumaTotal += M_preferences.get(0).get(changedResources.get(r));
    	}

    	// Calculamos cuántas veces tenemos que aplicar la distribución para que la constante se mantenga
	    for(int i = 0; i < (changedResources.size()); i++) {
	    	double oldValue = M_preferences.get(0).get(changedResources.get(i));
	    	double newValue = oldValue-(oldValue/sumaTotal)*totalVariation;
    		M_preferences.get(0).set(changedResources.get(i), newValue);
	    }
	    
	    if(M_preferences.get(0).get(changedResources.get(0)) <= 0.0)
	    	return false;
	    else
			return true;
    }
    
    /**
     * 
     * @param resources
     * @return
     */
    public boolean distributionVariationBaja() {
    	double totalVariation = 0.0;
    	// Acumulamos la distribución 
    	for(int k = 0; k < M_preferences.get(0).size(); k++) {
    		if(!changedResources.contains(k)) // Sólo se acumula la distribución de los n-m recursos
    			totalVariation += Temporal_preferences.get(k) - M_preferences.get(0).get(k);
    	}
    	
    	// Calculamos cuánto vale la suma de los recursos a incrementar
    	double sumaTotal = 0.0;
    	for(int r = 0; r < changedResources.size(); r++) {
    		sumaTotal += M_preferences.get(0).get(changedResources.get(r));
    	}

    	// Calculamos cuántas veces tenemos que aplicar la distribución para que la constante se mantenga
	    for(int i = 0; i < (changedResources.size()); i++) {
	    	double oldValue = M_preferences.get(0).get(changedResources.get(i));
	    	double newValue = oldValue+(oldValue/sumaTotal)*totalVariation;
    		M_preferences.get(0).set(changedResources.get(i), newValue);
	    }
	    
	    if(M_preferences.get(0).get(changedResources.get(0)) >= 100.0)
	    	return false;
	    else
			return true;
    }
    
    /**
     * 
     * @param row
     * @param col
     * @return
     */
    public boolean setValueAlza(int row, int col) {
    	double oldValue = M_preferences.get(row).get(col);
    	double newValue = oldValue+((100-oldValue)*0.01); // 1% del valor que le queda por crecer
    	
    	if(newValue < 100) {
    		M_preferences.get(row).set(col, newValue);
    		return true;
    	}else
    		return false;
    }
    
    /**
     * 
     * @param row
     * @param col
     * @return
     */
    public boolean setValueBaja(int row, int col) {
    	double oldValue = M_preferences.get(row).get(col);
    	double newValue = oldValue-(oldValue*0.03); // 3% del valor que le queda por decrecer
    	
    	if(newValue > 0) {
    		M_preferences.get(row).set(col, newValue);
    		return true;
    	}else
    		return false;
    }
    
    /**
     * 
     */
    public void initialResources() {
    	for(int i = 0; i < Individual.defaultGeneLength; i++) {
        	changedResources.add(i);
    	}
    }
    
    /**
     * 
     * @param resource
     */
    public void saveResources(Integer resource) {
    	changedResources.remove(resource);
    }
    
    /**
     * 
     */
    public void saveTemporalPreferences() {
    	Temporal_preferences = new Vector<Double>(Individual.defaultGeneLength);
    	for(int i = 0; i < Individual.defaultGeneLength; i++) {
    		Temporal_preferences.add(i, M_preferences.get(0).get(i));
    	}
    }
    
    /**
     * 
     * @throws IOException
     */
    public void readPreferenceAgent() throws IOException {
		this.readFile = new CustomReadFile("user_R_0.txt");
    	this.sc = new Scanner(this.readFile);
    	Vector<Double> newPreference = this.readFile.readVector(sc);
    	M_preferences.set(0, newPreference);
    }
    
    public void showPreferences() {
    	System.out.print(M_preferences.get(0).toString());
    }
    
    
	public void setBestOriginal(int averageIterations) {
    	Individual best = new Individual();
    	best = getBestIndividual();
    	this.max_O_result = best.getOnlyFitness();
		System.out.println("Best with real prefe: " +  best.getOnlyFitness());
	}
	
    public void saveIndividuals(Individual individual) {
    	this.indivs_Results.add(individual);
    }
    
    public Individual getBestIndividual() {
    	Individual best = new Individual();
    	best = this.indivs_Results.get(0);
    	for(int i = 1; i < this.indivs_Results.size(); i++) {
    		if(this.indivs_Results.get(i).getOnlyFitness() < best.getOnlyFitness()) {
    			best = this.indivs_Results.get(i);
    		}
    	}
		return best;
    }
}
