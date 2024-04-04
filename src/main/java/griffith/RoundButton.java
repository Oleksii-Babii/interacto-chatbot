/**
 * @author Ihor Tryndey 3105023 , Oleksii Babii 3104904
 * @since 2024
 * @version 1.0
 */
package griffith;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

/**
 * @author Ihor Tryndey 3105023 , Oleksii Babii 3104904
 * Class RoundButton that extends JButton 
 * The main idea of this class is to create round button instead of default
 */
public class RoundButton extends JButton {

	private static final long serialVersionUID = 2L;

	/**
	 * Parametrised constructor that takes as parameter its name(button)
	 * @param label
	 */
	public RoundButton(String label) {
        super(label);
        setContentAreaFilled(false); // Remove the default background
        setOpaque(false); // Make the button transparent
        setBorderPainted(false); // Remove the default border
    }

	/**
	 * Overrided method that prints button 
	 */
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
        	// Change color when button is pressed
            g.setColor(Color.lightGray); 
        } else {
        	// Use the background color
            g.setColor(getBackground()); 
        }
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Draw round shape
        super.paintComponent(g);
    }

    /**
     * Overrided method that sets size of the button
     */
    @Override
    public Dimension getPreferredSize() {
    	// Set the preferred size
        return new Dimension(80, 20); 
    }
}