import java.util.Scanner;
import java.io.File;

/**
* @author Turceratops
* @version .0.1
* @since 8/13/2018
* Class that demonstrates the usage of the perceptron
* as well as the usage of the test_generator class.
* Mostly exists as an example of how to use each class, and for 
* testing.
*/

public class main_test{

    public static void main(String args[]){
        //data traning information
        int epoch = 1000;
        int training_size = 1000;
        int testing_size = 50;
        //perceptrons set with super parameters
        perceptron perc_gate = new perceptron(2, .001);
        perceptron perc_line = new perceptron(2, .001);

        //gate set to AND
        int gate_type = 0;
        
        //line infromation 
        double m = .5;
        double b = 0;

        //trains respective perceptron
        train_gates(epoch, training_size, testing_size, gate_type, perc_gate);
        train_line(epoch, training_size, testing_size, m, b, perc_line);
    }

    /**
    * Trains an inputted perceptron to classify data from two different sides of a 
    * pre-determined line. Trains perceptron for an epoch the size of traning_size
    * then tests accuracy with a different set of testing variables at the size of testing_size.
    * Uses online learning techinque.
    * @param epoch size of the epoch for traning
    * @param training_size size of the desired traning sample
    * @param testing_size size of the desired testing sample
    * @param m slope of the desired line to train
    * @param b y-intercept of the desired line to train
    * @param perc perceptron to train 
    */
    private static void train_line(int epoch, int training_size, int testing_size, double m, double b, perceptron perc){
        double[][] training_sample = file_to_arr_line(training_size, m, b);
        double[][] test_sample = file_to_arr_line(testing_size, m, b);
        //online test
        double accuracy = train_and_test_online(epoch, training_sample, test_sample, perc);   
        System.out.println("Linear testing accuracy: " + accuracy);
    }

    /**
    * Trains an inputted perceptron to classify data from a gate to be mimicked. 
    * Trains perceptron for an epoch the size of traning_size then tests accuracy 
    * with a different set of testing variables at the size of testing_size.
    * Uses online learning techinque.
    * @param epoch size of the epoch for traning
    * @param training_size size of the desired traning sample
    * @param testing_size size of the desired testing sample
    * @param gate_type the desired gate to be mimicked
    * @param perc perceptron to train
    */
    private static void train_gates(int epoch, int training_size, int testing_size, int gate_type,  perceptron perc){
        double[][] training_sample = file_to_arr_gate(training_size, gate_type);
        double[][] test_sample = file_to_arr_gate(testing_size, gate_type);
        //online test
        double accuracy = train_and_test_online(epoch, training_sample, test_sample, perc); 
        System.out.println("Gate testing accuracy: " + accuracy);
    }

    /**
     * Trains a perceptron and tests them using traning and testing samples
     * trains perceptron for the size of the epoch inputted. Uses online learning.
     * @param epoch size of epoch
     * @param traning_sample 2d array for traning purposes
     * @param test_sample 2d array for testing purposes
     * @param perc percpetron to train
     */
    private static double train_and_test_online(int epoch, double[][] training_sample, double[][] test_sample, perceptron perc){
        double accuracy = 0.0;
        //train for epoch length
        while(epoch >= 0){
            train_online(perc, training_sample);
            epoch--;
        }
        //test accuracy with testing values
        for(int i = 0; i < test_sample.length; i++){
            double out = perc.feedForward(new double[] {test_sample[i][0], test_sample[i][1]});
            if(test_sample[i][2] - out == 0) accuracy++;
        }
        //calculate accuracy
        accuracy /= test_sample.length;
        return accuracy;
    }

    /**
     * Trains an inputted perceptron using online learning
     * feeds forward then error signals the perceptron for each 
     * individual traing set.
     * @param perc perceptron being trained
     * @param train traning set
     */
    private static void train_online(perceptron perc, double[][] train){
        for(int i = 0; i < train.length; i++){
            double out = perc.feedForward(new double[] {train[i][0], train[i][1]});
            perc.error_signal(train[i][2], out);
        }

    }

     /**
     * Uses test_generator in order to create a 2d array of linear
     * samples
     * @param perc perceptron being trained
     * @param m desired slope of the line
     * @param b desired y-intercept of the line
     */
    private static double[][] file_to_arr_line(int sample_size, double m, double b){
        test_generator gen = new test_generator(sample_size, m, b);
        double input[][] = new double[sample_size][3];
        Scanner scnr;
        try{
            scnr = new Scanner(new File(gen.file_name));
            for(int i = 0; i < sample_size; i++){
                input[i][0] = scnr.nextDouble();
                input[i][1] = scnr.nextDouble();
                input[i][2] = scnr.nextInt();
            }
            scnr.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return input;
    }

     /**
     * Uses test_generator in order to create a 2d array of gate cases and 
     * desired outputs e.g. (on an AND case) true true 1 ; true false -1 
     * @param sample_size desired sample size
     * @param gate_type desired gate to mimick 
     */
    private static double[][] file_to_arr_gate( int sample_size, int gate_type){
        test_generator gen = new test_generator(sample_size, gate_type);
        double input[][] = new double[sample_size][3];
        Scanner scnr;
        try{
            scnr = new Scanner(new File(gen.file_name));
            for(int i = 0; i < sample_size; i++){
                input[i][0] = scnr.nextBoolean() ? 1.0 : 0.0 ;
                input[i][1] = scnr.nextBoolean() ? 1.0 : 0.0;
                input[i][2] = scnr.nextInt();
            }
            scnr.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return input;
    }
}