package data;

class Layer {
	private String name;
	private Neuron[] neurons;
	
	public Layer(String name, int size, Neuron exampleNeuron) {
		this.name = name;
		this.neurons = new Neuron[size];
		
		for (int i = 0; i < neurons.length; i++)
			neurons[i] = new Neuron(exampleNeuron);
	}
	
	public String getName() {
		return name;
	}

	public Neuron[] getNeurons() {
		return neurons;
	}
	
	public Neuron getNeuron(int i) {
		return neurons[i];
	}
	
	public int size() {
		return neurons.length;
	}
	
	public void fire() {
		for (Neuron neuron : neurons)
			neuron.fire();
	}
	
	public void propagateSignalTo(Layer layer) {
		Neuron[] neuronsTo = layer.getNeurons();
		
		for (int i = 0; i < neurons.length; i++) {
			double out = neurons[i].getOut();
			
			for (int j = 0; j < neuronsTo.length; j++)
				neuronsTo[j].setSignal(i, out);
		}
	}
	
	public void recieveDeltaFrom(Layer layer) {
		Neuron[] neuronsFrom = layer.getNeurons();
		
		for (int i = 0; i < neurons.length; i++) {
			Neuron neuron = neurons[i];
			double out = neuron.getOut();
			Function fn = neuron.getFunction();
			
			double delta = 0;
			for (int j = 0; j < neuronsFrom.length; j++)
				delta += neuronsFrom[j].getDelta() * neuronsFrom[j].getWeight(i);
			
			neuron.setDelta( fn.df(out) * delta );
		}
	}
	
	public void updateWeightsOf(Layer layer, double speed) {
		Neuron[] neuronsOf = layer.getNeurons();
		
		for (int j = 0; j < neuronsOf.length; j++) {
			Neuron neuronOf = neuronsOf[j];
			
			for (int i = 0; i < neuronOf.signalsCount(); i++) {
				double w = neuronOf.getWeight(i);
				w += speed * neuronOf.getDelta() * neurons[i].getOut();
				neuronOf.setWeight(i, w);
			}
			
			neuronOf.setBias( neuronOf.getBias() - speed * neuronOf.getDelta() );
		}
	}
	
	public String toString() {
		return "Layer <" + name + ">, neurons: " + size();
	}

}