import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {

    private JButton playB = new JButton ("Play");
    private JButton quitB = new JButton ("Quit");

    public ButtonPanel(LevelPanel levelPanel) {
        // Decorate Buttons - Text
		playB.setFont(new Font("Verdana", Font.BOLD, 15));
		quitB.setFont(new Font("Verdana", Font.BOLD, 15));

		// Decorate Buttons - Foreground
		playB.setForeground(Color.WHITE);
		quitB.setForeground(Color.WHITE);

		// Decorate Buttons - Background
		playB.setBackground(Color.BLACK);
		quitB.setBackground(Color.BLACK);

		// Decorate Buttons - Border
		playB.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.WHITE, 3), // Outer Border
			BorderFactory.createEmptyBorder(5, 50, 5, 50) // Inner Padding
		));

		quitB.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.WHITE, 3),
			BorderFactory.createEmptyBorder(5, 50, 5, 50)
		));

		// Disable Button Focus
		playB.setFocusPainted(false);
		quitB.setFocusPainted(false);

        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout(1, 3,90,  0));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.add(playB);
        this.add(levelPanel);
        this.add (quitB);
    }

    public JButton getPlayB() {
        return playB;
    }

    public void setPlayB(JButton playB) {
        this.playB = playB;
    }

    public JButton getQuitB() {
        return quitB;
    }

    public void setQuitB(JButton quitB) {
        this.quitB = quitB;
    }
}

