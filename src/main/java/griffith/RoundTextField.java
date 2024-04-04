/**
 * @author Ihor Tryndey 3105023 , Oleksii Babii 3104904
 * @since 2024
 * @version 1.0
 */
package griffith;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

/**
 * @author Ihor Tryndey 3105023 , Oleksii Babii 3104904
 * Class RoundTextField which extends JTextField 
 * The main idea of this class is to create round text field to enter a statement 
 * inside chat bot
 */
class RoundTextField extends JTextField {

	private static final long serialVersionUID = 1L;

	/**
	 * Parametrised constructor which takes columns as parameter
	 * @param columns
	 */
	public RoundTextField(int columns) {
        super(columns);
        // Make the text field transparent
        setOpaque(false); 
        // Remove the default border
        setBorder(null); 
    }

	/**
	 * Overrided method that prints field 
	 */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); // Draw round shape
        super.paintComponent(g2);
    }

    /**
     * Overrided method that sets size of the text field
     */
    @Override
    public Dimension getPreferredSize() {
    	// Set the preferred size
        return new Dimension(200, 20);         
    }
}
