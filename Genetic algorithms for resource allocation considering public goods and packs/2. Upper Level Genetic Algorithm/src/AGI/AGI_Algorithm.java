package AGI;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;

import AGS.AGS_Population;

public class AGI_Algorithm {
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.2;
    private static final boolean elitism = true;

    /**
     * Evolves population
     * @param popEvolved
     * @param popSize
     * @return
     */
    public static AGI_Population evolvePopulation(AGI_Population popEvolved, int popSize) {
        // Keep our best individual
        if (elitism)
        	popEvolved.saveIndividual(0, popEvolved.getFittest()); 
        
        // Is it elitism?
        int elitismOffset;
        if (elitism)
            elitismOffset = 1;
        else
            elitismOffset = 0;
        
        AGI_Population newPop = new AGI_Population(popSize);
        newPop.saveIndividual(0, popEvolved.getIndividual(0));
        
        // Loop over the population size and create new individuals doing crossover
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
        	
        	AGI_Individual indiv1 = popEvolved.getIndividual(indexIndiv1);
        	AGI_Individual indiv2 = popEvolved.getIndividual(indexIndiv2);
        	AGI_Individual newIndiv = crossover(indiv1, indiv2);
			newPop.saveIndividual(i, newIndiv);
			
			if(i == popSize/2) {
		        Arrays.fill(taken, Boolean.FALSE);
			}
        }
        // We put the Fittest of the newPop in position 0
        newPop.saveIndividual(0, newPop.getFittest()); 
        AGI_Population definitivePop = new AGI_Population(popSize);
     // In order, we have them compete (this does not bias anything because the choice of the cross has been random)
        for (int i = 1; i < newPop.size(); i++) {
        	if(newPop.getIndividual(i).getOnlyFitness() > popEvolved.getIndividual(i).getOnlyFitness())
        		definitivePop.saveIndividual(i, newPop.getIndividual(i));
        	else
        		definitivePop.saveIndividual(i, popEvolved.getIndividual(i));
        }
        
        // The fittest is only calculated at the beginning, and then kept until the next evolution
        // We must therefore save it when returning the new population
        AGI_Individual best = copyIndiv(popEvolved.getIndividual(0));
        definitivePop.setBest(best);
        
        // Mutate population
        for (int i = elitismOffset; i < definitivePop.size(); i++)
            mutate(definitivePop.getIndividual(i));
        
        // We put the fittest again
        definitivePop.saveIndividual(0, best);

        return definitivePop;
    }
    
    /**
     * It mades the copy of an individual
     * @param fittest
     * @return
     */
    public static AGI_Individual copyIndiv(AGI_Individual fittest){
    	AGI_Individual best = new AGI_Individual();
        best.setFitness(fittest.getOnlyFitness());
        for(int i = 0; i < fittest.size(); i++)
        	best.setGeneCopy(i, fittest.getGene(i));
    	return best;
    }

    /**
     * Crossover individuals
     * @param indiv1
     * @param indiv2
     * @return
     */
    private static AGI_Individual crossover(AGI_Individual indiv1, AGI_Individual indiv2) {
    	AGI_Individual newSol = new AGI_Individual();
        // Loop through genes
        for (int i = 0; i < indiv1.size(); i++) {
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
    private static void mutate(AGI_Individual indiv) {
        // Loop through genes
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                int gene = (int) (Math.random() * AGI_FitnessCalc.numUsers);
                indiv.setGene(i, gene);
            }
        }
    }
}