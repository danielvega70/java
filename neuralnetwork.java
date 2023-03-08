import java.util.Random;

public class NeuralNetwork {

    private final int inputSize; // number of input neurons
    private final int hiddenSize; // number of hidden neurons
    private final int outputSize; // number of output neurons
    private final double learningRate; // learning rate
    private final double[][] weightsInputHidden; // weights from input to hidden layer
    private final double[][] weightsHiddenOutput; // weights from hidden to output layer

    // constructor to initialize the neural network
    public NeuralNetwork(int inputSize, int hiddenSize, int outputSize, double learningRate) {
        this.inputSize = inputSize;
        this.hiddenSize = hiddenSize;
        this.outputSize = outputSize;
        this.learningRate = learningRate;

        // initialize the weights randomly between -1 and 1
        this.weightsInputHidden = new double[inputSize][hiddenSize];
        Random rand = new Random();
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < hiddenSize; j++) {
                this.weightsInputHidden[i][j] = rand.nextDouble() * 2 - 1;
            }
        }

        this.weightsHiddenOutput = new double[hiddenSize][outputSize];
        for (int i = 0; i < hiddenSize; i++) {
            for (int j = 0; j < outputSize; j++) {
                this.weightsHiddenOutput[i][j] = rand.nextDouble() * 2 - 1;
            }
        }
    }

    // feedforward function to calculate the output of the neural network
    public double[] feedforward(double[] inputs) {
        double[] hidden = new double[hiddenSize];
        double[] output = new double[outputSize];

        // calculate the output of the hidden layer
        for (int i = 0; i < hiddenSize; i++) {
            double sum = 0;
            for (int j = 0; j < inputSize; j++) {
                sum += inputs[j] * weightsInputHidden[j][i];
            }
            hidden[i] = sigmoid(sum);
        }

        // calculate the output of the output layer
        for (int i = 0; i < outputSize; i++) {
            double sum = 0;
            for (int j = 0; j < hiddenSize; j++) {
                sum += hidden[j] * weightsHiddenOutput[j][i];
            }
            output[i] = sigmoid(sum);
        }

        return output;
    }

    // backpropagation function to train the neural network
    public void backpropagation(double[] inputs, double[] targets) {
        double[] hidden = new double[hiddenSize];
        double[] output = new double[outputSize];

        // calculate the output of the hidden layer
        for (int i = 0; i < hiddenSize; i++) {
            double sum = 0;
            for (int j = 0; j < inputSize; j++) {
                sum += inputs[j] * weightsInputHidden[j][i];
            }
            hidden[i] = sigmoid(sum);
        }

        // calculate the output of the output layer
        for (int i = 0; i < outputSize; i++) {
            double sum = 0;
            for (int j = 0; j < hiddenSize; j++) {
                sum += hidden[j] * weightsHiddenOutput[j][i];
            }
            output[i] = sigmoid(sum);
        }

        // calculate the error of the output layer
       
