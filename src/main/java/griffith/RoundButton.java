package griffith;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class RoundButton extends JButton {

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
