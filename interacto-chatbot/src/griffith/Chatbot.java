package griffith;

import java.awt.Color;
import java.awt.Dimension;
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
class RoundButton extends JButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public RoundButton(String label) {
        super(label);
        setContentAreaFilled(false); // Remove the default background
        setOpaque(false); // Make the button transparent
        setBorderPainted(false); // Remove the default border
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray); // Change color when button is pressed
        } else {
            g.setColor(getBackground()); // Use the background color
        }
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Draw round shape
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(80, 20); // Set the preferred size
    }
}
class RoundTextField extends JTextField {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoundTextField(int columns) {
        super(columns);
        setOpaque(false); // Make the text field transparent
        setBorder(null); // Remove the default border
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); // Draw round shape
        super.paintComponent(g2);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 20); // Set the preferred size
    }
}

class Chatbot extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea ca = new JTextArea();
    private JTextField cf = new RoundTextField(20); // Use RoundTextField instead of JTextField
    private JButton b = new RoundButton("SEND"); // Use RoundButton instead of JButton
    private JLabel l = new JLabel();

    Chatbot() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setSize(400, 400);
        getContentPane().setBackground(Color.gray);
        setTitle("Fitness Program");

        // Wrap the text area inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(ca);
        scrollPane.setBounds(20, 1, 300, 310);
        add(scrollPane);

        add(cf);
        add(b);
        l.setText("SEND");
        b.setSize(80, 30);
        b.setLocation(250, 320);
        ca.setSize(300, 310);
        ca.setLocation(20, 1);
        ca.setBackground(Color.BLACK);
        cf.setSize(200, 35);
        cf.setLocation(20, 320);

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b) {
                    
                    // Wrap text
                    String text = WordUtils.wrap(cf.getText(), 50);
                                       
                    ca.setForeground(Color.white);
                    ca.append("You-->" + text + "\n");
                    cf.setText("");

                    if (text.contains("hi")) {
                        replyMeth("Hi there");
                    } else if (text.contains("how are you")) {
                        replyMeth("I'm Good :).Thank you for asking");
                    } else if (text.contains("what is your name")) {
                        replyMeth("I'm the Trending BINOD");
                    } else if (text.contains("gender")) {
                        replyMeth("I'm Female. Don't Try to Flirt with me" + "\n" + "you Fell in love :)");
                    } else if (text.contains("love you")) {
                        replyMeth("I'm Feeling Shy :) Love you too");
                    } else if (text.contains("bye")) {
                        replyMeth("Too Soon :( Anyways" + "\n" + "STAY HOME STAY SAFE ");
                    } else
                        replyMeth("I Can't Understand");

                }

            }

        });

    }

    public void replyMeth(String s) {
        ca.append("\nChatBot-->" + s + "\n");
    }

    public static void main(String[] args) {
        new Chatbot().setVisible(true);
    }
}
