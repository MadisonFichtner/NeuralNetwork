package network;

public class Network {
	private int hidLayer;
	public Network(int inputs, int hidLayer, int hidNode, int outputs, int actFun, int outFun) {	//constructor for MLF network
		this.hidLayer = hidLayer;
		Layer in = new Layer();											//create input layer
		for (int i = 0; i < inputs; i++) {
			in.addNode(new Neuron(actFun));								//create node for each input
		}
		for (int i = 0; i < hidLayer; i++) {
			Layer l = new Layer();
			for (int j = 0; j < hidNode; j++) {		
				l.addNode(new Neuron(actFun));
			}
		}
		Layer out = new Layer();										//create output layer
		for (int i = 0; i < outputs; i++) {
			out.addNode(new Neuron(outFun));							//create node for each output
		}
	}
	public Network(int inputs, int gauss, int outputs, int actFun, int outFun) {					//constructor for RBF network
		hidLayer = 1;													//hidden layer is static (1)
		Layer in = new Layer();											//create input layer
		for (int i = 0; i < inputs; i++) {
			in.addNode(new Neuron(actFun));								//create node for each input
		}
		/* 	Layer l = new Layer();										//for adding hidden nodes - we need to decide on what number to use
		 * 	for (int i = 0; i < #; i++) {
		 * 		l.addNode(new Neuron(actFun));
		 * }
		 */
		Layer out = new Layer();										//create output layer
		for (int i = 0; i < outputs; i++) {
			out.addNode(new Neuron(outFun));							//create node for each output
		}
	}
	
	public void backprop() {
		
	}
	
}
