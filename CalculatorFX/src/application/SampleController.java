package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.lang.Math;

public class SampleController{
    @FXML
    private Label results_screen;
    @FXML
    private void updateScreen(ActionEvent event) {

    	/*** Variables ***/
    	String text = ((Button)event.getSource()).getText();
    	String screen = results_screen.getText();

	    switch(text){
	    	case "CC":
	    		results_screen.setText("");
	    		break;
	    	case "DEL":
	    		text = results_screen.getText();
	    		if(!text.isEmpty()){
		    		text = text.substring(0, text.length()-1);
		    		results_screen.setText(text);
	    		}
	    		break;
	    	case "+":
	    	case "-":
	    	case "÷":
	    	case "X":
	    	case "^":
	    	case "√":
	    	case "=":
	    		getOperation(text, screen);
	    		break;
    		default:
    			results_screen.setText(results_screen.getText() + text);
    			break;
    	}
    }

    private void getOperation(String text, String screen){

    	double screen_value = Double.parseDouble(screen);
    	double result = 0;
    	String strResult;
    	char operator = ' ';
    	char last_screen_char = screen.charAt(text.length() - 1);

    	if(operator != last_screen_char){
	    	switch(text){
	    		case "+":
	    			System.out.println("+");
	    			break;
	    		case "-":
	    			System.out.println("-");
	    			break;
	    		case "÷":
	    			System.out.println("/");
	    			break;
	    		case "X":
	    			System.out.println("*");
	    			break;
	    		case "^":
	    			result = screen_value * screen_value;
	    			strResult = Double.toString(result);
	    			results_screen.setText(strResult);
	    			break;
	    		case "√":
	    			result = Math.sqrt(screen_value);
	    		case "=":
	    			strResult = Double.toString(result);
	    			results_screen.setText(strResult);
	    			break;
	    	}
	    }
    }
}