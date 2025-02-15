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

public class WantedPanel extends JPanel {
    private JLabel wantedIntel;

    public WantedPanel(ShapePanel shapePanel) {
        wantedIntel = new JLabel("Wanted");
		wantedIntel.setFont(new Font("Verdana", Font.PLAIN, 12));
		wantedIntel.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		this.add(wantedIntel);
        this.add(shapePanel);
    }

    public JLabel getWantedIntel() {
        return wantedIntel;
    }

    public void setWantedIntel(JLabel wantedIntel) {
        this.wantedIntel = wantedIntel;
    }
}
