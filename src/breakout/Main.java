package breakout;

import java.util.Random;

import utils.*;

	public class Main {

    public static void main(String[] args) {

    Random random = new Random(); // Inicializa o gerador de números aleatórios
        int seed = random.nextInt(); // Gera um número aleatório para a seed
int hiddenDim = GeneticAlgorithm.HIDDENDIM; // Tamanho do hidden layer

	// Cria uma população inicial de redes neurais
	FeedforwardNeuralNetwork[] population = new FeedforwardNeuralNetwork[GeneticAlgorithm.POPULATION_SIZE];
	for (int i = 0; i < GeneticAlgorithm.POPULATION_SIZE; i++) {
	population[i] = new FeedforwardNeuralNetwork(hiddenDim);
	}
	
	        // Inicia a evolução da população
	GeneticAlgorithm ga = new GeneticAlgorithm();
	for (int gen = 0; gen < GeneticAlgorithm.NUM_GENERATIONS; gen++) {
	System.out.println("Generation: " + (gen + 1));
	
	for (int i = 0; i < population.length; i++) {
	// Cria uma instância do jogo com a rede neural atual
	GameController nn = population[i];
	BreakoutBoard b = new BreakoutBoard(nn, false, seed);
	
	// Realiza a simulação sem interface gráfica
	b.runSimulation();
	
	// Obtem o fitness da rede neural
	double fitness = b.getFitness();
	population[i].setFitness(fitness);
	}
	
	System.out.println("Best Fitness: " + ga.getFittest(population).getFitness());
	System.out.println();
	
	// Evolui a população
	population = ga.evolvePopulation(population);
	}
	
	// Obtem o melhor indivíduo da última geração
	FeedforwardNeuralNetwork bestNetwork = ga.getFittest(population);
	System.out.print("Final Best Fitness: " + bestNetwork.getFitness());
	
	// Testa o melhor indivíduo com interface gráfica
	Breakout breakout = new Breakout(bestNetwork, seed);
	
	}
	    
	}