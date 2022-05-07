package main;
import java.io.IOException;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.ThreadInfo;
import java.util.Scanner;
import java.util.Vector;
import javax.management.openmbean.OpenDataException;

/**
 * Class Engine
 * @author J. Carrero
 *
 */
public class Engine {
	private final int avgIterations = 1; // Average Iterations
	private final int generations = 50000; // Evolutions of population
	private final int popSize = 20; // Population Size
	private final int numUsers = 4; // Number of agents
	private final int numPacks = 2; // Number of packs
	private int num_apply = 1000; // Iterations to calculate the average
	private FitnessCalc fitness = new FitnessCalc(this.numUsers, this.numPacks);
	private Vector<Double> vec = new Vector<Double>(num_apply);private Population myPop;
	Scanner sc = new Scanner(System.in);
	
	/**
	 * This method controls the flow of execution
	 * @throws IOException
	 */
	public void start() throws IOException {	
		System.out.println("    (1) Read data \n" + "    (2) Gerenate new data");
		int op = this.sc.nextInt();
		if(op == 1) {
			System.out.println("Do you wanna analyze the data? \n" +  "    (1) Yes \n" +  "    (2) No");
			op = this.sc.nextInt();
			if(op == 1)
				readPopulation();
			else
				System.out.println("Bye!");
		}else 
			generatePopulation();
	}
	
	/**
	 * Read population from file and execute IGA
	 * @throws IOException
	 */
	public void readPopulation() throws IOException {
		double finalFitness = 0.0;
		//long startTime = System.currentTimeMillis();	
		
		for(int i = 0; i < num_apply; i++) { // Loop to increment or decrement preferences	
		this.fitness.resetValues();
		this.fitness.readMPreferences();
		this.fitness.readOPreferences();
		this.fitness.readOPacks();
		this.fitness.readMPacks();
		double deviation = 0.0;
		this.fitness.applyNormal(deviation);
			for(int j = 0; j < avgIterations; j++) { // Loop to select our best egalitarian result
		    	this.myPop = new Population(this.popSize, this.numUsers, false);
		    	int generationCount = 0;
				while (generationCount < this.generations) {
			        generationCount++;
					//System.out.println("Ite: " + generationCount + " Fitness(lowest): " + myPop.getIndividual(0).getOnlyFitness()  + " Genes: " + myPop.getIndividual(0).toString());
			        this.myPop = Algorithm.evolvePopulation(this.myPop, this.popSize);
				}
				this.fitness.saveIndividuals(myPop.getIndividual(0));
			}
			double bestFitness = this.fitness.updateBestFitness().getOnlyFitness();

			vec.add(bestFitness);
		    System.out.println("Ite: " + i + " Utility: " + bestFitness + " Genes: " + this.fitness.getBestIndividual().toString());
	    	finalFitness += bestFitness;
		}
		
		double max = 0.0;
		for(int i = 0; i < vec.size(); i++){
			if(vec.get(i) > max)
				max = vec.get(i);
		}
		
    	System.out.println("---------- Max ----------");
    	System.out.println(max);
    	System.out.println("---------- Average ----------");
    	System.out.println(finalFitness/this.num_apply);
		//System.out.println(System.currentTimeMillis() - startTime);
	}
	
	/**
	 * Generate a new Population to files
	 * @throws IOException
	 */
	public void generatePopulation() throws IOException {
		System.out.println("ATENTION, new data will be generate. Press ENTER to continue");
		this.sc.nextLine(); this.sc.nextLine();
	    Population myPop = new Population(this.popSize, this.fitness.getNumUsers(), true);
		this.fitness.randomPreferences();
		this.fitness.writePreferences();
		System.out.println("Data generated");
	}
}

