package breakout;

import java.util.Random;
import utils.*;

public class FeedforwardNeuralNetwork implements GameController {

	private int inputDim;
	private int hiddenDim;
	private int outputDim;
	private double[][] hiddenWeights;
	private double[] hiddenBiases;
	private double[][] outputWeights;
	private double[] outputBiases;
	private double fitness;
	
	public FeedforwardNeuralNetwork(int hiddenDim) {
		this.inputDim = Commons.BREAKOUT_STATE_SIZE;
		this.hiddenDim = hiddenDim;
		this.outputDim = Commons.BREAKOUT_NUM_ACTIONS;
		initializeParameters(); 
	}
	
	public FeedforwardNeuralNetwork(int hiddenDim, double[] values) {
		this.inputDim = Commons.BREAKOUT_STATE_SIZE;
        this.hiddenDim = hiddenDim;
        this.outputDim = Commons.BREAKOUT_NUM_ACTIONS;
        setParameters(values);
	}
	
	private void initializeParameters() {
        Random rand = new Random();
        hiddenWeights = new double[inputDim][hiddenDim];
        hiddenBiases = new double[hiddenDim];
        outputWeights = new double[hiddenDim][outputDim];
        outputBiases = new double[outputDim];

        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                hiddenWeights[i][j] = rand.nextDouble() * 0.005;
            }
        }

        for (int i = 0; i < hiddenDim; i++) {
            hiddenBiases[i] = rand.nextDouble() * 0.005;
        }

        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < outputDim; j++) {
                outputWeights[i][j] = rand.nextDouble() * 0.005;
            }
        }

        for (int i = 0; i < outputDim; i++) {
            outputBiases[i] = rand.nextDouble() * 0.005;
        }
    }
	
	public void setParameters(double[] values) {
        int index = 0;
        hiddenWeights = new double[inputDim][hiddenDim];
        hiddenBiases = new double[hiddenDim];
        outputWeights = new double[hiddenDim][outputDim];
        outputBiases = new double[outputDim];

        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                hiddenWeights[i][j] = values[index++];
            }
        }

        for (int i = 0; i < hiddenDim; i++) {
            hiddenBiases[i] = values[index++];
        }

        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < outputDim; j++) {
                outputWeights[i][j] = values[index++];
            }
        }

        for (int i = 0; i < outputDim; i++) {
            outputBiases[i] = values[index++];
        }
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
    
    public double[] forward(int[] inputValues) {
        double[] hiddenLayerOutput = new double[hiddenDim];
        double[] outputLayerOutput = new double[outputDim];

        // Calcula o output do hidden layer
        for (int i = 0; i < hiddenDim; i++) {
            double sum = 0.0;
            for (int j = 0; j < inputDim; j++) {
                sum += inputValues[j] * hiddenWeights[j][i];
            }
            sum += hiddenBiases[i];
            hiddenLayerOutput[i] = sigmoid(sum);
        }

        // Calcula o output do output layer
        for (int i = 0; i < outputDim; i++) {
            double sum = 0.0;
            for (int j = 0; j < hiddenDim; j++) {
                sum += hiddenLayerOutput[j] * outputWeights[j][i];
            }
            sum += outputBiases[i];
            outputLayerOutput[i] = sigmoid(sum);
        }

        return outputLayerOutput;
    }
    
    public double[] getNeuralNetwork() {
        int size = inputDim * hiddenDim + hiddenDim + hiddenDim * outputDim + outputDim;
        double[] networkParams = new double[size];
        int index = 0;

        for (int i = 0; i < inputDim; i++) {
            for (int j = 0; j < hiddenDim; j++) {
                networkParams[index++] = hiddenWeights[i][j];
            }
        }

        for (int i = 0; i < hiddenDim; i++) {
            networkParams[index++] = hiddenBiases[i];
        }

        for (int i = 0; i < hiddenDim; i++) {
            for (int j = 0; j < outputDim; j++) {
                networkParams[index++] = outputWeights[i][j];
            }
        }

        for (int i = 0; i < outputDim; i++) {
            networkParams[index++] = outputBiases[i];
        }

        return networkParams;
    }
    
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }
       
    @Override
    public String toString() {
		String result = "Neural Network: \nNumber of inputs: " 
				+ inputDim + "\n" + "Weights between input and hidden layer with " 
				+ hiddenDim + "neurons: \n";
		
		String hidden = "";
		for (int input = 0; input < inputDim; input++) {
			for (int i = 0; i < hiddenDim; i++) {
				hidden+= " w"+(input+1) + (i+1) +": " + hiddenWeights[input][i] + "\n";
			}
		}
		result += hidden;
		
		String biasHidden = "Hidden biases: \n";
		for (int i = 0; i < hiddenDim; i++) {
			biasHidden += " b "+(i+1)+": " + hiddenBiases[i] +"\n";
		}
		result+= biasHidden;
		
		String output = "Weights between hidden and output layer with " 
				+ outputDim +" neurons: \n";
		for (int hiddenw = 0; hiddenw < hiddenDim; hiddenw++) {
			for (int i = 0; i < outputDim; i++) {
				output+= " w"+(hiddenw+1) +"o"+(i+1)+": " + outputWeights[hiddenw][i] + "\n";
			}
		}
		result += output;
		
		String biasOutput = "Ouput biases: \n";
		for (int i = 0; i < outputDim; i++) {
			biasOutput += " bo"+(i+1)+": " + outputBiases[i] + "\n";
		}
		result+= biasOutput;
		
		return result;
    }
    
	@Override
	public int nextMove(int[] currentState) {
		// Verifica se o tamanho do currentState[] é igual ao inputDim
		if (currentState.length != inputDim) {
			throw new IllegalArgumentException("Tamanho incorreto do vetor de entrada.");
		}

		// Calcula os valores da saída da rede neuronal
		double[] outputLayerOutput = forward(currentState); 

		// Valor usado como comparação (soma dos outputs arredondado às centésimas)
		double comparisonValue = Math.round((outputLayerOutput[0] + outputLayerOutput[1]) * 100.0) / 100.0;
		
		// Determina a ação com base na saída da rede neural pela condição descrita abaixo
		if (outputLayerOutput[0] + outputLayerOutput[1] < comparisonValue) {
			return BreakoutBoard.LEFT; // Movimento para a esquerda
		} else {
			return BreakoutBoard.RIGHT; // Movimento para a direita
		}
	}
    
    

 
}