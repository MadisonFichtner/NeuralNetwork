package network;

import java.util.ArrayList;

/* Represents a single layer in the network and it's functions. Contains
 * a list of neurons/nodes, the number of those, the type of node (weighted sum or Gaussian),
 * and the activation function for that layer, which is passed on to neurons for the layer.
 */
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

	//adds a neuron to the layer
	public void addNeuron(Neuron n) {
		neurons.add(n);
	}

	//returns the neuron at the provided index
	public Neuron getNeuron(int index){
		return neurons.get(index);
	}

	//returns the size of the neuron array; effectively the layer size in neurons
	public int size(){
		return neurons.size();
	}

	//prints out information about layer
	public void printLayer(int num){
		System.out.println("Layer " + num + ":");

		if(nodeType == 0){
			System.out.println("\tNeuron Type: Weighted Sum");
			switch(actFun){
			case 1:
				System.out.println("\tActivation Function: Linear");
				break;
			case 2:
				System.out.println("\tActivation Function: Logistic");
				break;
			}
		}
		else{
			System.out.println("\tNeuron Type: Gaussian");
			System.out.println("\t  Activation Function: None");
		}

		for(Neuron n : neurons){
			n.printNeuron(neurons.indexOf(n)+1);
		}
		System.out.println();
	}
}
