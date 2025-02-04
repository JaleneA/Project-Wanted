import java.awt.*;			// GUI Objects
import java.awt.event.*;	// Layout Managers
import javax.swing.*;		// Respond to GUI Events

public class GameWindow extends JFrame implements ActionListener, MouseListener //, KeyListener
{
	// Declare Info
	private int timeInteger = 10;
	private int scoreInteger = 0;
	private int levelInteger = 0;

	// Declare Static Labels 
	private JLabel timerLabel;
	private JLabel wantedIntel;
	private JLabel scoreLabel;

	// Declare Dynamic Labels
	private JLabel timerValue;
	private JLabel scoreValue;
	private JLabel currentLevel;

	// Declare Buttons
	private JButton playB;
	private JButton quitB;

	private Container mainContainer;
	private Timer timer;

	private JPanel mainPanel;
	private GamePanel gamePanel;
	private ShapePanel shapePanel;

	public GameWindow() {

		setTitle ("Wanted!");
		setSize (800, 600);

		// Create Static Labels
		timerLabel = new JLabel ("Timer");
		wantedIntel = new JLabel("Wanted");
		scoreLabel = new JLabel("Score");

		// Decorate Static Labels
		timerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		wantedIntel.setFont(new Font("Verdana", Font.PLAIN, 12));
		scoreLabel.setFont(new Font("Verdana", Font.PLAIN, 12));

		// Create Dynamic Labels
		timerValue = new JLabel(String.valueOf(timeInteger));
		scoreValue = new JLabel(String.valueOf(scoreInteger));
		currentLevel = new JLabel(String.valueOf(levelInteger));

		// Decorate Dynamic Labels
		timerValue.setFont(new Font("Verdana", Font.BOLD, 40));
		scoreValue.setFont(new Font("Verdana", Font.BOLD, 40));
		currentLevel.setFont(new Font("Verdana", Font.BOLD, 40));
		currentLevel.setForeground(Color.WHITE);

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
			BorderFactory.createEmptyBorder(5, 50, 5, 50) // Inner Padding
		));

		quitB.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.WHITE, 3),
			BorderFactory.createEmptyBorder(5, 50, 5, 50)
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

		// Create shapePanel
		shapePanel = new ShapePanel();
		shapePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		shapePanel.setBackground(Color.WHITE);

		// Create infoPanel
		JPanel infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(800, 100));
		infoPanel.setLayout(new GridLayout(1, 3, 0, 0));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

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

		// create wantedPanel
		JPanel wantedPanel = new JPanel();
		wantedPanel.setLayout(new BoxLayout(wantedPanel, BoxLayout.Y_AXIS));
		wantedPanel.setBackground(Color.WHITE);
		wantedPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		wantedPanel.add(wantedIntel);
		wantedPanel.add(shapePanel);
		wantedIntel.setAlignmentX(Component.CENTER_ALIGNMENT);
		shapePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// create ScorePanel
		JPanel scorePanel = new JPanel();
		scorePanel.add(scoreValue);
		scorePanel.add(scoreLabel);
		scoreValue.setBackground(Color.WHITE);
		scorePanel.setBackground(Color.WHITE);
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreValue.setAlignmentX(Component.CENTER_ALIGNMENT);
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

		//create infoPanel
		infoPanel.add(timerPanel);
		infoPanel.add(wantedPanel);
		infoPanel.add(scorePanel);
		infoPanel.setBackground(Color.BLACK);
		infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create buttonPanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.BLACK);
		gridLayout = new GridLayout(1, 3,90,  0);
		buttonPanel.setLayout(gridLayout);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Create levelPanel
		JPanel levelPanel = new JPanel();
		levelPanel.add(currentLevel);
		levelPanel.setBackground(Color.BLACK);

		// Add Buttons To buttonPanel
		buttonPanel.add (playB);
		buttonPanel.add(levelPanel);
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

		if (command.equals(playB.getText())) {
			// playB.setEnabled(false);
			levelInteger = levelInteger + 1;
			currentLevel.setText(String.valueOf(levelInteger));
			startTimerCountdown();
			gamePanel.drawGameEntities();
			shapePanel.pickWantedShape();
			shapePanel.drawWantedShape();

			// DEBUG
			GamePanel.Shapes selectedShape = shapePanel.getSelectedShape();
			System.out.println("Selected Shape: " + selectedShape);
		}

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
		int x = e.getX();
		int y = e.getY();

		GamePanel.Shapes shape = gamePanel.isOnShape(x, y);
		GamePanel.Shapes selectedShape = shapePanel.getSelectedShape();

		if (shape != GamePanel.Shapes.NONE) {
			wantedIntel.setText(shape.name());
			if (shape == selectedShape) {
				// Time Award
				timeInteger = timeInteger + 5;
				timerValue.setText(String.valueOf(timeInteger));

				// Score Award
				scoreInteger = scoreInteger + 100;
				scoreValue.setText(String.valueOf(scoreInteger));
			}
			else {
				// Time Consequence
				timeInteger = timeInteger - 10;
				timerValue.setText(String.valueOf(timeInteger));
			}

			shapePanel.panelEraser();
		} else {
			wantedIntel.setText("Wanted");
		}
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

	// Method For Timer Countdown
	private void startTimerCountdown() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(1000, (ActionEvent e) -> {
            if (timeInteger > 0) {
                timeInteger--;
                if (timeInteger <= 5) {
                    timerValue.setForeground(Color.RED);
                }
                timerValue.setText(String.valueOf(timeInteger));
            } else {
                timer.stop();
            }
        });

        timer.start();
    }
}