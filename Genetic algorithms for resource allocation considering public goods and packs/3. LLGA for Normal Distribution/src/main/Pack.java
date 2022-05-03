package main;
import java.util.Vector;

/**
 * Class Pack
 * @author J. Carrero
 *
 */
public class Pack {
	private int[] resources;
	private double plus;
	private int owner;
	
	/**
	 * Constructor
	 * @param _resources
	 * @param _plus
	 * @param _owner
	 */
	public Pack(int[] _resources, double _plus, int _owner){
		this.resources = _resources;
		this.plus = _plus;
		this.owner = _owner;
	}
	
	/**
	 * Get resources
	 * @return
	 */
	public int[] getResources(){
		return this.resources;
	}
	
	/**
	 * Get resource
	 * @param _index
	 * @return
	 */
	public int getResource(int _index){
		return this.resources[_index];
	}
	
	/**
	 * Get plus
	 * @return
	 */
	public double getPlus(){
		return this.plus;
	}
	
	/**
	 * Set plus
	 * @param _plus
	 */
	public void setPlus(double _plus){
		this.plus = _plus;
	}
	
	/**
	 * Get owner
	 * @return
	 */
	public int getOwner(){
		return this.owner;	
	}
}
