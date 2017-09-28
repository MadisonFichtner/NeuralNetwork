package network;
/* Notes: need to read in a data file, probably
 * - need to add a connection from each node in 1 layer to every node in the next
 * - backprop is how MLP learns, but RBF we have to choose 3 algorithms
 * 
 */
import java.util.Scanner;

public class Main {	
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		boolean valid = true;
		int input = 0;
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
		if (input == 1) {
			int inputs = 0, hidLayer = 0, hidNode = 0, outputs = 0, actFun = 0, outFun = 0;
			valid = true;
			do {
				try {
					System.out.println("How many input nodes will there be?");
					inputs = in.nextInt();
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
					System.out.println("Choose an activation function for the input and hidden layers:");
					System.out.println("1. Linear: Adeline");
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
			do {
				try {
					System.out.println("Choose an activation function for the output layer:");
					System.out.println("1. Linear: Adeline");
					System.out.println("2. Sigmoidal: Logistic");
					System.out.println("3. Sigmoidal: Hyperbolic Tangent");
					outFun = in.nextInt();
					valid = true;
				}
				catch (Exception e) {
					System.out.println("That is not an integer. Please enter an integer.\n");
					valid = false;
					in.nextLine();
				}
			} while (valid == false);
			Network MLP = new Network(inputs, hidLayer, hidNode, outputs, actFun, outFun);
		}
		else if (input == 2) {
			//prompt for # of inputs
			//prompt for # gaussians
			//prompt for # of outputs
			//prompt for activation function
			//prompt for output layer activation function
		}
		else {
			System.out.println("Exiting.");
			System.exit(0);
		}
		in.close();
	}
}
