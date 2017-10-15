package network;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/* This class generates data for the Rosenbrock function and writes it
 * to a file. The user can choose to read in the data from that file or 
 * create a new dataset using this class.
 */
public class RosenbrockGenerator {
	
	/*	Create a new instance of the RosenbrockGenerator
	 * 	@param numInputs - the number of inputs/dimensions for the Rosenbrock function
	 *  @param numDataPoints - the number of data points the user wants to generate
	 */
	public ArrayList<Sample> generate(int numInputs, int numDataPoints){
		Random random = new Random();
		ArrayList<Sample> samples = new ArrayList<Sample>();

		File outfile = null;						//create output file
		PrintWriter writer = null;
		try {
			outfile = new File("data.txt");
			writer = new PrintWriter(outfile, "UTF-8");

		} catch (Exception e) {
			System.out.println("Something went wrong generating the output file.");
		}
		
		double maxOutput = Double.MIN_VALUE;
		double minOutput = Double.MAX_VALUE;
		for(int i = 0; i < numDataPoints; i++){								//for all the data points desired,
			double inputs[] = new double[numInputs];						//create a new input array
			for(int j = 0; j < numInputs; j++){								//for that input array, loop through and
				inputs[j] = (random.nextDouble()*6)-3;						//generate a random double as the input(s)
				writer.print(inputs[j] + ",");								//print that to the file,
			}
			double output = calculate(inputs);								//then calculate the output using the input array
			if(output > maxOutput)											//track the min and the max outputs for range of the data
				maxOutput = output;
			if(output < minOutput)
				minOutput = output;
			writer.println(output);
			samples.add(new Sample(inputs, output));						//create a data sample from the information
		}

		System.out.println("Range of Data: " + (maxOutput-minOutput));
		writer.close();

		return samples;														//return the list of samples
	}

	//calculates the output based on the inputs read in
	//@param inputs - the array of inputs based on dimension (if 3 dimensions, 3 inputs, etc.)
	private double calculate(double[] inputs){									
		double output = 0;
		for(int i = 0; i < inputs.length-1; i++){
			output += (Math.pow((1 - inputs[i]), 2) + (100 * Math.pow(inputs[i+1] - Math.pow(inputs[i], 2), 2)));	//Rosenbrock function
		}
		return output;
		//return Math.sin(inputs[0]);										//if we want to test using the sine function, this is here as well
	}
}
