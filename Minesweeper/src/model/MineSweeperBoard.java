package model;

import java.util.Random;

import util.GameConstants;

public class MineSweeperBoard implements GameConstants {
	
	MineSweeperTile[][] tiles = new MineSweeperTile[NUM_TILES_PER_SIDE][NUM_TILES_PER_SIDE];

	public MineSweeperBoard() {

		Random random = new Random();
		int count = 0;
		int numTiles = NUM_TILES_PER_SIDE * NUM_TILES_PER_SIDE;

		for (int i = 0; i < NUM_TILES_PER_SIDE; i++) {
			for (int j = 0; j < NUM_TILES_PER_SIDE; j++) {
				boolean hasBomb = false;
                if (random.nextInt(numTiles) == count) {
                	hasBomb = true;
                }
                tiles[i][j] = new MineSweeperTile(hasBomb);
                count++;
			}
		}
	}
}
