package main;
import java.awt.Image;
import java.awt.KeyEventPostProcessor;
import java.awt.RenderingHints;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.util.Scanner;
import java.util.Vector;

import javax.security.auth.kerberos.KerberosKey;

import data.CustomReadFile;
import data.CustomWriteFile;

/**
 * Class FitnessCalc
 * @author J. Carrero
 *
 */
public class FitnessCalc {
	private static Vector<Vector<Double>> M_preferences;
	private static Vector<Vector<Double>> O_preferences;
	private static Vector<Pack> O_packs;
	private static Vector<Pack> M_packs;
	private int numPacks;
	static int numUsers;
	private int maxValuePreference;
	private Vector<Individual> indiv_Results;
	private CustomReadFile readFile;
	private CustomWriteFile writeFile;
	Scanner sc;
	
	/**
	 * Constructor
	 * @param _numUser
	 * @param _numPacks
	 */
	public FitnessCalc(int _numUser, int _numPacks) {
		this.numUsers = _numUser;
		this.numPacks = _numPacks;
		this.M_preferences = new Vector<Vector<Double>>(this.numUsers);
		this.O_preferences = new Vector<Vector<Double>>(this.numUsers);
		this.O_packs = new Vector<Pack>(this.numPacks);
		this.M_packs = new Vector<Pack>(this.numPacks);
	}
	
	/**
	 * Reads M preferences
	 * @param index
	 * @param newPreference
	 * @throws IOException 
	 */
    public void readMPreferences() throws IOException {
        // Set a user preferences for each resource
        for (int i = 0; i < numUsers; i++) {
    		this.readFile = new CustomReadFile("user_R_" + i + ".txt");
        	this.sc = new Scanner((Readable) this.readFile);
        	Vector<Double> newPreference = this.readFile.readVector(sc);
        	M_preferences.add(i, newPreference);
        }
    }
    
    /**
     * Reads O preferences
     * @throws IOException
     */
    public void readOPreferences() throws IOException {
    	this.readFile = new CustomReadFile("user_Real_0.txt");
        this.sc = new Scanner((Readable) this.readFile);
        Vector<Double> newPreference = this.readFile.readVector(sc);
        O_preferences.add(0, newPreference);
    }
    
    /**
     * Reads all O_packs
     * @throws IOException 
     */
    public void readOPacks() throws IOException{
    	for(int i = 0; i < this.numPacks; i++){
    		this.readFile = new CustomReadFile("O_pack" + i + ".txt");
        	this.sc = new Scanner((Readable) this.readFile);
    		double plus = this.readFile.readDouble(sc);
    		double owner = this.readFile.readDouble(sc);
    		double sizeArray = this.readFile.readDouble(sc);
    		int[] resources = this.readFile.readArray(sc, (int)sizeArray);
    		Pack newPack = new Pack(resources, plus, (int)owner);
    		this.O_packs.add(newPack);
    	}
    }
    
    /**
     * Reads all M_packs
     * @throws IOException 
     */
    public void readMPacks() throws IOException{
    	for(int i = 0; i < this.numPacks; i++){
    		this.readFile = new CustomReadFile("M_pack" + i + ".txt");
        	this.sc = new Scanner((Readable) this.readFile);
    		double plus = this.readFile.readDouble(sc);
    		double owner = this.readFile.readDouble(sc);
    		double sizeArray = this.readFile.readDouble(sc);
    		int[] resources = this.readFile.readArray(sc, (int)sizeArray);
    		Pack newPack = new Pack(resources, plus, (int)owner);
    		this.M_packs.add(newPack);
    	}
    }
    
    /**
     * Writes preferences for each user
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
     * Generates random preferences for each user
     */
    public void randomPreferences() {
        for (int i = 0; i < numUsers; i++) {
        	Vector<Double> newPreference = new Vector<Double>(Individual.defaultGeneLength);
        	for(int j = 0; j < Individual.defaultGeneLength; j++)
        		newPreference.add(j, ((Math.random() * this.maxValuePreference) + 1));
        	M_preferences.add(i, newPreference);
        }
    }

    /**
     * Calculate individuals fitness
     * @param individual
     * @return
     */
    public static double getFitness(Individual individual) {
    	// The assignments for each resource is a vector of numUsers positions
    	double[] assignments = new double[numUsers];
        // Loop through our individuals genes
        for (int i = 0; i < Individual.defaultGeneLength; i++)
        	assignments[individual.getGene(i)] += M_preferences.get(individual.getGene(i)).get(i);
        
        // We add the plus for each pack
        for(int i = 0; i < numUsers; i++){
        	for(int j = 0; j < FitnessCalc.M_packs.size(); j++){
        		if(i == FitnessCalc.M_packs.get(j).getOwner()){ // Si es el owner del pack...
        			boolean keep = true; int r = 0;
        			while(keep && r < FitnessCalc.M_packs.get(j).getResources().length){
        				if(individual.getGene(FitnessCalc.M_packs.get(j).getResource(r)) != i) // Miramos los genes que indican los recursos de los packs	
        					keep = false;
        				r++;
        			}
        			if(keep == true)
        				assignments[i] += FitnessCalc.M_packs.get(j).getPlus(); // Añadimos el plus si tenía todos los recursos del pack
        		}
        	}
        }	
        return getMinValue(assignments); 
    }
    
    /**
     * Return of minimum value
     * @param vector
     * @return
     */
    public static double getMinValue(double[] vector) {
    	double min = vector[0];
    	for(int i = 0; i < vector.length; i++) {
    		if(vector[i] < min)
    			min = vector[i];
    	}
    	return min;
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
    public void resetValues() {
    	this.indiv_Results = new Vector<Individual>();
		this.M_preferences = new Vector<Vector<Double>>(this.numUsers);
		this.O_preferences = new Vector<Vector<Double>>(this.numUsers);
		this.O_packs = new Vector<Pack>(this.numPacks);
		this.M_packs = new Vector<Pack>(this.numPacks);
    }
    
    /** 
     * Write to file all results
     * @param numIterations
     * @param preference
     * @throws 
     */
    public Individual updateBestFitness() throws IOException {
    	// We get the best individual of the population
    	Individual best = new Individual();
    	best = this.indiv_Results.get(0);
		//System.out.println("----> Fittest of AGI: " + best.getOnlyFitness() + "  |  " + best.toString());
    	// We calculate the fitness of Agent 0 considering the original preferences
    	double fitness = 0.0;
        for(int i = 0; i < O_preferences.get(0).size(); i++) {
        	if(best.getGene(i) == 0)
        		fitness += O_preferences.get(best.getGene(i)).get(i);
        }
        
        // We add the plus for Agent 0
        for(int j = 0; j < FitnessCalc.O_packs.size(); j++){
    		if(0 == FitnessCalc.O_packs.get(j).getOwner()){ // If he's the owner of the pack...
        		boolean keep = true; int r = 0;
        		while(keep && r < FitnessCalc.O_packs.get(j).getResources().length){
        			if(best.getGene(FitnessCalc.O_packs.get(j).getResource(r)) != 0) // We look at the genes indicating by the resources of the packs
        				keep = false;
        			r++;
        		}
        		if(keep == true)
        			fitness += FitnessCalc.O_packs.get(j).getPlus(); // If he got all the resouces of the pack, we add the plus utility
    		}
        }     

		//System.out.println(/*"Execution number: " + preferenceIteration + " |  Utility: " + */textAverage.replace(".", ",") + " |  Genes: " + best.toString());
        best.setFitness(fitness);
        return best;
    }
    
    /**
     * Caculate the fitness increasing the utility of the preferences
     * @param row
     * @param col
     * @return
     */
    public boolean setValueAlza(int row, int col) {
    	double oldValue = M_preferences.get(row).get(col);
    	double newValue = oldValue+((100-oldValue)*0.05); // 5% of the value left to grow (up to 100)
    	if(newValue < 100) {
    		M_preferences.get(row).set(col, newValue);
    		return true;
    	}else
    		return false;
    }
    
    /**
     * Caculate the fitness decreasing the utility of the preferences
     * @param row
     * @param col
     * @return
     */
    public boolean setValueBaja(int row, int col) {
    	double oldValue = M_preferences.get(row).get(col);
    	double newValue = oldValue-(oldValue*0.05); // 5% of the remaining value to be decremented (to 0)
    	if(newValue > 0) {
    		M_preferences.get(row).set(col, newValue);
    		return true;
    	}else
    		return false;
    }
    
    /**
     * Caculate the fitness increasing the utility of the pack
     * @param _numPack
     */
    public void setPlusAlza(int _numPack) {
    	double oldValue = this.M_packs.get(_numPack).getPlus();
    	double newValue = oldValue+((100-Math.abs(oldValue))*0.1); // 10% of its remaining value to grow (up to 100)
    	if(newValue < 100)
    		this.M_packs.get(_numPack).setPlus(newValue);
    }
    
    /**
     * Caculate the fitness decreasing the utility of the pack
     * @param _numPack
     */
    public void setPlusBaja(int _numPack) {
    	double oldValue = this.M_packs.get(_numPack).getPlus();
    	double limit = countValue(_numPack);
    	limit = limit*-1; // We conver it in a negative value
    	double newValue = oldValue-(Math.abs(Math.abs(limit-oldValue)*0.1)); // 10% of the remaining value to be decremented (up to the 'limit')
    	if(newValue > limit)
    		this.M_packs.get(_numPack).setPlus(newValue);
    }
    
    /**
     * 
     * @param _numPack
     * @return
     */
    public double countValue(int _numPack) {
    	double value = 0.0;
    	int[] resources = this.M_packs.get(_numPack).getResources();
    	for(int i = 0; i < resources.length; i++)
    		value += this.O_preferences.get(0).get(resources[i]);
    	return value;
    }
	
    /**
     * Save an individual
     * @param individual
     */
    public void saveIndividuals(Individual individual) {
    	this.indiv_Results.add(individual);
    }
    
    /**
     * Get the fittest
     * @return
     */
    public Individual getBestIndividual() {
    	return this.indiv_Results.get(0);
    }
    
    /**
     * Print the M and O preferences
     */
    public void printPreferences(){
    	System.out.println("------------------ M_Preferences ------------------");
    	for(int i = 0; i < M_preferences.size(); i++) {
        	System.out.println(M_preferences.get(i).toString());
    	}
    	System.out.println("------------------ O_Preferences ------------------");
        System.out.println(O_preferences.get(0).toString());

    }
    
    /**
     * Apply Normal Distribution to preferences
     * @param deviation
     */
    public void applyNormal(double deviation) {
    	Normal normal = new Normal();
    	// For each user (except the first)
    	for(int i = 1; i < this.numUsers; i++) {
    		// For each preference
    		for(int j = 0; j < Individual.defaultGeneLength; j++) {
    			Double mean = M_preferences.get(i).get(j);
    			Double newValue = normal.generateValue(deviation, mean);
    			while(newValue <= 0 || newValue >= 100)
        			newValue = normal.generateValue(deviation, mean);
        		M_preferences.get(i).set(j, newValue);
    		}
    		formalizePreferences(M_preferences.get(i)); // We formalize after apply the normal distribution
    	}
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
     * Method to formalize the preferences
     */
    public void formalizeMutationPreferences() {
    	M_preferences.set(0, formalizePreferences(M_preferences.get(0)));
    }
}
