package data;

class Neuron {
	private static final double MIN = 0.1, MAX = 0.2;
	
	private Function function;
	private double[] signals;
	private double[] weights;
	private double bias;
	private double out;
	private double delta;
	
	public Neuron(Function function, int signalsCount) {
		this.function = function;
		this.signals = new double[signalsCount];
		this.weights = new double[signalsCount];
		initWeights();
	}
	
	public Neuron(Neuron neuron) {
		this.function = neuron.getFunction();
		this.signals = new double[ neuron.signalsCount() ];
		this.weights = new double[ neuron.signalsCount() ];
		initWeights();
	}
	
	private void initWeights() {
		if ( function.getType().equals(Function.TYPE_STUB) ) {	// input neuron
			for (int i = 0; i < weights.length; i++)
				weights[i] = 1;
			bias = 0;
		} else {
			for (int i = 0; i < weights.length; i++)
				weights[i] = Math.random() * MAX + MIN;
			bias = Math.random() * MAX + MIN;
		}
	}
	
	public void fire() {
		double s = 0;
		
		for (int i = 0; i < signals.length; i++)
			s += signals[i] * weights[i];
		
		out = function.calc(s - bias);
	}
	
	public Function getFunction() {
		return function;
	}

	public int signalsCount() {
		return signals.length;
	}
	
	public void setSignal(int i, double signal) {
		signals[i] = signal;
	}
	
	public double getOut() {
		return out;
	}
	
	public double getWeight(int i) {
		return weights[i];
	}
	
	public void setWeight(int i, double weight) {
		weights[i] = weight;
	}
	
	public double getBias() {
		return bias;
	}
	
	public void setBias(double bias) {
		this.bias = bias;
	}
	
	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}
	
	public String toString() {
		String res = "";
		if ( !function.getType().equals(Function.TYPE_STUB) ) {
			res = "neuron, function: " + function.getType() + ", signals: " + signalsCount();
			for (int k = 0; k < signalsCount(); k++)
				res += "\n\t\tweigth #" + (k+1) + ": " + weights[k];
			res += "\n\t\tbias: " + bias;
		} else
			res = "input neuron";
		return res;
	}
	
}