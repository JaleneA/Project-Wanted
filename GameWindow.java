import java.awt.*;			// GUI Objects
import java.awt.event.*;	// Layout Managers
import javax.swing.*;		// Respond to GUI Events

public class GameWindow extends JFrame implements ActionListener, MouseListener
{
	// Declare Info
	private int timeInteger = 5;
	private int scoreInteger = 0;
	private int levelInteger = 0;

	// Declare Static Labels 
	private JLabel timerLabel;
	private JLabel wantedIntel;
	private JLabel scoreLabel;
	private JLabel titleLabel;
	private JLabel descLabel1;
	private JLabel descLabel2;
	private JLabel creditsLabel;

	// Declare Dynamic Labels
	private JLabel timerValue;
	private JLabel scoreValue;
	private JLabel currentLevel;

	// Declare Buttons
	private JButton playB;
	private JButton quitB;

	// Declare Managers
	private Container mainContainer;
	private Timer timer;
	private boolean isTimerStopped = false;
	private boolean gameEnd = false;
	private boolean gameWon = false;

	// Declare Panels
	private JPanel mainPanel;
	private GamePanel gamePanel;
	private ShapePanel shapePanel;

	private JPanel splashPanel;

	public GameWindow() {
		// Create Static Labels
		timerLabel = new JLabel ("Timer");
		wantedIntel = new JLabel("Wanted");
		scoreLabel = new JLabel("Score");
		titleLabel = new JLabel("Wanted!");
		descLabel1 = new JLabel("It's easy to get lost in a crowd!");
		descLabel2 = new JLabel("Find the one who disappeared and touch him. There he is!");
		creditsLabel = new JLabel("Jalene Armstrong • 2025");

		// Decorate Static Labels
		timerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		wantedIntel.setFont(new Font("Verdana", Font.PLAIN, 12));
		wantedIntel.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
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

		// Create Dynamic Labels
		timerValue = new JLabel(String.valueOf(timeInteger));
		timerValue.setAlignmentX(Component.CENTER_ALIGNMENT);
		timerValue.setBackground(Color.WHITE);
		scoreValue = new JLabel(String.valueOf(scoreInteger));
		scoreValue.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreValue.setBackground(Color.WHITE);
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

		// Create mainPanel
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		mainPanel.setBackground(Color.BLACK);

		// Create gamePanel
		gamePanel = new GamePanel();
		gamePanel.setBackground(Color.BLACK);
		gamePanel.setPreferredSize(new Dimension(665, 390));
		gamePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

		// Create splashPanel
		splashPanel = new JPanel();
		splashPanel.add(titleLabel);
		splashPanel.add(Box.createVerticalStrut(20));
		splashPanel.add(descLabel1);
		splashPanel.add(descLabel2);
		splashPanel.add(Box.createVerticalStrut(120));
		splashPanel.add(creditsLabel);
		splashPanel.setBackground(Color.BLACK);
		splashPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		splashPanel.setLayout(new BoxLayout(splashPanel, BoxLayout.Y_AXIS));
		splashPanel.setBorder(BorderFactory.createEmptyBorder(75, 0, 0, 0));

		// Create shapePanel
		shapePanel = new ShapePanel();
		shapePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		shapePanel.setBackground(Color.WHITE);
		shapePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create infoPanel
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.BLACK);
		infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoPanel.setPreferredSize(new Dimension(800, 100));
		infoPanel.setLayout(new GridLayout(1, 3, 0, 0));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

		// Create Individual Panels For Each Info Component
		JPanel timerPanel = new JPanel();
		timerPanel.add(timerValue);
		timerPanel.add(timerLabel);
		timerPanel.setBackground(Color.WHITE);
		timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
		timerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

		// create wantedPanel
		JPanel wantedPanel = new JPanel();
		wantedPanel.setLayout(new BoxLayout(wantedPanel, BoxLayout.Y_AXIS));
		wantedPanel.setBackground(Color.WHITE);
		wantedPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		wantedPanel.add(wantedIntel);
		wantedPanel.add(shapePanel);

		// create ScorePanel
		JPanel scorePanel = new JPanel();
		scorePanel.add(scoreValue);
		scorePanel.add(scoreLabel);
		scorePanel.setBackground(Color.WHITE);
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

		// Create buttonPanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.BLACK);
		buttonPanel.setLayout(new GridLayout(1, 3,90,  0));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Create levelPanel
		JPanel levelPanel = new JPanel();
		levelPanel.setBackground(Color.BLACK);

		// Add Buttons To buttonPanel
		buttonPanel.add (playB);
		buttonPanel.add(levelPanel);
		buttonPanel.add (quitB);

		// Add Panels
		levelPanel.add(currentLevel);
		infoPanel.add(timerPanel);
		infoPanel.add(wantedPanel);
		infoPanel.add(scorePanel);
		gamePanel.add(splashPanel);
		mainPanel.add(infoPanel);
		mainPanel.add(gamePanel);
		mainPanel.add(buttonPanel);

		// Set Up Listeners
		setupListeners();

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
			playB.setEnabled(false);
			splashPanel.setVisible(false);

			levelInteger = levelInteger + 1;
			gamePanel.setLevel(levelInteger);
			currentLevel.setText(String.valueOf(levelInteger));

			startTimerCountdown();

			shapePanel.pickWantedShape();
			gamePanel.createGameEntities();

			gamePanel.repaint();
			shapePanel.repaint();
		}

		if (command.equals(quitB.getText()))
			System.exit(0);
	}

	// MouseListener Interface
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		GamePanel.Shapes shape = gamePanel.isOnShape(x, y);
		GamePanel.Shapes selectedShape = ShapePanel.getSelectedShape();

		if (shape != GamePanel.Shapes.NONE) {
			if (shape == selectedShape) {
				continueGame();
				endGame();
			}
			else {
				// Time Consequence/.
				timeInteger = timeInteger - 10;
				timerValue.setText(String.valueOf(timeInteger));
			}
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

	// Method To Set Up Listeners
	private void setupListeners() {
        playB.addActionListener(this);
		quitB.addActionListener(this);
		gamePanel.addMouseListener(this);
    }

	// Method To Start Timer Countdown
	private void startTimerCountdown() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(1000, (ActionEvent e) -> {
            if (timeInteger > 0 && !isTimerStopped) {
                timeInteger--;
                timerValue.setText(String.valueOf(timeInteger));
            } else {
				gamePanel.stopFlicker();
				gameEnd = true;
				displaySplash();
				playB.setVisible(true);
                timer.stop();
            }
        });

        timer.start();
    }

	// Method To Stop Timer Countdown
	private void stopTimer() {
		isTimerStopped = true;
		if (timer != null) {
			timer.stop();
		}
	}

	// Method To End Game
	private void endGame() {
		if (levelInteger == 11) {
			// Clear gamePamel
			shapePanel.panelEraser();
			gamePanel.panelEraser();
			wantedIntel.setText("yay :D");
			levelInteger = levelInteger - 1;
			currentLevel.setText(String.valueOf(levelInteger));
			gamePanel.stopFlicker();
			gameWon = true;
			displaySplash();
			stopTimer();
		}
	}

	// Method To Continue Game
	private void continueGame() {
		// Time Award
		timeInteger = timeInteger + 5;
		timerValue.setText(String.valueOf(timeInteger));

		// Score Award
		scoreInteger = scoreInteger + 100;
		scoreValue.setText(String.valueOf(scoreInteger));

		// Next Level
		if (levelInteger <= 10) {
			levelInteger = levelInteger + 1;
			gamePanel.setLevel(levelInteger);
			currentLevel.setText(String.valueOf(levelInteger));
		}

		// Stopping Movement Levels
		gamePanel.stopFlicker();
		gamePanel.stopAllThreads();

		shapePanel.panelEraser();
		shapePanel.pickWantedShape();
		shapePanel.drawWantedShape();
		gamePanel.panelEraser();
		gamePanel.createGameEntities();
	}

	// Method To Update Splash Panel 
	private void displaySplash() {
		if (gameEnd) {
			titleLabel.setText("Times Up!");
			descLabel1.setText("Oh-No!");
			descLabel2.setText("He got away! Try to be quicker next time!");
			creditsLabel.setText("Jalene Armstrong • 2025");
			playB.setEnabled(true);
		} else if (gameWon) {
			titleLabel.setText("Found!");
			descLabel1.setText("You Did It!");
			descLabel2.setText("You found them all—nothing can hide from you!");
			creditsLabel.setText("Jalene Armstrong • 2025");
			playB.setEnabled(true);
		}
		splashPanel.setVisible(true);
	}

	// Getter & Setter Methods
    public JLabel getTimerLabel() {
        return timerLabel;
    }

    public void setTimerLabel(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    public JLabel getWantedIntel() {
        return wantedIntel;
    }

    public void setWantedIntel(JLabel wantedIntel) {
        this.wantedIntel = wantedIntel;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public JLabel getTimerValue() {
        return timerValue;
    }

    public void setTimerValue(JLabel timerValue) {
        this.timerValue = timerValue;
    }

    public JLabel getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(JLabel scoreValue) {
        this.scoreValue = scoreValue;
    }

    public JLabel getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(JLabel currentLevel) {
        this.currentLevel = currentLevel;
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

    public Container getMainContainer() {
        return mainContainer;
    }

    public void setMainContainer(Container mainContainer) {
        this.mainContainer = mainContainer;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public ShapePanel getShapePanel() {
        return shapePanel;
    }

    public void setShapePanel(ShapePanel shapePanel) {
        this.shapePanel = shapePanel;
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

    public void setDescLabel1(JLabel descLabel) {
        this.descLabel1 = descLabel;
    }

	public JLabel getDescLabel2() {
        return descLabel2;
    }

    public void setDescLabel2(JLabel descLabel) {
        this.descLabel2 = descLabel;
    }

    public JPanel getSplashPanel() {
        return splashPanel;
    }

    public void setSplashPanel(JPanel splashPanel) {
        this.splashPanel = splashPanel;
    }

    public JLabel getCreditsLabel() {
        return creditsLabel;
    }

    public void setCreditsLabel(JLabel creditsLabel) {
        this.creditsLabel = creditsLabel;
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }
}