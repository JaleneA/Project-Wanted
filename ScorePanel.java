/**
 * @author Jalene Armstrong
 * @date 2025-02-15
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
    private int scoreInteger = 0;
    private JLabel scoreLabel;
	private JLabel scoreValue;

    public ScorePanel(){
        scoreLabel = new JLabel("Score");
        scoreLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreValue = new JLabel(String.valueOf(scoreInteger));
		scoreValue.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreValue.setBackground(Color.WHITE);
        scoreValue.setFont(new Font("Verdana", Font.BOLD, 40));

		this.add(scoreValue);
		this.add(scoreLabel);
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
		
    }

    public int getScoreInteger() {
        return scoreInteger;
    }

    public void setScoreInteger(int scoreInteger) {
        this.scoreInteger = scoreInteger;
        scoreValue.setText(String.valueOf(scoreInteger));
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public JLabel getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(JLabel scoreValue) {
        this.scoreValue = scoreValue;
    }
}
