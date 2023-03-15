import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class NeuralNetworkExample {

    public static void main(String[] args) throws Exception {

        // Set the seed for reproducibility
        int seed = 123;
        double learningRate = 0.05;

        // Number of input features
        int numInput = 784;

        // Number of output classes
        int numClasses = 10;

        // Number of hidden nodes
        int numHiddenNodes = 500;

        // Number of training epochs
        int numEpochs = 15;

        // Load the MNIST dataset
        DataSetIterator mnistTrain = new MnistDataSetIterator(64, true, seed);
        DataSetIterator mnistTest = new MnistDataSetIterator(64, false, seed);

        // Define the neural network configuration
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(seed)
            .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
            .iterations(1)
            .learningRate(learningRate)
            .updater(Updater.NESTEROVS)
            .momentum(0.9)
            .regularization(true)
            .l2(1e-4)
            .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
            .gradientNormalizationThreshold(1.0)
            .list()
            .layer(0, new DenseLayer.Builder()
                .nIn(numInput)
                .nOut(numHiddenNodes)
                .weightInit(WeightInit.XAVIER)
                .activation(Activation.RELU)
                .build())
            .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                .nIn(numHiddenNodes)
                .nOut(numClasses)
                .weightInit(WeightInit.XAVIER)
                .activation(Activation.SOFTMAX)
                .build())
            .pretrain(false)
            .backprop(true)
            .build();

        // Create the neural network model
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(10));

        // Train the model
        for (int i = 0; i < numEpochs; i++) {
            while (mnistTrain.hasNext()) {
                DataSet dataSet = mnistTrain.next();
                model.fit(dataSet);
            }
            mnistTrain.reset();
        }

        // Test the model
        int numCorrect = 0;
        int numTotal = 0;
        while (mnistTest.hasNext()) {
            DataSet
