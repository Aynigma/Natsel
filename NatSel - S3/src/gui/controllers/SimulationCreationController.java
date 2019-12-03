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
import sim.Pop;
import sim.Simulation;

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
    private Button    crea_sim_button_pop_delete;

    @FXML
    private TableView  <SimulationPopulationRowModel         > crea_sim_table_view_pop;
    
    @FXML
    private TableColumn<SimulationPopulationRowModel, Integer> crea_sim_table_column_pop_quantity;

    @FXML
    private TableColumn<SimulationPopulationRowModel, String > crea_sim_table_column_pop_name;

    @FXML
    private TableColumn<SimulationPopulationRowModel, String > crea_sim_table_column_pop_desc;
    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		crea_sim_table_column_pop_quantity.setEditable(true);
		crea_sim_table_column_pop_quantity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SimulationPopulationRowModel,Integer>>(){

			@Override
			public void handle(CellEditEvent<SimulationPopulationRowModel, Integer> event) {
				// TODO Auto-generated method stub
				event.getRowValue().setQuantity(event.getNewValue());
			}
			
		});
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
				// TODO Auto-generated method stub
				
				Simulation simulation = Window.getInstance().getSimulation();
				simulation.setName       ((crea_sim_name.getText() != "") ? crea_sim_name.getText() : "Untitled Simulation");
				simulation.setDescription((crea_sim_desc.getText() != "") ? crea_sim_desc.getText() : "No description available...");
				
				for (SimulationPopulationRowModel simulationPopulationRowModel : Window.getInstance().getPopulations()) {
					for (int i = 0; i < simulationPopulationRowModel.getQuantity(); i++) {
						simulation.addPop(new Pop(simulationPopulationRowModel.getPopulation()));
					}
				}
				
				Window.getInstance().changeScene(Window.getSceneFromFXML(Window.SCENE_PATH_SIMULATION));
			}
		});
		
		crea_sim_button_pop_add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Window.getInstance().changeScene(Window.getSceneFromFXML(Window.SCENE_PATH_CREATE_POPULATION));
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
