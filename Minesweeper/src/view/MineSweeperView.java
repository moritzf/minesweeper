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

import model.MineSweeperModel;
import model.MineSweeperTile;
import model.ModelObserver;
import util.GameConstants;
import util.ViewConstants;
import controller.MineSweeperController;

/**
 * Contains the view associated with the Minesweeper game.
 */
public class MineSweeperView extends JFrame implements ViewConstants, GameConstants, ModelObserver{
	
	private JLabel mineCounter = new JLabel("300");
	private JLabel statusMessage = new JLabel("Status", SwingConstants.CENTER);
	private JButton refreshButton = new JButton(new ImageIcon(REFRESH_BUTTON_IMAGE_PATH));
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel gamePanel = new JPanel(new GridLayout(NUM_TILES_PER_SIDE, NUM_TILES_PER_SIDE));
	private JButton[][] tiles = new JButton[NUM_TILES_PER_SIDE][NUM_TILES_PER_SIDE];
	
	MineSweeperController controller;
	MineSweeperModel model;

	public MineSweeperView(MineSweeperController controller, MineSweeperModel model) {
		this.controller = controller;
		this.model = model;
		createView();
		model.addObserver(this);
	}
	
	private void createView() {
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
                JButton tile = new JButton("");
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

	@Override
	public void update(MineSweeperTile[][] tilesModel, String message,
			int numFlagsRemaining) {
		mineCounter.setText("" + numFlagsRemaining);
		statusMessage.setText(message);
		for (int i = 0; i < NUM_TILES_PER_SIDE; i++) {
			for (int j = 0; j < NUM_TILES_PER_SIDE; j++) {
				setTileState(i, j, tilesModel[i][j]); 
			}
		}
	}

	private void setTileState(int i, int j,
			MineSweeperTile mineSweeperTile) {
		if (mineSweeperTile.hasBomb() && !mineSweeperTile.isCovered()) {
			if (mineSweeperTile.hasExploded()) {
				tiles[i][j].setText("");;
				tiles[i][j].setIcon(new ImageIcon(EXPLODED_BOMB_PATH));
			} else {
				tiles[i][j].setIcon(new ImageIcon(BOMB_PATH));
			}
		} else if (mineSweeperTile.isCovered()) {
			tiles[i][j].setText("");
		} else if (mineSweeperTile.hasFlag()) {
			tiles[i][j].setIcon(new ImageIcon(FLAG_PATH));
		} else if (!mineSweeperTile.isCovered()) {
			int numAdjacentBombs = model.getAdjacentBombs(i, j);
			if(numAdjacentBombs == 0) {
				tiles[i][j].setText("");
			} else {
				tiles[i][j].setText("" + numAdjacentBombs);
			}
		}
	}
}
