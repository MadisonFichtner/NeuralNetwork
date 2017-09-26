package network;

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
				System.out.println("That is not an integer. Please enter either 1 or 2.\n");
				valid = false;
				in.nextLine();
			}
		} while(valid == false);
		if (input == 1) {
			//enter into prompts for mlp network stuff
		}
		else if (input == 2) {
			//enter prompts for rbf network stuff
		}
		else {
			System.out.println("Exiting.");
			System.exit(0);
		}
		in.close();
	}
}
