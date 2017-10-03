package network;

import java.util.ArrayList;

public class Layer {
	ArrayList<Neuron> nodes;
	int numNodes;
	int nodeType;
	int actFun;
	
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
		nodes = new ArrayList<Neuron>();
		
		//add nodes to layer
		for(int i = 0; i < numNodes; i++){
			addNode(new Neuron(nodeType, actFun));
		}
	}
	
	public void addNode(Neuron n) {
		nodes.add(n);
	}

}
