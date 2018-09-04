/**
* @author Turceratops
* @version .0.2
* @since 8/9/2018
* Generates a desired amount of test cases for the purpose of
* testing and traning a rossenblat perceptron, since the perceptron
* can only identify two classes, so this generator 
* creates two cases based on a line and also simulates 
* logic gates based on the constructor 
**/
import java.util.Random;
import java.io.*;

public class test_generator{
    String file_name = "Samples.txt";
    /**
     * Constructor that delcares how many samples are requested
     * based entirely on a user-defined line which is declared 
     * using the y = mx + b line equation, dosen't do vertical lines
     * @param test_size user-defined test case sizes
     * @param m is the slope of the desired line
     * @param b is the y-intercept of the line
     */
    public test_generator(int test_size, double m, double b){
        Random rand = new Random();
        //create new file
        try{
            File samples = new File(file_name);
            FileWriter fw = new FileWriter(samples, false);
            if(samples.createNewFile()) System.out.println("Samples file has been created");
           
            boolean class_type = rand.nextBoolean();
            //add sample to file
            for(int i = 0; i < test_size; i++){
                fw.write(sample_generator(class_type, m, b, rand) + "\n"); 
                class_type = rand.nextBoolean();
            }
            fw.close();
        }catch(IOException e){
            System.out.println("Exception Occured at sample generation:");
            e.printStackTrace();
        }
        
    }

    /**
     * Constructor for logic gates generation, uses a number
     * between 0-2 to generate the following logic gates:
     * 0 - AND, 1 - OR, 2 - XOR 
     * @param test_size user-defined test case size
     * @param gate_type is the desired gate
     */
    public test_generator(int test_size, int gate_type){
        if(gate_type < 0 || gate_type > 2) throw new java.lang.RuntimeException("Gate type range between [0 and 2]"); 
        Random rand = new Random();
        try{
            File samples = new File(file_name);
            FileWriter fw = new FileWriter(samples, false);
            if(samples.createNewFile()) System.out.println("Samples file has been created");

            //add sample to file
            for(int i = 0; i < test_size; i++){
                fw.write(sample_generator(gate_type, rand) + "\n"); 
            }
            fw.close();
        }catch(IOException e){
            System.out.println("Exception Occured at sample generation:");
            e.printStackTrace();
        } 
    }

    /**
     * Depending on the gate parameter this method creates  
     * a test sample based on the desired gate
     * @param gate_type a boolean that determines the class type
     * @param rand is the global Random variable 
     */
    private String sample_generator(int gate_type, Random rand){
        //generate the two inputs
        boolean inputs[] = new boolean[] {rand.nextBoolean(), rand.nextBoolean()};
        switch (gate_type){
            //and case
            case 0: 
            if(inputs[0] && inputs[1]) return inputs[0] + " " + inputs[1] + " 1";
            else return inputs[0] + " " + inputs[1] + " -1";
            //or case
            case 1:
            if(inputs[0] || inputs[1]) return inputs[0] + " " + inputs[1] + " 1";
            else return inputs[0] + " " + inputs[1] + " -1";
            //xor case
            case 2:
            if(inputs[0] ^ inputs[1]) return inputs[0] + " " + inputs[1] + " 1";
            else return inputs[0] + " " + inputs[1] + " -1";
            //error case
            default:
                return "ERROR at gate selection at sample_generator"; 
        }
    }

    /**
     * Depending on the class parameter this method creates either 
     * a test sample from one class or another
     * @param class_type a boolean that determines the class type
     * @param m user-defined slope of the line
     * @param b is the y-intercept of the line
     */
    private String sample_generator(boolean class_type, double m, double b, Random rand){
        //generate number on the line
        double x = rand.nextDouble() * 10000;
        double y = x * m + b;
            
        if(class_type){
            //above the line
            y += rand.nextDouble() * 10000;
            return x + " " + y + " -1";  
        }else{
            //below the line
            y -= rand.nextDouble() * 10000;
            return x + " " + y + " 1";
        }
    }  
}
