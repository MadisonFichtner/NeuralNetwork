package network;

public class Network {
	private int hidLayer;
	public Network(int inputs, int hidLayer, int hidNode, int outputs) {	//constructor for MLF network
		this.hidLayer = hidLayer;
		Layer in = new Layer();					//create input layer
		for (int i = 0; i < inputs; i++) {
			in.addNode();						//create node for each input
		}
		for (int i = 0; i < hidLayer; i++) {
			Layer l = new Layer();
			for (int j = 0; j < hidNode; j++) {		//unless each layer has a different number of hidden nodes
				Neuron n = new Neuron();
			}
		}
		Layer out = new Layer();					//create output layer
		for (int i = 0; i < outputs; i++) {
			out.addNode();							//create node for each output
		}
	}
	public Network(int inputs, int gauss, int outputs) {					//constructor for RBF network
		hidLayer = 1;
		Layer in = new Layer();					//create input layer
		for (int i = 0; i < inputs; i++) {
			in.addNode();						//create node for each input
		}
		Layer out = new Layer();					//create output layer
		for (int i = 0; i < outputs; i++) {
			out.addNode();							//create node for each output
		}
	}
}
