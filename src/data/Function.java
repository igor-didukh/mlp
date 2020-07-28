package data;

public abstract class Function {
	public static final String TYPE_STUB = "Stub", TYPE_SIGMOID = "Sigmoid";
	
	public abstract String getType();
	public abstract double calc(double x);
	public abstract double df(double x);
	
	public static final Function STUB = 
		new Function() {
			@Override
			public String getType() {
				return TYPE_STUB;
			}
			
			@Override
			public double calc(double x) {
				return x;
			}

			@Override
			public double df(double x) {
				return 1;
			}
		};
	
	public static final Function SIGMOID =
		new Function() {
			@Override
			public String getType() {
				return TYPE_SIGMOID;
			}
			
			@Override
			public double calc(double x) {
				return 1 / ( 1 + Math.exp(-x) );
			}

			@Override
			public double df(double f) {
				return (1 - f) * f;
			}
		};
	
}