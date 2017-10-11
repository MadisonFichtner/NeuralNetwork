package network;

import java.util.ArrayList;
import java.util.Random;

public class Network {
	private Random random = new Random();
	private Layer inLayer;
	private ArrayList<Layer> hidLayers;
	private Layer outLayer;
	private int type;	//what type of network it is
	private double overallError;
	private double learningRate;
	private int numInputs, numHidLayers, numHidNodes, numOutputs;

	/*
	 * Create an MLP network
	 * @param numInputs: number of input nodes
	 * @param numHidLayers: number of hidden layers
	 * @param numHidNodes: number of nodes in hidden layers
	 * @param numOutputs: number of output nodes
	 * @param actFun: type of activation function for nodes
	 */
	public Network(int numInputs, int numHidLayers, int numHidNodes, int numOutputs, int actFun, double learningRate) {
		//create input layer with inputs number of nodes, node type of 0, and a linear activation function
		inLayer = new Layer(numInputs, 0, 1);

		//create hidden layers with hidNode number of nodes, node type of 0, and given activation function
		hidLayers = new ArrayList<Layer>();
		for(int i = 0; i < numHidLayers; i++){
			hidLayers.add(new Layer(numHidNodes, 0, actFun));
		}

		//create output layer with outputs number of nodes, node type of 0, and linear activation function
		outLayer = new Layer(numOutputs, 0, 1);

		//add connections from input layer to first hid layer with random weights between -1 and 1
		for(int i = 0; i < numInputs; i++){
			for(int j = 0; j < numHidNodes; j++){
				inLayer.getNeuron(i).addConnection(hidLayers.get(0).getNeuron(j), (random.nextDouble()*2)-1);
			}
		}

		//add connections from each hidden layer to the next hidden layer with random weights between -1 and 1
		for(int i = 0; i < numHidLayers-1; i ++){
			for(int j = 0; j < numHidNodes; j++){
				for(int k = 0; k < numHidNodes; k++){
					hidLayers.get(i).getNeuron(j).addConnection(hidLayers.get(i+1).getNeuron(k), (random.nextDouble()*2)-1);
				}
			}
		}

		//add connections from last hidden layer to output layer with random weights between -1 and 1
		for(int i = 0; i < numHidNodes; i ++){
			for(int j = 0; j < numOutputs; j++){
				hidLayers.get(hidLayers.size()-1).getNeuron(i).addConnection(outLayer.getNeuron(j), (random.nextDouble()*2)-1);
			}
		}

		if(hidLayers.isEmpty()){	//if there are no hidden layers, connect input layer to output layer with random weights between -1 and 1
			for(int i = 0; i < numInputs; i ++){
				for(int j = 0; j < numOutputs; j++){
					inLayer.getNeuron(i).addConnection(outLayer.getNeuron(j), (random.nextDouble()*2)-1);
				}
			}
		}

		type = 1;

		//Learning Rate
		this.learningRate = learningRate;
		this.numInputs = numInputs;
		this.numHidLayers = numHidLayers;
		this.numHidNodes = numHidNodes;
		this.numOutputs = numOutputs;
	}

	/*
	 * Create an RBF network
	 * @param numInputs: number of input nodes
	 * @param numGaussians: number of Gaussian nodes
	 * @param numOutputs: number of output nodes
	 */
	public Network(int numInputs, int numGaussians, int numOutputs, double learningRate) {					//constructor for RBF network
		//create input layer with inputs number of nodes, node type of 0, and no activation function
		inLayer = new Layer(numInputs, 0, 0);

		//create hidden layer with gauss number of nodes, node type of 1, and no activation function
		hidLayers = new ArrayList<Layer>();
		hidLayers.add(new Layer(numGaussians, 1, 0));

		//create output layer with outputs number of nodes, node type of 0, and linear activation function
		outLayer = new Layer(numOutputs, 0, 1);

		//add connections from input layer to Gaussian layer with random weights between -1 and 1
		for(int i = 0; i < numInputs; i++){
			for(int j = 0; j < numGaussians; j++){
				inLayer.getNeuron(i).addConnection(hidLayers.get(0).getNeuron(j), (random.nextDouble()*2)-1);
			}
		}

		//add connections from Gaussian layer to output layer with random weights between -1 and 1
		for(int i = 0; i < numGaussians; i ++){
			for(int j = 0; j < numOutputs; j++){
				hidLayers.get(0).getNeuron(i).addConnection(outLayer.getNeuron(j), (random.nextDouble()*2)-1);
			}
		}

		type = 2;
		this.learningRate = learningRate;
	}

	public void reset(){
		//add connections from input layer to first hid layer with random weights between -1 and 1
		for(int i = 0; i < numInputs; i++){
			for(int j = 0; j < numHidNodes; j++){
				inLayer.getNeuron(i).addConnection(hidLayers.get(0).getNeuron(j), (random.nextDouble()*2)-1);
			}
		}

		//add connections from each hidden layer to the next hidden layer with random weights between -1 and 1
		for(int i = 0; i < numHidLayers-1; i ++){
			for(int j = 0; j < numHidNodes; j++){
				for(int k = 0; k < numHidNodes; k++){
					hidLayers.get(i).getNeuron(j).addConnection(hidLayers.get(i+1).getNeuron(k), (random.nextDouble()*2)-1);
				}
			}
		}

		//add connections from last hidden layer to output layer with random weights between -1 and 1
		for(int i = 0; i < numHidNodes; i ++){
			for(int j = 0; j < numOutputs; j++){
				hidLayers.get(hidLayers.size()-1).getNeuron(i).addConnection(outLayer.getNeuron(j), (random.nextDouble()*2)-1);
			}
		}

		if(hidLayers.isEmpty()){	//if there are no hidden layers, connect input layer to output layer with random weights between -1 and 1
			for(int i = 0; i < numInputs; i ++){
				for(int j = 0; j < numOutputs; j++){
					inLayer.getNeuron(i).addConnection(outLayer.getNeuron(j), (random.nextDouble()*2)-1);
				}
			}
		}
	}

	// 1. Calculate net input for h1 = w1*i1 + ... wn*in + b1 * 1 (where b is bias node)
	// 2. Calculate output for h1 = 1/(1+e^(-neth1))
	// 3. Do exact same thing for output layer using outputs from hidden layer as inputs to get output for each output node
	// 4. Take squared sum 1/2(target - output)^2 to find total errors
	public double calcError(double result, double desired){
		overallError = (.5) * Math.pow((desired - result), 2);
		return overallError;
	}

	/*
	 * Backpropogates through the network, updating all weights based on the output of the network
	 * @param error: overall error of network
	 * @param output: the expected output of network and nodes
	 */
	public void backprop(double error, double output){
		//adjust all weights for MLP
		//adjust only the weights between gaussian layer and output layer

		if(type == 1){ //MLP

			//No hidden layers case
			if(hidLayers.size() == 0)
			{
				double delta = 0;
				//Output layer update
				for(int i = 0; i < outLayer.size(); i++)
				{
					for(int j = 0; j < inLayer.size(); j++)
					{
						//Update weights from inLayer to outLayer
						double updatedWeight = inLayer.getNeuron(j).getWeightTo(i);		//Initializes value of updatedWeight to the original weight
						delta = (output - outLayer.getNeuron(i).getOutput());
						updatedWeight = updatedWeight + (learningRate * delta * inLayer.getNeuron(j).getOutput());
						inLayer.getNeuron(j).setWeightTo(i, updatedWeight);
					}
				}
			}

			//One hidden layer case
			else if(hidLayers.size() == 1)
			{
				double delta = 0;
				//Output layer update
				//weight(old) + (learning rate * output error * output(neurons i) * output(neurons i+1) * (1-output(neurons i+1)
				for(int i = 0; i < outLayer.size(); i++)
				{
					for(int j = 0; j < hidLayers.get(0).size(); j++)
					{
						double updatedWeight = hidLayers.get(0).getNeuron(j).getWeightTo(i);	//Initializes value of updatedWeight to the original weight
						delta = (output - outLayer.getNeuron(i).getOutput());
						updatedWeight = updatedWeight + (learningRate * delta * hidLayers.get(0).getNeuron(j).getOutput());
						hidLayers.get(0).getNeuron(j).setWeightTo(i, updatedWeight);
					}
				}
				//Input layer update
				double oldDelta = delta;
				for(int i = 0; i < hidLayers.get(0).size(); i++)
				{
					for(int j = 0; j < inLayer.size(); j++)
					{
						//Update weights from inLayer to hidLayer(0)
						double updatedWeight = inLayer.getNeuron(j).getWeightTo(i);		//Initializes value of updatedWeight to the original weight
						delta = oldDelta * hidLayers.get(0).getNeuron(i).getWeightTo(0);
						updatedWeight = updatedWeight + (learningRate * delta * inLayer.getNeuron(j).getOutput());
						inLayer.getNeuron(j).setWeightTo(i, updatedWeight);
					}
				}
			}

			//delta = delta * hidLayers.get(0).getNeuron(i).getWeightTo(0) * hidLayers.get(0).getNeuron(i).getOutput() * (1-hidLayers.get(0).getNeuron(i).getOutput());

			//2 hidden layer case
			else if(hidLayers.size() == 2)
			{
				//Output layer update
				//weight(old) + (learning rate * output error * output(neurons i) * output(neurons i+1) * (1-output(neurons i+1)
				for(int i = 0; i < outLayer.size(); i++)
				{
					for(int j = 0; j < hidLayers.get(1).size(); j++)
					{
						double updatedWeight = hidLayers.get(1).getNeuron(j).getWeightTo(i);	//Initializes value of updatedWeight to the original weight
						updatedWeight = updatedWeight + (learningRate * (output - outLayer.getNeuron(i).getOutput()) * (outLayer.getNeuron(i).getOutput() * (1 - outLayer.getNeuron(i).getOutput())) * hidLayers.get(1).getNeuron(j).getOutput());
						hidLayers.get(1).getNeuron(j).setWeightTo(i, updatedWeight);
					}
				}
				//Update between hidden layers
				for(int i = 0; i < hidLayers.get(1).size(); i++)
				{
					for(int j = 0; j < hidLayers.get(0).size(); j++)
					{
						//Update weights from hidLayer(0) to hidLayer(1)
					}
				}
				//Update between input and hidLayer.get(0)
				for(int i = 0; i < hidLayers.get(0).size(); i++)
				{
					for(int j = 0; j < inLayer.size(); j++)
					{
						//Update weights from inLayer to hidLayer(0)
					}
				}
			}
		}
		else if(type == 2){ //RBF just do output layer updates
			//Output Layer Updates
			double delta = 0;
			for(int i = 0; i < outLayer.size(); i++)
			{
				for(int j = 0; j < hidLayers.get(0).size(); j++)
				{
					double updatedWeight = hidLayers.get(0).getNeuron(j).getWeightTo(i);	//Initializes value of updatedWeight to the original weight
					delta = (output - outLayer.getNeuron(i).getOutput());
					updatedWeight = updatedWeight + (learningRate * delta * hidLayers.get(0).getNeuron(j).getOutput());
					hidLayers.get(0).getNeuron(j).setWeightTo(i, updatedWeight);
				}
			}
		}
	}


	public void calcOutputs() {
		//calculate hidden layers outputs
				for(int i = 0; i < hidLayers.size(); i++){					//iterate through each hidden layer
					for(int j = 0; j < hidLayers.get(i).size(); j++){		//iterate through each neuron in the hidden layer
						ArrayList<Double> ins = new ArrayList<Double>();	//inputs to the neuron
						ArrayList<Double> weights = new ArrayList<Double>();//corresponding weights to the neuron
						if(i == 0){											//if it is the first hidden layer we want to get inputs from input layer
							for(int k = 0; k < inLayer.size(); k++){		//iterate through each neuron in input layer
								ins.add(inLayer.getNeuron(k).getOutput());
								weights.add(inLayer.getNeuron(k).getWeightTo(j));
							}
						}
						else{																	//otherwise we want input from previous hidden layer
							for(int k = 0; k < hidLayers.get(i-1).size(); k++){					//iterate through each neuron in previous hidden layer
								ins.add(hidLayers.get(i-1).getNeuron(k).getOutput());
								weights.add(hidLayers.get(i-1).getNeuron(k).getWeightTo(j));
							}
						}
						hidLayers.get(i).getNeuron(j).calculate(ins, weights); //calculate output of each neuron in hidden layer
					}
				}

				//calculate output layer outputs
				for(int i = 0; i < outLayer.size(); i++){								//iterate through each neuron in output layer
					ArrayList<Double> ins = new ArrayList<Double>();					//inputs to the neuron
					ArrayList<Double> weights = new ArrayList<Double>();				//corresponding weights to the neuron
					if(!hidLayers.isEmpty()){											//if there are no hidden layers
						for(int j = 0; j < hidLayers.get(hidLayers.size()-1).size(); j++){	//iterate through each neuron in last hidden layer
							ins.add(hidLayers.get(hidLayers.size()-1).getNeuron(j).getOutput());
							weights.add(hidLayers.get(hidLayers.size()-1).getNeuron(j).getWeightTo(i));
							outLayer.getNeuron(i).calculate(ins, weights);					//calculate output of each output node
						}
					}
					else{	//calculate output from input layer if there are no hidden layers
						for(int j = 0; j < inLayer.size(); j++){	//iterate through each neuron in last hidden layer
							ins.add(inLayer.getNeuron(j).getOutput());
							weights.add(inLayer.getNeuron(j).getWeightTo(i));
							outLayer.getNeuron(i).calculate(ins, weights);					//calculate output of each output node
						}
					}
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

		calcOutputs();

		//calculate error and back propagate
		double actualOutput = outLayer.getNeuron(0).getOutput();
		double error = calcError(actualOutput, output);
		backprop(error, output);
	}

	public double evaluate(double inputs[], double output){
		for(int i = 0; i < inLayer.size(); i++){
			inLayer.getNeuron(i).setOutput(inputs[i]);
		}

		calcOutputs();
		double actualOutput = outLayer.getNeuron(0).getOutput();

		return Math.abs(actualOutput - output);	//return absolute error
	}

	public void setCenters(ArrayList<Sample> samples){
		for(int i = 0; i < hidLayers.get(0).size(); i++){
			int center = random.nextInt(samples.size());
			hidLayers.get(0).getNeuron(i).setCenter(samples.get(center).getInputs());
		}
	}

	public int getType(){
		return type;
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

