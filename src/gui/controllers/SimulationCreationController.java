package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.Window;
import gui.models.SimulationPopulationRowModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import rules.Rule;
import rules.Eat;
import rules.NeedFood;
import rules.Reproduce;
import sim.Pop;
import sim.Simulation;

/**
 * This class inherit the Controller class and is meant to manage creation of simulations
 * @author Aynigma
 * @see Controller Controller
 */
public class SimulationCreationController extends Controller {

    @FXML
    private Button    crea_sim_button_cancel;
    @FXML
    private Button    crea_sim_button_create;
    @FXML
    private TextField crea_sim_name;
    @FXML
    private TextArea  crea_sim_desc;
    @FXML
    private Button    crea_sim_button_pop_add;
    @FXML 
    private Button    crea_sim_button_pop_edit;
    @FXML
    private Button    crea_sim_button_pop_delete;
    @FXML
    private TableView  <SimulationPopulationRowModel         > crea_sim_table_view_pop;
    @FXML
    private TableColumn<SimulationPopulationRowModel, Integer> crea_sim_table_column_pop_quantity;
    @FXML
    private TableColumn<SimulationPopulationRowModel, String > crea_sim_table_column_pop_name;
    @FXML
    private TableColumn<SimulationPopulationRowModel, String > crea_sim_table_column_pop_desc;
    
	/**
	 * This method initialize all javafx components of the scene.
	 * <br/><b>And is automatically called on scene load</b>
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		crea_sim_table_column_pop_quantity.setEditable(true);
		crea_sim_table_column_pop_quantity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SimulationPopulationRowModel,Integer>>(){

		@Override
		public void handle(CellEditEvent<SimulationPopulationRowModel, Integer> event) {
			event.getRowValue().setQuantity(event.getNewValue());
		}});
		
		crea_sim_table_column_pop_quantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		crea_sim_table_column_pop_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"   ));
		crea_sim_table_column_pop_name    .setCellValueFactory(new PropertyValueFactory<>("name"       ));
		crea_sim_table_column_pop_desc    .setCellValueFactory(new PropertyValueFactory<>("description"));
		
		ObservableList<SimulationPopulationRowModel> observableList = FXCollections.observableArrayList();
		for (SimulationPopulationRowModel simulationPopulationRowModel : Window.getInstance().getPopulations()) {
			observableList.add(simulationPopulationRowModel);
		}
		
		crea_sim_table_view_pop.setItems(observableList);
		crea_sim_table_view_pop.setEditable(true);
		
		crea_sim_name.setText(Window.getInstance().getSimulation().getName());
		crea_sim_desc.setText(Window.getInstance().getSimulation().getDescription());
		
		crea_sim_button_cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Window.getInstance().changeScene(Window.getSceneFromFXML(Window.SCENE_PATH_CREATE_SIMULATION));
			}
		});
		
		crea_sim_button_create.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				Simulation simulation = Window.getInstance().getSimulation();
				simulation.setName       ((crea_sim_name.getText() != "") ? crea_sim_name.getText() : "Untitled Simulation");
				simulation.setDescription((crea_sim_desc.getText() != "") ? crea_sim_desc.getText() : "No description available...");
				
				for (SimulationPopulationRowModel simulationPopulationRowModel : Window.getInstance().getPopulations()) {
					for (int i = 0; i < simulationPopulationRowModel.getQuantity(); i++) {
						
						Pop populationModel = simulationPopulationRowModel.getPopulation();
						
						Pop population = new Pop(populationModel.getName(), populationModel.getDescription(), populationModel.getSimulation(), populationModel.getFoodType(), 1);
						for (Rule rule : populationModel.getRules()) {
							if(rule instanceof Eat){
								population.addRule(new Eat((Eat)rule));
							}
							if(rule instanceof NeedFood){
								population.addRule(new NeedFood((NeedFood)rule));
							}
							if(rule instanceof Reproduce){
								population.addRule(new Reproduce((Reproduce)rule));
							}
						}

						simulation.addPop(population);
					}
				}

				Window.getInstance().changeScene(Window.getSceneFromFXML(Window.SCENE_PATH_SIMULATION));
			}
		});
		
		crea_sim_button_pop_add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//save textfields values
				Window.getInstance().getSimulation().setName       (crea_sim_name.getText());
				Window.getInstance().getSimulation().setDescription(crea_sim_desc.getText());
        
				Window.getInstance().changeScene(Window.getSceneFromFXML(Window.SCENE_PATH_CREATE_POPULATION));
			}
		});
		
		crea_sim_button_pop_edit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				SimulationPopulationRowModel selectedRow = crea_sim_table_view_pop.getSelectionModel().getSelectedItem();
				int populationIndex = Window.getInstance().getPopulations().indexOf(selectedRow);
				if(populationIndex >= 0) {
					SimulationPopulationRowModel selectedPopulation = Window.getInstance().getPopulations().get(populationIndex);
					
					Window.getInstance().changeScene(Window.getSceneFromFXML(Window.SCENE_PATH_CREATE_POPULATION));
					PopulationCreationController populationCreationController = (PopulationCreationController)Window.getInstance().getController();
					populationCreationController.load(selectedPopulation);
				}
			}
		});
		
		crea_sim_button_pop_delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SimulationPopulationRowModel selectedRow = crea_sim_table_view_pop.getSelectionModel().getSelectedItem();
				Window.getInstance().getPopulations().remove(selectedRow);
				System.out.println("/_\\ Deleted pop : "+selectedRow.getName());
				ObservableList<SimulationPopulationRowModel> observableList = FXCollections.observableArrayList();
				for (SimulationPopulationRowModel simulationPopulationRowModel : Window.getInstance().getPopulations()) {
					observableList.add(simulationPopulationRowModel);
				}
				crea_sim_table_view_pop.setItems(observableList);
			}
		});
		
	}
}
