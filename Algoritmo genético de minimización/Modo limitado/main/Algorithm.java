package main;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;

public class Algorithm {

    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.0;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    /**
     * Evolve Population
     * @param pop
     * @return
     */
    public static Population evolvePopulation(Population popEvolved, int popSize) {
    	
    	Population newPop = new Population(popSize);

        // Keep our best individual
        if (elitism)
        	popEvolved.saveIndividual(0, popEvolved.getFittest()); 
        
        newPop.saveIndividual(0, popEvolved.getIndividual(0));

        // Crossover population
        int elitismOffset;
        if (elitism)
            elitismOffset = 1;
        else
            elitismOffset = 0;
        
        // Loop over the population size and create new individuals with crossover
        Boolean[] taken = new Boolean[popSize];
        Arrays.fill(taken, Boolean.FALSE);
        for (int i = elitismOffset; i < popSize; i++) {
        	int indexIndiv1 = (int) Math.floor(Math.random()*((popSize-1)-0+1)+0);
        	if(taken[indexIndiv1] == true) {
        		while(taken[indexIndiv1] == true) {
        			indexIndiv1++;
        			if(indexIndiv1 == taken.length) {
        				indexIndiv1 = 0;
        			}
        		}
        	}
        	taken[indexIndiv1] = true;
        	
        	int indexIndiv2 = (int) Math.floor(Math.random()*((popSize-1)-0+1)+0);
        	if(taken[indexIndiv2] == true) {
        		while(taken[indexIndiv2] == true) {
        			indexIndiv2++;
        			if(indexIndiv2 == taken.length) {
        				indexIndiv2 = 0;
        			}
        		}
        	}
        	taken[indexIndiv2] = true;
        	
        	Individual indiv1 = popEvolved.getIndividual(indexIndiv1);
        	Individual indiv2 = popEvolved.getIndividual(indexIndiv2);
        	Individual newIndiv = crossover(indiv1, indiv2);
			newPop.saveIndividual(i, newIndiv);
			
			if(i == popSize/2) {
		        Arrays.fill(taken, Boolean.FALSE);
			}
        }
        
        newPop.saveIndividual(0, newPop.getFittest()); // Metemos el Fittest de la newPop en la posición 0
    	Population definitivePop = new Population(popSize);
    	definitivePop.saveIndividual(0, newPop.getIndividual(0));
        
        // Por orden, hacemos que compitan (esto no sesga nada porque la elección del cruzamiento ha sido aleatoria)
        for (int i = elitismOffset; i < popSize; i++) {
        	if(newPop.getIndividual(i).getOnlyFitness() < popEvolved.getIndividual(i).getOnlyFitness())
        		definitivePop.saveIndividual(i, newPop.getIndividual(i));
        	else
        		definitivePop.saveIndividual(i, popEvolved.getIndividual(i));
        }

        // Mutate population
        for (int i = elitismOffset; i < popEvolved.size(); i++)
            mutate(popEvolved.getIndividual(i));

        return definitivePop;
    }

    /**
     * Crossover individuals
     * @param indiv1
     * @param indiv2
     * @return
     */
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        // Loop through genes
        for (int i = 0; i < indiv1.size(); i++) {
            // Crossover
            if (Math.random() <= uniformRate)
                newSol.setGene(i, indiv1.getGene(i));
            else
                newSol.setGene(i, indiv2.getGene(i));
        }
        return newSol;
    }

    /**
     * Mutate an individual
     * @param indiv
     */
    private static void mutate(Individual indiv) {
        // Loop through genes
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                int gene = (int) (Math.random() * FitnessCalc.numUsers);
                indiv.setGene(i, gene);
            }
        }
    }
}