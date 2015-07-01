package model;

public interface ModelObserver {
	void update(MineSweeperTile[][] tilesModel, String message, int numFlagsRemaining);
}
