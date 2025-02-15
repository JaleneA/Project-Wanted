/**
 * @author Jalene Armstrong
 * @date 2025-02-15
 */

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;

public class MainPanel extends JPanel {
    public MainPanel(InfoPanel infoPanel, GamePanel gamePanel, ButtonPanel buttonPanel) {
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setBackground(Color.BLACK);
        this.add(infoPanel);
		this.add(gamePanel);
		this.add(buttonPanel);
    }
}
