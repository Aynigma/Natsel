package gui.controllers;

import gui.Window;
import javafx.fxml.Initializable;

public abstract class Controller implements Initializable{
	
	public Controller() {
		Window.getInstance().setController(this);
	}
}
