package gui;

import java.util.ArrayList;
import gui.controllers.Controller;
import gui.models.SimulationPopulationRowModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sim.Simulation;

// fxml version attributes : xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml"

/**
 * This class is responsible of handling the window of the application
 * and therefore should be the entry point of the application.
 * <br/>This class also contains all needed references of simulation elements.
 * @author Aynigma
 * @see gui
 */
public class Window extends Application{
	
	private static Window instance = null;
	public static final String SCENE_PATH_SIMULATION        = "fxml/Simulation.fxml";
	public static final String SCENE_PATH_CREATE_POPULATION = "fxml/CreatePopulation.fxml";
	public static final String SCENE_PATH_CREATE_SIMULATION = "fxml/CreateSimulation.fxml";
	
	private static final String initialScene = SCENE_PATH_CREATE_SIMULATION;
	
	private Stage stage = null;
	private Controller controller = null;
	private Simulation simulation;
	private ArrayList<SimulationPopulationRowModel> populations;

	public static Window getInstance() {
		return instance;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public Controller getController() {
		return this.controller;
	}
	public Simulation getSimulation() {
		return this.simulation;
	}
	public ArrayList<SimulationPopulationRowModel> getPopulations(){
		return this.populations;
	}
	
	public Window() {
		if(instance == null) {
			instance = this;
		}
		this.simulation  = new Simulation("Untitled Simulation", "No description available...");
		this.populations = new ArrayList<SimulationPopulationRowModel>();
	}
	
	public static void main(String[] args) {
		Application.launch(Window.class, args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		this.stage = primaryStage;
		System.out.println("Initialising Window...");
		stage.setTitle("NatSel");

    //end program on exit
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent e) {

           quitApp();

        }
     });
	
    changeScene(getSceneFromFXML(initialScene)); 
    stage.show();

    System.out.println("Window Initialised\n");
	}
	
	public static Scene getSceneFromFXML(String path) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Window.class.getResource(path));
		try {
			System.out.println("--- Loading : "+path);
			return  (Scene) loader.load();
		}catch(Exception e) {
			System.out.println("!-Failed to load Scene\n"+e);
			e.printStackTrace();
			return null;
		}
	}
	
	public void changeScene(Scene scene) {
		if(scene != null) {
			stage.setScene(scene);
			System.out.println("--- Scene changed\n");
		}else {
			System.out.println("!-Can't change to a null Scene");
		}
	}
	
	public void quitApp() {
		System.out.println("/_\\ Exiting NatSel");
		Platform.exit();
        System.exit(0);
	}
	
	public void resetSimulation() {
		this.simulation = new Simulation("Untitled Simulation", "No description available...");
		this.populations = new ArrayList<SimulationPopulationRowModel>();
	}
}
