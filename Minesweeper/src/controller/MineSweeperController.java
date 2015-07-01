package controller;

import model.MineSweeperBoardModel;
import view.MineSweeperView;

public class MineSweeperController {
	
	MineSweeperBoardModel model;
	MineSweeperView view;
	public MineSweeperController(MineSweeperBoardModel model) {
		this.model = model;
		view = new MineSweeperView(this, model);
	}
	
}
