import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int choice;
        String fileName = "D:\\weights.data";
        Scanner scanner = new Scanner(System.in);
        NeuralNetwork NeuralNetwork = new NeuralNetwork();

        System.out.println("1. Learn the network");
        System.out.println("2. Guess a number");
        System.out.print("Your choice: ");

        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                NeuralNetwork.learning(200);
                SerializationUtils.serialize(NeuralNetwork, fileName);
                break;
            case 2:
                NeuralNetwork = (NeuralNetwork) SerializationUtils.deserialize(fileName);
                NeuralNetwork.setInputNeurons(readInput());
                NeuralNetwork.guessNumber();
                break;
            default:

        }
    }

    private static double[] readInput() {
        double[] a = new double[15];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input grid:");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(scanner.nextLine().replace("\n", ""));
        }
        String input = builder.toString();
        for (int i = 0; i < 15; i++) {
            if (input.charAt(i) == 'X') {
                a[i] = 1;
            } else {
                a[i] = 0;
            }
        }
        return a;
    }

}