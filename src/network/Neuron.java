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

	public void activate() {
		switch (actFun) {
		case 2:						//sigmoidal - logistic
			output = 1/(1+Math.exp(-output));
			break;
		case 3:						//sigmoidal - hyperbolic tangent
			output = 2/(1+Math.exp(-2*output)) - 1;
			break;
		default:					//linear function - no change
			break;
		}
	}

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

			double squaredDistance = 0;
			for(int i = 0; i < ins.size(); i++){
				squaredDistance += Math.pow(ins.get(i)-center[i], 2);
			}
			double distance = Math.sqrt(squaredDistance);

			output = -(Math.pow(distance, 2)/(2*Math.pow(sigma, 2)));	//radial basis function
		}
	}

	public void setCenter(double[] center){
		System.out.println(center[0]);
		this.center = new double[center.length];
		this.center = center;
	}

	public double getWeightTo(int index){
		return connections.get(index).getWeight();
	}

	//prints out info about neuron
	public void printNeuron(int num){
		System.out.println("\tNode " + num + ":");

		for(int i = 0; i < connections.size(); i++){
			System.out.println("\t  Connection " + (i+1) + " Weight: " + connections.get(i).getWeight());
		}

		System.out.printf("\t  Output: %.4f\n",output);
	}

	public double getOutput(){
		return output;
	}

	public void setOutput(double value){
		output = value;
	}

	public int getType(){
		return type;
	}

	public int getActFun(){
		return actFun;
	}
}
