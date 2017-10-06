package network;
import java.io.PrintWriter;
/* Notes: need to read in a data file
 * - need to add a connection from each node in 1 layer to every node in the next
 *
 */
import java.util.Scanner;

public class Main {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);																	//create scanner for input
		boolean valid = true;																					//flag variable for correct user input
		int input = 0, n = 0, inputs = 0;												//inputs here is #of inputs; input is user choice
		Network network = null;
		
		//opening menu
		do {
			valid = true;
			try {
				System.out.println("Would you like to create a new data set?\n1. Yes\n2. No");
				System.out.print(">");
				input = in.nextInt();
			}
			catch (Exception e) {
				System.out.println("That is not an integer. Please enter an integer.\n");
				valid = false;		//input is invalid, flag
				in.nextLine();		//clear input buffer
			}
		} while (valid == false);
		
		//entering the dimensions
		if (input == 1) {		//user chose to create new output file
			do {
				valid = true;
				try {
					System.out.println("Please enter the number of dimensions for the Rosenbrock function:");
					n = in.nextInt();
				}
				catch (Exception e) {
					System.out.println("That is not an integer. Please enter an integer.\n");
					valid = false;		//input is invalid, flag
					in.nextLine();		//clear input buffer
				}
			} while (valid == false);
			do {
				valid = true;
				try {
					System.out.println("Please enter the number of data points you want:");
					inputs = in.nextInt();
				}
				catch (Exception e) {
					System.out.println("That is not an integer. Please enter an integer.\n");
					valid = false;		//input is invalid, flag
					in.nextLine();		//clear input buffer
				}
			} while (valid == false);
			//initialize input array
			int[] p = new int[inputs];
			for (int i = 0; i < inputs; i++) {
				p[i] = i;
			}
			int total = 0;
			for (int i = 1; i < n; i++) {
				total += (Math.pow((1 - p[i]), n) + (100 * Math.pow(p[i + 1] - Math.pow(p[i], n), n)));
				
				
				
				//FOCUS WORK HERE
			}
			try {
				PrintWriter writer = new PrintWriter("data.txt", "UTF-8");
				
			} catch (Exception e) {
				System.out.println("Something went wrong generating the output file.");
			}
		}
		
		//this block prompts the user for what kind of network to create
		input = 0;
		do {
			valid = true;
			try {
				System.out.println("Please enter an integer corresponding to a value below:");
				System.out.println("1. Create an MLP with backpropagation.");
				System.out.println("2. Create an RBF network.");
				System.out.print("3. Quit.\n> ");
				input = in.nextInt();
			}
			//catch input mismatch exception
			catch (Exception e) {
				System.out.println("That is not an integer. Please enter an integer.\n");
				valid = false;		//input is invalid, flag
				in.nextLine();		//clear input buffer
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
			network = new Network(inputs, hidLayer, hidNode, outputs, actFun);
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
			network = new Network(inputs, gaussians, outputs);
			//train with chosen training method
			/*
			int train = 0;
			do {
				try {
					System.out.println("Choose a training function:");
					for (int i = 0; i < gaussians; i++) {
						System.out.println(i + ". <<basis function name>>");
					}
					train = in.nextInt();
					valid = true;
				}
				catch (Exception e) {
					System.out.println("That is not an integer. Please enter an integer.\n");
					valid = false;
					in.nextLine();
				}
			} while (valid == false);
			//train
			*/

		}
		else {
			System.out.println("Exiting.");
			System.exit(0);
		}
		in.close();

		//print network
		network.printNetwork();
		
		//train network
		double example[] = {1,2,3};
		network.train(example, 4);
	}
}
