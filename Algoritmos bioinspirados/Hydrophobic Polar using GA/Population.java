package main;

import java.io.IOException;
import java.time.Year;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageTypeSpecifier;
import javax.jws.HandlerChain;
import javax.sql.rowset.FilteredRowSet;
import javax.swing.text.AsyncBoxView.ChildLocator;

import data.CustomReadFile;
import data.CustomWriteFile;

public class Population {
	private int popSize;
	private Vector<Individual> population;
	private Individual fittest;
	private double linkMutation;
	private double typeMutation;

	
	private CustomReadFile readFile;
	private CustomWriteFile writeFile;
	Scanner sc;
	
	/**
	 * Constructor
	 * @param popSize
	 */
	public Population(int popSize) {
		this.popSize = popSize;
		this.population = new Vector<Individual>(popSize);
		this.linkMutation = 0.05;
		this.typeMutation = 0.01;
	}
	
	/**
	 * Evolves the population
	 * @param oldPop
	 * @return
	 */
	public Population evolvePopulation(Population oldPop) {
		setFitness();
		this.fittest = getFittest();
		System.out.println("Location: (" + fittest.getLocation().getX() + ", " +  fittest.getLocation().getY() + ")" + " | Fitness: " + fittest.getFitness());
		Population newPop = movePopulation(oldPop);
		newPop = mutationTypes(newPop);
		newPop = mutationLinks(newPop);
		return newPop;
	}
	
	/**
	 * Moves each individual of population
	 * @param oldPop
	 * @return
	 */
	private Population movePopulation(Population oldPop) {
		for(int i = 0; i < this.popSize; i++){
			double p = Math.random();
			double cumulativeP = 0.0;
			Vector<Double> probabilities = getProbabilities(oldPop.getIndiv(i), i);
			int choice = 0;
			boolean follow = true;
			for(int j = 0; j < this.fittest.numLinks() && follow; j++){ // We select the direction according to its probability
				cumulativeP += probabilities.get(j);
				if(p <= cumulativeP){
					choice = j;
					follow = false;
				}
			}
			// Get the new (posible) location
			Location newLocation = getNewLocation(oldPop.getIndiv(i).getLocation(), probabilities.get(choice), choice, oldPop, i);
			if(oldPop.getIndiv(i).getLocation().getX() != newLocation.getX() || oldPop.getIndiv(i).getLocation().getY() != newLocation.getY()){ // If the indiv 'i' has a new location
				// Set the new location
				oldPop.getIndiv(i).getLocation().setX(newLocation.getX());
				oldPop.getIndiv(i).getLocation().setY(newLocation.getY());
			}
			oldPop.getIndiv(i).resetFitness(); // We need to reset each fitness (cause in the next evolution we are gonna calculate it again)
		}
		return oldPop;
	}
	
	/**
	 * Mutation of each individual's type
	 * @param newPop
	 * @return
	 */
	public Population mutationTypes(Population newPop){
		for(int i = 0; i < this.popSize; i++){
            if (Math.random() <= this.typeMutation) {
            	int type = newPop.getIndiv(i).getType();
            	if(type == 4)
                	newPop.getIndiv(i).setType(5);
            	else
                	newPop.getIndiv(i).setType(4);
            }
		}
		return newPop;
	}
	
	/**
	 * Mutation of each link (for all individuals)
	 * @param newPop
	 * @return
	 */
	public Population mutationLinks(Population newPop){
		for(int i = 0; i < this.popSize; i++){
			for(int j = 0; j < this.fittest.numLinks(); j++){
	            if (Math.random() <= this.linkMutation) {
	            	int link = newPop.getIndiv(i).getLink(j);
	            	if(link == 1)
	            		newPop.getIndiv(i).setLink(j, 2);
	            	else
	            		newPop.getIndiv(i).setLink(j, 1);	
	            }
			}
		}
		return newPop;
	}
	
	/**
	 * Make a new location (if that is posible)
	 * @param oldLocation
	 * @param p
	 * @param direction
	 * @param oldPop
	 * @param currentIndiv
	 * @return
	 */
	public Location getNewLocation(Location oldLocation, double p, int direction, Population oldPop, int indexCurrent){
		Location newLocation = new Location(oldLocation.getX(), oldLocation.getY());
		double random = Math.random();
		int x = oldLocation.getX();
		int y = oldLocation.getY();
		if(random < p){ // We have to move the individual (if there's a slot)
			if(direction == 0 && y-1 >= 0){ // To the North
				boolean move = true;
				for(int k = 0; k < this.popSize; k++){
					if((oldPop.getIndiv(k).getLocation().getX() == x && oldPop.getIndiv(k).getLocation().getY() == y-1)) // if there's another indiv in the north
						move = false;
				}
				if(move == true){
					newLocation.setX(x);
					newLocation.setY(y-1);
				}
			}else if(direction == 1 && x+1 < this.popSize){  // To the East
				boolean move = true;
				for(int k = 0; k < this.popSize; k++){
					if((oldPop.getIndiv(k).getLocation().getX() == x+1 && oldPop.getIndiv(k).getLocation().getY() == y)) // if there's another indiv in the north
						move = false;
				}
				if(move == true){
					newLocation.setX(x+1);
					newLocation.setY(y);
				}
			}else if(direction == 2 && y+1 < this.popSize){  // To the South
				boolean move = true;
				for(int k = 0; k < this.popSize; k++){
					if((oldPop.getIndiv(k).getLocation().getX() == x && oldPop.getIndiv(k).getLocation().getY() == y+1)) // if there's another indiv in the north
						move = false;
				}
				if(move == true){
					newLocation.setX(x);
					newLocation.setY(y+1);
				}
			}else if(direction == 3 && x-1 >= 0){  // To the West
				boolean move = true;
				for(int k = 0; k < this.popSize; k++){
					if((oldPop.getIndiv(k).getLocation().getX() == x-1 && oldPop.getIndiv(k).getLocation().getY() == y)) // if there's another indiv in the north
						move = false;
				}
				if(move == true){
					newLocation.setX(x-1);
					newLocation.setY(y);
				}
			}
		}
		return newLocation;
	}
	
	
	/**
	 * Gets probabilities as an array
	 * @return
	 */
	public Vector<Double> getProbabilities(Individual current, int i){
		Vector<Double> probabilities = new Vector<Double>(this.fittest.numLinks());
		probabilities.add(goNorth(current, this.fittest, i));
		probabilities.add(goEast(current, this.fittest, i));
		probabilities.add(goWest(current, this.fittest, i));
		probabilities.add(goSouth(current, this.fittest, i));
		// We need to calculate the weighting
		probabilities = weighting(probabilities);
		return probabilities;
	}
	
	/**
	 * Weighting the probabilities
	 * @param probabilities
	 * @return
	 */
	private Vector<Double> weighting(Vector<Double> probabilities) {
		double total = 0.0;
		for(int i = 0; i < probabilities.size(); i++)
			total += probabilities.get(i);
		for(int i = 0; i < probabilities.size(); i++)
			probabilities.set(i, ((1*probabilities.get(i))/total));
		return probabilities;
	}

	/**
	 * Set fitness of each individual
	 */
	public void setFitness(){
		int x, y;
		for(int i = 0; i < this.popSize; i++){ // We gotta check each indiv
			x = this.population.get(i).getLocation().getX();
			y = this.population.get(i).getLocation().getY();
			for(int j = 0; j < this.population.get(0).numLinks(); j++){ // also we need to check each individual's link
				if(j == 0 && y-1 >= 0){ // North bound
					for(int k = 0; k < this.popSize; k++){ // Search a posible individual linked with this one in the north
						if(k != i && (this.population.get(k).getLocation().getX() == x && this.population.get(k).getLocation().getY() == y-1)){ // if there's another indiv in the north
							if(this.population.get(i).getType() == this.population.get(k).getType() && this.population.get(i).getLink(0) != this.population.get(k).getLink(2)) // if they have the same type and different charge in its north/south links
								if(this.population.get(i).getType() == 4 && this.population.get(k).getType() == 4) // if its type is "H"
									this.population.get(i).incrementFitness();
						}
					}
				}else if(j == 1 && x+1 < this.popSize){ // East bound
					for(int k = 0; k < this.popSize; k++){  // Search a posible individual linked with this one in the east
						if(k != i && (this.population.get(k).getLocation().getX() == x+1 && this.population.get(k).getLocation().getY() == y)){  // if there's another indiv in the east
							if(this.population.get(i).getType() == this.population.get(k).getType() && this.population.get(i).getLink(1) != this.population.get(k).getLink(3)) // if they have the same type and different charge in its east/west links
								if(this.population.get(i).getType() == 4 && this.population.get(k).getType() == 4) // if its type is "H"
									this.population.get(i).incrementFitness();
						}
					}
				}else if(j == 2 && y+1 < this.popSize){ // South bound
					for(int k = 0; k < this.popSize; k++){  // Search a posible individual linked with this one in the south
						if(k != i && (this.population.get(k).getLocation().getX() == x && this.population.get(k).getLocation().getY() == y+1)){  // if there's another indiv in the south
							if(this.population.get(i).getType() == this.population.get(k).getType() && this.population.get(i).getLink(2) != this.population.get(k).getLink(0)) // if they have the same type and different charge in its south/north links
								if(this.population.get(i).getType() == 4 && this.population.get(k).getType() == 4) // if its type is "H"
									this.population.get(i).incrementFitness();
						}
					}
				}else if(j == 3 && x-1 >= 0){ // West bound
					for(int k = 0; k < this.popSize; k++){  // Search a posible individual linked with this one in the west
						if(k != i && (this.population.get(k).getLocation().getX() == x-1 && this.population.get(k).getLocation().getY() == y)){  // if there's another indiv in the west
							if(this.population.get(i).getType() == this.population.get(k).getType() && this.population.get(i).getLink(3) != this.population.get(k).getLink(1)) // if they have the same type and different charge in its west/east links
								if(this.population.get(i).getType() == 4 && this.population.get(k).getType() == 4) // if its type is "H"
									this.population.get(i).incrementFitness();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Gets the fittest
	 * @return
	 */
	public Individual getFittest(){
		int index = 0;
		for(int i = 0; i < this.popSize; i++){
			if(this.population.get(i).getFitness() >= this.population.get(index).getFitness())
				index = i;
		}
		return this.population.get(index);
	}
	
	/**
	 * Reads individuals from file and makes a new location for each individual
	 * @return
	 */
	public Vector<Individual> initialization(){
		try {
			this.readFile = new CustomReadFile("chain.txt");
			this.sc = new Scanner(this.readFile);
			Vector<Integer> chain = this.readFile.readVector(sc, this.popSize);
			for(int j = 0; j < chain.size(); j++) {
				Individual newIndiv = new Individual(chain.get(j), getRandomLocation(j));
				this.population.add(newIndiv);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.population;
	}

	/**
	 * Gets a new valid location in the population's matrix
	 * @param index
	 * @return
	 */
	public Location getRandomLocation(int index) {
		int x, y;
		do {
			x = (int) Math.floor(Math.random()*((this.popSize-1)-0+1)+0);
			y = (int) Math.floor(Math.random()*((this.popSize-1)-0+1)+0);
		} while (!isFree(x, y, index));
		return new Location(x, y);
	}
	
	/**
	 * Returns if a location is free
	 * @param x
	 * @param y
	 * @param index
	 * @return
	 */
	public boolean isFree(int x, int y, int index) {
		boolean isFree = true;
		for(int i = 0; i < index; i++) { // The 'index' is used to check each position (not null) till index's position
			if((this.population.get(i).getLocation().getX() == x) && (this.population.get(i).getLocation().getY() == y))
				isFree = false;		
		}
		return isFree;
	}
	
	/**
	 * Returns the individual in index's position
	 * @param index
	 * @return
	 */
	public Individual getIndiv(int index){
		return this.population.get(index);
	}
	
	/**
	 * Probability to go North
	 * @param current
	 * @param fittest
	 * @return
	 */
	public double goNorth(Individual current, Individual fittest, int i){
		double probability = 1;
		if(isFree(current.getLocation().getX(), current.getLocation().getY()-1, i)){
			// About fitness of our fittest
			if(current.getFitness() != 0) 
				probability -= (1-(1/(2*current.getFitness()))); // 1 - 1/2*n, where n is the fitness of our fittest
			// About temperature
			probability -= (probability/current.getTemperature());
			// About distance of fittest
			double subtraction = (fittest.getLocation().getX() - current.getLocation().getX()) + (fittest.getLocation().getY() - current.getLocation().getY()-1); // (fx - cx) + (fy - cy), where f is "fittest" and c is "current" 
			if(Math.abs(subtraction) == 1.0)
				subtraction = 1.2;
			probability -= probability/Math.abs(subtraction);
			if(probability < 0)
				probability = 0.1;
		}else
			probability = 0.0;
		return probability;
	}
	
	/**
	 * Probability to go East
	 * @param current
	 * @param fittest
	 * @return
	 */
	public double goEast(Individual current, Individual fittest, int i){
		double probability = 1;
		if(isFree(current.getLocation().getX()+1, current.getLocation().getY(), i)){
			// About fitness of our fittest
			if(current.getFitness() != 0) 
				probability -= (1-(1/(2*current.getFitness()))); // 1 - 1/2*n, where n is the fitness of our fittest
			// About temperature
			probability -= (probability/current.getTemperature());
			// About distance of fittest
			double subtraction = (fittest.getLocation().getX() - current.getLocation().getX()+1) + (fittest.getLocation().getY() - current.getLocation().getY()); // (fx - cx) + (fy - cy), where f is "fittest" and c is "current" 
			if(Math.abs(subtraction) == 1.0)
				subtraction = 1.2;
			probability -= probability/Math.abs(subtraction);
			if(probability < 0)
				probability = 0.1;
		}else
			probability = 0.0;
		return probability;
	}
	
	/**
	 * Probability to go South
	 * @param current
	 * @param fittest
	 * @return
	 */
	public double goSouth(Individual current, Individual fittest, int i){
		double probability = 1;
		if(isFree(current.getLocation().getX(), current.getLocation().getY()+1, i)){
			// About fitness of our fittest
			if(current.getFitness() != 0) 
				probability -= (1-(1/(2*current.getFitness()))); // 1 - 1/2*n, where n is the fitness of our fittest
			// About temperature
			probability -= (probability/current.getTemperature());
			// About distance of fittest
			double subtraction = (fittest.getLocation().getX() - current.getLocation().getX()) + (fittest.getLocation().getY() - current.getLocation().getY()+1); // (fx - cx) + (fy - cy), where f is "fittest" and c is "current" 
			if(Math.abs(subtraction) == 1.0)
				subtraction = 1.2;
			probability -= probability/Math.abs(subtraction);
			if(probability < 0)
				probability = 0.1;
		}else
			probability = 0.0;
		return probability;
	}
	
	/**
	 * Probability to go West
	 * @param current
	 * @param fittest
	 * @return
	 */
	public double goWest(Individual current, Individual fittest, int i){
		double probability = 1;
		if(isFree(current.getLocation().getX()-1, current.getLocation().getY(), i)){
			// About fitness of our fittest
			if(current.getFitness() != 0) 
				probability -= (1-(1/(2*current.getFitness()))); // 1 - 1/2*n, where n is the fitness of our fittest
			// About temperature
			probability -= (probability/current.getTemperature());
			// About distance of fittest
			double subtraction = (fittest.getLocation().getX() - current.getLocation().getX()-1) + (fittest.getLocation().getY() - current.getLocation().getY()); // (fx - cx) + (fy - cy), where f is "fittest" and c is "current" 
			if(Math.abs(subtraction) == 1.0)
				subtraction = 1.2;
			probability -= probability/Math.abs(subtraction);
			if(probability < 0)
				probability = 0.1;
		}else
			probability = 0.0;
		return probability;
	}
}
