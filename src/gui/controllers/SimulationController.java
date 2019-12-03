package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.Window;
import gui.models.TurnDataRowModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import javafx.scene.control.cell.PropertyValueFactory;
import sim.Simulation;

public class SimulationController extends Controller {

    @FXML
    private TableView<TurnDataRowModel> turn_table_view;

    @FXML
    private TableColumn<TurnDataRowModel, Integer> turn_table_col_turn;

    @FXML
    private Label turn_label;

    @FXML
    private TextField turn_textField;

    @FXML
    private Button turn_doTurn_button;
	
    private Simulation simulation;
    
    private ObservableList<TurnDataRowModel> observableList;
    
    public void addRow(TurnDataRowModel row) {
    	observableList.add(row);
    	turn_table_view.setItems(observableList);
    }
	
	public SimulationController() {
		super();
		this.simulation = Window.getInstance().getSimulation();
		simulation.setController(this);
	}
	
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
		turn_table_col_turn.setCellValueFactory(new PropertyValueFactory<>("turn"));
	
		
		ObservableList<TurnDataRowModel> data = FXCollections.observableArrayList();
		
		
		TableView<TurnDataRowModel> table = turn_table_view;

		for (int i = 0; i < Window.getInstance().getPopulations().size(); i++) {
            TableColumn<TurnDataRowModel, Integer> tc = new TableColumn<TurnDataRowModel, Integer>(""+Window.getInstance().getPopulations().get(i).getName());
            final int colNo = i;
            tc.setCellValueFactory(p -> new SimpleIntegerProperty(((TableColumn.CellDataFeatures<TurnDataRowModel, Integer>)p).getValue().getPopulationQuantities()[colNo]).asObject());
            		

            table.getColumns().add(tc);
		}
		
		table.setItems(data);
		//simulation.UpdateControllerTable();
		
		
		observableList = FXCollections.observableArrayList();
		
		
		turn_doTurn_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				int value = 1;
				try {
					value = Integer.parseInt(turn_textField.getText());
				}catch(Exception e) {
					System.out.println("!- Value of textfield isn't an integer");
					System.out.println(e);
				}finally{
					simulation.iterate(value, true);
					turn_label.setText(""+simulation.iteration);
				}
				
			}
			
		});
		

		//Make sure TextField contains only numbers
		turn_textField.setText("1");
		turn_textField.setTextFormatter(new TextFormatter<>(change -> {
			String text = change.getText();

		    if (text.matches("[0-9]*")) {
		        return change;
		    }

		    return null;
		}));
		
		
		turn_label.setText(""+simulation.iteration);
		
		
    }


	
}