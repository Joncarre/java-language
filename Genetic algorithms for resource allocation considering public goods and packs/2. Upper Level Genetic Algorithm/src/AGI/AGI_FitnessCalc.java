package AGI;
import java.awt.Image;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.text.DefaultEditorKit.BeepAction;
import javax.xml.parsers.DocumentBuilder;
import files.CustomReadFile;
import files.CustomWriteFile;
import AGS.Pack;

/**
 * AGI_FitnessCalc
 * @author J. Carrero
 *
 */
public class AGI_FitnessCalc {
	private static Vector<Vector<Double>> M_preferences;
	private static Vector<Vector<Double>> O_preferences; 
	private static Vector<Pack> O_packs;
	private static Vector<Pack> M_packs;
	static int numUsers;
	private int maxValuePreference;
	private Vector<AGI_Individual> indiv_Results;
	private CustomReadFile readFile;
	private CustomWriteFile writeFile;
	Scanner sc;
	
	/**
	 * Constructor
	 * @param numUser
	 * @param maxValuePreference
	 */
	public AGI_FitnessCalc(int numUser, int maxIterations) {
		this.numUsers = numUser;
		this.maxValuePreference = 99;
		M_preferences = new Vector<Vector<Double>>(this.numUsers);
		O_preferences = new Vector<Vector<Double>>(this.numUsers);
	}
	
	/**
	 * Used to read preferences from AGS
	 * @param O_preferences
	 */
	public void setO_Preferences(Vector<Vector<Double>> O_preferences) {
		this.O_preferences = O_preferences;
	}
	
	/**
	 * Used to read preferences from AGS
	 * @param preferences
	 */
    public void setM_Preferences(Vector<Vector<Double>> M_preferences) {
    	this.M_preferences = M_preferences;
    }
    
    /**
	 * Used to read packs from AGS
     * @param packs
     */
    public void setOPacks(Vector<Pack> packs){
    	this.O_packs = packs;
    }
    
    /**
	 * Used to read packs from AGS
     * @param packs
     */
    public void setMPacks(Vector<Pack> packs){
    	this.M_packs = packs;
    }
	
    /**
     * Write preference for each user
     * @throws IOException
     */
    public void writePreferences() throws IOException {
		for(int i = 0; i < numUsers; i++) {
			String text = "";
			this.writeFile = new CustomWriteFile("user" + i + ".txt");
			for(int j = 0; j < AGI_Individual.defaultGeneLength; j++)
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
        	Vector<Double> newPreference = new Vector<Double>(AGI_Individual.defaultGeneLength);
        	for(int j = 0; j < AGI_Individual.defaultGeneLength; j++)
        		newPreference.add(j, ((Math.random() * this.maxValuePreference) + 1));
        	M_preferences.add(i, newPreference);
        }
    }

    /**
     * Calculate individuals fitness
     * @param individual
     * @return
     */
    public static double getFitness(AGI_Individual individual) {
    	// The assignments for each resource is a vector of numUsers positions
    	double[] assignments = new double[numUsers];
        // Loop through our individuals genes
        for (int i = 0; i < AGI_Individual.defaultGeneLength; i++)
        	assignments[individual.getGene(i)] += M_preferences.get(individual.getGene(i)).get(i);
        
        // We add the plus for each pack
        for(int i = 0; i < numUsers; i++){
        	for(int j = 0; j < AGI_FitnessCalc.M_packs.size(); j++){
        		if(i == AGI_FitnessCalc.M_packs.get(j).getOwner()){ // Si es el owner del pack...
        			boolean keep = true; int r = 0;
        			while(keep && r < AGI_FitnessCalc.M_packs.get(j).getResources().length){
        				if(individual.getGene(AGI_FitnessCalc.M_packs.get(j).getResource(r)) != i) // Miramos los genes que indican los recursos de los packs	
        					keep = false;
        				r++;
        			}
        			if(keep == true)
        				assignments[i] += AGI_FitnessCalc.M_packs.get(j).getPlus(); // Añadimos el plus si tenía todos los recursos del pack
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
     * Reset the values
     */
    public void resetValues(int avgIterations) {
    	this.indiv_Results = new Vector<AGI_Individual>(avgIterations);
    }
    
    /**
     * Set the promising
     * @param finalBest
     * @return
     */
    public AGI_Data setPromising(AGI_Individual finalBest){
    	AGI_Individual best = finalBest;
    	AGI_Data data = new AGI_Data();
    	// We put the fittest
        data.setFitness(finalBest.getOnlyFitness());
     // We enter the promising: We calculate the difference with the lowest utility of the rest of the agents
        data.setPromising(getMinUtility(best, finalBest.getOnlyFitness()));
        // We put the solution
        data.setSolution(finalBest.getGenes());
        return data;
    }
    
    /**
     * Returns min utility of the rest agents
     * @param best
     * @return
     */
    public double getMinUtility(AGI_Individual best, double fitnessAgent0) {
    	double utility = Double.MAX_VALUE;
    	for(int j = 1; j < numUsers; j++) {
    		double fitness = 0;
            for(int i = 0; i < O_preferences.get(j).size(); i++) {
            	if(best.getGene(i) == j)
            		fitness += O_preferences.get(best.getGene(i)).get(i);
            }
            if((fitnessAgent0 - fitness) < utility)
            	utility = fitnessAgent0 - fitness;
    	}
    	return utility;
    }
    
    /**
     * Save an individual
     * @param individual
     */
    public void saveIndividuals(AGI_Individual individual) {
    	this.indiv_Results.add(individual);
    }
    
    /**
     * Get the fittest
     * @return
     */
    public AGI_Individual getBestIndividual() {
    	return this.indiv_Results.get(0);
    }
    
    /**
     * Update the best fitness
     * @param best
     * @return
     */
	public AGI_Individual updateBestFitness() {
		// We get the best individual of the population
		AGI_Individual best = new AGI_Individual();
    	best = this.indiv_Results.get(0);
		//System.out.println("----> Fittest of AGI: " + best.getOnlyFitness() + "  |  " + best.toString());
    	// We calculate the fitness of Agent 0 with the original preferences
    	double fitness = 0.0;
        for(int i = 0; i < O_preferences.get(0).size(); i++) {
        	if(best.getGene(i) == 0)
        		fitness += O_preferences.get(best.getGene(i)).get(i);
        }
        // We add the plus for Agent 0
        for(int j = 0; j < AGI_FitnessCalc.O_packs.size(); j++){
    		if(0 == AGI_FitnessCalc.O_packs.get(j).getOwner()){ // If it is the owner of the pack...
        		boolean keep = true; int r = 0;
        		while(keep && r < AGI_FitnessCalc.O_packs.get(j).getResources().length){
        			if(best.getGene(AGI_FitnessCalc.O_packs.get(j).getResource(r)) != 0) // We look at the genes that indicate the resources of the packs
        				keep = false;
        			r++;
        		}
        		if(keep == true)
        			fitness += AGI_FitnessCalc.O_packs.get(j).getPlus(); // We added the plus if you had all the resources of the pack
    		}
        } 
        
        best.setFitness(fitness);
		//System.out.println(" |  Utility: " + fitness + " |  Genes: " + best.toString());
		return best;
	}
	
	/**
	 * Support method
	 */
	public void printM_Preferences() {
		for(int i = 0; i < this.M_preferences.size(); i++) {
			System.out.println(this.M_preferences.get(i).toString());
		}
	}
	
	/**
	 * Support method
	 */
	public void printO_Preferences() {
		for(int i = 0; i < this.O_preferences.size(); i++) {
			System.out.println(this.O_preferences.get(i).toString());
		}
	}
}
