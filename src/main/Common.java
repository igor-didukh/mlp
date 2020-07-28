package main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class Common {
	public static final String TXT = "txt", HTML = "html";
	public static int MAX_AMOUNT = 9;
	
	// Show frame on the center of screen 
	public static void showFrame(JFrame frame) {
		int screenWidth = 0, screenHeight = 0;
		
		GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (GraphicsDevice graphicsDevice : screenDevices) {
            screenWidth = graphicsDevice.getDefaultConfiguration().getBounds().width;
            screenHeight = graphicsDevice.getDefaultConfiguration().getBounds().height;
        }
		
        Rectangle r = frame.getBounds();
		
		int frameWidth = r.width, frameHeight = r.height;
		int posX = (screenWidth - frameWidth) / 2;
		int posY = (screenHeight - frameHeight) / 2 - 40;
		
		frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.setBounds(posX, posY, r.width, r.height);
		//frame.setState(Frame.ICONIFIED);
		
		frame.setVisible(true);
	}
	
	// String --> int
	public static int parseInt(String s) {
		int n = 0;
		try {
			n = Integer.parseInt(s);
		} catch (Exception e) {}
		return n;
	}
	
	// String --> double
	public static double parseDouble(String s) {
		double d = 0;
		try {
			d = Double.parseDouble(s);
		} catch (Exception e) {}
		return d;
	}
	
	// Inform message
	public static void showInformMessage(Component cmp, String message) {
		JOptionPane.showMessageDialog(cmp, message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// Error message
	public static void showErrorMessage(Component cmp, String message) {
		JOptionPane.showMessageDialog(cmp, message, "Error!", JOptionPane.ERROR_MESSAGE);
	}
	
	// Yes-No dialog
	public static int showConfirmDialog(Component cmp, String message, String title) {
		Object[] options = { "Yes", "No" };
        return JOptionPane.showOptionDialog(
        		cmp, message, title,
        		JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
        		null, options, options[1]
        );
	}
	
	// To select number from 2 to MAX_AMOUNT
	public static int showNumberDialog(Component cmp, String message, String title, int initValue, int min) {
		Integer[] numbers = new Integer[MAX_AMOUNT+1];
		int pos = 0;
		
		for (int i = min; i <= MAX_AMOUNT; i++) {
			numbers[i-min] = i;
			if (i == initValue)
				pos = i-min;
		}
			
		Integer num = (Integer) JOptionPane.showInputDialog(
			cmp, 
	        message,
	        title,
	        JOptionPane.QUESTION_MESSAGE, 
	        null, 
	        numbers, 
	        numbers[pos]
		);
		
        return num != null ? num.intValue() : -1; 
	}
	
	// Round double value
	public static double Round(double value, int digits) {
		return new BigDecimal(value).setScale(digits, RoundingMode.HALF_UP).doubleValue();
	}
	
	//Text filter to set allowed symbols in text field
	private static DocumentFilter getTextFilter(String filterType) {
		String bannedSymbols;
		
		switch (filterType.toUpperCase()) {
		case "DOUBLE":
			bannedSymbols = "[^0123456789.-]";
			break;
		case "NOT_NEG_DOUBLE":
			bannedSymbols = "[^0123456789.]";
			break;
		default:
			bannedSymbols = "[^0123456789]";
			break;
		}
		
		return new DocumentFilter() {
		    @Override
		    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		        string = string.replaceAll(bannedSymbols, "");
		        super.insertString(fb, offset, string, attr);
		    }

		    @Override
		    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		        text = text.replaceAll(bannedSymbols, "");
		        super.replace(fb, offset, length, text, attrs);
		    }
		};
		
	}
	
	// Set restriction for text field with numeric values
	public static void setRestrictions(JTextField field, String filterType) {
		((AbstractDocument) field.getDocument()).setDocumentFilter(getTextFilter(filterType));
	}
	
	public static String clip(double f) {
		String res = String.format("%.6f", f);
		
		for (int i = 0; i < 6; i++)
			if ( res.endsWith("0") )
				res = res.substring(0, res.length() - 1);
			
		if ( res.endsWith(",") )
			res = res.substring(0, res.length() - 1);
		
		res = res.replace(",", ".");
		res = res.replace(" ", "");
		res = res.equals("-0") ? "0" : res;
		
		return res;
	}
	
}