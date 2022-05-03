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
	private final int generations = 5000; // Evolutions of population
	private final int popSize = 20; // Population Size
	private final int numUsers = 4; // Number of agents
	private final int numPacks = 2; // Number of packs
	private int pfcIterations = 100; // Preference Iterations
	private FitnessCalc fitness = new FitnessCalc(this.numUsers, this.numPacks);
	private Population myPop;
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
		this.fitness.readMPreferences();
		this.fitness.readOPreferences();
		this.fitness.readOPacks();
		this.fitness.readMPacks();
		boolean seguir = true;
		
		/* We have to save the resources which are gonna be modified */
		/*this.fitness.initialResources();
		this.fitness.saveResources(0);
		this.fitness.saveResources(1);
		this.fitness.saveResources(2);
		this.fitness.saveResources(3);
		this.fitness.saveResources(4);
		this.fitness.saveResources(5);
		this.fitness.saveResources(6);
		this.fitness.saveResources(7);
		this.fitness.saveResources(8);
		this.fitness.saveResources(9);*/
		
		// Loop to increment or decrement preferences	
		for(int i = 0; i < pfcIterations && seguir == true; i++) { 
			this.fitness.resetValues(avgIterations);
			this.fitness.resetPopulations(popSize);
			this.fitness.saveTemporalPreferences();

			// Loop to select our best egalitarian result
			for(int j = 0; j < avgIterations; j++) { 
		    	this.myPop = new Population(this.popSize, this.numUsers, false);
		    	int generationCount = 0;
				while (generationCount < this.generations) {
			        generationCount++;
					//System.out.println("Ite: " + generationCount + " Fitness(lowest): " + myPop.getIndividual(0).getOnlyFitness()  + " Genes: " + myPop.getIndividual(0).toString());
			        this.myPop = Algorithm.evolvePopulation(this.myPop, this.popSize);
				}
				this.fitness.saveIndividuals(myPop.getFittest());
			}
			this.fitness.writeDistribution(avgIterations, i);

			/* Modification of the resources */
			/*this.fitness.setValueAlza(0, 0);
			this.fitness.setValueAlza(0, 1);
			this.fitness.setValueAlza(0, 2);
			this.fitness.setValueAlza(0, 3);
			this.fitness.setValueAlza(0, 4);
			this.fitness.setValueAlza(0, 5);
			this.fitness.setValueAlza(0, 6);
			this.fitness.setValueAlza(0, 7);
			this.fitness.setValueAlza(0, 8);
			this.fitness.setValueAlza(0, 9);

			seguir = this.fitness.distributionVariationAlza();*/
			
			this.fitness.setPlusAlza(0);
			this.fitness.setPlusAlza(1);
		}
		
		System.out.println("To be continued...");
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

