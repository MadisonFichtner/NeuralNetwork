package network;

import java.util.ArrayList;

public class Network {
	private Layer inLayer;
	private ArrayList<Layer> hidLayers;
	private Layer outLayer;
	private int type;	//what type of network it is

	/*
	 * Create an MLP network
	 * @param numInputs: number of input nodes
	 * @param numHidLayers: number of hidden layers
	 * @param numHidNodes: number of nodes in hidden layers
	 * @param numOutputs: number of output nodes
	 * @param actFun: type of activation function for nodes
	 */
	public Network(int numInputs, int numHidLayers, int numHidNodes, int numOutputs, int actFun) {
		//create input layer with inputs number of nodes, node type of 0, and no activation function
		inLayer = new Layer(numInputs, 0, 0);

		//create hidden layers with hidNode number of nodes, node type of 0, and given activation function
		hidLayers = new ArrayList<Layer>();
		for(int i = 0; i < numHidLayers; i++){
			hidLayers.add(new Layer(numHidNodes, 0, actFun));
		}

		//create output layer with outputs number of nodes, node type of 0, and linear activation function
		outLayer = new Layer(numOutputs, 0, 1);

		//add connections from input layer to first hid layer with initial weights of 1
		for(int i = 0; i < numInputs; i++){
			for(int j = 0; j < numHidNodes; j++){
				inLayer.getNeuron(i).addConnection(hidLayers.get(0).getNeuron(j), 1);
			}
		}

		//add connections from each hidden layer to the next hidden layer with initial weights of 1
		for(int i = 0; i < numHidLayers-1; i ++){
			for(int j = 0; j < numHidNodes; j++){
				for(int k = 0; k < numHidNodes; k++){
					hidLayers.get(i).getNeuron(j).addConnection(hidLayers.get(i+1).getNeuron(k), 1);
				}
			}
		}

		//add connections from last hidden layer to output layer with initial weights of 1
		for(int i = 0; i < numHidNodes; i ++){
			for(int j = 0; j < numOutputs; j++){
				hidLayers.get(hidLayers.size()-1).getNeuron(i).addConnection(outLayer.getNeuron(j), 1);
			}
		}

		type = 1;
	}

	/*
	 * Create an RBF network
	 * @param numInputs: number of input nodes
	 * @param numGaussians: number of Gaussian nodes
	 * @param numOutputs: number of output nodes
	 */
	public Network(int numInputs, int numGaussians, int numOutputs) {					//constructor for RBF network
		//create input layer with inputs number of nodes, node type of 0, and no activation function
		inLayer = new Layer(numInputs, 0, 0);

		//create hidden layer with gauss number of nodes, node type of 1, and no activation function
		hidLayers = new ArrayList<Layer>();
		hidLayers.add(new Layer(numGaussians, 1, 0));

		//create output layer with outputs number of nodes, node type of 0, and linear activation function
		outLayer = new Layer(numOutputs, 0, 1);

		//add connections from input layer to Gaussian layer with weights of 1
		for(int i = 0; i < numInputs; i++){
			for(int j = 0; j < numGaussians; j++){
				inLayer.getNeuron(i).addConnection(hidLayers.get(0).getNeuron(j), 1);
			}
		}

		//add connections from Gaussian layer to output layer with initial weights of 1
		for(int i = 0; i < numGaussians; i ++){
			for(int j = 0; j < numOutputs; j++){
				hidLayers.get(0).getNeuron(i).addConnection(outLayer.getNeuron(j), 1);
			}
		}

		type = 2;
	}

	public double calcError(double result, double desired){
		return 0.0;
	}

	public void backprop(double error){
		//adjust all weights for MLP
		//adjust only the weights between gaussian layer and output layer
		if(type == 1){

		}
		else if(type == 2){

		}
	}

	/*
	 * Trains the neural network
	 * @param inputs: an array which stores the input values of a Rosenbrock function
	 * @param output: stores the output value from the Rosenbrock function with given x values
	 */
	public void train(double inputs[], double output){
		// 1. It should run the inputs through the network and get an output
		// 2. Then call calcError() with the output and the desired output to calculate error
		// 3. Then call backprop() with the value of the error
		// 4. Repeat with different input and output values

		//initialize input layer
		for(int i = 0; i < inLayer.size(); i++){
			inLayer.getNeuron(i).setOutput(inputs[i]);
		}

		//calculate hidden layers outputs
		for(int i = 0; i < hidLayers.size(); i++){
			for(int j = 0; j < hidLayers.get(i).size(); j++){
				ArrayList<Double> ins = new ArrayList<Double>();	//inputs to the neuron
				ArrayList<Double> weights = new ArrayList<Double>();//corresponding weights to the neuron
				if(i == 0){
					for(int k = 0; k < inLayer.size(); k++){
						ins.add(inLayer.getNeuron(k).getOutput());
						weights.add(inLayer.getNeuron(k).getWeightTo(j));
						hidLayers.get(0).getNeuron(j).calculate(ins, weights);
					}
				}
				else{
					for(int k = 0; k < hidLayers.get(i-1).size(); k++){
						ins.add(hidLayers.get(i-1).getNeuron(k).getOutput());
						weights.add(hidLayers.get(i-1).getNeuron(k).getWeightTo(j));
						hidLayers.get(i).getNeuron(j).calculate(ins, weights);
					}
				}
			}
		}

		//calculate output layer outputs
		for(int i = 0; i < outLayer.size(); i++){
			ArrayList<Double> ins = new ArrayList<Double>();	//inputs to the neuron
			ArrayList<Double> weights = new ArrayList<Double>();//corresponding weights to the neuron
			for(int j = 0; j < hidLayers.get(hidLayers.size()-1).size(); j++){
				ins.add(hidLayers.get(hidLayers.size()-1).getNeuron(j).getOutput());
				weights.add(hidLayers.get(hidLayers.size()-1).getNeuron(j).getWeightTo(i));
				outLayer.getNeuron(i).calculate(ins, weights);
			}
		}

		//calculate error and back propagate
		double error = calcError(outLayer.getNeuron(0).getOutput(), output);
		backprop(error);
	}

	//prints out information about network
	public void printNetwork(){
		inLayer.printLayer(1);
		for(Layer l : hidLayers){
			l.printLayer(hidLayers.indexOf(l) + 2);
		}
		outLayer.printLayer(2 + hidLayers.size());
	}

}
