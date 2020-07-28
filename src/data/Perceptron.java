package data;

import main.Common;
import main.MainWindow;
import output.Output;
import output.OutputTXT;

public class Perceptron {
	private static Perceptron perceptron = new Perceptron();
	private Perceptron() {}
	public static Perceptron get() {
		return perceptron;
	}
	
	private Layer[] layers;
	private Output out;
	
	public void init(int[] layersSizes, Function fireFunction) {
		int layersCount = layersSizes.length;
		layers = new Layer[layersCount];		
		
		layers[0] = new Layer(
				"Input layer", 
				layersSizes[0], 
				new Neuron(
						Function.STUB, 
						1 
					)
			);
		
		layers[layersCount-1] = new Layer(
				"Result layer", 
				layersSizes[layersCount-1], 
				new Neuron(
						fireFunction, 
						layersSizes[layersCount-2] 
					)
			);
		
		for (int i = 1; i < layersCount - 1; i++)
			layers[i] = new Layer(
					"Hidden layer #" + i, 
					layersSizes[i], 
					new Neuron(
							fireFunction, 
							layersSizes[i-1] 
						)
				);
			
		out = new OutputTXT();
		out.printH1("Perceptron train result");
		info("after initialization");
	}
	
	private void info(String title) {
		out.printH2("Perceptron ("+ title + ")");
		
		for (int i = 0; i < layers.length; i++) {
			out.println("" + layers[i]);
			
			for (int j = 0; j < layers[i].size(); j++) {
				Neuron neuron = layers[i].getNeuron(j);
				out.println("\t" + (j+1) + ". "+ neuron);
			}
		}
		out.emptyLine();
	}
	
	public Output getOut() {
		return out;
	}
	
	private Layer resLayer;		// result layer pseudonym
	
	public void calc(double[] set) {
		for (int i = 0; i < set.length; i++) {
			Neuron neuron = layers[0].getNeuron(i);
			neuron.setSignal(0, set[i]);
			neuron.fire();
		}
		
		for (int i = 0; i < layers.length - 1; i++) {
			layers[i].propagateSignalTo( layers[i+1] );
			layers[i+1].fire();
		}
		
		resLayer = layers[layers.length - 1];
	}
	
	public double[] result() {
		double[] res = new double[ resLayer.size() ];
		
		for (int i = 0; i < resLayer.size(); i++)
			res[i] = resLayer.getNeuron(i).getOut(); 
		
		return res;
	}
	
	private double getResultError(double[] results) {
		double res = 0;
		
		for (int i = 0; i < resLayer.size(); i++) {
			double err = Math.pow( results[i] - resLayer.getNeuron(i).getOut(), 2);
			if (err > res) 
				res = err;
		}
					
		return res;
	}
	
	public boolean train(double minErr, double speed) {
		final long TIMEOUT = (long) MainWindow.TIMEOUT;
		
		long time, t0 = System.currentTimeMillis();
		double spow = 0, err = 0;
		boolean timeout = false;
		
		int n = 0;
		do {
			time = System.currentTimeMillis() - t0;
			timeout = time > TIMEOUT;
			if (timeout) break;
			
			TrainPattern set = new TrainPattern();
			
			calc( set.getArguments() );
			double[] pOut = set.getResultats();
			
			n++;
			spow += getResultError(pOut);	// MSE
			err = spow / n;
			
			// output layer error
			for (int i = 0; i < resLayer.size(); i++) {
				Neuron outNeuron = resLayer.getNeuron(i);
				double out = outNeuron.getOut();
				Function fn = outNeuron.getFunction();
				outNeuron.setDelta( (pOut[i] - out) * fn.df(out) );
			}
			
			// back error propagation
			for (int i = layers.length - 2; i >= 0; i--) {
				layers[i].recieveDeltaFrom( layers[i+1] );
				layers[i].updateWeightsOf( layers[i+1], speed );
			}
			
			//out.println( String.format("Set # %d: %s", n, set) );
			//out.println( "\terror = " + err );
		} while (err > minErr);
		
		out.println("Training time = " + Common.clip(time) + " ms");
		
		if (timeout) {
			out.println("\nTimeout error! Try to change parameters");
			return false;
		} else {
			out.println("Perceptron is trained.");
			out.println("\tpatterns used: " + n);
			out.println("\terror value  : " + err);
			out.emptyLine();
			info("after training");
			return true;
		}
	}
	
}