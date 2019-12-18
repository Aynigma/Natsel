package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.Window;
import gui.models.SimulationPopulationRowModel;
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
import sim.Pop;
import sim.Simulation;

/**
 * This class handle the Simulation Scene and inherit from the Controller class
 * @author Aynigma
 * @see Controller Controller
 */
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
    
    /**
     * This method allows to add a row in the table about populations quantities per turn.
     * @param row
     */
    public void addRow(TurnDataRowModel row) {
    	observableList.add(row);
    	turn_table_view.setItems(observableList);
    }
	
	public SimulationController() {
		super();
		this.simulation = Window.getInstance().getSimulation();
		
		sql.SimulationHandler simHandler = new sql.SimulationHandler();
		simHandler.uploadSimulation(this.simulation.getName(), this.simulation.getDescription());
		
		sql.PopHandler popHandler = new sql.PopHandler();
		for (SimulationPopulationRowModel turnDataRowModel : Window.getInstance().getPopulations()) 
		{
            Pop pop = turnDataRowModel.getPopulation();
            popHandler.uploadPopulation(pop.getName(), pop.getDescription());
        }
		
		simulation.setController(this);
	}
	
	/**
	 * This method initialize all javafx components of the scene.
	 * <br/><b>And is automatically called on scene load</b>
	 */
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
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
