package util;

/**
 * Describes constants concerning the core game mechanic.
 */
public interface GameConstants {

	/** Number of tiles per side of the game board. */
	public static final int NUM_TILES_PER_SIDE = 10;

	/**
	 * Number of bombs per game board. Obtained by analyzing existing
	 * MineSweeper games online. Approximately 13 % of the tiles have bombs.
	 */
	public static final int NUM_BOMBS = (NUM_TILES_PER_SIDE * NUM_TILES_PER_SIDE) / 8;
}
