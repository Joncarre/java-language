package main;

public class Location {
	private int x;
	private int y;
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Set a new value for x
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Set a new value for y
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Get value of x
	 * @return
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Get value of y
	 * @return
	 */
	public int getY() {
		return this.y;
	}
}
