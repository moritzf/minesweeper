package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Listener attached to every tile on the game board. A subclass is used to save
 * the coordinates of the tile. Also, the method mouseReleased will be
 * overridden as it would otherwise be necessary to save a reference to the
 * model in this class.
 * 
 * @author Moritz
 *
 */
public class TileMouseListener implements MouseListener {

	int i;

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	int j;

	public TileMouseListener(int i, int j) {
		this.i = i;
		this.j = j;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
