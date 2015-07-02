package model;

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

	private boolean[][] generateBombDistribution() {

		int numBombs = 0;
		Random random = new Random();
		HashSet<Integer> visitedX = new HashSet<>();
		HashSet<Integer> visitedY = new HashSet<>();

		// field equal to true hav bombs
		boolean[][] bombDistribution = new boolean[NUM_TILES_PER_SIDE][NUM_TILES_PER_SIDE];

		while (true) {
			if (numBombs == NUM_BOMBS) {
				break;
			}
			int i = random.nextInt(NUM_TILES_PER_SIDE);
			int j = random.nextInt(NUM_TILES_PER_SIDE);

			if (!(visitedX.contains(new Integer(i)) && visitedY
					.contains(new Integer(i)))) {
				visitedX.add(new Integer(i));
				visitedY.add(new Integer(j));
				bombDistribution[i][j] = true;
				numBombs++;

			}
		}
		return bombDistribution;
	}

	@Override
	public int getNumNeighboringBombs(int x, int y) {
		int result = 0;
		for (int i = x - 1; i < x + 1; i++) {
			for (int j = y - 1; j < j + 1; j++) {
				if (inBounds(i, j) && tiles[i][j].hasBomb()) {
					result++;
				}
			}
		}
		return result;
	}

	/**
	 * Checks if the coordinates are within the bounds of the game board.
	 */
	private boolean inBounds(int x, int y) {
		if (!(x < 0) && x < NUM_TILES_PER_SIDE && !(y < 0)
				&& y < NUM_TILES_PER_SIDE)
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
				message = "Gewonnen!";
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

	/* Helper method used to recursively uncover tiles. A tile is uncoverable if */
	private void uncoverCurrentAndAdjacentTilesRecursively(int x, int y,
			HashSet<Integer> visitedX, HashSet<Integer> visitedY) {
		if (tiles[x][y].hasBomb() || tiles[x][y].hasFlag()) {
			return;
		} else {
			visitedX.add(new Integer(x));
			visitedY.add(new Integer(y));
			tiles[x][y].setCovered(false);
			decrementCoveredByOne();
			for (int i = x - 1; i < x + 1; i++) {
				for (int j = y - 1; j < y + 1; j++) {
					if (inBounds(i, j)
							&& !(visitedX.contains(new Integer(i)) && visitedY
									.contains(new Integer(j)))) {
						uncoverCurrentAndAdjacentTilesRecursively(i, j,
								visitedX, visitedY);
					}
				}
			}
		}

	}

	/* Uncovers all bombs on the board */
	private void uncoverBombs() {
		for (int i = 0; i < NUM_TILES_PER_SIDE; i++) {
			for (int j = 0; j < NUM_TILES_PER_SIDE; j++) {
				if (tiles[i][j].hasBomb()) {
					tiles[i][j].setCovered(false);
					decrementCoveredByOne();
				}
			}
		}
	}

	@Override
	public void uncoverTiles(int x, int y) {
		if (tiles[x][y].hasBomb()) {
			tiles[x][y].setCovered(false);
			decrementCoveredByOne();
			tiles[x][y].setHasExploded(true);
			uncoverBombs();
			setGameProgress(true, false);
			boardObserver.update();
			return;
		}
		HashSet<Integer> visitedX = new HashSet<>();
		HashSet<Integer> visitedY = new HashSet<>();
		uncoverCurrentAndAdjacentTilesRecursively(x, y, visitedX, visitedY);
	}

	private void decrementCoveredByOne() {
		numCovered--;
		if (numCovered == NUM_BOMBS) {
			setGameProgress(true, true);
		}
	}

	@Override
	public void markTile(int x, int y) {
		if (tiles[x][y].hasFlag()) {
			tiles[x][y].setFlag(false);
			tiles[x][y].setWronglyMarked(false);
			incrementFlagCountByOne();
		} else {
			if (getRemainingFlags() > 0) {
				tiles[x][y].setFlag(true);
				if (!tiles[x][y].hasBomb()) {
					tiles[x][y].setWronglyMarked(true);
				}
				decrementFlagCountByOne();
			}
		}
		boardObserver.update();
	}

	private void incrementFlagCountByOne() {
		numFlagsRemaining++;
		flagsObserver.update(numFlagsRemaining);
	}

}
