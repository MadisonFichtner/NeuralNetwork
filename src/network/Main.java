package network;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);																	//create scanner for input
		boolean valid = true;																					//flag variable for correct user input
		int input = 0, numInputs = 0, numDataPoints = 0;														//numInputs here is #of input nodes; input is user choice
		Network network = null;

		//opening menu - prompt user to create a new data set
		do {
			valid = true;												//assume input is valid
			try {
				System.out.println("Would you like to create a new data set?\n1. Yes\n2. No");
				System.out.print(">");
				input = in.nextInt();									//attempt to read integer input for choice
			}
			catch (Exception e) {										//if that fails
				System.out.println("That is not an integer. Please enter an integer.\n");
				valid = false;											//input is invalid, flag
				in.nextLine();											//clear input buffer
			}
		} while (valid == false);										//repeat if bad choice

		ArrayList<Sample> samples = new ArrayList<Sample>();			//create list of samples to use - data set essentially

		//entering the dimensions - if the user chooses to create new data set
		if (input == 1) {
			do {
				valid = true;
				try {
					System.out.println("Please enter the number of dimensions for the Rosenbrock function:");
					numInputs = in.nextInt();
				}
				catch (Exception e) {
					System.out.println("That is not an integer. Please enter an integer.\n");
					valid = false;
					in.nextLine();
				}
			} while (valid == false);

			//enter data points (or inputs)
			do {
				valid = true;
				try {
					System.out.println("Please enter the number of data points you want:");
					numDataPoints = in.nextInt();
				}
				catch (Exception e) {
					System.out.println("That is not an integer. Please enter an integer.\n");
					valid = false;
					in.nextLine();
				}
			} while (valid == false);

			File outfile = null;						//create output file
			PrintWriter writer = null;
			try {
				outfile = new File("data.txt");
				writer = new PrintWriter(outfile, "UTF-8");

			} catch (Exception e) {
				System.out.println("Something went wrong generating the output file.");
			}

			//for all the input values, set total = 0 and run through all the inputs
			double firstNumber = 0;
			for (int i = 0; i < numDataPoints; i++) {
				double x1 = firstNumber;
				double x2 = firstNumber+.01;
				double[] ins = new double[numInputs];		//array of inputs
				double output = 0;
				ins[0] = x1;

				writer.printf("%.2f, ", x1);

				//loop through (depending on the dimension) and calculate function based on that -- this loop is essentially Sigma in the Rosenbrock function
				for (int j = 0; j < numInputs-1; j++) {
					ins[j+1] = x2;
					writer.printf("%.2f, ", x2);
					output += (Math.pow((1 - x1), 2) + (100 * Math.pow(x2 - Math.pow(x1, 2), 2)));	//Rosenbrock function
					x1 += 0.01;
					x2 += 0.01;
				}
				samples.add(new Sample(ins, output));			//create new sample based on that array

				//after each set of calculations (each output generated) print the results
				writer.println(output);

				firstNumber+=.01;
			}
			writer.close();
		}
		
		//user chooses not to create data file - assumes one exists already
		else {
			try {
				Scanner s = new Scanner(new File("data.txt"));							//create a new scanner, checks lines of data in file
				while (s.hasNextLine()) {												//loop while there is another line
					String line = s.nextLine();											//grab the next line
					ArrayList<Double> inputs = new ArrayList<Double>();					//create an arraylist for the inputs
					int counter = 0;													//counter to determine size of input array
					Scanner lineScan = new Scanner(line);								//create new scanner that checks individual pieces of a line
					lineScan.useDelimiter(", ");										//separates tokens with a comma, space (as they were inputted that way)
					while (lineScan.hasNext()) {										//loop while there are still tokens to be read
						counter++;														//update counter (1 more input)
						inputs.add(Double.parseDouble(lineScan.next()));				//parse the token to be a double and add to the input arraylist
					}
					counter--;															//update counter to reflect input size (total - 1, since last token is output
					double[] passIn = new double[counter];								//this is the array that will be passed to sample class
					for (int i = 0; i < counter; i++) {									
						passIn[i] = inputs.get(i);										//initialize the input array
					}
					samples.add(new Sample(passIn, inputs.get(counter)));				//create new sample with input array, output, and add that sample to the list of samples
					lineScan.close();
				}
				s.close();
			} 
			catch (Exception e) {
				System.out.println("File not found.");
			}
		}

		
		//this block prompts the user for what kind of network to create
		input = 0;										//variable for user input for network
		do {
			valid = true;
			try {
				System.out.println("Please enter an integer corresponding to a value below:");
				System.out.println("1. Create an MLP with backpropagation.");
				System.out.println("2. Create an RBF network.");
				System.out.print("3. Quit.\n> ");
				input = in.nextInt();
			}
			catch (Exception e) {
				System.out.println("That is not an integer. Please enter an integer.\n");
				valid = false;
				in.nextLine();
			}
		} while(valid == false);

		//if the user creates an MLP
		if (input == 1) {
			int hidLayer = 0, hidNode = 0, outputs = 0, actFun = 0;
			double learningRate = 0;
			valid = true;

			//grab number of hidden layers (with error checking)
			do {
				try {
					System.out.println("How many hidden layers will there be? Please enter 0, 1, or 2.");
					hidLayer = in.nextInt();
					valid = true;
				}
				catch (Exception e) {
					System.out.println("That is not an integer. Please enter an integer.\n");
					valid = false;
					in.nextLine();
				}
			} while (valid == false);

			//grab number of hidden nodes if there are hidden layers (with error checking)
			if (hidLayer != 0) {
				do {
					try {
						System.out.println("How many hidden nodes will there be?");
						hidNode = in.nextInt();
						valid = true;
					}
					catch (Exception e) {
						System.out.println("That is not an integer. Please enter an integer.\n");
						valid = false;
						in.nextLine();
					}
				} while (valid == false);
			}

			//grab number of output nodes (with error checking)
			do {
				try {
					System.out.println("How many output nodes will there be?");
					outputs = in.nextInt();
					valid = true;
				}
				catch (Exception e) {
					System.out.println("That is not an integer. Please enter an integer.\n");
					valid = false;
					in.nextLine();
				}
			} while (valid == false);
			
			do {
				try {
					System.out.println("Choose an activation function for the hidden layers:");
					System.out.println("1. Linear");
					System.out.println("2. Sigmoidal: Logistic");
					actFun = in.nextInt();
					valid = true;
				}
				catch (Exception e) {
					System.out.println("That is not an integer. Please enter an integer.\n");
					valid = false;
					in.nextLine();
				}
			} while (valid == false);

			//let the user choose an activation function from a list (with error checking)
			do {
				try {
					System.out.println("What do you want the learning rate to be? (0 - .05)");
					learningRate = in.nextDouble();
					if(learningRate > .05 || learningRate < 0)
					{
						valid = false;
						System.out.println("That double is not between 0 and .05");
					}
					else
						valid = true;
				}
				catch (Exception e) {
					System.out.println("That is not an double. Please enter an integer.\n");
					valid = false;
					in.nextLine();
				} 
			} while (valid == false);
			//finally, create a MLP with all the information needed initially
			network = new Network(numInputs, hidLayer, hidNode, outputs, actFun, learningRate);
		}


		//STILL NEEDS TRAINING FUNCTIONS AND CORRESPONDING MENU FOR RBF
		/*	from video: e^(-Beta * ||x - c||^2)
		 *
		 */

		//if the user chooses to create an RBF network
		else if (input == 2) {
			int gaussians = 0, outputs = 0;
			double learningRate = 0;
			valid = true;

			//grab number of Gaussians (with error checking)
			do {
				try {
					System.out.println("How many Gaussians will there be?");
					gaussians = in.nextInt();
					valid = true;
				}
				catch (Exception e) {
					System.out.println("That is not an integer. Please enter an integer.\n");
					valid = false;
					in.nextLine();
				}
			} while (valid == false);

			//grab number of output nodes (with error checking)
			do {
				try {
					System.out.println("How many output nodes will there be?");
					outputs = in.nextInt();
					valid = true;
				}
				catch (Exception e) {
					System.out.println("That is not an integer. Please enter an integer.\n");
					valid = false;
					in.nextLine();
				}
			} while (valid == false);
			
			do {
				try {
					System.out.println("What do you want the learning rate to be? (0 - .05)");
					learningRate = in.nextDouble();
					if(learningRate > .05 || learningRate < 0)
					{
						valid = false;
						System.out.println("That double is not between 0 and .05");
					}
					else
						valid = true;
				}
				catch (Exception e) {
					System.out.println("That is not an double. Please enter an integer.\n");
					valid = false;
					in.nextLine();
				} 
			} while (valid == false);

			//create the RBF network with inputed parameters
			network = new Network(numInputs, gaussians, outputs, learningRate);

		}
		else {
			System.out.println("Exiting.");
			System.exit(0);
		}
		in.close();

		if(network.getType() == 2){
			network.setCenters(samples);
		}
		
		Collections.shuffle(samples);

		//train network
		for(int i = 0; i < samples.size()/2; i++) {
			network.train(samples.get(i).getInputs(), samples.get(i).getOutput());
			network.printNetwork();
			System.out.println("Desired Output: " + samples.get(i).getOutput() + "\n");
		}
		
	}
}