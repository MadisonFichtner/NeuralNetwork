package network;

import java.util.ArrayList;

public class Neuron {
	private int actFun;
	private int type;
	private ArrayList<Connection> connections;

	/*
	 * @param actFun: activation function to be used
	 * @param type: type of neuron - 0 for weighted sum, 1 for Gaussian
	 */
	public Neuron(int type, int actFun) {
		this.actFun = actFun;
		this.type = type;
		connections = new ArrayList<Connection>();
	}

	public void activate() {
		switch (actFun) {
		case 0:						//no activation function -- don't change output
			break;
		case 1:						//linear function
			break;
		case 2:						//sigmoidal - logistic
			break;
		case 3:						//sigmoidal - hyperbolic tangent
			break;
		}
	}

	public void addConnection(Neuron n, double weight){
		connections.add(new Connection(this, n, weight));
	}

	//prints out info about neuron
	public void printNeuron(int num){
		System.out.println("\tNode " + num + ":");
		if(type == 0){
			System.out.println("\t  Type: Weighted Sum");
			switch(actFun){
			case 0:
				System.out.println("\t  Activation Function: None");
				break;
			case 1:
				System.out.println("\t  Activation Function: Linear");
				break;
			case 2:
				System.out.println("\t  Activation Function: Logistic");
				break;
			case 3:
				System.out.println("\t  Activation Function: Hyperbolic Tangent");
				break;
			}
		}
		else{
			System.out.println("\t  Type: Gaussian");
			System.out.println("\t  Activation Function: None");
		}

		for(int i = 0; i < connections.size(); i++){
			System.out.println("\t  Connection " + (i+1) + " Weight: " + connections.get(i).getWeight());
		}
	}
}
