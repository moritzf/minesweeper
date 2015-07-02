package model;

/**
 * Classes that observe the MineSweeper board state implement this interface.
 */
public interface BoardObserverInterface {
	void update(MineSweeperTile[][] tiles);
}
