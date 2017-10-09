package network;

import java.util.ArrayList;

public class Cluster {
	private ArrayList<Neuron> clus = new ArrayList<Neuron>();
	private Neuron center = null;
	
	public Cluster(Neuron center) {
		this.center = center;
	}
	
	public double addToCluster(Neuron n) {
		clus.add(n);
		return mean();
	}
	
	private double mean() {
		double total = 0;
		for (int i = 0; i < clus.size(); i++) {
			total += clus.get(i).getOutput();
		}
		return total / clus.size();
	}
}
