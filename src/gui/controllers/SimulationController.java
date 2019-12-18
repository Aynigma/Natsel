package gui.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import exporters.CSVExporter;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import sim.Pop;
import sim.Simulation;
/**
 * This class handle the Simulation Scene and inherit from the Controller class
 * @author Aynigma
 * @see Controller Controller
 */
public class SimulationController extends Controller {

	@FXML
	private MenuItem menu_export_csv;
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
    
    private ObservableList<TurnDataRowModel> turnData;
    
    /**
     * This method allows to add a row in the table about populations quantities per turn.
     * @param row
     */
    public void addRow(TurnDataRowModel row) {
    	turnData.add(row);
    	turn_table_view.setItems(turnData);
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
		
		TableView<TurnDataRowModel> table = turn_table_view;

		for (int i = 0; i < Window.getInstance().getPopulations().size(); i++) {
            TableColumn<TurnDataRowModel, Integer> tc = new TableColumn<TurnDataRowModel, Integer>(""+Window.getInstance().getPopulations().get(i).getName());
            final int colNo = i;
            tc.setCellValueFactory(p -> new SimpleIntegerProperty(((TableColumn.CellDataFeatures<TurnDataRowModel, Integer>)p).getValue().getPopulationQuantities()[colNo]).asObject());
            		

            table.getColumns().add(tc);
		}
		
		
		turnData = FXCollections.observableArrayList();
		
		
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
		
		menu_export_csv.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialFileName("NatSel - "+simulation.getName());
				
				ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
	            fileChooser.getExtensionFilters().add(extFilter);
				fileChooser.setSelectedExtensionFilter(extFilter);
				
				File selectedFile = fileChooser.showSaveDialog(Window.getInstance().getStage());
				System.out.println("--- Trying to save data at : "+selectedFile);
				if(selectedFile != null) {
					
					String[] populationsNames = new String[Window.getInstance().getPopulations().size()];
					for (int i = 0; i < populationsNames.length; i++) {
						populationsNames[i] = Window.getInstance().getPopulations().get(i).getName();
					}

					
					TurnDataRowModel[] populationQuantities = new TurnDataRowModel[turnData.size()];
					for (int i = 0; i < populationQuantities.length; i++) {
						populationQuantities[i] = turnData.get(i);
					}
					
					CSVExporter csvExporter = new CSVExporter(populationsNames, populationQuantities);
					
					boolean saveSucces = csvExporter.export(selectedFile.getAbsolutePath());
					System.out.println(saveSucces ? "-- File succesfully saved" : "/_\\ Save failed");
				}
				
			}
		});
		
		
    }


	
}