import java.io.Serializable;
import java.util.Random;

class NeuralNetwork implements Serializable {

    private static final long serialVersionUID = 3594958720970505521L;

    private double[][] idealNeurons;
    private double[] bias;
    private double[][] weight;
    private double[][] trainingInputNeurons;
    private double[] outputNeuron;
    private final int OUTPUT_NEURONS = 10;
    private final int NEURON_WEIGHTS = 15;
    private double[] inputNeurons;

    public NeuralNetwork() {
        idealNeurons = new double[OUTPUT_NEURONS][OUTPUT_NEURONS];
        trainingInputNeurons = new double[NEURON_WEIGHTS][];
        weight = new double[OUTPUT_NEURONS][NEURON_WEIGHTS];
        bias = new double[10];
        Random random = new Random();

        for (int i = 0; i < OUTPUT_NEURONS; i++) {
            idealNeurons[i][i] = 1;
            for (int j = 0; j < NEURON_WEIGHTS; j++) {
                weight[i][j] = random.nextGaussian();
            }
        }

        trainingInputNeurons[0] = new double[]{
                1, 1, 1,
                1, 0, 1,
                1, 0, 1,
                1, 0, 1,
                1, 1, 1,};

        trainingInputNeurons[1] = new double[]{
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,};

        trainingInputNeurons[2] = new double[]{
                1, 1, 1,
                0, 0, 1,
                1, 1, 1,
                1, 0, 0,
                1, 1, 1};
        trainingInputNeurons[3] = new double[]{
                1, 1, 1,
                0, 0, 1,
                1, 1, 1,
                0, 0, 1,
                1, 1, 1};

        trainingInputNeurons[4] = new double[]{
                1, 0, 1,
                1, 0, 1,
                1, 1, 1,
                0, 0, 1,
                0, 0, 1};

        trainingInputNeurons[5] = new double[]{
                1, 1, 1,
                1, 0, 0,
                1, 1, 1,
                0, 0, 1,
                1, 1, 1};

        trainingInputNeurons[6] = new double[]{
                1, 1, 1,
                1, 0, 0,
                1, 1, 1,
                1, 0, 1,
                1, 1, 1};

        trainingInputNeurons[7] = new double[]{
                1, 1, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1};

        trainingInputNeurons[8] = new double[]{
                1, 1, 1,
                1, 0, 1,
                1, 1, 1,
                1, 0, 1,
                1, 1, 1};

        trainingInputNeurons[9] = new double[]{
                1, 1, 1,
                1, 0, 1,
                1, 1, 1,
                0, 0, 1,
                1, 1, 1};

    }

    void setInputNeurons(double[] inputNeurons) {
        this.inputNeurons = inputNeurons;
    }

    void guessNumber() {
        double max = Double.MIN_VALUE;
        int indexMax = 0;
        outputNeuron = calcOutNeuron(inputNeurons);
        for (int i = 0; i < outputNeuron.length; i++) {
            if (outputNeuron[i] > max) {
                max = outputNeuron[i];
                indexMax = i;
            }
        }
        System.out.print("This number is " + indexMax);
    }

    private double[] calcOutNeuron(double[] inputNeuron) {
        double[] outputNeuron = new double[OUTPUT_NEURONS];
        for (int i = 0; i < OUTPUT_NEURONS; i++) {
            for (int j = 0; j < NEURON_WEIGHTS; j++) {
                outputNeuron[i] += weight[i][j] * inputNeuron[j] + bias[i];
            }
            outputNeuron[i] = sigmoid(outputNeuron[i]);
        }
        return outputNeuron;
    }

    void learning(int generations) {
        System.out.println("Learning...");
        for (int g = 0; g < generations; g++) {
            double[][] deltaWeight;
            deltaWeight = new double[OUTPUT_NEURONS][NEURON_WEIGHTS];
            double[][] meanDelta = new double[OUTPUT_NEURONS][NEURON_WEIGHTS];

            for (int k = 0; k < OUTPUT_NEURONS; k++) {
                outputNeuron = calcOutNeuron(trainingInputNeurons[k]);
                for (int i = 0; i < OUTPUT_NEURONS; i++) {
                    for (int j = 0; j < NEURON_WEIGHTS; j++) {
                        double learningRate = 0.5;
                        deltaWeight[i][j] = learningRate * trainingInputNeurons[k][j]
                                * (idealNeurons[k][i] - outputNeuron[i]);
                        meanDelta[i][j] += deltaWeight[i][j] / OUTPUT_NEURONS;
                    }
                }
            }
            updateWeight(meanDelta);
        }

        System.out.print("Done! Saved to the file.");
    }

    private void updateWeight(double[][] meanDelta) {
        for (int i = 0; i < OUTPUT_NEURONS; i++) {
            for (int j = 0; j < NEURON_WEIGHTS; j++) {
                weight[i][j] += meanDelta[i][j];
            }
        }
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E, -x));
    }
}
