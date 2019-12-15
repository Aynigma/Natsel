package gui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gui.Window;
import gui.models.SimulationPopulationRowModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import rules.Eat;
import rules.NeedFood;
import rules.Reproduce;
import rules.Rule;
import sim.FoodType;
import sim.Pop;

/**
 * This class inherit the Controller abstract class and is meant to manage the population creation screen.
 * @author Aynigma
 * @see Controller Controller
 */

public class PopulationCreationController extends Controller {

    @FXML
    private Button crea_pop_button_cancel;
    @FXML
    private Button crea_pop_button_create;
    @FXML
    private RadioButton crea_pop_type_animal;
    @FXML
    private RadioButton crea_pop_type_vegetal;
    @FXML
    private TextField crea_pop_quantity;
    @FXML
    private TextField crea_pop_name;
    @FXML
    private TextArea crea_pop_desc;
    @FXML
    private CheckBox crea_pop_rule_eat_active;
    @FXML
    private CheckBox crea_pop_rule_eat_carnivore;
    @FXML
    private CheckBox crea_pop_rule_eat_herbivore;
    @FXML
    private TextField crea_pop_rule_eat_maxEatenOnce;
    @FXML
    private TextField crea_pop_rule_eat_maxEaten;
    @FXML
    private CheckBox crea_pop_rule_needFood_active;
    @FXML
    private TextField crea_pop_rule_needFood_need;
    @FXML
    private TextField crea_pop_rule_needFood_survivalRate;
    @FXML
    private CheckBox crea_pop_rule_reproduce_active;
    @FXML
    private TextField crea_pop_rule_reproduce_need;
    @FXML
    private TextField crea_pop_rule_reproduce_children;
    
    private ToggleGroup popTypeRadio;
    private boolean isLoaded = false;
    private SimulationPopulationRowModel model = null;
    
    /**
     * This method enables to edit a population's data
     * @param simulationPopulationRowModel the model where the data is read and will be edited
     */
    public void load(SimulationPopulationRowModel simulationPopulationRowModel) {
    	isLoaded = true;
    	model = simulationPopulationRowModel;
    	crea_pop_button_create.setText("Submit");
    	
    	crea_pop_quantity.setText(""+simulationPopulationRowModel.getQuantity());
    	crea_pop_name    .setText(""+simulationPopulationRowModel.getName());
    	crea_pop_desc    .setText(""+simulationPopulationRowModel.getDescription());
    	
    	FoodType foodType = simulationPopulationRowModel.getPopulation().getFoodType();
    	crea_pop_type_animal .setSelected(foodType == FoodType.animal);
    	crea_pop_type_vegetal.setSelected(foodType == FoodType.vegetable);
    	
    	ArrayList<Rule> rules = simulationPopulationRowModel.getPopulation().getRules();
    	
    	//disable all rules by default then enable only active rules
    	crea_pop_rule_eat_active.setSelected(false);
    	crea_pop_rule_needFood_active.setSelected(false);
    	crea_pop_rule_reproduce_active.setSelected(false);
    	
    	
    	for (Rule rule : rules) {
			if(rule instanceof Eat) {
				crea_pop_rule_eat_active.setSelected(true);
				Eat eatRule = (Eat)rule;
				
				ArrayList<FoodType> eatFoodTypes = eatRule.getEdibles();
				crea_pop_rule_eat_carnivore.setSelected(eatFoodTypes.contains(FoodType.animal));
				crea_pop_rule_eat_herbivore.setSelected(eatFoodTypes.contains(FoodType.vegetable));
				
				crea_pop_rule_eat_maxEatenOnce.setText(""+(int)eatRule.getMaxEatenOnce());
				crea_pop_rule_eat_maxEaten    .setText(""+(int)eatRule.getMaxEaten());
			}
			else if(rule instanceof NeedFood) {
				crea_pop_rule_needFood_active.setSelected(true);
				NeedFood needFoodRule = (NeedFood)rule;
				
				crea_pop_rule_needFood_need        .setText(""+(int)needFoodRule.getNeed());
				crea_pop_rule_needFood_survivalRate.setText(""+(float)needFoodRule.getSurvivalRate());
			}
			else if(rule instanceof Reproduce) {
				crea_pop_rule_reproduce_active.setSelected(true);
				Reproduce reproduceRule = (Reproduce)rule;
				
				crea_pop_rule_reproduce_need.setText(""+(int)reproduceRule.getEatenNeed());
				crea_pop_rule_reproduce_children.setText(""+(int)reproduceRule.getChildren());
			}
		}
    }
    
    /**
     * Initialize all the Javafx components of the corresponding scene
     * <br/><b>Automatically called on scene loading</b>
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		popTypeRadio = new ToggleGroup();
		crea_pop_type_animal .setToggleGroup(popTypeRadio);
		crea_pop_type_vegetal.setToggleGroup(popTypeRadio);
		
		crea_pop_type_animal          .setSelected(true);
		
		crea_pop_rule_eat_carnivore   .setSelected(true);
		
		crea_pop_rule_eat_active      .setSelected(true);
		crea_pop_rule_needFood_active .setSelected(true);
		crea_pop_rule_reproduce_active.setSelected(true);
		
		crea_pop_name.setText("Unnamed population");
		crea_pop_desc.setText("No description yet");
		crea_pop_quantity.setText("1");
		crea_pop_quantity.setTextFormatter(new TextFormatter<>(change -> {
			String text = change.getText();
		    return (text.matches("[0-9]*")) ? change : null;
		}));
		
		crea_pop_rule_eat_maxEatenOnce.setText("1");
		crea_pop_rule_eat_maxEatenOnce.setTextFormatter(new TextFormatter<>(change -> {
			String text = change.getText();
		    return (text.matches("[0-9]*")) ? change : null;
		}));
		crea_pop_rule_eat_maxEaten.setText("1");
		crea_pop_rule_eat_maxEaten.setTextFormatter(new TextFormatter<>(change -> {
			String text = change.getText();
		    return (text.matches("[0-9]*")) ? change : null;
		}));
		crea_pop_rule_needFood_need.setText("1");
		crea_pop_rule_needFood_need.setTextFormatter(new TextFormatter<>(change -> {
			String text = change.getText();
		    return (text.matches("[0-9]*")) ? change : null;
		}));
		crea_pop_rule_needFood_survivalRate.setText("100.0");
		crea_pop_rule_needFood_survivalRate.setTooltip(new Tooltip("100.0 = 100%, \n0.0 = 0%"));
		crea_pop_rule_needFood_survivalRate.setTextFormatter(new TextFormatter<>(change -> {
			String text = change.getText();
			return (text.matches("(([1-9][0-9]*)|0)?(\\.[0-9]*)?")) ? change : null;
		}));
		crea_pop_rule_reproduce_need.setText("1");
		crea_pop_rule_reproduce_need.setTextFormatter(new TextFormatter<>(change -> {
			String text = change.getText();
		    return (text.matches("[0-9]*")) ? change : null;
		}));
		crea_pop_rule_reproduce_children.setText("1");
		crea_pop_rule_reproduce_children.setTextFormatter(new TextFormatter<>(change -> {
			String text = change.getText();
		    return (text.matches("[0-9]*")) ? change : null;
		}));
	
		crea_pop_button_cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				Window.getInstance().changeScene(Window.getSceneFromFXML(Window.SCENE_PATH_CREATE_SIMULATION));
			}
		});
		
		crea_pop_button_create.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				RadioButton poptype = (RadioButton) popTypeRadio.getSelectedToggle();
				FoodType foodtype = (poptype == crea_pop_type_vegetal) ? FoodType.vegetable : FoodType.animal;
				
				Pop population = new Pop(crea_pop_name.getText(), crea_pop_desc.getText(), Window.getInstance().getSimulation(), foodtype,0);
				
				if(crea_pop_rule_eat_active.isSelected()) {
					
					ArrayList<FoodType> foodTypes = new ArrayList<FoodType>();
					if(crea_pop_rule_eat_carnivore.isSelected()) {foodTypes.add(FoodType.animal   );}
					if(crea_pop_rule_eat_herbivore.isSelected()) {foodTypes.add(FoodType.vegetable);}
					
					int maxEatenOnce = 1;
					int maxEaten = 1;
					
					try {
						maxEatenOnce = Integer.parseInt(crea_pop_rule_eat_maxEatenOnce.getText());
						maxEaten     = Integer.parseInt(crea_pop_rule_eat_maxEaten    .getText());
								
					}catch(Exception e) {
						System.out.println("!- "+e);
						e.printStackTrace();
					}
					
					Eat eatRule = new Eat(foodTypes, maxEatenOnce, maxEaten);
					population.addRule(eatRule);
				}
				
				if(crea_pop_rule_needFood_active.isSelected()) {
					
					int   need         = 0;
					float survivalRate = 0;
					
					try {
						need         = Integer.parseInt  (crea_pop_rule_needFood_need        .getText());
						survivalRate = Float  .parseFloat(crea_pop_rule_needFood_survivalRate.getText());
					}catch(Exception e) {
						System.out.println("!- "+e);
						e.printStackTrace();
					}
					
					NeedFood needFoodRule = new NeedFood(need, survivalRate);
					population.addRule(needFoodRule);
				}
				
				if(crea_pop_rule_reproduce_active.isSelected()) {
					
					int need     = 0;
					int children = 0;
					
					try {
						need     = Integer.parseInt(crea_pop_rule_reproduce_need    .getText());
						children = Integer.parseInt(crea_pop_rule_reproduce_children.getText());
					}catch(Exception e) {
						System.out.println("!- "+e);
						e.printStackTrace();
					}
					
					Reproduce reproduceRule = new Reproduce(need, children);
					population.addRule(reproduceRule);
				}
				
				System.out.println(population.getRules());
				
				int quantity = 1;
				try {
					quantity = Integer.parseInt(crea_pop_quantity.getText());
				}catch(Exception e) {
					System.out.println(e);
					e.printStackTrace();
				}
				
				//check if this pop already exists
				ArrayList<SimulationPopulationRowModel> simulationPopulationRowModels = Window.getInstance().getPopulations();
				if(isLoaded && simulationPopulationRowModels.contains(model)) {
					int index = simulationPopulationRowModels.indexOf(model);
					System.out.println("index : " + index);
					SimulationPopulationRowModel newModel = new SimulationPopulationRowModel(quantity, population);
					simulationPopulationRowModels.set(index, newModel);
					
				}else {
					simulationPopulationRowModels.add(new SimulationPopulationRowModel(quantity, population));
				}
			
				Window.getInstance().changeScene(Window.getSceneFromFXML(Window.SCENE_PATH_CREATE_SIMULATION));
			}
		});
		
	}
}
