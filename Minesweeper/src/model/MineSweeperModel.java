package model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Random;

import util.GameConstants;

public class MineSweeperModel implements GameConstants,
MineSweeperModelInterface {

	private MineSweeperTile[][] tiles;

	private boolean gameOver;
	private boolean hasWon;
	private int numFlagsRemaining;
	private int numCovered;
	private String message;
	private BoardObserver boardObserver;
	private FlagsObserver flagsObserver;
	private GameProgressObserver gameProgressObserver;

	public MineSweeperModel() {
	}

	@Override
	public void initGame() {
		boolean[][] bombDistribution = generateBombDistribution();
		tiles = new MineSweeperTile[NUM_TILES_PER_SIDE][NUM_TILES_PER_SIDE];
		int numTiles = NUM_TILES_PER_SIDE * NUM_TILES_PER_SIDE;
		for (int i = 0; i < NUM_TILES_PER_SIDE; i++) {
			for (int j = 0; j < NUM_TILES_PER_SIDE; j++) {
				boolean hasBomb = bombDistribution[i][j];
				tiles[i][j] = new MineSweeperTile(hasBomb);
			}
		}
		numFlagsRemaining = NUM_BOMBS;
		numCovered = numTiles;
		gameOver = false;
		hasWon = false;
		message = "";

		flagsObserver.update(numFlagsRemaining);
		boardObserver.update();
		gameProgressObserver.update(message);

	}

	/**
	 * Distributes a set number of bombs specified through the NUM_BOMBS
	 * constant randomly on the game board. Also ensures that no bomb is placed
	 * twice on the same tile.
	 */
	private boolean[][] generateBombDistribution() {

		int numBombs = 0;
		Random random = new Random();
		HashSet<Point2D> visited = new HashSet<>();

		// field equal to true has a bomb
		boolean[][] bombDistribution = new boolean[NUM_TILES_PER_SIDE][NUM_TILES_PER_SIDE];

		while (true) {
			if (numBombs == NUM_BOMBS) {
				break;
			}
			int i = random.nextInt(NUM_TILES_PER_SIDE);
			int j = random.nextInt(NUM_TILES_PER_SIDE);

			Point2D pt = new Point(i, j);
			if (!visited.contains(pt)) {
				visited.add(pt);
				bombDistribution[i][j] = true;
				numBombs++;
				System.out.println(numBombs);
			}
		}
		return bombDistribution;
	}

	@Override
	public int getNumNeighboringBombs(int x, int y) {
		int result = 0;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (!(i == x && j == y)) {
					if (inBounds(i, j) && tiles[i][j].hasBomb()) {
						result++;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Checks if the coordinates are within the bounds of the game board.
	 */
	private boolean inBounds(int x, int y) {
		if ((!(x < 0)) && (x < NUM_TILES_PER_SIDE) && (!(y < 0))
				&& (y < NUM_TILES_PER_SIDE))
			return true;
		else
			return false;
	}

	@Override
	public int getRemainingFlags() {
		return numFlagsRemaining;
	}

	@Override
	public MineSweeperTile getTileState(int x, int y) {
		return tiles[x][y];
	}

	@Override
	public boolean isGameOver() {
		return gameOver;
	}

	@Override
	public void addObserver(BoardObserver o) {
		boardObserver = o;
	}

	@Override
	public void addObserver(FlagsObserver o) {
		flagsObserver = o;
	}

	@Override
	public void addObserver(GameProgressObserver o) {
		gameProgressObserver = o;
	}

	@Override
	public void decrementFlagCountByOne() {
		if (numFlagsRemaining >= 1) {
			numFlagsRemaining--;
			flagsObserver.update(numFlagsRemaining);
		}
	}

	@Override
	public void setGameProgress(boolean gameOver, boolean hasWon) {
		this.gameOver = gameOver;
		this.hasWon = hasWon;

		if (gameOver) {
			if (hasWon) {
				message = "Gewonnen :)";
			} else {
				message = "Verloren :(";
			}
		} else {
			message = "";
		}

		gameProgressObserver.update(message);
	}

	@Override
	public void resetGame() {
		initGame();
	}

	@Override
	public boolean hasWon() {
		return hasWon;
	}

	/* Helper method used to recursively uncover tiles. */
	private void uncoverCurrentAndAdjacentTilesRecursively(int x, int y) {

		if (!inBounds(x, y)
				|| (tiles[x][y].hasBomb() || tiles[x][y].hasFlag() || !tiles[x][y]
						.isCovered())) {
			return;
		}

		tiles[x][y].setCovered(false);
		decrementCoveredByOne();
		if (getNumNeighboringBombs(x, y) > 0) {
			return;
		}
		uncoverCurrentAndAdjacentTilesRecursively(x + 1, y);
		uncoverCurrentAndAdjacentTilesRecursively(x - 1, y);
		uncoverCurrentAndAdjacentTilesRecursively(x, y - 1);
		uncoverCurrentAndAdjacentTilesRecursively(x, y + 1);
	}

	private void uncoverBombsAndWronglyMarkedFlags() {
		for (int i = 0; i < NUM_TILES_PER_SIDE; i++) {
			for (int j = 0; j < NUM_TILES_PER_SIDE; j++) {
				if (tiles[i][j].hasBomb()) {
					tiles[i][j].setCovered(false);
					decrementCoveredByOne();
				}
				if (tiles[i][j].isWronglyMarked()) {
					tiles[i][j].setCovered(false);
					decrementCoveredByOne();
				}
			}
		}
	}

	@Override
	public void uncoverTiles(int x, int y) {
		if (!isGameOver()) {
			if (tiles[x][y].hasBomb()) {
				tiles[x][y].setCovered(false);
				decrementCoveredByOne();
				tiles[x][y].setHasExploded(true);
				uncoverBombsAndWronglyMarkedFlags();
				setGameProgress(true, false);
				boardObserver.update();
				return;
			}

			uncoverCurrentAndAdjacentTilesRecursively(x, y);
			boardObserver.update();
		}
	}

	/*
	 * Helper method to decrement the number of covered tiles. Used to determine
	 * whether or not the player has won.
	 */
	private void decrementCoveredByOne() {
		numCovered--;
		if (numCovered == NUM_BOMBS) {
			setGameProgress(true, true);
		}
	}

	@Override
	public void markTile(int x, int y) {
		if (!isGameOver()) {
			if (tiles[x][y].hasFlag()) {
				tiles[x][y].setFlag(false);
				tiles[x][y].setWronglyMarked(false);
				incrementFlagCountByOne();
			} else {
				if (getRemainingFlags() > 0 && tiles[x][y].isCovered()) {
					tiles[x][y].setFlag(true);
					if (!tiles[x][y].hasBomb()) {
						tiles[x][y].setWronglyMarked(true);
					}
					decrementFlagCountByOne();
				}
			}
			boardObserver.update();
		}
	}

	/*
	 * Helper method to increment the number of flags. Used when the player
	 * clicks on a flagged tile.
	 */
	private void incrementFlagCountByOne() {
		numFlagsRemaining++;
		flagsObserver.update(numFlagsRemaining);
	}

}
