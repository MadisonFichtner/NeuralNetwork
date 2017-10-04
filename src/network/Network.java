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
				for(int k = 0; k < numHidLayers; k++){
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

	public double calcError(){
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

	public void train(){
		// 1. It should run the inputs through the network and get an output
		// 2. Then call calcError() with the output and the desired output to calculate error
		// 3. Then call backprop() with the value of the error
		// 4. Repeat with different input and output values
		if(type == 1){
			//initialize input layer
			for(int i = 0; i < inLayer.size(); i++){
				inLayer.getNeuron(i).setOutput(5);
			}

			//calculate hidden layers outputs
			for(int i = 0; i < hidLayers.size(); i++){
				for(int j = 0; j < hidLayers.get(i).size(); j++){
					hidLayers.get(i).getNeuron(j).calculate();
				}
			}

			//calculate output layer outputs
			for(int i = 0; i < outLayer.size(); i++){
				outLayer.getNeuron(i).calculate();
			}

			//calculate error and back propagate
			double error = calcError();
			backprop(error);
		}
		else if(type == 2){

		}
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
