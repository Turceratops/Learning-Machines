/**
* @author Turceratops
* @version .0.1
* @since 9/8/2018
* Generates a desired amount of test cases for two half-moons
* that may or may not be linearly seperable depending on the distance
* between them. The moons are asymetrical. 
**/
import java.util.Random;
import java.io.*;

public class Half_Moon_Gen{
    Random rand;
    String file_name;

    /**
     * Constructor that delcares how many samples are requested
     * the radius, width and distance between the halfmoons
     * @param test_size user-defined test case sizes
     * @param radius must be positive
     * @param width must be positive
     * @param distance distance must be between [-radius, radius]
     */
    public Half_Moon_Gen(int test_size, int radius, int width, int distance){ 
        file_name = "Half_moon_gen.csv";
        rand = new Random();
        rand.setSeed(0);
        generate_file(test_size, radius, width, distance);
    }

    /**
     * Constructor that delcares how many samples are requested
     * the radius, width and distance between the halfmoons
     * and the name of the output file
     * @param test_size user-defined test case sizes
     * @param radius must be positive
     * @param width must be positive
     * @param distance distance must be between [-radius, radius]
     * @param name the name of the output file without ".txt"
     */
    public Half_Moon_Gen(int test_size, int radius, int width, int distance, String name){ 
        file_name = name + ".csv";
        rand = new Random();
        rand.setSeed(0);
        generate_file(test_size, radius, width, distance);
    }

    /***
     * Generates the file based on the desired name 
     * and the user-defined dimenstions of the half moons 
     * @param test_size
     * @param radius
     * @param width
     * @param distance
     */
    private void generate_file(int test_size, int radius, int width, int distance){
        boolean bottom = true;
        
        //create new file 
        try{
            File half_moon = new File(file_name);
            FileWriter fw = new FileWriter(half_moon, false);
            
            for(int i = 0; i < test_size; i++){
                fw.write(generate_point( radius, width, bottom, distance) + "\n");
                bottom = !bottom;
            }
            fw.close();
        }catch(IOException e){
            System.out.println("Exception at half-moon sample generation:");
            e.printStackTrace();
        }

    }

    /***
     * Generates an individual point
     * @param rand Random object
     * @param radius radius of the halfmoon
     * @param width width of the halfmoon
     */
    private String generate_point(int radius, int width, boolean bottom, int distance){
        int y = 0;
        //generate x - between [-r, r]
        int x = rand.nextInt( radius * 2 ) - radius;
        //generate random width 
        width = rand.nextInt(  width * 2 ) - width;
  
        //top half 
        y = (int)Math.sqrt(radius * radius - x * x) + width;
        //add the desired distance between the values - should be half
        y += distance / 2;

        //bottom half
        if(bottom){
            x += radius / 2;
            y = y * -1;
        }  
        
        //add to the file, with a mark letting you know if it's
        //top or bottom of the 
        return bottom ?  x + ", " + y + ", -1" : x + ", " + y + ", 1"; 

    }
}