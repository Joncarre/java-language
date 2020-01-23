package main;

import java.time.Year;
import java.util.Vector;

public class Individual {
	private int type;
	private int fitness;
	private double temperature;
	private Vector<Integer> links;
	private Location location;
	
	/**
	 * Constructor
	 * @param type
	 * @param location
	 */
	public Individual(int type, Location location){
		this.type = type;
		this.fitness = 0;
		this.links = newLinks();
		this.location = location;
		this.temperature = Math.random()*((50+1)-30)+30;
	}
	
	/**
	 * Creates new links
	 * @return
	 */
	public Vector<Integer> newLinks(){
		Vector<Integer> newLinks = new Vector<Integer>(4); // Each invididual has four links
		for(int i = 0; i < 4; i++)
			newLinks.add(i, (int) (Math.random()*((2+1)-1))+1); // (Math.random()*((maximum+1) - minimum)) + minimum
		return newLinks;
	}
	
	/**
	 * Resets the value of fitness
	 */
	public void resetFitness() {
		this.fitness = 0;
	}
	
	/**
	 * Increments +1 our fitness
	 */
	public void incrementFitness() {
		this.fitness++;
	}
	
	/**
	 * Get Fitness
	 * @return
	 */
	public int getFitness() {
		return this.fitness;
	}
	
	/**
	 * Get Location
	 * @return
	 */
	public Location getLocation() {
		return this.location;
	}
	
	/**
	 * Get Type
	 * @return
	 */
	public int getType(){
		return this.type;
	}
	
	/**
	 * Set Type
	 * @param type
	 */
	public void setType(int type){
		this.type = type;
	}
	
	/**
	 * Get number of links
	 * @return
	 */
	public int numLinks(){
		return this.newLinks().size();
	}
	
	/**
	 * Get the link in position 'index'
	 * @param index
	 * @return
	 */
	public Integer getLink(int index){
		return this.links.get(index);
	}
	
	/**
	 * Set a new link
	 * @param index
	 * @param value
	 */
	public void setLink(int index, int value){
		this.links.set(index, value);
	}
	
	/**
	 * Get Temperature
	 * @return
	 */
	public double getTemperature(){
		return this.temperature;
	}
	
	

}
