package AGS;
import java.awt.Image;
import java.awt.datatransfer.SystemFlavorMap;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import javax.print.attribute.standard.MediaSize.Engineering;
import AGI.AGI_Data;
import AGI.AGI_Engine;
import AGI.AGI_Individual;
import files.CustomReadFile;
import files.CustomWriteFile;

/**
 * Class AGS_FitnessCal
 * @author J. Carrero
 *
 */
public class AGS_FitnessCalc {
	private static Vector<Vector<Double>> O_preferences;
	private static Vector<Vector<Double>> M_preferences;
	private static Vector<Pack> O_packs;
	private static Vector<Pack> M_packs;
	static int numUsers;
	private int numPacks;
	private int maxValuePreference;
	private CustomReadFile readFile;
	private CustomWriteFile writeFile;
	Scanner sc;
    private final double mutationRate = 0.000001; // 1/100
    private final double addPackRate = 0.000000001; // 1/5000
	
	/**
	 * Constructor
	 * @param numUser
	 * @param maxValuePreference
	 */
	public AGS_FitnessCalc(int _numUser, int _numPacks) {
		this.numUsers = _numUser;
		this.numPacks = _numPacks;
		this.O_preferences = new Vector<Vector<Double>>(this.numUsers);
		this.M_preferences = new Vector<Vector<Double>>(this.numUsers);
		this.maxValuePreference = 99;
		this.O_packs = new Vector<Pack>(this.numPacks);
		this.M_packs = new Vector<Pack>(this.numPacks);
	}
	
	/**
	 * Read O preferences for each user
	 * @throws IOException
	 */
    public void readPreferences_O() throws IOException {
        for (int i = 0; i < numUsers; i++) {
    		this.readFile = new CustomReadFile("user_R_" + i + ".txt");
        	this.sc = new Scanner(this.readFile);
        	Vector<Double> newPreference = this.readFile.readVector(sc);
        	O_preferences.add(i, newPreference);
        }
    }
    
    /**
     * Read M preferences for each user
     * @throws IOException
     */
    public void readPreferences_M() throws IOException {
        for (int i = 0; i < numUsers; i++) {
    		this.readFile = new CustomReadFile("user_R_" + i + ".txt");
        	this.sc = new Scanner(this.readFile);
        	Vector<Double> newPreference = this.readFile.readVector(sc);
        	M_preferences.add(i, newPreference);
        }
    }
    
    /**
     * Reads all packs
     * @throws IOException 
     */
    public void readOPacks() throws IOException{
    	for(int i = 0; i < this.numPacks; i++){
    		this.readFile = new CustomReadFile("pack" + i + ".txt");
        	this.sc = new Scanner(this.readFile);
    		double plus = this.readFile.readDouble(sc);
    		double owner = this.readFile.readDouble(sc);
    		double sizeArray = this.readFile.readDouble(sc);
    		int[] resources = this.readFile.readArray(sc, (int)sizeArray);
    		Pack newPack = new Pack(resources, plus, (int)owner);
    		this.O_packs.add(newPack);
    	}
    }
    
    /**
     * Read M packs
     * @throws IOException
     */
    public void readMPacks() throws IOException{
    	for(int i = 0; i < this.numPacks; i++){
    		this.readFile = new CustomReadFile("pack" + i + ".txt");
        	this.sc = new Scanner(this.readFile);
    		double plus = this.readFile.readDouble(sc);
    		double owner = this.readFile.readDouble(sc);
    		double sizeArray = this.readFile.readDouble(sc);
    		int[] resources = this.readFile.readArray(sc, (int)sizeArray);
    		Pack newPack = new Pack(resources, plus, (int)owner);
    		this.M_packs.add(newPack);
    	}
    }
    
    /**
     * Write population on files
     * @param indiv
     * @throws IOException
     */
    public void writePopulation(AGS_Individual indiv) throws IOException {
		for(int i = 0; i < numUsers; i++) {
			String text = "";
			this.writeFile = new CustomWriteFile("final_indiv_" + i + ".txt");
			for(int j = 0; j < AGS_Individual.defaultGeneLength; j++)
				text += indiv.getGene(j) + " ";
			text += -1.0;
			this.writeFile.writeVector(this.writeFile, text);
			this.writeFile.closeWriteFile(this.writeFile);
		}
    }
    
    /**
     * Write O preference for each user
     * @throws IOException
     */
    public void writePreferences_O() throws IOException {
		for(int i = 0; i < numUsers; i++) {
			String text = "";
			this.writeFile = new CustomWriteFile("user_U_" + i + ".txt");
			for(int j = 0; j < AGS_Individual.defaultGeneLength; j++)
				text += O_preferences.get(i).get(j) + " ";
			text += -1.0;
			this.writeFile.writeVector(this.writeFile, text);
			this.writeFile.closeWriteFile(this.writeFile);
		}
    }
    
    /**
     * Write M preference for each user
     * @throws IOException
     */
    public void writePreferences_M() throws IOException {
		for(int i = 0; i < numUsers; i++) {
			String text = "";
			this.writeFile = new CustomWriteFile("user_R_" + i + ".txt");
			for(int j = 0; j < AGS_Individual.defaultGeneLength; j++)
				text += M_preferences.get(i).get(j) + " ";
			text += -1.0;
			this.writeFile.writeVector(this.writeFile, text);
			this.writeFile.closeWriteFile(this.writeFile);
		}
    }
    
    /**
     * Generates random O preference for each user
     */
    public void randomPreferences_O() {
        for (int i = 0; i < numUsers; i++) {
        	Vector<Double> newPreference = new Vector<Double>(AGS_Individual.defaultGeneLength);
        	for(int j = 0; j < AGS_Individual.defaultGeneLength; j++) 
        		newPreference.add(j, ((Math.random() * this.maxValuePreference) + 1));	
        	O_preferences.add(i, newPreference);
        }
    }
    
    /**
     * Generates random M preference for each user
     */
    public void randomPreferences_M() {
        for (int i = 0; i < numUsers; i++) {
        	Vector<Double> newPreference = new Vector<Double>(AGS_Individual.defaultGeneLength);
        	for(int j = 0; j < AGS_Individual.defaultGeneLength; j++)
        		newPreference.add(j, ((Math.random() * this.maxValuePreference) + 1));
        	M_preferences.add(i, newPreference);
        }
        // Initial preferences should be equal and then modified according to each individual in the population
        M_preferences.set(0, O_preferences.get(0)); 
    }

    /**
     * Calculate individuals fitness
     * @param individual
     * @return
     * @throws IOException 
     */
    public static double getFitness(AGI_Engine engine, AGS_Individual individual, int indiv_i) throws IOException {
    	engine.setM_Preferences(M_preferences);
    	engine.setMPacks(M_packs);
    	if(indiv_i == 0) // In the first iteration we calculate the real utility
    		System.out.println("Real: " + engine.executeIGA(indiv_i, true).getFitness());
    	// We replace the i-th individual (remember that each individual in the EMS is a resource allocation of U_0)
    	M_preferences.set(0, individual.getGenes());
    	// The fitness is calculated first so that the first of all (when U_0 = R_0) is not modified
    	AGI_Data data = engine.executeIGA(indiv_i, false);
    	individual.setPromising(data.getPromising());
    	individual.setFitness(data.getFitness());
    	individual.setSolution(data.getSolution());
    	return individual.getOnlyFitness();
    } 
    
    /**
     * Get number of users
     * @return
     */
    public int getNumUsers() {
    	return this.numUsers;
    }
    
	/**
	 * Used to get preferences to send it to AGI
     * @return
     */
    public Vector<Vector<Double>> getM_Preferences(){
    	return this.M_preferences;
    }
    
	/**
	 * Used to get preferences to send it to AGI
     * @return
     */
    public Vector<Vector<Double>> getO_Preferences(){
    	return this.O_preferences;
    }
    
    /**
     * Used to get packs to send it to AGI
     * @return
     */
    public Vector<Pack> getOPacks(){
    	return this.O_packs;
    }
    
    /**
     * Used to get packs to send it to AGI
     * @return
     */
    public Vector<Pack> getMPacks(){
    	return this.M_packs;
    }

    /**
     * Mutates plus value of each pack
     * @param _numPacks
     */
	public void mutationPlus(int _numPacks) {
		double variation, oldValue, value;
		for(int i = 0; i < _numPacks; i++) {
            if (Math.random() <= mutationRate) {
            	oldValue = this.M_packs.get(i).getPlus();
            	do {
            		Random r = new Random();
            		variation = (r.nextInt(2)==0?-1:1)*10*r.nextDouble(); // Random interval (-20, +20)
            		value = oldValue + variation;
            	} while (value > 100);
            	this.M_packs.get(i).setPlus(value);
            }
		}
	}

	/**
	 * Creates new pack
	 * @param _addPack
	 * @return
	 * @throws IOException 
	 */
	public boolean createPack(boolean _addPack, int _numPacks) throws IOException {
		boolean created = false;
		if(_addPack == true) {
			if(Math.random() <= addPackRate) {
				int owner = 0;
				double Mplus = (Math.random()*20) + 1; // Plus interval (0, 30)
				double Oplus = 0.0;
				int numResources;
				if(Math.random() <= 0.66)
					numResources = 2; // 66%
				else 
					numResources = 3; // 33%

				// Create and initialize the resource array to -1 (because 0 is a valid resource)
				int[] resources = new int[numResources];
				for(int i = 0; i < resources.length; i++)
					resources[i] = -1;

				String resourText = "";
				
				// Create the resource array of the pack without repetitions
				for(int i = 0; i < numResources; i++) {
					// For each resource I must go through the array and see if it already exists
					boolean existe; int resour;
					do {
						existe = false;
						resour = (int)(Math.random() * (((M_preferences.get(0).size()-1) - 0) + 1));
						for(int k = 0; k < resources.length; k++) {
							if(resources[k] == resour)  
								existe = true;
						}
					} while (existe);
					// Once I have the new resource, I add it
					resources[i] = resour;
					resourText += resour + " ";
				}
				// Add the new pack
				Pack newOPack = new Pack(resources, Oplus, owner);
				Pack newMPack = new Pack(resources, Mplus, owner);
				this.O_packs.add(newOPack);	
				this.M_packs.add(newMPack);
				created = true;
				
				// Save the pack into the file
				String text = "";
				this.writeFile = new CustomWriteFile("pack" + _numPacks + ".txt");
				text += "+" + Oplus + " " + (double)owner + " " + (double)numResources + " " + resourText + -1.0;
				this.writeFile.writeVector(this.writeFile, text);
				this.writeFile.closeWriteFile(this.writeFile);
			}
		}
		return created;
	}
}
