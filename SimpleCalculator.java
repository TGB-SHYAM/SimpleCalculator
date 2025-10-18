import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple calculator application built with Java Swing.
 * It supports addition, subtraction, multiplication, and division.
 */
public class SimpleCalculator extends JFrame implements ActionListener {

    // The text field to display numbers and results
    private JTextField display;

    // Variables to store the first number, the pending operation,
    // and a flag to check if we are starting a new number input.
    private double num1 = 0;
    private char operator = ' ';
    private boolean isStartingNewNumber = true;

    /**
     * Constructor to set up the calculator's GUI.
     */
    public SimpleCalculator() {
        // --- 1. Set up the main window (JFrame) ---
        super("Simple Calculator");
        setSize(300, 400); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
        setLayout(new BorderLayout(5, 5)); // Use BorderLayout with gaps
        setLocationRelativeTo(null); // Center the window

        // --- 2. Create the display field ---
        display = new JTextField("0");
        display.setEditable(false); // User cannot type directly into it
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH); // Add display to the top

        // --- 3. Create the button panel ---
        JPanel buttonPanel = new JPanel();
        // Use a 4x4 grid for the buttons
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5)); 

        // Define all the button labels in order
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        };

        // Create and add all buttons
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            // Add 'this' class as the listener for button clicks
            button.addActionListener(this); 
            buttonPanel.add(button);
        }

        // Add the button panel to the center of the window
        add(buttonPanel, BorderLayout.CENTER);

        // --- 4. Make the window visible ---
        setVisible(true);
    }

    /**
     * This method is called whenever a button is clicked.
     * It contains all the calculator's logic.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the text from the button that was pressed
        String command = e.getActionCommand();

        // Use a try-catch block for safe number parsing
        try {
            // Check if the command is a number
            if (Character.isDigit(command.charAt(0))) {
                if (isStartingNewNumber) {
                    display.setText(command);
                    isStartingNewNumber = false;
                } else {
                    display.setText(display.getText() + command);
                }
            } 
            // Check if the command is the "Clear" button
            else if (command.equals("C")) {
                display.setText("0");
                num1 = 0;
                operator = ' ';
                isStartingNewNumber = true;
            } 
            // Check if the command is the "Equals" button
            else if (command.equals("=")) {
                if (operator != ' ') {
                    double num2 = Double.parseDouble(display.getText());
                    double result = 0;

                    switch (operator) {
                        case '+':
                            result = num1 + num2;
                            break;
                        case '-':
                            result = num1 - num2;
                            break;
                        case '*':
                            result = num1 * num2;
                            break;
                        case '/':
                            if (num2 == 0) {
                                display.setText("Error"); // Handle division by zero
                            } else {
                                result = num1 / num2;
                            }
                            break;
                    }

                    // Display the result
                    if (!display.getText().equals("Error")) {
                        display.setText(String.valueOf(result));
                    }
                    
                    num1 = result; // Store result for chained operations
                    operator = ' ';
                    isStartingNewNumber = true;
                }
            } 
            // Otherwise, the command must be an operator (+, -, *, /)
            else {
                // If we are not in the middle of an operation, store the first number
                if (!isStartingNewNumber) {
                    num1 = Double.parseDouble(display.getText());
                }
                // Store the operator and prepare for the next number
                operator = command.charAt(0);
                isStartingNewNumber = true;
            }
        } catch (NumberFormatException ex) {
            // Handle any unexpected number parsing errors
            display.setText("Error");
            isStartingNewNumber = true;
        }
    }

    /**
     * The main method to run the application.
     */
    public static void main(String[] args) {
        // Run the GUI code on the Event Dispatch Thread (EDT)
        // This is the standard and safest way to start a Swing application.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleCalculator();
            }
        });
    }
}