package AGS;
import java.io.IOException;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.ThreadInfo;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.Vector;

import javax.management.openmbean.OpenDataException;

import AGI.AGI_Engine;
import files.CustomReadFile;
import files.CustomWriteFile;

/**
 * Class AGS_Engine
 * @author J. Carrero
 *
 */
public class AGS_Engine {
	private final int maxGenerations;
	private final int populationSize;
	private final int numUsers;
	private int numPacks;
	private boolean addPack;
	private final int limitePacks;
	private AGS_FitnessCalc fitness;
	private AGS_Population myPop;
	Scanner sc;
	private CustomWriteFile writeFile;
	public static String textFittest = "";
	public String textPacks = "";
	public String textSolutions = "";
	
	/**
	 * Constructor
	 */
	public AGS_Engine() {
		this.maxGenerations = 100000;
		this.populationSize = 20;
		this.numUsers = 4;
		this.numPacks = 2;
		this.limitePacks = 8;
		this.addPack = true;
		this.fitness = new AGS_FitnessCalc(this.numUsers, this.numPacks);
		this.sc = new Scanner(System.in);
	}
	
	/**
	 * This method controls the flow of execution
	 * @throws IOException
	 */
	public void start() throws IOException {
		System.out.println("    (1) Execute SGA \n" + 
						   "    (2) Gerenate new Preferences \n" +  
						   "    (3) Gerenate new Population \n" + 
						   "    (*) Exit");
		int op = this.sc.nextInt();
		if(op == 1) 
			executeSGA();
		else if(op == 2) {
			generatePreferences();
			System.exit(0);
		}else  if(op == 3) {
			generatePopulation();
			System.exit(0);
		}else
			System.out.println("Bye, Bye!");
	}
	
	/**
	 * Read population from file and execute GA
	 * @throws IOException
	 */
	public void executeSGA() throws IOException {
		this.fitness.readPreferences_O();
		this.fitness.readPreferences_M();
		this.fitness.readOPacks();
		this.fitness.readMPacks();
		this.myPop = new AGS_Population();
		this.myPop.readPopulation(this.populationSize, this.numUsers);
		int generationCount = 0;
		AGI_Engine engine = new AGI_Engine();
        engine.setO_Preferences(this.fitness.getO_Preferences());
		engine.setOPacks(this.fitness.getOPacks());
		while (generationCount < this.maxGenerations) {
			//double t_1 = System.currentTimeMillis(); 
			generationCount++;
			this.myPop = AGS_Algorithm.evolvePopulation(this.myPop, engine, populationSize, generationCount);
			printBest();
			printPacks();
			printSolution();
		
			this.fitness.mutationPlus(this.numPacks);
			if(this.numPacks < this.limitePacks) {
				if(this.fitness.createPack(this.addPack, this.numPacks))
					this.numPacks++;
			}
			//System.out.println("time: " + (System.currentTimeMillis() - t_1));
		}
		System.out.println("End.");
	}
	
	/**
	 * Saves every fittest found
	 * @throws IOException
	 */
	public void printBest() throws IOException{
    	this.writeFile = new CustomWriteFile("result_Fittest.txt");
    	String textConsole = this.myPop.getBest().getOnlyFitness() + "    " + this.myPop.getBest().toString() + "\n";
    	AGS_Engine.setText(textConsole);
    	System.out.print(textConsole.replace('.', ','));
    	this.writeFile.writeVector(this.writeFile, AGS_Engine.getText().replace('.', ','));
    	this.writeFile.closeWriteFile(this.writeFile);
	}
	
	/**
	 * Saves every plus of all packs
	 * @throws IOException
	 */
	public void printPacks() throws IOException{
    	this.writeFile = new CustomWriteFile("result_Packs.txt");
    	String text = "";
    	for(int i = 0; i < this.numPacks; i++) 
    		text += this.fitness.getMPacks().get(i).getPlus() + " ";
    	text += "\n";
    	textPacks += text;
    	this.writeFile.writeVector(this.writeFile, textPacks.replace('.', ','));
    	this.writeFile.closeWriteFile(this.writeFile);
	}
	
	/**
	 * Saves every solution
	 * @throws IOException
	 */
	public void printSolution() throws IOException{
    	this.writeFile = new CustomWriteFile("result_Solution.txt");
    	int[] solution = new int[AGS_Individual.defaultGeneLength];
    	String text = "";
    	solution = this.myPop.getBest().getSolution();
    	for(int i = 0; i < solution.length; i++) {
    		text += solution[i] + " ";
    	}
    	text += "\n";
    	this.textSolutions += text;
    	this.writeFile.writeVector(this.writeFile, textSolutions.replace('.', ','));
    	this.writeFile.closeWriteFile(this.writeFile);
	}
	
	/**
	 * Generate a new Preferences to files
	 * @throws IOException
	 */
	public void generatePreferences() throws IOException {
		System.out.println("ATENTION, new Preferences will be generate. Press ENTER to continue");
		this.sc.nextLine(); this.sc.nextLine();
		this.fitness.randomPreferences_O();
		this.fitness.randomPreferences_M();
		this.fitness.writePreferences_O();
		this.fitness.writePreferences_M();
		System.out.println("Data generated");
	}

	/**
	 * Generate a new Population to files
	 * @throws IOException
	 */
	private void generatePopulation() throws IOException {
		System.out.println("ATENTION, new Population will be generate. Press ENTER to continue");
		this.sc.nextLine(); this.sc.nextLine();
	    this.myPop = new AGS_Population(this.populationSize, this.numUsers);
		System.out.println("Data generated");
	}
	
	/**
	 * Support method to set the text of the fittest
	 * @param text
	 */
	public static void setText(String text){
		AGS_Engine.textFittest += text;
	}
	
	/**
	 * Support method to get the text of the fittest
	 * @return
	 */
	public static String getText(){
		return AGS_Engine.textFittest;
	}
}

