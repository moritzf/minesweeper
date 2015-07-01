package application;

import javax.swing.JFrame;

import model.MineSweeperBoardModel;
import util.ViewConstants;
import controller.MineSweeperController;

public class MineSweeperDriver extends JFrame implements ViewConstants {

	public static void main(String[] args) {
		MineSweeperBoardModel model = new MineSweeperBoardModel();
		MineSweeperController controller = new MineSweeperController(model);
	}

}
