package model;

import java.util.Random;

import util.GameConstants;

public class MineSweeperModel implements GameConstants {
	
	MineSweeperTile[][] tiles = new MineSweeperTile[NUM_TILES_PER_SIDE][NUM_TILES_PER_SIDE];
	String status;
	int numFlagsRemaining;
	ModelObserver observer;
	
	public MineSweeperModel() {
		initGame();
	}
	
	public void initGame() {
		Random random = new Random();
		int count = 0;
		int numTiles = NUM_TILES_PER_SIDE * NUM_TILES_PER_SIDE;
		int numBombs = 0;
		for (int i = 0; i < NUM_TILES_PER_SIDE; i++) {
			for (int j = 0; j < NUM_TILES_PER_SIDE; j++) {
				boolean hasBomb = false;
                if (random.nextInt(numTiles) == count) {
                	numBombs++;
                	hasBomb = true;
                }
                tiles[i][j] = new MineSweeperTile(hasBomb);
                count++;
			}
		}
		numFlagsRemaining = numBombs;
	}
	
	public void addObserver(ModelObserver o) {
		observer = o;
		notifyObserver();
	}
	
	public void notifyObserver() {
		observer.update(tiles, status, numFlagsRemaining);
	}

	public int getAdjacentBombs(int i, int j) {
		//TODO
		return 0;
	}
	
	
	
}
