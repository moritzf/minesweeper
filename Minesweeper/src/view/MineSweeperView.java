package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import util.GameConstants;
import util.ViewConstants;

/**
 * Contains the view associated with the Minesweeper game.
 */
public class MineSweeperView extends JFrame implements ViewConstants, GameConstants{
	
	private JLabel mineCounter = new JLabel("300");
	private JLabel statusMessage = new JLabel("Status", SwingConstants.CENTER);
	private JButton refreshButton = new JButton(new ImageIcon(REFRESH_BUTTON_IMAGE_PATH));
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel gamePanel = new JPanel(new GridLayout(NUM_TILES_PER_SIDE, NUM_TILES_PER_SIDE));
	private JButton[][] tiles = new JButton[NUM_TILES_PER_SIDE][NUM_TILES_PER_SIDE];

	public MineSweeperView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Minesweeper");
		setContentPane(mainPanel);
		setResizable(false);
		initActionBar();
		initGameContent();
		pack();
		setVisible(true);
	}
	
	private void initActionBar() {
		JPanel actionBarPanel = new JPanel(new BorderLayout());
		actionBarPanel.setBackground(Color.WHITE);
		actionBarPanel.setPreferredSize(new Dimension(APPLICATION_WIDTH, ACTION_BAR_HEIGHT));

		actionBarPanel.add(mineCounter, BorderLayout.WEST);
		mineCounter.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		mineCounter.setFont(new Font(ACTION_BAR_FONT, Font.PLAIN, ACTION_BAR_FONT_SIZE));

		actionBarPanel.add(refreshButton, BorderLayout.EAST);
		refreshButton.setBorder(BorderFactory.createEmptyBorder());
		refreshButton.setBackground(Color.WHITE);
		
		actionBarPanel.add(statusMessage, BorderLayout.CENTER);
		statusMessage.setFont(new Font(ACTION_BAR_FONT, Font.PLAIN, ACTION_BAR_FONT_SIZE));
		
		mainPanel.add(actionBarPanel, BorderLayout.PAGE_START);
	}
	
	private void initGameContent() {
		int count = 0;
		for (int i = 0; i < NUM_TILES_PER_SIDE; i++) {
			for (int j = 0; j < NUM_TILES_PER_SIDE; j++) {
                JButton tile = new JButton("" + count++);
                tile.setPreferredSize(new Dimension(50, 50));
                tile.setBorder(BorderFactory.createRaisedBevelBorder());
                tile.setBackground(Color.LIGHT_GRAY);
                tiles[i][j] = tile;
                gamePanel.add(tile);
			}
		}
		gamePanel.setBackground(Color.GRAY);
		mainPanel.add(gamePanel, BorderLayout.CENTER);
	}
}
