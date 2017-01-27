package run;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class ClientUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
    private JTextField inputTextField;
    private JButton sendButton;
    
    public void addMessage(String message){
    	textArea.setText(textArea.getText() + "\r\n" + message);
    }
   
    public void enabledButton(){
    	sendButton.setEnabled(true);
    }
    
	public ClientUI(final SocketClient client, String title) {
        setTitle(title);
        setSize(450, 300);
        
        JPanel totalGUI = new JPanel();
        totalGUI.setLayout(null);
        
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setSize(450, 150);
        totalGUI.add(panel);
        
        /* textarea */
        textArea = new JTextArea();
        textArea.setLocation(12, 12);
        textArea.setEnabled(false);
        textArea.setSize(426, 126);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(Color.DARK_GRAY);
        panel.add(textArea);
        
        
        
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setLocation(0, 162);
        panel2.setSize(450, 120);
        panel2.setBorder(new TitledBorder(null, "Envoyer message"));
        totalGUI.add(panel2);

        /* Textfield */
        JLabel comboLbl2 = new JLabel("Message :");
        comboLbl2.setLocation(12, 33);
        comboLbl2.setSize(197, 34);
        
        inputTextField = new JTextField();
        inputTextField.setLocation(188, 34);
        inputTextField.setSize(250, 34);
        
        panel2.add(comboLbl2);
        panel2.add(inputTextField);
        
        sendButton = new JButton("Envoyer");
        sendButton.setBounds(172, 79, 117, 25);
        sendButton.setEnabled(false);
        
        //Action for the inputTextField and the goButton
        ActionListener sendListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = inputTextField.getText();
                if (str != null && str.trim().length() > 0)
                	client.send("message", str);
                
                inputTextField.selectAll();
                inputTextField.requestFocus();
                inputTextField.setText("");
            }
        };
        inputTextField.addActionListener(sendListener);
        sendButton.addActionListener(sendListener);


        panel2.add(sendButton);
        
        add(totalGUI);
	}
}