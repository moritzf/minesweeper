package model;

/**
 * Classes that observe the remaining flags the user can place implement this
 * interface.
 */
public interface FlagsObserver {
	void update(int remainingFlags);
}
