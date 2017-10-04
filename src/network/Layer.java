package network;

import java.util.ArrayList;

public class Layer {
	private ArrayList<Neuron> neurons;
	private int numNodes;
	private int nodeType;
	private int actFun;

	/*
	 * creates new layer
	 * @param numNodes: number of nodes in the layer
	 * @param nodeType: type of node - 0 for weighted sum, 1 for Gaussian
	 * @param actFun: type of activation function to be used
	 */
	public Layer(int numNodes, int nodeType, int actFun) {
		this.numNodes = numNodes;
		this.nodeType = nodeType;
		this.actFun = actFun;
		neurons = new ArrayList<Neuron>();

		//add nodes to layer
		for(int i = 0; i < numNodes; i++){
			addNeuron(new Neuron(nodeType, actFun));
		}
	}

	public void addNeuron(Neuron n) {
		neurons.add(n);
	}

	public Neuron getNeuron(int index){
		return neurons.get(index);
	}

	//prints out information about layer
	public void printLayer(int num){
		System.out.println("Layer " + num + ":");
		for(Neuron n : neurons){
			n.printNeuron(neurons.indexOf(n)+1);
		}
		System.out.println();
	}
}
