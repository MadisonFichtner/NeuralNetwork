package network;

/* Represents a single connection between neurons, which will
 * be inter-layer. Contains information about destination neuron, 
 * origin neuron (since connections in a feedforward net are directed),
 * and information about the weight. 
 */
public class Connection {
	private Neuron from, to;
	private double weight;
	
	/* Create a new connection
	 * @param from: the neuron from which the connection originates
	 * @param to: the neuron to which the connection terminates
	 * @param weight: the weight of the connection
	 */
	public Connection(Neuron from, Neuron to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	//return the origin neuron
	public Neuron getFrom() {
		return from;
	}
	
	//return the destination neuron
	public Neuron getTo() {
		return to;
	}
	
	//return the weight of the connection
	public double getWeight() {
		return weight;
	}
	
	//set weight in order to update weights
	//@param w: the new weight to update to
	public void setWeight(double w){
		weight = w;
	}
}
