import java.awt.*;			// GUI Objects
import java.awt.event.*;	// Layout Managers
import javax.swing.*;		// Respond to GUI Events

public class GameWindow extends JFrame implements ActionListener, MouseListener //, KeyListener
{
	// Declare Info
	private int timeInteger = 10;
	private int scoreInteger = 0;

	// Declare Static Labels 
	private JLabel timerLabel;
	private JLabel wantedIntel;
	private JLabel scoreLabel;

	// Declare Dynamic Labels
	private JLabel timerValue;
	private JLabel wantedShape;
	private JLabel scoreValue;

	// Declare Buttons
	private JButton playB;
	private JButton quitB;

	private Container mainContainer;

	private JPanel mainPanel;
	private GamePanel gamePanel;

	public GameWindow() {

		setTitle ("Wanted!");
		setSize (800, 600);

		// Create Static Labels
		timerLabel = new JLabel ("Timer");
		wantedIntel = new JLabel("Hates Circles");
		scoreLabel = new JLabel("Score");

		// Decorate Static Labels
		timerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		wantedIntel.setFont(new Font("Verdana", Font.PLAIN, 12));
		scoreLabel.setFont(new Font("Verdana", Font.PLAIN, 12));

		// Create Dynamic Labels
		timerValue = new JLabel(String.valueOf(timeInteger));
		wantedShape = new JLabel("?");
		scoreValue = new JLabel(String.valueOf(scoreInteger));

		// Decorate Dynamic Labels
		timerValue.setFont(new Font("Verdana", Font.BOLD, 40));
		wantedShape.setFont(new Font("Verdana", Font.BOLD, 50));
		scoreValue.setFont(new Font("Verdana", Font.BOLD, 40));

		// Create Buttons
		playB = new JButton ("Play");
		quitB = new JButton ("Quit");

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
			BorderFactory.createEmptyBorder(10, 50, 10, 50) // Inner Padding
		));

		quitB.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.WHITE, 3),
			BorderFactory.createEmptyBorder(10, 50, 10, 50)
		));

		// Disable Button Focus
		playB.setFocusPainted(false);
		quitB.setFocusPainted(false);

		// Add Listener To Each Button
		playB.addActionListener(this);
		quitB.addActionListener(this);

		// Create mainPanel
		mainPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
		mainPanel.setLayout(flowLayout);
		mainPanel.setBackground(Color.BLACK);

		GridLayout gridLayout;

		// Create gamePanel
		gamePanel = new GamePanel();
		gamePanel.setBackground(Color.BLACK);
		gamePanel.setPreferredSize(new Dimension(680, 394));
		gamePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
		gamePanel.createGameEntities();

		// Create infoPanel
		JPanel infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(800, 100));
		infoPanel.setLayout(new GridLayout(1, 3, 0, 0));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		// Create Individual Panels For Each Info Component
		JPanel timerPanel = new JPanel();
		timerPanel.add(timerValue);
		timerPanel.add(timerLabel);
		timerValue.setBackground(Color.WHITE);
		timerPanel.setBackground(Color.WHITE);
		timerValue.setAlignmentX(Component.CENTER_ALIGNMENT);
		timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
		timerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

		JPanel wantedPanel = new JPanel();
		wantedPanel.add(wantedIntel);
		wantedPanel.add(wantedShape);
		wantedShape.setBackground(Color.WHITE);
		wantedPanel.setBackground(Color.WHITE);
		wantedIntel.setAlignmentX(Component.CENTER_ALIGNMENT); 
		wantedShape.setAlignmentX(Component.CENTER_ALIGNMENT); 
		wantedPanel.setLayout(new BoxLayout(wantedPanel, BoxLayout.Y_AXIS));
		wantedPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

		JPanel scorePanel = new JPanel();
		scorePanel.add(scoreValue);
		scorePanel.add(scoreLabel);
		scoreValue.setBackground(Color.WHITE);
		scorePanel.setBackground(Color.WHITE);
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreValue.setAlignmentX(Component.CENTER_ALIGNMENT);
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

		infoPanel.add(timerPanel);
		infoPanel.add(wantedPanel);
		infoPanel.add(scorePanel);
		infoPanel.setBackground(Color.BLACK);
		infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create buttonPanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.BLACK);
		gridLayout = new GridLayout(1, 2,250,  0);
		buttonPanel.setLayout(gridLayout);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Add Buttons To buttonPanel
		buttonPanel.add (playB);
		buttonPanel.add (quitB);

		mainPanel.add(infoPanel);
		mainPanel.add(gamePanel);
		mainPanel.add(buttonPanel);

		gamePanel.addMouseListener(this);
		// mainPanel.addKeyListener(this);

		// Add mainPanel To Window Surface
		mainContainer = getContentPane();
		mainContainer.add(mainPanel);

		// Window Properties
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// ActionListener Interface
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals(playB.getText()))
			gamePanel.drawGameEntities();

		if (command.equals(quitB.getText()))
			System.exit(0);
	}

	// KeyListener Interface
	// public void keyPressed(KeyEvent e) {

	// }

	// public void keyReleased(KeyEvent e) {

	// }

	// public void keyTyped(KeyEvent e) {

	// }

	// MouseListener Interface

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	
	}

	@Override
	public void mouseExited(MouseEvent e) {
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	
	}
}