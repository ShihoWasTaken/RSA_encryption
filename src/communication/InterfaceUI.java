package communication;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

public class InterfaceUI extends JFrame {
	
	static protected Logger logger =  Logger.getLogger(InterfaceUI.class);

	private static final long serialVersionUID = 1L;
	private JEditorPane textArea;
	private JEditorPane textArea_log;
	private String text_textarea, text_textarealog;
    private JTextField inputTextField;
    private JButton sendButton;
    
    public void addMessage(String message){
    		text_textarea = text_textarea + message;
			textArea.setText( text_textarea);
    }
    
    public void addLog(String message){
    	text_textarealog = text_textarealog + "<p style='word-wrap: break-word;width:450px'>" + message  + "</p>";
	    textArea_log.setText( text_textarealog);
    }
   
    public void enabledButton(){
    	sendButton.setEnabled(true);
    }
    
	public InterfaceUI(final SocketClient client, String title) {
		//Init Variable 
		text_textarea = text_textarealog = "";
		
		setTitle(title);
        setSize(650, 500);
        
        JPanel totalGUI = new JPanel();
        totalGUI.setLayout(null);
        
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setLocation(0, 328);
        panel2.setSize(650, 160);
        panel2.setBorder(new TitledBorder(null, "Envoyer message"));
        totalGUI.add(panel2);

        /* Textfield */
        JLabel comboLbl2 = new JLabel("Message :");
        comboLbl2.setLocation(12, 33);
        comboLbl2.setSize(197, 34);
        
        inputTextField = new JTextField();
        inputTextField.setLocation(188, 34);
        inputTextField.setSize(450, 34);
        
        panel2.add(comboLbl2);
        panel2.add(inputTextField);
        
        sendButton = new JButton("Envoyer");
        sendButton.setBounds(250, 120, 150, 25);
        sendButton.setEnabled(false);
        
        //Action for the inputTextField and the goButton
        ActionListener sendListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = inputTextField.getText();
                if (str != null && str.trim().length() > 0)
                	client.send(str);
                
                inputTextField.selectAll();
                inputTextField.requestFocus();
                inputTextField.setText("");
            }
        };
        inputTextField.addActionListener(sendListener);
        sendButton.addActionListener(sendListener);


        panel2.add(sendButton);
        
        getContentPane().add(totalGUI);
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 0, 650, 310);
        totalGUI.add(tabbedPane);
        
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        textArea = new JEditorPane();
        textArea.setContentType("text/html");
        textArea.setLocation(12, 12);
        textArea.setEditable(false);
        textArea.setSize(626, 271);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane (textArea, 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tabbedPane.add("Messages",scroll);

        JPanel panel3 = new JPanel();
        panel3.setLayout(null);
        textArea_log = new JEditorPane();
        textArea_log.setContentType("text/html");
        textArea_log.setLocation(12, 12);
        textArea_log.setEditable(false);
        textArea_log.setSize(626, 271);
        textArea_log.setBackground(Color.GRAY);
        JScrollPane scroll2 = new JScrollPane (textArea_log, 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tabbedPane.add("Logs",scroll2);
        
        
        add(totalGUI);
	}
}