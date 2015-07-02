package application;

import javax.swing.JFrame;

import model.MineSweeperModel;
import util.ViewConstants;
import controller.MineSweeperController;

public class MineSweeperDriver extends JFrame implements ViewConstants {

	public static void main(String[] args) {
		MineSweeperModel model = new MineSweeperModel();
		MineSweeperController controller = new MineSweeperController(model);
	}

}
