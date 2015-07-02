package model;

public class MineSweeperTile {
	private boolean hasBomb;
	private boolean isCovered;
	private boolean hasFlag;
	private boolean hasExploded;
	private boolean wronglyMarked;

	/**
	 * Since the tile starts out covered and without a flag, only hasBomb is
	 * parameterized.
	 */
	public MineSweeperTile(boolean hasBomb) {
		this.hasBomb = hasBomb;
		isCovered = true;
		hasFlag = false;
		hasExploded = false;
		wronglyMarked = false;
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

	public void setHasExploded(boolean exploded) {
		hasExploded = exploded;
	}

	public boolean hasExploded() {
		return hasExploded;
	}

	public boolean isWronglyMarked() {
		return wronglyMarked;
	}

	public void setWronglyMarked(boolean wronglyMarked) {
		this.wronglyMarked = wronglyMarked;
	}

}
