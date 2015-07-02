package model;

/**
 * Classes that observe the game progress, namely if it's over or not, implement
 * this interface.
 */
public interface GameProgressObserverInterface {
	void update(boolean gameOver);
}
