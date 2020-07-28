package data;

import main.MainWindow;

class TrainPattern {
	private double[] arguments;
	private double[] resultats;
	
	TrainPattern() {
		double p = MainWindow.MIN + Math.random() * (MainWindow.MAX - MainWindow.MIN);
		arguments = new double[] {p / 100};
		
		double q = Math.sqrt(p);
		resultats = new double[] {q / 10};
	}

	public double[] getArguments() {
		return arguments;
	}
	
	public double[] getResultats() {
		return resultats;
	}
	
	private String makeStr(double[] arr, String title) {
		String res = title + " = {";
		
		for (double t : arr)
			res += "" + t + "; ";
		
		return res.substring(0, res.length()-2) + "}";
	}

	public String toString() {
		return "\n\t" + makeStr(arguments, "args") + "\n\t" + makeStr(resultats, "res");
	}
}