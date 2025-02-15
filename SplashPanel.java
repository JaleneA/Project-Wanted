/**
 * @author Jalene Armstrong
 * @date 2025-02-15
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SplashPanel extends JPanel {

    private JLabel titleLabel;
	private JLabel descLabel1;
	private JLabel descLabel2;
	private JLabel creditsLabel;
    
    public SplashPanel() {
        titleLabel = new JLabel("Wanted!");
		descLabel1 = new JLabel("It's easy to get lost in a crowd!");
		descLabel2 = new JLabel("Find the one who disappeared and touch him. There he is!");
		creditsLabel = new JLabel("Jalene Armstrong • 2025");

        titleLabel.setFont(new Font("Verdana", Font.BOLD, 50));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setForeground(Color.WHITE);
		descLabel1.setFont(new Font("Verdana", Font.PLAIN, 15));
		descLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		descLabel1.setForeground(Color.WHITE);
		descLabel2.setFont(new Font("Verdana", Font.PLAIN, 15));
		descLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
		descLabel2.setForeground(Color.WHITE);
		creditsLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		creditsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		creditsLabel.setForeground(Color.WHITE);

		this.add(titleLabel);
		this.add(Box.createVerticalStrut(20));
		this.add(descLabel1);
		this.add(descLabel2);
		this.add(Box.createVerticalStrut(120));
		this.add(creditsLabel);
		this.setBackground(Color.BLACK);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(75, 0, 0, 0));
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(JLabel titleLabel) {
        this.titleLabel = titleLabel;
    }

    public JLabel getDescLabel1() {
        return descLabel1;
    }

    public void setDescLabel1(JLabel descLabel1) {
        this.descLabel1 = descLabel1;
    }

    public JLabel getDescLabel2() {
        return descLabel2;
    }

    public void setDescLabel2(JLabel descLabel2) {
        this.descLabel2 = descLabel2;
    }

    public JLabel getCreditsLabel() {
        return creditsLabel;
    }

    public void setCreditsLabel(JLabel creditsLabel) {
        this.creditsLabel = creditsLabel;
    }
}
