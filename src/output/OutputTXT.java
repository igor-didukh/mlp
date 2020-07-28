package output;

import main.Common;

public class OutputTXT extends Output {
	
	public OutputTXT() {}

	@Override
	public void printH1(String s) {
		s += (s.length() % 2 == 1) ? " " : "";
		
		String ss = "";
		for (int i = 0; i < (50 - s.length()) / 2; i++)
			ss += "=";
		
		println(ss + " " + s + " " + ss);
		emptyLine();
	}

	@Override
	public void printH2(String s) {
		println("== " + s + " ==");
		emptyLine();
	}

	@Override
	public void printH3(String s) {
		println(s + ": ");
	}
	
	@Override
	public void printP(String s) {
		println(s);
	}

	@Override
	public void printBR(String s) {
		println(s);
	}
	
	@Override
	public void printWarning(String s1, String s2, String s3) {
		emptyLine();
		println(s1);
		println(s2);
		
		emptyLine();
		printH3(s3);
	}

	@Override
	public String getMode() {
		return Common.TXT;
	}
	
}