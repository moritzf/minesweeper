package model;

/**
 * Classes that observe the game progress, namely if it's over or not and
 * whether or not the game was successfully completed, implement this interface.
 */
public interface GameProgressObserver {
	void update(String message);
}
