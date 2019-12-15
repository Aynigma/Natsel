package gui.controllers;

import java.awt.Desktop;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.Window;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;

/**
 * This abstract class should be inherited by all controllers.
 * <br/>This class also handle the menu bar of the application if inherited classes calls super.initialize method. 
 * @author Aynigma
 */
public abstract class Controller implements Initializable{
	
	private static final String helpURL = "https://github.com/Whismirk/Natsel/wiki";
	
	@FXML
	private MenuItem menu_new;
	@FXML
	private MenuItem menu_quit;
	@FXML
	private MenuItem menu_help;
	
	public Controller() {
		Window.getInstance().setController(this);
	}

	/**
	 * Initialize menu bar items, giving them actions.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		menu_new.setOnAction(new EventHandler<ActionEvent>() {
			@Override //reset the current simulation to start a new
			public void handle(ActionEvent event) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText("Do you want to start a new Simulation ?");
				alert.setContentText("The current simulation's data will not be saved.");
				
				Optional<ButtonType> option = alert.showAndWait();
				if(option.get() == ButtonType.OK) {
					Window.getInstance().resetSimulation();
					Window.getInstance().changeScene(Window.getSceneFromFXML(Window.SCENE_PATH_CREATE_SIMULATION));
				}
			}
		});
		
		menu_quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override //gracefully exit the application
			public void handle(ActionEvent event) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText("Do you want to exit Natsel ?");
				alert.setContentText("The current simulation's data will not be saved.");
				
				Optional<ButtonType> option = alert.showAndWait();
				if(option.get() == ButtonType.OK) {
					Window.getInstance().quitApp();
				}
			}
		});
		
		menu_help.setOnAction(new EventHandler<ActionEvent>() {
			@Override //open helpURL page in default system browser
			public void handle(ActionEvent event) {
				try {
					System.out.println("- Opening help at : "+helpURL);
				    Desktop.getDesktop().browse(new URL(helpURL).toURI());
				} catch (Exception e) {
				    e.printStackTrace();
				}
			}
		});
		
	}
}
