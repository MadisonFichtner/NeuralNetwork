package network;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RosenbrockGenerator {
	public ArrayList<Sample> generate(int numInputs, int numDataPoints){
		ArrayList<Sample> samples = new ArrayList<Sample>();

		File outfile = null;						//create output file
		PrintWriter writer = null;
		try {
			outfile = new File("data.txt");
			writer = new PrintWriter(outfile, "UTF-8");

		} catch (Exception e) {
			System.out.println("Something went wrong generating the output file.");
		}

		//for all the input values, set total = 0 and run through all the inputs
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

		return samples;
	}

	private double calculate(double[] inputs){
		double output = 0;
		for(int i = 0; i < inputs.length-1; i++){
			output += (Math.pow((1 - inputs[i]), 2) + (100 * Math.pow(inputs[i+1] - Math.pow(inputs[i], 2), 2)));	//Rosenbrock function
		}
		return output;
	}
}
