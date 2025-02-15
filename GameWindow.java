/**
 * @author Jalene Armstrong
 * @date 2025-02-15
 */

import java.awt.*;			// GUI Objects
import java.awt.event.*;	// Layout Managers
import javax.swing.*;		// Respond to GUI Events

public class GameWindow extends JFrame implements ActionListener, MouseListener
{
	// Game Managers
	private Timer timer;
	private boolean gameLost = false;
	private boolean gameWon = false;
	private final Container mainContainer;
	private boolean isTimerStopped = false;

	// Panels
	private final ShapePanel shapePanel = new ShapePanel();
	private final TimerPanel timerPanel = new TimerPanel();
	private final ScorePanel scorePanel = new ScorePanel();
	private final WantedPanel wantedPanel = new WantedPanel(shapePanel);
	private final InfoPanel infoPanel = new InfoPanel(timerPanel, wantedPanel, scorePanel);

	private final SplashPanel splashPanel = new SplashPanel();
	private final GamePanel gamePanel = new GamePanel(splashPanel);
	private final LevelPanel levelPanel = new LevelPanel();
	private final ButtonPanel buttonPanel = new ButtonPanel(levelPanel);
	private final MainPanel mainPanel = new MainPanel(infoPanel, gamePanel, buttonPanel);

	public GameWindow() {
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

		if (command.equals(buttonPanel.getPlayB().getText())) {
			if (gameLost || gameWon) {
				resetGame();
				buttonPanel.getPlayB().setEnabled(false);
			}

			buttonPanel.getPlayB().setEnabled(false);
			splashPanel.setVisible(false);

			levelPanel.setLevelInteger(levelPanel.getLevelInteger() + 1);
			gamePanel.setLevel(levelPanel.getLevelInteger());

			startTimerCountdown();

			shapePanel.pickWantedShape();
			gamePanel.createGameEntities();

			gamePanel.repaint();
			shapePanel.repaint();
		}

		if (command.equals(buttonPanel.getQuitB().getText()))
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
				winGame();
			}
			else {
				// Time Consequence
				timerPanel.setTimeInteger(timerPanel.getTimeInteger() - 5);
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
        buttonPanel.getPlayB().addActionListener(this);
		buttonPanel.getQuitB().addActionListener(this);
		gamePanel.addMouseListener(this);
    }

	// Method To Start Timer Countdown
	private void startTimerCountdown() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(1000, (ActionEvent e) -> {
            if (timerPanel.getTimeInteger() > 0 && !isTimerStopped && !gameLost) {
                timerPanel.setTimeInteger(timerPanel.getTimeInteger() - 1);
            } else {
				timeUp();
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

	// Method To Times Up!
	private void timeUp() {
			shapePanel.panelEraser();
			gamePanel.panelEraser();
			gameLost = true;
			buttonPanel.getPlayB().setVisible(true);
			displaySplash();
			stopTimer();
	}

	// Method To Win Game
	private void winGame() {
		if (levelPanel.getLevelInteger() == 51) {
			shapePanel.panelEraser();
			gamePanel.panelEraser();
			levelPanel.setLevelInteger(levelPanel.getLevelInteger() - 1);
			gameWon = true;
			displaySplash();
			stopTimer();
		}
	}

	// Method To Continue Game
	private void continueGame() {
		// Time Award
		timerPanel.setTimeInteger(timerPanel.getTimeInteger() + 3);

		// Score Award
		scorePanel.setScoreInteger(scorePanel.getScoreInteger() + 10);

		// Next Level
		if (levelPanel.getLevelInteger() <= 50) {
			levelPanel.setLevelInteger(levelPanel.getLevelInteger() + 1);
			gamePanel.setLevel(levelPanel.getLevelInteger());
		}

		// Stopping Movement Levels
		gamePanel.panelEraser();
		shapePanel.panelEraser();
		shapePanel.pickWantedShape();
		shapePanel.drawWantedShape();
		gamePanel.createGameEntities();
	}

	// Method To Update Splash Panel 
	private void displaySplash() {
		if (gameLost) {
			splashPanel.getTitleLabel().setText("Times Up!");
			splashPanel.getDescLabel1().setText("Oh-No!");
			splashPanel.getDescLabel2().setText("He got away! Try to be quicker next time!");
			splashPanel.getCreditsLabel().setText("Jalene Armstrong • 2025");
			buttonPanel.getPlayB().setEnabled(true);
		} else if (gameWon) {
			splashPanel.getTitleLabel().setText("Found!");
			splashPanel.getDescLabel1().setText("You Did It!");
			splashPanel.getDescLabel2().setText("You found them all—nothing can hide from you!");
			splashPanel.getCreditsLabel().setText("Jalene Armstrong • 2025");
			buttonPanel.getPlayB().setEnabled(true);
		}
		splashPanel.setVisible(true);
	}

	// Method To Restart Game
	private void resetGame() {
		splashPanel.setVisible(false);
		isTimerStopped = false;
		gameLost = false;
		gameWon = false;
		startTimerCountdown();
		timerPanel.setTimeInteger(5);
		scorePanel.setScoreInteger(0);
		levelPanel.setLevelInteger(0);
	}
}