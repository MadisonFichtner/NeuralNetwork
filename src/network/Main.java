package network;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
/* Notes: need to read in a data file
 * - need to add a connection from each node in 1 layer to every node in the next
 *
 */
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
				double x2 = firstNumber+.1;
				double[] ins = new double[numInputs];		//array of inputs
				double output = 0;
				ins[0] = x1;

				writer.printf("%.1f, ", x1);

				//loop through (depending on the dimension) and calculate function based on that -- this loop is essentially Sigma in the Rosenbrock function
				for (int j = 0; j < numInputs-1; j++) {
					ins[j+1] = x2;
					writer.printf("%.1f, ", x2);
					output += (Math.pow((1 - x1), 2) + (100 * Math.pow(x2 - Math.pow(x1, 2), 2)));	//Rosenbrock function
					x1 += 0.1;
					x2 += 0.1;
				}
				samples.add(new Sample(ins, output));			//create new sample based on that array

				//after each set of calculations (each output generated) print the results
				writer.println(output);

				firstNumber+=.1;
			}
			writer.close();
		}
		else {
			//need to add reading in the data set for # of inputs and creating the samples list from the data set -- HERE NATE, HERE BOY
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

			//let the user choose an activation function from a list (with error checking)
			do {
				try {
					System.out.println("Choose an activation function for the hidden layers:");
					System.out.println("1. Linear");
					System.out.println("2. Sigmoidal: Logistic");
					System.out.println("3. Sigmoidal: Hyperbolic Tangent");
					actFun = in.nextInt();
					valid = true;
				}
				catch (Exception e) {
					System.out.println("That is not an integer. Please enter an integer.\n");
					valid = false;
					in.nextLine();
				}
			} while (valid == false);

			//finally, create a MLP with all the information needed initially
			network = new Network(numInputs, hidLayer, hidNode, outputs, actFun);
		}


		//STILL NEEDS TRAINING FUNCTIONS AND CORRESPONDING MENU FOR RBF
		/*	from video: e^(-Beta * ||x - c||^2)
		 *
		 */

		//if the user chooses to create an RBF network
		else if (input == 2) {
			int gaussians = 0, outputs = 0;
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

			//create the RBF network with inputed parameters
			network = new Network(numInputs, gaussians, outputs);

		}
		else {
			System.out.println("Exiting.");
			System.exit(0);
		}
		in.close();

		if(network.getType() == 2){
			network.setCenters(samples);
		}

		//train network
		network.train(samples.get(0).getInputs(), samples.get(0).getOutput());

		//print network
		network.printNetwork();
	}
}
