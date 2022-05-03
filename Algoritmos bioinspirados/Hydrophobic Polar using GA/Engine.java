package main;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.Scanner;
import java.util.Vector;

public class Engine {
	private int popSize;
	private int maxGenerations = 50;
	Scanner sc = new Scanner(System.in);
	
	/**
	 * Controls the flow of execution
	 */
	public void start() {
		System.out.println("What size of population do you want?");
		int op = this.sc.nextInt();
		this.popSize = op;
		
		Population myPop = new Population(this.popSize);
		myPop.initialization();
		int generation = 0;
		while (generation < this.maxGenerations) {
        	makePopMatrix(myPop);
			System.out.print("Iteration: " + generation + " | ");
			generation++;
	        myPop = myPop.evolvePopulation(myPop);
		}

	}
	
	/**
	 * Transforms our population into a matrix
	 */
	public void makePopMatrix(Population myPop){
		int[][] matrix = new int[this.popSize*3][this.popSize*3];
		int x;
		int y;
		for(int i = 0; i < this.popSize; i++){
			x = myPop.getIndiv(i).getLocation().getX();
			x = 1+3*x; // new location = 1+3*x
			y = myPop.getIndiv(i).getLocation().getY();	
			y = 1+3*y; // new location = 1+3*y
			matrix[y][x] = myPop.getIndiv(i).getType(); 
			for(int j = 0; j < myPop.getIndiv(i).numLinks(); j++){ // We have north, east, south and west (links of each individual)
				if(j == 0 && y+1 < this.popSize*3) // We "need" to control the bounds (just in case)
					matrix[y-1][x] = myPop.getIndiv(i).getLink(j);
				else if(j == 1  && x+1 < this.popSize*3)
				 	matrix[y][x+1] = myPop.getIndiv(i).getLink(j);
				else if(j == 2  && y-1 >= 0)
					matrix[y+1][x] = myPop.getIndiv(i).getLink(j);
				else if(j == 3  && x-1 >= 0)
					matrix[y][x-1] = myPop.getIndiv(i).getLink(j);
			}
		}
		printPop(matrix);
	}
	
	/**
	 * Prints our population
	 * @param matrix
	 */
	public void printPop(int[][] matrix){

		for(int i = 0; i < matrix.length; i++){

			for(int j = 0; j < matrix.length; j++){
				if(j == 0)
					System.out.print("	");
				if(matrix[i][j] == 0){
					System.out.print("*");
				}else if(matrix[i][j] == 1) {
					System.out.print("-");	
				}else if(matrix[i][j] == 2){
					System.out.print("+");
				}else if(matrix[i][j] == 4){
					System.out.print("H");
				}else if(matrix[i][j] == 5){
					System.out.print("P");
				}
			}
			System.out.println("");
		}
	}
}
