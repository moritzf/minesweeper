package model;

public class MineSweeperTile {
	private boolean hasBomb;
	private boolean isCovered;
	private boolean hasFlag;

	/**
	 * Since the tile starts out covered and without a flag, only hasBomb is
	 * paramterized.
	 */
	public MineSweeperTile(boolean hasBomb) {
		this.hasBomb = hasBomb;
		isCovered = true;
		hasFlag = false;
	}

	public boolean isCovered() {
		return isCovered;
	}

	public boolean hasBomb() {
		return hasBomb;
	}

	public boolean hasFlag() {
		return hasFlag;
	}

	public void setFlag(boolean hasFlag) {
		this.hasFlag = hasFlag;
	}

	public void setCovered(boolean isCovered) {
		this.isCovered = isCovered;
	}

}
