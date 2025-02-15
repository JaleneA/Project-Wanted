/**
 * @author Jalene Armstrong
 * @date 2025-02-15
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

    public InfoPanel(TimerPanel timerPanel, WantedPanel wantedPanel, ScorePanel scorePanel) {
        this.setBackground(Color.BLACK);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setPreferredSize(new Dimension(800, 100));
		this.setLayout(new GridLayout(1, 3, 0, 0));
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        this.add(timerPanel);
		this.add(wantedPanel);
		this.add(scorePanel);
    }
}
