package main;

import java.io.IOException;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.ThreadInfo;
import java.util.Scanner;
import java.util.Vector;

import javax.management.openmbean.OpenDataException;

import org.omg.CORBA.PRIVATE_MEMBER;

public class Engine {
	private final int averageIterations = 2000;
	private final int maxGenerations = 40;
	private int preferenceIterations = 100;
	private final int populationSize = 100;
	private final int numUsers = 4;
	private FitnessCalc fitness = new FitnessCalc(this.numUsers, this.averageIterations);
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
			if(op == 1) {
				readPopulation();
			}else
				System.out.println("Bye!");
		}else 
			generatePopulation();
	}
	
	/**
	 * Read population from file and execute IGA
	 * @throws IOException
	 */
	public void readPopulation() throws IOException {
		this.fitness.readPreferences();
		this.fitness.readPreferences2();
		//long startTime = System.currentTimeMillis();	
		
		
		for(int i = 0; i < preferenceIterations; i++) {	
			this.fitness.resetValues(averageIterations, numUsers);
			this.fitness.resetPopulations(averageIterations);

			for(int j = 0; j < averageIterations; j++) {
		    	this.myPop = new Population(this.populationSize, this.fitness.getNumUsers(), false);
		    	int generationCount = 0;
				while (generationCount < this.maxGenerations) {
			        generationCount++;
			        this.myPop = Algorithm.evolvePopulation(this.myPop, this.populationSize);
			        //System.out.println("Fittest: " + myPop.getFittest().getFitness() + "  Genes: " + myPop.getFittest().toString());
				}
				
				//System.out.println("---------------------------------------");
				
			    /*System.out.println("\n" + "Solution found! " + "(" + i + ")");
			    System.out.println("	Generation -> " + (generationCount-1));
			    System.out.println("	Genes -> " + myPop.getFittest());*/
			    //System.out.println("	Fittest -> " + myPop.getFittest().getFitness());
				//System.out.println("Rest TIME -> " + "Fittest: " + myPop.getFittest().getFitness() + "  Genes: " + myPop.getFittest().toString());
				this.fitness.saveIndividuals(myPop.getFittest());
			}	
			
			// Establecer el mejor reparto con preferencias reales
			if(i == 0) {
				this.fitness.setBestOriginal(averageIterations);
			}
			this.fitness.writeDistribution(this.myPop.getFittest(), averageIterations, i);
			
			// Mostrar reparto y preferencias modificadas
			/*if(i > 0) {
				System.out.print(this.fitness.getBestIndividual() + "   ");
				this.fitness.showPreferences();
			}*/
			
			/* Modificación de recursos */
			//this.fitness.setValueBaja(0, 0);



			//this.fitness.showPreferences();


		}
		System.out.println("To be continued...");
		//System.out.println(System.currentTimeMillis() - startTime);
	}
	
	/**
	 * Generate a new Population to files
	 * @throws IOException
	 */
	public void generatePopulation() throws IOException {
		System.out.println("ATENTION, new data will be generate. Press ENTER to continue");
		this.sc.nextLine(); this.sc.nextLine();
	    Population myPop = new Population(this.populationSize, this.fitness.getNumUsers(), true);
		this.fitness.randomPreferences();
		this.fitness.writePreferences();
		System.out.println("Data generated");
	}
}

