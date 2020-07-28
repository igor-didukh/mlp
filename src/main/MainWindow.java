package main;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import data.Function;
import data.Perceptron;
import output.InfoWindow;

import javax.swing.border.LineBorder;

public class MainWindow extends JFrame implements ActionListener {
	public static final double MIN = 1, MAX = 100, TIMEOUT = 6e4;
	
	private static final long serialVersionUID = 1L;
	private static final String CHANGE_AMOUNT1 = "CHANGE_AMOUNT1", CHANGE_AMOUNT2 = "CHANGE_AMOUNT2", REINIT = "REINIT", TRAIN = "TRAIN", CALC = "CALC", EXIT = "EXIT";
	private static final int YES = JOptionPane.YES_OPTION;

	private int neuronsAmount1, neuronsAmount2;
	private JPanel contentPane;
	private JTextField txtAmount1, txtAmount2, txtOutput;
	private DoubleTextField txtInput, txtSpeed, txtError;
	private JButton btnReinit, btnTrain, btnCalc;

	public MainWindow() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				closeFrame(evt);
			}
		});
		
		setTitle("The multilayer perceptron");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setBounds(100, 100, 650, 350);
		
		contentPane = new JPanel();
		contentPane.setBackground( new Color(238, 238, 238) );
		contentPane.setBorder( new BevelBorder(BevelBorder.LOWERED, null, null, null, null) );
		contentPane.setLayout( new BorderLayout(0, 0) );
		setContentPane(contentPane);
		
		JPanel panelTitle = new JPanel();
		contentPane.add(panelTitle, BorderLayout.NORTH);
		panelTitle.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblTitle1 = new JLabel("The multilayer perceptron");
		lblTitle1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle1.setForeground(new Color(0, 0, 128));
		lblTitle1.setFont(new Font("Tahoma", Font.BOLD, 20));
		panelTitle.add(lblTitle1);
		
		JLabel lblTitle2 = new JLabel("(square root calculation)");
		lblTitle2.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle2.setForeground(new Color(0, 0, 128));
		lblTitle2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelTitle.add(lblTitle2);
		
		JPanel panelReinit = new JPanel();
		panelTitle.add(panelReinit);
		
		btnReinit = new JButton("Reinit");
		btnReinit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnReinit.setActionCommand(REINIT);
		btnReinit.addActionListener(this);
		panelReinit.add(btnReinit);
		
		JPanel panelPerceptron = new JPanel();
		contentPane.add(panelPerceptron, BorderLayout.CENTER);
		panelPerceptron.setLayout(new GridLayout(0, 4, 0, 0));
		
		JPanel panelInput = new JPanel();
		panelInput.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), " Input layer: ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelPerceptron.add(panelInput);
		panelInput.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblInput = new JLabel("Input value:");
		lblInput.setForeground(new Color(0, 0, 128));
		panelInput.add(lblInput);
		
		txtInput = new DoubleTextField(25, 1, 100);
		panelInput.add(txtInput);
		
		JPanel panelHidden1 = new JPanel();
		panelHidden1.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), " Hidden layer #1: ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelPerceptron.add(panelHidden1);
		panelHidden1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNeurons1 = new JLabel("Neurons amount:");
		lblNeurons1.setForeground(new Color(0, 0, 128));
		panelHidden1.add(lblNeurons1);
		
		txtAmount1 = new JTextField();
		txtAmount1.setEditable(false);
		txtAmount1.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtAmount1.setForeground(new Color(128, 0, 0));
		txtAmount1.setHorizontalAlignment(SwingConstants.CENTER);
		txtAmount1.setColumns(10);
		panelHidden1.add(txtAmount1);
		
		JButton btnChangeAmount1 = new JButton("Change...");
		btnChangeAmount1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnChangeAmount1.setActionCommand(CHANGE_AMOUNT1);
		btnChangeAmount1.addActionListener(this);
		panelHidden1.add(btnChangeAmount1);
		
		JPanel panelHidden2 = new JPanel();
		panelHidden2.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), " Hidden layer #2: ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelPerceptron.add(panelHidden2);
		panelHidden2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNeurons2 = new JLabel("Neurons amount:");
		lblNeurons2.setForeground(new Color(0, 0, 128));
		panelHidden2.add(lblNeurons2);
		
		txtAmount2 = new JTextField();
		txtAmount2.setEditable(false);
		txtAmount2.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtAmount2.setForeground(new Color(128, 0, 0));
		txtAmount2.setHorizontalAlignment(SwingConstants.CENTER);
		txtAmount2.setColumns(10);
		panelHidden2.add(txtAmount2);
		
		JButton btnChangeAmount2 = new JButton("Change...");
		btnChangeAmount2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnChangeAmount2.setActionCommand(CHANGE_AMOUNT2);
		btnChangeAmount2.addActionListener(this);
		panelHidden2.add(btnChangeAmount2);
		
		JPanel panelOutput = new JPanel();
		panelOutput.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), " Output layer: ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelPerceptron.add(panelOutput);
		panelOutput.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblOutput = new JLabel("Square root:");
		lblOutput.setForeground(new Color(0, 0, 128));
		panelOutput.add(lblOutput);
		
		txtOutput = new JTextField();
		txtOutput.setEditable(false);
		txtOutput.setEditable(false);
		txtOutput.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtOutput.setForeground(new Color(128, 0, 0));
		txtOutput.setHorizontalAlignment(SwingConstants.CENTER);
		txtOutput.setColumns(10);
		panelOutput.add(txtOutput);
		
		JLabel lblNewLabel_1 = new JLabel("");
		panelPerceptron.add(lblNewLabel_1);
		
		JPanel panelTrain = new JPanel();
		panelPerceptron.add(panelTrain);
		
		JLabel lblSpeed = new JLabel("Train speed:");
		lblSpeed.setForeground(new Color(0, 0, 128));
		panelTrain.add(lblSpeed);
		
		txtSpeed = new DoubleTextField(0.7, 0.4, 0.9);
		txtSpeed.setColumns(5);
		panelTrain.add(txtSpeed);
		
		JLabel lblError = new JLabel("Result error:");
		lblError.setForeground(new Color(0, 0, 128));
		panelTrain.add(lblError);
		
		txtError = new DoubleTextField(0.0005, 0.00005, 0.01);
		txtError.setColumns(5);
		panelTrain.add(txtError);
		
		btnTrain = new JButton("Train perceptron");
		btnTrain.setEnabled(true);
		btnTrain.setActionCommand(TRAIN);
		btnTrain.addActionListener(this);
		panelTrain.add(btnTrain);
		
		JLabel lblInfo = new JLabel("up to " + (int) (TIMEOUT / 1000) + " sec.");
		panelTrain.add(lblInfo);
		
		JPanel panelCalc = new JPanel();
		panelPerceptron.add(panelCalc);
		
		btnCalc = new JButton("Calc square root");
		btnCalc.setActionCommand(CALC);
		btnCalc.addActionListener(this);
		panelCalc.add(btnCalc);
		
		neuronsAmount1 = 2;
		neuronsAmount2 = 0;
		onAmountChanged();
	}
	
	// Set form controls properties & init the perceptron
	private void onAmountChanged() {
		txtInput.setEnabled(false);
		txtAmount1.setText("" + neuronsAmount1);
		txtAmount2.setText("" + neuronsAmount2);
		txtOutput.setText("");
		txtSpeed.setEnabled(true);
		txtError.setEnabled(true);
		btnReinit.setEnabled(false);
		btnTrain.setEnabled(true);
		btnCalc.setEnabled(false);
		
		int[] layersAmounts;
		String layersInfo;
		if (neuronsAmount2 == 0) {
			// input layer (1 neuron), hiddenlayer (<neuronsAmount1> neurons), output layer (1 neuron)
			layersAmounts = new int[] {1, neuronsAmount1, 1};
			layersInfo = String.format("1; %d; 1", neuronsAmount1);
		} else {
			// input layer (1 neuron), hiddenlayer #1 (<neuronsAmount1> neurons), hiddenlayer #2 (<neuronsAmount2> neurons), output layer (1 neuron)
			layersAmounts = new int[] {1, neuronsAmount1, neuronsAmount2, 1};
			layersInfo = String.format("1; %d; %d; 1", neuronsAmount1, neuronsAmount2);
		}
		
		Perceptron.get().init(layersAmounts, Function.SIGMOID);
		Common.showInformMessage(this, "The perceptron is initialized. \nNumber of neurons at layers: " + layersInfo);
	}
	
	// Show dialog to change vars amount
	private int changeNeuronsAmount(String layer, int val, int min) {
		int n = Common.showNumberDialog(this, "Select neurons amount", "Neurons amount", val, min);
		
		if ( (n < 0) || (n == val) ) return -1;
		
		if ( Common.showConfirmDialog(this, "Change " + layer + " neurons amount from " + val + " to " + n + "?", "Change neurons amount") == YES )
			return n;
		else
			return -1;
	}
	
    @Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
		
		case CHANGE_AMOUNT1:
			int a1 = changeNeuronsAmount("Layer #1", neuronsAmount1, 1);
			if (a1 != -1) {
				neuronsAmount1 = a1;
				onAmountChanged();
			}
			break;
			
		case CHANGE_AMOUNT2:
			int a2 = changeNeuronsAmount("Layer #2", neuronsAmount2, 0);
			if (a2 != -1) {
				neuronsAmount2 = a2;
				onAmountChanged();
			}
			break;
			
		case REINIT:
			if ( Common.showConfirmDialog(this, "Do you want to reinitialize the perceptron\n(all training results will be lost)?", "Reinit") == YES )
				onAmountChanged();
			break;
			
		case TRAIN:
			boolean trainResult = Perceptron.get().train(
						txtError.getDouble(),
						txtSpeed.getDouble()
					); 
			
			Common.showFrame( new InfoWindow( Perceptron.get().getOut() ) );
			
			if (trainResult) {
				
				txtInput.setEnabled(true);
				txtSpeed.setEnabled(false);
				txtError.setEnabled(false);
				btnReinit.setEnabled(true);
				btnTrain.setEnabled(false);
				btnCalc.setEnabled(true);
			} else 
				Common.showErrorMessage(this, "Error training perceptron!");
			break;
			
		case CALC:
			// in this problem input layer has only one neuron
			Perceptron.get().calc(
					new double[] { txtInput.getDouble() / 100 } // normalization
				);
						
			// in this problem output layer has only one neuron
			double[] res = Perceptron.get().result();
			txtOutput.setText( "" + Common.clip(res[0] * 10) );
			
			break;
        
        case EXIT:
        	closeFrame(e);
        	break;
		}
	}
    
	// Action on close main window
	private void closeFrame(java.awt.AWTEvent evt) {
		//if ( Common.showConfirmDialog(this, "You really want to exit?", "Exit") == JOptionPane.YES_OPTION )
			System.exit(0);
    }
	
	// Text field with restriction and listeners
	class DoubleTextField extends JTextField {
		private static final long serialVersionUID = 1L;
		private double min;
		private double max;
		
		DoubleTextField() {
			this(0,0,0);
		}
		
		DoubleTextField(double val, double min, double max) {
			super();
			this.min = min;
			this.max = max;
			setFont(new Font("Tahoma", Font.BOLD, 14));
			setForeground(new Color(128, 0, 0));
			setHorizontalAlignment(SwingConstants.CENTER);
			setColumns(10);
			setRestrictions("not_neg_double");
			setText( Common.clip(val) );
			addListeners(this);
		}
		
		private void checkValue(DoubleTextField res) {
			double d = res.getDouble();
			if ( (d < min) || (d > max) )
				d = min;
			res.setText( Common.clip(d) );
		}
		
		private void addListeners(DoubleTextField res) {
			
			res.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					res.selectAll();
				}
			});
			
			res.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					checkValue(res);
				}
			});
			
			res.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					checkValue(res);
				}
			});
			
		}
		
		private void setRestrictions(String restr) {
			Common.setRestrictions(this, restr);
		}
		
		double getDouble() {
			return Common.parseDouble( this.getText().trim() );
		}

	}
}