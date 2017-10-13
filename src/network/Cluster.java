package network;

import java.util.ArrayList;
/* Represents a single cluster for the RBF network. Contains a list of the 
 * neurons in the cluster as well as a reference to the neuron serving as the 
 * cluster center.
 */
public class Cluster {
	private ArrayList<Neuron> clus = new ArrayList<Neuron>();
	private Neuron center = null;
	
	/* Creates a new cluster with the specified center
	 * @param center: the center node for the cluster
	 */
	public Cluster(Neuron center) {
		this.center = center;
	}
	
	/* adds a new neuron to the cluster and calculates the average of the cluster, returning it
	 * @param n: the neuron to add to the cluster
	 */
	public double addToCluster(Neuron n) {
		clus.add(n);
		return mean();
	}
	
	/* Calculates the mean of the cluster based on the outputs of the neurons in the cluster
	 * and returns the value
	 */
	private double mean() {
		double total = 0;
		for (int i = 0; i < clus.size(); i++) {
			total += clus.get(i).getOutput();
		}
		return total / clus.size();
	}
}
