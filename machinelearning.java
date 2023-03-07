import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.analysis.function.Sigmoid;

public class Main {

    public static void main(String[] args) {

        // Define the training data
        double[][] inputs =
        {
            new double[] { 0, 0 },
            new double[] { 0, 1 },
            new double[] { 1, 0 },
            new double[] { 1, 1 }
        };

        int[] outputs = { 0, 1, 1, 0 };

        // Create the machine learning model
        Sigmoid sigmoid = new Sigmoid();
        BackPropagationTrainer trainer = new BackPropagationTrainer();
        trainer.setActivationFunction(sigmoid);
        NeuralNetwork network = new NeuralNetwork(2, 2, 1);
        trainer.train(network, inputs, outputs);

        // Use the model to make predictions
        double[] prediction = network.compute(new double[] { 0, 1 });

        // Output the prediction
        System.out.println(prediction[0]);
    }
}

class BackPropagationTrainer {

    private double learningRate = 0.1;
    private double momentum = 0.0;
    private Sigmoid activationFunction;

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }

    public void setActivationFunction(Sigmoid activationFunction) {
        this.activationFunction = activationFunction;
    }

    public void train(NeuralNetwork network, double[][] inputs, int[] outputs) {

        int numInputs = inputs[0].length;
        int numOutputs = 1;

        int numHiddenLayers = network.getNumHiddenLayers();
        int[] layerSizes = network.getLayerSizes();

        RealMatrix[] weights = network.getWeights();
        RealVector[] biases = network.getBiases();

        int numExamples = inputs.length;

        // Convert inputs and outputs to matrices
        RealMatrix inputMatrix = MatrixUtils.createRealMatrix(inputs);
        RealMatrix outputMatrix = MatrixUtils.createRealMatrix(numExamples, numOutputs);
        for (int i = 0; i < numExamples; i++) {
            outputMatrix.setEntry(i, 0, outputs[i]);
        }

        // Initialize delta arrays for weights and biases
        RealMatrix[] deltaWeights = new RealMatrix[numHiddenLayers + 1];
        RealVector[] deltaBiases = new RealVector[numHiddenLayers + 1];
        for (int i = 0; i < numHiddenLayers + 1; i++) {
            deltaWeights[i] = MatrixUtils.createRealMatrix(layerSizes[i + 1], layerSizes[i]);
            deltaBiases[i] = new ArrayRealVector(layerSizes[i + 1]);
        }

        // Initialize previous delta arrays for weights and biases
        RealMatrix[] prevDeltaWeights = new RealMatrix[numHiddenLayers + 1];
        RealVector[] prevDeltaBiases = new RealVector[numHiddenLayers + 1];
        for (int i = 0; i < numHiddenLayers + 1; i++) {
            prevDeltaWeights[i] = MatrixUtils.createRealMatrix(layerSizes[i + 1], layerSizes[i]);
            prevDeltaBiases[i] = new ArrayRealVector(layerSizes[i + 1]);
        }

        // Train the network
        for (int epoch = 0; epoch < 1000; epoch++) {

            // Compute
