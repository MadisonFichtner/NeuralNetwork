package network;

public class Neuron {
	private int actFun;
	private int type;
	
	/*
	 * @param actFun: activation function to be used
	 * @param type: type of neuron - 0 for weighted sum, 1 for Gaussian
	 */
	public Neuron(int type, int actFun) {	
		this.actFun = actFun;
		this.type = type;
	}
	
	public void activate() {
		switch (actFun) {
		case 0:						//no activation function -- don't change output
			break;
		case 1:						//linear function
			break;
		case 2:						//sigmoidal - logistic
			break;
		case 3:						//sigmoidal - hyperbolic tangent			
			break;
		}
	}
}
