import java.util.*;

public class MDP {
    private int numStates;
    private int numActions;
    private double[][] rewards;
    private double[][][] transitions;
    private double[][] qValues;
    private double learningRate;
    private double discountFactor;

    public MDP(int numStates, int numActions, double[][] rewards, double[][][] transitions,
               double learningRate, double discountFactor) {
        this.numStates = numStates;
        this.numActions = numActions;
        this.rewards = rewards;
        this.transitions = transitions;
        this.qValues = new double[numStates][numActions];
        this.learningRate = learningRate;
        this.discountFactor = discountFactor;
    }

    public int getAction(int state) {
        Random rand = new Random();
        if (rand.nextDouble() < 0.1) {
            return rand.nextInt(numActions);
        } else {
            double maxQValue = Double.NEGATIVE_INFINITY;
            int maxAction = 0;
            for (int action = 0; action < numActions; action++) {
                double qValue = qValues[state][action];
                if (qValue > maxQValue) {
                    maxQValue = qValue;
                    maxAction = action;
                }
            }
            return maxAction;
        }
    }

    public void update(int state, int action, int nextState, double reward) {
        double maxQValue = Double.NEGATIVE_INFINITY;
        for (int nextAction = 0; nextAction < numActions; nextAction++) {
            double qValue = qValues[nextState][nextAction];
            if (qValue > maxQValue) {
                maxQValue = qValue;
            }
        }
        qValues[state][action] += learningRate * (reward + discountFactor * maxQValue - qValues[state][action]);
    }

    public void train(int numEpisodes) {
        for (int episode = 0; episode < numEpisodes; episode++) {
            int state = 0;
            while (state != numStates - 1) {
                int action = getAction(state);
                double rand = Math.random();
                double sum = 0;
                int nextState = 0;
                for (nextState = 0; nextState < numStates; nextState++) {
                    sum += transitions[state][action][nextState];
                    if (sum > rand) {
                        break;
                    }
                }
                double reward = rewards[state][action];
                update(state, action, nextState, reward);
                state = nextState;
            }
        }
    }

    public static void main(String[] args) {
        // Define the MDP
        int numStates = 3;
        int numActions = 2;
        double[][] rewards = {{0, 1}, {0, 0}, {0, 0}};
        double[][][] transitions = {{{0.5, 0.5, 0}, {0, 0, 1}}, {{1, 0, 0}, {0.5, 0, 0.5}}, {{0, 1, 0}, {0, 1, 0}}};
        double learningRate = 0.5;
        double discountFactor = 0.9;
        MDP mdp = new MDP(numStates, numActions, rewards, transitions, learningRate, discountFactor);

        // Train the MDP
        mdp.train(1000);

        // Print the learned Q-values
        System.out.println("Learned Q-values:");
        for (int state =
