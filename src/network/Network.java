package network;

import java.util.ArrayList;

public class Network {
	private Layer inLayer;
	private ArrayList<Layer> hidLayers;
	private Layer outLayer;
	
	/*
	 * Create a MLP network
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
	}
	
	public void backprop() {
		
	}
	
}
