package griffith;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.commons.lang3.text.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class Chatbot extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea ca = new JTextArea();
    private JTextField cf = new RoundTextField(20); // Use RoundTextField instead of JTextField
    private JButton b = new RoundButton("SEND"); // Use RoundButton instead of JButton
    private JLabel l = new JLabel();

    public Chatbot() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setSize(500, 430);
        setLocation(500,300);
        getContentPane().setBackground(Color.gray);
        setTitle("Fitness Program");

        // Wrap the text area inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(ca);
        scrollPane.setBounds(20, 20, 440, 280);
        add(scrollPane);

        add(cf);
        add(b);
        l.setText("SEND");
        b.setSize(90, 30);
        b.setLocation(350, 320);
        ca.setSize(400, 310);
        ca.setLocation(20, 1);
        ca.setBackground(Color.white);
        cf.setSize(300, 30);
        cf.setLocation(20, 320);
        
        cf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b) {
                	sendMessage();
                	
                }

            }

        });
        	
    }
    

    private void sendMessage() {
         String input = WordUtils.wrap(cf.getText(), 70);
         
         ca.setForeground(Color.black);
         ca.setFont(new Font("SANS_SERIF", Font.BOLD, 12));
         ca.append("\nYou-->\n" + input + "\n");
         cf.setText("");

         if (input.contains("hi")) {
            output("Hi there");
         } else if (input.contains("how are you")) {
            output("I'm Good :). Thank you for asking");
         } else if (input.contains("what is your name")) {
            output("I'm the Trending BINOD");
         } else if (input.contains("gender")) {
            output("I'm Female. Don't Try to Flirt with me" + "\n" + "you Fell in love :)");
         } else if (input.contains("love you")) {
                	        output("I'm Feeling Shy :) Love you too");
         } else if (input.contains("bye")) {
            output("Too Soon :( Anyways" + "\n" + "STAY HOME STAY SAFE ");
         } else
            output("I Can't Understand");
         
         }

    public void output(String s) {
        ca.append("\nChatBot-->\n" + s + "\n");
    }

    public static void main(String[] args) {
        new Chatbot().setVisible(true);
    }
}
