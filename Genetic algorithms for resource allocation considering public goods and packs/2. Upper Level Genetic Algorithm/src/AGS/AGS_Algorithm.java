package AGS;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;
import java.util.zip.ZipEntry;

import javax.print.attribute.Size2DSyntax;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import AGI.AGI_Engine;
import AGI.AGI_Individual;
import AGI.AGI_Population;

/**
 * Class AGS_Algorithm
 * @author J. Carrero
 *
 */
public class AGS_Algorithm {
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.3;
    private static final boolean elitism = true;

    /**
     * Evolve Population
     * @param pop
     * @return
     * @throws IOException 
     */
    public static AGS_Population evolvePopulation(AGS_Population popEvolved, AGI_Engine engine, int popSize, int generation) throws IOException {
        // Keep our best individual
        if (elitism)
            popEvolved.sustituteIndividual(0, popEvolved.getFittest(engine, true));
        
        //System.out.println("Indiv: 		  " + popEvolved.getIndividual(0).toString() + "  |   " + popEvolved.getIndividual(0).getSolution());

        int elitismOffset;
        if (elitism)
            elitismOffset = 1;
        else
            elitismOffset = 0;
        
    	AGS_Population newPop = new AGS_Population(popEvolved.size());
        newPop.saveIndividual(0, popEvolved.getIndividual(0));
        
        // Crossover phase
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
        	
        	AGS_Individual indiv1 = popEvolved.getIndividual(indexIndiv1);
        	AGS_Individual indiv2 = popEvolved.getIndividual(indexIndiv2);
        	AGS_Individual newIndiv = crossover(indiv1, indiv2);
			newPop.saveIndividual(i, newIndiv);
			
			if(i == popSize/2)
		        Arrays.fill(taken, Boolean.FALSE);
        }

     // The fitness of all indiv of the 'newPop' is calculated
        newPop.getFittest(engine, false);
        AGS_Population definitivePop = new AGS_Population(popSize);


        // Tournament phase
        for (int i = 0; i < popEvolved.size(); i++) {
        	if(newPop.getIndividual(i).getOnlyFitness() > popEvolved.getIndividual(i).getOnlyFitness()) {
        		definitivePop.saveIndividual(i, newPop.getIndividual(i));
        	}else if(newPop.getIndividual(i).getOnlyFitness() < popEvolved.getIndividual(i).getOnlyFitness()){
        		definitivePop.saveIndividual(i, popEvolved.getIndividual(i));
        	}else {
        		if(newPop.getIndividual(i).getPromising() > popEvolved.getIndividual(i).getPromising()) {
        			definitivePop.saveIndividual(i, popEvolved.getIndividual(i));
        		}else {
        			definitivePop.saveIndividual(i, newPop.getIndividual(i));
        		}
        	}
        }
        
        AGS_Individual best = copyIndiv(popEvolved.getIndividual(0));
        definitivePop.setBest(best);
        
        // Mutation phase
        for (int i = elitismOffset; i < newPop.size(); i++)
            mutate(popEvolved.getIndividual(i));
        
        definitivePop.sustituteIndividual(0, best);
        
        return definitivePop;
    }
    
    /**
     * 
     * @param fittest
     * @return
     */
    public static AGS_Individual copyIndiv(AGS_Individual fittest){
    	AGS_Individual best = new AGS_Individual();
        best.setFitness(fittest.getOnlyFitness());
        best.setPromising(fittest.getPromising());
        int[] solution = fittest.getSolution();
        best.setSolution(solution);
        for(int i = 0; i < fittest.size(); i++)
        	best.setGene(i, fittest.getGene(i));
    	return best;
    }

    /**
     * Crossover individuals
     * @param indiv1
     * @param indiv2
     * @return
     */
    private static AGS_Individual crossover(AGS_Individual indiv1, AGS_Individual indiv2) {
        AGS_Individual newSol = new AGS_Individual();
        // Loop through genes
        for (int i = 0; i < indiv1.size(); i++) {
            // Crossover (new gen = 50% old gen of indiv1 and 50% old gen of indiv2)
            if (Math.random() <= uniformRate)
                newSol.setGene(i, indiv1.getGene(i));
            else
                newSol.setGene(i, indiv2.getGene(i));
        }
        newSol.formalizePreferences();
        return newSol;
    }
    
    /**
     * Mutate an individual
     * @param indiv
     */
    private static void mutate(AGS_Individual indiv) {
        // Loop through genes
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= mutationRate) {
            	Double gene;
                gene = aproxValue(indiv.getGene(i));
                indiv.changeGene(i, gene);
            }
        }
        indiv.formalizePreferences();
    }
    
    /**
     * Range of mutation modification
     * @param value
     * @return
     */
    public static double aproxValue(double value) {
    	double variation;
    	double oldValue = value;
    	do {
    		Random r = new Random();
    		variation = (r.nextInt(2)==0?-1:1)*4*r.nextDouble(); // Random interval (-4, 4)
    		value = oldValue + variation;
		} while (value < 0 || value > 100);

    	return value;
    }
}