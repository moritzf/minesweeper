package model;

/**
 * Describes all methods that can be called on the model. This interface
 * combines subroutines for accessing and altering state.
 *
 */
public interface MineSweeperModelInterface {

	/* Methods for accessing state. */

	/**
	 * Returns the number of adjacent bombs in a 3x3 area with the tile to check
	 * in the middle.
	 */
	int getNumNeighboringBombs(int x, int y);

	/**
	 * Returns the number of remaining flags that the player can place on the
	 * board. The return value is less than or equal to the number of mines
	 * placed on the board.
	 */
	int getRemainingFlags();

	/**
	 * Returns a tile object containing the individual state of a single tile.
	 */
	MineSweeperTile getTileState(int x, int y);

	/**
	 * Returns whether or not the game is over.
	 */
	boolean isGameOver();

	/**
	 * Returns whether or not the player has won.
	 */
	boolean hasWon();

	/* These methods allow the view to become an observer. */

	void addObserver(BoardObserver o);

	void addObserver(FlagsObserver o);

	void addObserver(GameProgressObserver o);

	/* Methods for altering state */

	/**
	 * As the player places a flag on the board, the flag count is decreased by
	 * one via this method.
	 */
	void decrementFlagCountByOne();

	/**
	 * Sets whether or not the game is over and whether or not the player has
	 * won.
	 */
	void setGameProgress(boolean gameOver, boolean hasWon);

	/**
	 * Sets up the game for the first time. Only called once at the beginning.
	 */
	void initGame();

	/**
	 * Called after the player presses the replay button.
	 */
	void resetGame();

	/**
	 * Uncovers the tiles in the surrounding area and the current tile.
	 */
	void uncoverTiles(int x, int y);

	/**
	 * Marks the specified tile.
	 */
	void markTile(int x, int y);

}
