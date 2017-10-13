package network;

import java.util.ArrayList;

public class Neuron {
	private int actFun;
	private int type;	//type of neuron
	private ArrayList<Connection> connections;
	private double output;
	private double[] center; //center of cluster

	/*
	 * @param actFun: activation function to be used
	 * @param type: type of neuron - 0 for weighted sum, 1 for Gaussian
	 */
	public Neuron(int type, int actFun) {
		this.actFun = actFun;
		this.type = type;
		connections = new ArrayList<Connection>();
	}

	//the function to calculate the output of the activation function of the neuron
	public void activate() {
		switch (actFun) {
		case 1:
			break;
		case 2:						//sigmoidal - logistic
			output = 1/(1+Math.exp(-output));
			break;
		}
	}

	//adds a connection to the previous layer neuron and the weight of the connection
	public void addConnection(Neuron n, double weight){
		connections.add(new Connection(this, n, weight));
	}

	/*
	 * calculates the output of the neuron
	 * @param ins: inputs to the neuron
	 * @param weights: corresponding weights of inputs
	 */
	public void calculate(ArrayList<Double> ins, ArrayList<Double> weights){
		if(type == 0){	//calculate weighted sum if it is a weighted sum neuron
			output = 0;
			for(int i = 0; i < ins.size(); i++){
				output+=ins.get(i)*weights.get(i);
			}
			activate(); //call activation function to adjust output
		}
		else{	//calculate output using radial basis function
			double sigma = 1;  //this needs to be tuned

			//this is how distance will be calculated once we use k-means clustering to find the center of the radial basis function
			double squaredDistance = 0;
			for(int i = 0; i < ins.size(); i++){
				squaredDistance += Math.pow(ins.get(i)-center[i], 2);
			}
			double distance = Math.sqrt(squaredDistance);

			output = Math.exp(-Math.pow(distance, 2)/(2*Math.pow(sigma, 2)));	//radial basis function
		}
	}
<<<<<<< HEAD
	
	//sets this neuron to be a center of a cluster
=======

>>>>>>> d1a89e7cdb6f059b890f4de04dd685b120161f13
	public void setCenter(double[] center){
		this.center = new double[center.length];
		this.center = center;
	}
<<<<<<< HEAD
	
	//returns the weight of the connection this neuron has
=======

>>>>>>> d1a89e7cdb6f059b890f4de04dd685b120161f13
	public double getWeightTo(int index){
		return connections.get(index).getWeight();
	}

	//sets the weight of the connection this neuron is the end of
	public void setWeightTo(int index, double weight) {
		connections.get(index).setWeight(weight);
	}

	//prints out info about neuron
	public void printNeuron(int num){
		System.out.println("\tNode " + num + ":");

		for(int i = 0; i < connections.size(); i++){
			System.out.println("\t  Connection " + (i+1) + " Weight: " + connections.get(i).getWeight());
		}

		System.out.println("\t  Output: " + output);
	}

	//returns the neuron's output
	public double getOutput(){
		return output;
	}

	//set's the neuron's output (if special layer)
	public void setOutput(double value){
		output = value;
	}

	//returns the type of the neuron
	public int getType(){
		return type;
	}

	//returns the neuron's activation function
	public int getActFun(){
		return actFun;
	}
<<<<<<< HEAD
	
	//returns this neuron's connection list
	public ArrayList<Connection> getConnections() {
=======

	public ArrayList getConnections() {
>>>>>>> d1a89e7cdb6f059b890f4de04dd685b120161f13
		return connections;
	}
}


