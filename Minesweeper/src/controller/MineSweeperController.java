package controller;

import model.MineSweeperModel;
import view.MineSweeperView;

public class MineSweeperController {

	MineSweeperModel model;
	MineSweeperView view;

	public MineSweeperController(MineSweeperModel model) {
		this.model = model;
		view = new MineSweeperView(this, model);

		/*
		 * An empty view is created first since the initGame() is dependent on
		 * an initialized view
		 */
		view.createView();

		model.initGame();
	}

}
