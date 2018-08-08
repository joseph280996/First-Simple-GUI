package edu.wit.dcsn.comp1050.lab04;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.json.*;
/* 
    * Comp 1050 ‐ Computer Science II 
   * Summer, 2018 
    * Lab 4 ‐ JavaFX Freeform 
    * Tung Pham
*/

public class Freedom extends Application{
	//Initialize
	private CirclePane circlePane = new CirclePane();
	private TextField FirstNameText = new TextField();
	private TextField LastNameText = new TextField();
	private TextField tfMi = new TextField();
	private BorderPane borderPane=new BorderPane();
	private VBox pane2 = new VBox();
	private HBox pane1=new HBox();
	
	@Override
	public void start (Stage primaryStage) throws Exception {
		
		//First Pane
		pane2.setPadding(new Insets(11,12,13,14));
		pane2.setSpacing(5);
		
		//Create Submit Button
		Button submitBt= new Button("Submit");
		submitBt.setOnAction(new importText());
		//Create input for Middle name
		pane2.getChildren().addAll(new Label("First Name:"), FirstNameText, new Label("MI:"), tfMi , new Label("Last Name"), LastNameText , submitBt);
		
		//Create second pane
		Button bt1=new Button("Englarge");
		Button bt2=new Button("Shrink");
		pane1.setSpacing(10);
		pane1.setAlignment(Pos.CENTER);
		pane1.getChildren().addAll(bt1,bt2);
		
		//Create third Pane
		borderPane.setTop(pane2);
		borderPane.setCenter(circlePane);
		borderPane.setBottom(pane1);
		
		//Create enlarge and shrink buttons
		bt1.setOnAction(new EnlargeHandler());
		bt2.setOnAction(new ShrinkHandler());
		
		//Create scene and set scene's property
		Scene scene = new Scene(borderPane);
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		
		//set Stage property
		primaryStage.setTitle("Main Pane");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	//Handle event on click submit
	class importText implements EventHandler<ActionEvent>{
		public void JSONWriter() {
			//try open file testOut.json
			try (PrintWriter jsonOut = new PrintWriter (new File("testOut.json")) ){
				//create JSON object
				JSONObject jo = new JSONObject();
				try{
					//put attributes to JSON object
					jo.put("firstName", FirstNameText.getText());
					jo.put("middleInitial", tfMi.getText());
					jo.put("lastName", LastNameText.getText());
					//write to JSON file
					jsonOut.write(jo.toString());
				}
				catch(JSONException e) {
					//check for JSONException
					circlePane.addText("Can't create Object", "" , "");
				}
				
			}
			//catch FileNotFoundException
			catch (FileNotFoundException ex) {
				circlePane.addText("File testOut.json not found","" , "");
			}
		}
		@Override
		public void handle(ActionEvent event) {
			//call addText function
			circlePane.addText(FirstNameText.getText(), tfMi.getText() ,LastNameText.getText());
			JSONWriter();
		}
	}
	
	//Handle event on click enlarge
	class EnlargeHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//call enlarge function
			circlePane.enlarge();
		}
	}
	
	//Handle event on click shrink
	class ShrinkHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event) {
			//Call shrink function
			circlePane.shrink();
		}
	}
	public static void main(String[] args) {
		Application.launch(args);
	}
}

//Implement circlePane class
class CirclePane extends StackPane {
	
	//Initialize the circle
	private Circle circle = new Circle(70);
	public CirclePane() {
		getChildren().add(circle);
		circle.setStroke(Color.BLACK);
		circle.setFill(Color.GREEN);
	}
	
	//Add Text to the Circle
	public void addText(String myFirstName, String myMI , String myLastName) {
		getChildren().clear();	
		Text text=new Text(String.format("Full Name: %s %s %s", myFirstName , myMI , myLastName));
		text.setBoundsType(TextBoundsType.VISUAL);
		getChildren().addAll(circle,text);
	}
	
	//Implement enlarge function
	public void enlarge() {
		circle.setRadius(circle.getRadius() + 2);
	}
	
	//Implement shrink function
	public void shrink() {
		circle.setRadius(circle.getRadius() > 70 ? circle.getRadius() - 2 : circle.getRadius());
	}
}
