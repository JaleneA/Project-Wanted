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

public class TimerPanel extends JPanel {
   
    private int timeInteger = 5;
    private JLabel timerLabel;
    private JLabel timerValue;

    public TimerPanel(){
        timerLabel = new JLabel ("Timer");
        timerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        timerValue = new JLabel(String.valueOf(timeInteger));
		timerValue.setAlignmentX(Component.CENTER_ALIGNMENT);
		timerValue.setBackground(Color.WHITE);

        timerValue.setFont(new Font("Verdana", Font.BOLD, 40));

        this.add(timerValue);
		this.add(timerLabel);
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

    }

    public int getTimeInteger() {
        return timeInteger;
    }

    public void setTimeInteger(int timeInteger) {
        this.timeInteger = timeInteger;
        timerValue.setText(String.valueOf(timeInteger));
    }

    public JLabel getTimerLabel() {
        return timerLabel;
    }

    public void setTimerLabel(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    public JLabel getTimerValue() {
        return timerValue;
    }

    public void setTimerValue(JLabel timerValue) {
        this.timerValue = timerValue;
    }
}
