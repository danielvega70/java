import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Perceptron;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

public class BanknoteClassifier {

    public static void main(String[] args) throws Exception {

        // Load data from file
        BufferedReader reader = new BufferedReader(new FileReader("banknotes.arff"));
        Instances data = new Instances(reader);
        data.setClassIndex(data.numAttributes() - 1);

        // Separate data into training and testing groups
        int numInstances = data.numInstances();
        int numTraining = (int) Math.round(numInstances * 0.6);
        int numTesting = numInstances - numTraining;
        data.randomize(new java.util.Random(0));
        Instances training = new Instances(data, 0, numTraining);
        Instances testing = new Instances(data, numTraining, numTesting);

        // Train classifier on training set
        Classifier classifier = new NaiveBayes();
        classifier.buildClassifier(training);

        // Evaluate classifier on testing set
        Evaluation evaluation = new Evaluation(training);
        evaluation.evaluateModel(classifier, testing);

        // Print results
        System.out.println("Results for classifier " + classifier.getClass().getSimpleName());
        System.out.println("Correct: " + (int) evaluation.correct());
        System.out.println("Incorrect: " + (int) evaluation.incorrect());
        System.out.println("Accuracy: " + evaluation.pctCorrect() + "%");

    }
}
