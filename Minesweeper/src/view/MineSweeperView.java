package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import model.BoardObserver;
import model.FlagsObserver;
import model.GameProgressObserver;
import model.MineSweeperModel;
import model.MineSweeperTile;
import util.GameConstants;
import util.ViewConstants;
import controller.MineSweeperController;
import controller.TileMouseListener;

/**
 * Contains the view associated with the Minesweeper game.
 */
public class MineSweeperView extends JFrame implements ViewConstants,
		GameConstants, BoardObserver, FlagsObserver, GameProgressObserver {

	private JLabel mineCounter = new JLabel("" + NUM_BOMBS);
	private JLabel statusMessage = new JLabel("", SwingConstants.CENTER);
	private JButton refreshButton = new JButton(new ImageIcon(
			REFRESH_BUTTON_IMAGE_PATH));
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel gamePanel = new JPanel(new GridLayout(NUM_TILES_PER_SIDE,
			NUM_TILES_PER_SIDE));
	private JButton[][] tiles = new JButton[NUM_TILES_PER_SIDE][NUM_TILES_PER_SIDE];

	MineSweeperController controller;
	MineSweeperModel model;

	public MineSweeperView(MineSweeperController controller,
			MineSweeperModel model) {
		this.controller = controller;
		this.model = model;
		model.addObserver((BoardObserver) this);
		model.addObserver((FlagsObserver) this);
		model.addObserver((GameProgressObserver) this);
	}

	/**
	 * Creates the game view i.e. the game board and the action bar.
	 */
	public void createView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Minesweeper");
		setContentPane(mainPanel);
		setResizable(true);
		initActionBar();
		initGameContent();
		pack();
		setVisible(true);
	}

	private void initActionBar() {
		JPanel actionBarPanel = new JPanel(new BorderLayout());
		actionBarPanel.setBackground(Color.WHITE);
		actionBarPanel.setPreferredSize(new Dimension(APPLICATION_WIDTH,
				ACTION_BAR_HEIGHT));

		actionBarPanel.add(mineCounter, BorderLayout.WEST);
		mineCounter.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		mineCounter.setFont(new Font(ACTION_BAR_FONT, Font.PLAIN,
				ACTION_BAR_FONT_SIZE));

		actionBarPanel.add(refreshButton, BorderLayout.EAST);
		refreshButton.setBorder(BorderFactory.createEmptyBorder());
		refreshButton.setBackground(Color.WHITE);
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.resetGame();
			}
		});

		actionBarPanel.add(statusMessage, BorderLayout.CENTER);
		statusMessage.setFont(new Font(ACTION_BAR_FONT, Font.PLAIN,
				ACTION_BAR_FONT_SIZE));

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
				tile.addMouseListener(new TileMouseListener(i, j) {

					@Override
					public void mouseReleased(MouseEvent e) {
						if (SwingUtilities.isLeftMouseButton(e)) {
							model.uncoverTiles(getI(), getJ());
						} else if (SwingUtilities.isRightMouseButton(e)) {
							model.markTile(getI(), getJ());
						}
					}

					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub

					}
				});
				tiles[i][j] = tile;
				gamePanel.add(tile);
			}
		}
		gamePanel.setBackground(Color.GRAY);
		mainPanel.add(gamePanel, BorderLayout.CENTER);
	}

	/**
	 * Sets the state of a single tile on the visible game board.
	 */
	private void setViewTileState(int x, int y, MineSweeperTile mineSweeperTile) {
		if (mineSweeperTile.hasBomb() && !mineSweeperTile.isCovered()) {
			if (mineSweeperTile.hasExploded()) {
				tiles[x][y].setText("");
				;
				tiles[x][y].setIcon(new ImageIcon(EXPLODED_BOMB_PATH));
			} else {
				tiles[x][y].setIcon(new ImageIcon(BOMB_PATH));
			}
		} else if (mineSweeperTile.hasFlag()) {
			if (mineSweeperTile.isWronglyMarked()
					&& !mineSweeperTile.isCovered()) {
				tiles[x][y].setIcon(new ImageIcon(WRONGLY_FLAGGED_PATH));
			} else {
				tiles[x][y].setIcon(new ImageIcon(FLAG_PATH));
			}
		} else if (!mineSweeperTile.isCovered()) {
			int numAdjacentBombs = model.getNumNeighboringBombs(x, y);
			tiles[x][y].setBackground(Color.WHITE);
			if (numAdjacentBombs == 0) {
				tiles[x][y].setText("");
			} else {
				tiles[x][y].setText("" + numAdjacentBombs);
			}
		} else if (mineSweeperTile.isCovered()) {
			tiles[x][y].setBackground(Color.LIGHT_GRAY);
			tiles[x][y].setIcon(null);
			tiles[x][y].setText("");
		}
	}

	@Override
	public void update(String message) {
		statusMessage.setText(message);
	}

	@Override
	public void update(int remainingFlags) {
		mineCounter.setText("" + remainingFlags);
	}

	@Override
	public void update() {
		for (int i = 0; i < NUM_TILES_PER_SIDE; i++) {
			for (int j = 0; j < NUM_TILES_PER_SIDE; j++) {
				setViewTileState(i, j, model.getTileState(i, j));
			}
		}
	}
}
