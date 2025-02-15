/**
 * @author Jalene Armstrong
 * @date 2025-02-15
 */

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LevelPanel extends JPanel {

    private int levelInteger = 0;
    private JLabel currentLevel;

    public LevelPanel() {
        currentLevel = new JLabel(String.valueOf(levelInteger));
        currentLevel.setFont(new Font("Verdana", Font.BOLD, 40));
		currentLevel.setForeground(Color.WHITE);
		this.setBackground(Color.BLACK);
        this.add(currentLevel);
    }

    public int getLevelInteger() {
        return levelInteger;
    }

    public void setLevelInteger(int levelInteger) {
        this.levelInteger = levelInteger;
        currentLevel.setText(String.valueOf(levelInteger));
    }

    public JLabel getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(JLabel currentLevel) {
        this.currentLevel = currentLevel;
    }
}
