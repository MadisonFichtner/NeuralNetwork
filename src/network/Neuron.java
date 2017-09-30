package network;

public class Neuron {
	//inputs, outputs (connections, that is)
	private int actFun;
	public Neuron(int actFun) {		//actFun is activation function - number corresponds to which function to use (methods for each)
		
	}
	
	public void activate() {
		switch (actFun) {
		case 1:						//linear function
			break;
		case 2:						//sigmoidal - logistic
			break;
		case 3:						//sigmoidal - hyperbolic tangent			
			break;
		}
	}
}
