package network;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class RosenbrockGenerator {
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

		/*//for all the input values, set total = 0 and run through all the inputs
		double inputIncrement = .01;
		double firstNumber = -(numDataPoints*inputIncrement)/4;
		for (int i = 0; i < numDataPoints/2; i++) {
			double inputs[] = new double[numInputs];
			for(int j = 0; j < numInputs; j++){
				inputs[j] = j*inputIncrement + firstNumber;
				writer.printf("%.2f,", inputs[j]);
			}

			double output = calculate(inputs);
			samples.add(new Sample(inputs, output));			//create new sample based on that array

			//after each set of calculations (each output generated) print the results
			writer.println(output);

			for(int j = 0; j < numInputs; j++){
				inputs[j] = (numInputs-j-1)*inputIncrement + firstNumber;
				writer.printf("%.2f,", inputs[j]);
			}

			output = calculate(inputs);
			samples.add(new Sample(inputs, output));			//create new sample based on that array

			//after each set of calculations (each output generated) print the results
			writer.println(output);

			firstNumber+=inputIncrement;
		}
		writer.close();
*/
		double maxOutput = Double.MIN_VALUE;
		double minOutput = Double.MAX_VALUE;
		for(int i = 0; i < numDataPoints; i++){
			double inputs[] = new double[numInputs];
			for(int j = 0; j < numInputs; j++){
				inputs[j] = (random.nextDouble()*10)-5;
				writer.print(inputs[j] + ",");
			}
			double output = calculate(inputs);
			if(output > maxOutput)
				maxOutput = output;
			if(output < minOutput)
				minOutput = output;
			writer.println(output);
			samples.add(new Sample(inputs, output));
		}

		System.out.println("Range of Data: " + (maxOutput-minOutput));
		writer.close();

		return samples;
	}

	private double calculate(double[] inputs){
		double output = 0;
		for(int i = 0; i < inputs.length-1; i++){
			output += (Math.pow((1 - inputs[i]), 2) + (100 * Math.pow(inputs[i+1] - Math.pow(inputs[i], 2), 2)));	//Rosenbrock function
		}
		return output;
		//return Math.sin(inputs[0]);
	}
}
