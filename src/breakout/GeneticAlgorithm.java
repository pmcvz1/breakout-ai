package breakout;

import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {

    public static final int POPULATION_SIZE = 150;    // Tamanho da população
    public static final int NUM_GENERATIONS = 50000;  // Número de gerações
    public static final int HIDDENDIM = 200;          // Tamanho da camada intermédia
    
    private static final double UNIFORM_RATE = 0.5;   // Taxa de crossover
    private static final double MUTATION_RATE = 0.01; // Taxa de mutação
    private static final int TOURNAMENTSIZE = 20;     // Tamanho do torneio de seleção de pais
    
	
	public FeedforwardNeuralNetwork[] evolvePopulation(FeedforwardNeuralNetwork[] population) {
	    FeedforwardNeuralNetwork[] newPopulation = population;
	       
	    int numElites = Math.max(1, (int) (0.1 * POPULATION_SIZE)); // 10% da nova pupulação são elites da anterior
	    int numMutations = Math.max(1, (int) (0.4 * POPULATION_SIZE)); // 40% da nova população vem de mutações da anterior 
	    
	    // Guarda os melhores indivíduos diretamente na nova população
	    FeedforwardNeuralNetwork[] elites = getTopFittest(population, numElites);
	    int elitesLength = Math.min(numElites, elites.length);
	    for (int i = 0; i < elitesLength; i++) {
	        newPopulation[i] = elites[i]; // Copia as elites diretamente
	    }

	    // Mutação de alguns indivíduos
	    for (int i = 0; i < numMutations && i < elites.length; i++) {
	        newPopulation[numElites + i] = mutate(elites[i]);
	    }

	    // Realiza crossovers para preencher os espaços que sobram da nova população
	    for (int i = numElites + numMutations; i < POPULATION_SIZE; i++) {
	        FeedforwardNeuralNetwork parent1 = tournamentSelection(population);
	        FeedforwardNeuralNetwork parent2 = tournamentSelection(population);
	        newPopulation[i] = crossover(parent1, parent2);
	    }

	    return newPopulation;
	}
	
	// Mutação de redes neuronais
    public FeedforwardNeuralNetwork mutate(FeedforwardNeuralNetwork neuralNetwork) {
        Random random = new Random();
        double[] genes = neuralNetwork.getNeuralNetwork();

        for (int i = 0; i < genes.length; i++) {
            if (random.nextDouble() <= MUTATION_RATE) {
                genes[i] = random.nextDouble(); // Alterar para um valor aleatório
            }
        }
        
        FeedforwardNeuralNetwork mutated = new FeedforwardNeuralNetwork(HIDDENDIM, genes);
        return mutated;
    }
	
	// Crossover de redes neuronais
    private FeedforwardNeuralNetwork crossover(FeedforwardNeuralNetwork indiv1, FeedforwardNeuralNetwork indiv2) {
    	FeedforwardNeuralNetwork newSol = new FeedforwardNeuralNetwork(HIDDENDIM);
    	double[] valuesNewSol = new double[newSol.getNeuralNetwork().length];
        
        for (int i = 0; i < indiv1.getNeuralNetwork().length; i++) {
            // Crossover
            if (Math.random() <= UNIFORM_RATE) {
                valuesNewSol[i] = indiv1.getNeuralNetwork()[i];
            } else {
                valuesNewSol[i] = indiv2.getNeuralNetwork()[i];
            }
        }
        newSol.setParameters(valuesNewSol);
        return newSol;
    }
    
    // Seleção de redes neuronais para servirem de pais no crossover
    private FeedforwardNeuralNetwork tournamentSelection(FeedforwardNeuralNetwork[] population) {
        // Cria uma população de torneio
    	FeedforwardNeuralNetwork[] tournament = new FeedforwardNeuralNetwork[TOURNAMENTSIZE];
        // Para cada lugar no torneio, obtem-se um indivíduo aleatório
        for (int i = 0; i < TOURNAMENTSIZE; i++) {
            int randomId = (int) (Math.random() * population.length);
            tournament[i] = population[randomId];
        }
        // Escolhe o que temmlhor fitness
        FeedforwardNeuralNetwork fittest = getFittest(tournament);
        return fittest;
    }
    
    
    public FeedforwardNeuralNetwork getFittest(FeedforwardNeuralNetwork[] population) {
    	FeedforwardNeuralNetwork fittest = population[0];
        // Procura o melhor fitness
        for (int i = 0; i < population.length; i++) {
            if (fittest.getFitness() <= population[i].getFitness()) {
                fittest = population[i];
            }
        }
        return fittest; // Retorna a rede com melhor fitness
    }

    public FeedforwardNeuralNetwork[] getTopFittest(FeedforwardNeuralNetwork[] population, int numElites) {
        FeedforwardNeuralNetwork[] fittest = new FeedforwardNeuralNetwork[numElites];
        
        // Ordena a população com base no fitness
        Arrays.sort(population, (a, b) -> Double.compare(b.getFitness(), a.getFitness()));

        // Seleciona as melhores redes neurais
        for (int i = 0; i < numElites; i++) {
            fittest[i] = population[i];
        }
        
        return fittest; // Retorna as melhores numElites redes neuronais
    }
   
    
    
}