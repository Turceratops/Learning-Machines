/**
* @author Turceratops
* @version .0.1
* @since 8/7/2018
* A single perceptron using the Rossenblat model built
* off of the McCulloch-Pitts model of a neuron uses the 
* signum function as it's activation function, the perceptron
* only discerns between two classes
**/
import java.util.Random;

public class perceptron{

    //global variables
    private double[] input;
    private double[] weights;
    private double output; 
    //eta is the learning rate for the perceptron
    private double eta = .5; 

    /**
     * Declares a new perceptron with the input size of passed parameter
     * uses the default value of eta which is 0.5 
     * @param input_size the desired size of the input signal
     */
    public perceptron(int input_size) {
        input = new double[input_size + 1];
        weights = new double[input_size + 1];
        //set bias input
        input[input_size] = 1.0; 
        
        init_weights();
    }

    /**
     * Declares a new perceptron with the input size of passed parameter
     * and a user decided learning rate that must be [-1, 1], that is 
     * equal or between the values -1 and 1 
     * @param input_size the desired size of the input signal
     * @param learning_rate desired user learning rate between or equal to -1 and 1
     */
    public perceptron(int input_size, double learning_rate){
        this(input_size);
        if(learning_rate > 1 || learning_rate < 0)
            throw new IllegalArgumentException("The learning rate must be between or equal to -1 and 1");
        eta = learning_rate; 
    }

    /**
     *Takes in an input signal and calculates the output
     * @return a double value between 1 and -1 signaling the perceptron guess 
     * of classification
     */
    public double feedForward(double[] input_to_feed){
        System.arraycopy(input_to_feed, 0, input, 0, input_to_feed.length);
        //input = input_to_feed; 
        output = 0.0; 
        //sum of the weights and input
        for(int i = 0; i < input.length; i++){
            output += input[i] * weights[i];
        }

        //calculate activation function
        return out(); 
    }

    /**
     * Takes in an error signal and feeds it back to correct the weights
     * in the perceptron
     * NOTE: Error in this case is (desired - output), where desired is +1 or -1
     * relative to which ever is the desired class
     */
    public void error_signal(double desired, double out){
       
        //calculates the new value for each weight
        for(int i = 0; i < weights.length; i++){
           weights[i] = weights[i] + eta * (desired - out) *input[i];  
        }
    }

    /**
     * Returns the resulting output of a feed forward action
     * uses the signum activation function
     * @return Returns the result of a feed forward action
     */
    public double out(){
        //signum function
        if(output > 0)
            return 1;
        else
            return -1; 
    }

    /**
     * Initalizes all the weights in the perceptron with a random double 
     * between 0 and 1 thats whithin the normal distribution
     */
    private void init_weights (){
        for(int i = 0; i < weights.length; i++){
            weights[i] = rand_distribution(); 
        }
    }

    /**
     * A helper method that ensures the random number generated is within the 
     * normal distribution 
     * @return a random number within the normal distribution
     */
    private double rand_distribution(){
        Random rand = new Random();
        double out = 0;
        for(int i = 0; i < 12; i++){
            out += rand.nextDouble();
        }
        return out / 12.0;
    }

}