package network;

public class Sample {
	private double[] inputs;
	
	public Sample(double[] inputs) {
		this.inputs = inputs;
	}
	public double getInput(int index) {
		return inputs[index];
	}
	public double getOutput() {
		return inputs[inputs.length - 1];
	}
}
