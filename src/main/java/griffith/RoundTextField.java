package griffith;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

class RoundTextField extends JTextField {

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
