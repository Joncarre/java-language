package AGI;
import java.io.IOException;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.ThreadInfo;
import java.util.Scanner;
import java.util.Vector;
import java.util.function.DoubleBinaryOperator;
import javax.management.openmbean.OpenDataException;
import javax.swing.InputMap;
import AGS.AGS_Engine;
import AGS.AGS_FitnessCalc;
import AGS.AGS_Population;
import AGS.Pack;
import files.CustomWriteFile;

/**
 * AGI_Engine
 * @author J. Carrero
 *
 */
public class AGI_Engine {
	private final int avgIterations = 1; // Average Iterations
	private final int generations = 5000; // Evolutions of population
	private final int popSize = 20; // Population Size
	private final int numUsers = 4; // Number of agents
	private AGI_FitnessCalc fitness = new AGI_FitnessCalc(this.numUsers, this.avgIterations);
	private AGI_Population myPop;
	Scanner sc = new Scanner(System.in);

	/**
	 * 
	 * Execute IGA
	 * @return
	 * @throws IOException
	 */
	public AGI_Data executeIGA(int indiv_i, boolean firstTime) throws IOException {	
		/* change 'op' to chose read data or generate population */
		int op = 1; 
		AGI_Data data = new AGI_Data();
		AGI_Data dataFinal = new AGI_Data();
		if(op == 1) {
			this.fitness.resetValues(avgIterations);
			for(int j = 0; j < avgIterations; j++) { // Loop to select our best egalitarian result
		    	this.myPop = new AGI_Population(this.popSize, this.numUsers, false);
		    	int generationCount = 0;
				while (generationCount < this.generations) {
			        generationCount++;
					//System.out.println("Ite: " + generationCount + " Fitness(lowest): " + myPop.getIndividual(0).getOnlyFitness()  + " Genes: " + myPop.getIndividual(0).toString());
			        this.myPop = AGI_Algorithm.evolvePopulation(this.myPop, this.popSize);
				}
				this.fitness.saveIndividuals(myPop.getIndividual(0));
			}
			// Its fitness is updated and the promising and the solution are stored
			data = this.fitness.setPromising(this.fitness.updateBestFitness());
			// It is checked if it is the most promising one
			if(data.getFitness() > dataFinal.getFitness()) {
				dataFinal = data;
			}else if(data.getFitness() == dataFinal.getFitness()) {
				if(data.getPromising() < dataFinal.getPromising())
					dataFinal = data;	
			}
		}else if(op == 2) {
			generatePopulation();
			System.exit(0);
		}
		else 
			System.out.println("Bye, Bye!");
		
		return dataFinal;
	}
	
	/**
	 * Generate a new Population to files
	 * @throws IOException
	 */
	public void generatePopulation() throws IOException {
		System.out.println("ATENTION, new Population for IGA will be generate. Press ENTER to continue");
		this.sc.nextLine();
	    AGI_Population myPop = new AGI_Population(this.popSize, this.fitness.getNumUsers(), true);
		System.out.println("Data generated");
	}
	
	/**
	 * Used to read preferences from AGS
	 * @param M_preferences
	 */
	public void setM_Preferences(Vector<Vector<Double>> M_preferences) {
		this.fitness.setM_Preferences(M_preferences);
	}
	
	/**
	 * Used to read preferences from AGS
	 * @param O_preferences
	 */
	public void setO_Preferences(Vector<Vector<Double>> O_preferences) {
		this.fitness.setO_Preferences(O_preferences);
	}
	
	/**
	 * Used to read O packs from AGS
	 * @param packs
	 */
    public void setOPacks(Vector<Pack> packs){
    	this.fitness.setOPacks(packs);
    }
    
    /**
     * Used to read M packs from AGS
     * @param packs
     */
    public void setMPacks(Vector<Pack> packs){
    	this.fitness.setMPacks(packs);
    }
}

