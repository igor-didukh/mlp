package output;

public abstract class Output {
	String output = "";
	
	public void print(String s) {
		output += s;
	}
	
	public void println(String s) {
		output += s + "\n";
	}
	
	public void emptyLine() {
		println("");
	}
	
	public String getOut() {
		return output;
	}
	
	public abstract void printH1(String s);
	public abstract void printH2(String s);
	public abstract void printH3(String s);
	public abstract void printP(String s);
	public abstract void printBR(String s);
	public abstract void printWarning(String s1, String s2, String s3);
	public abstract String getMode();
}
