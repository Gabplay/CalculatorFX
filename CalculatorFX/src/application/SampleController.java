package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.lang.Math;

public class SampleController{
    @FXML
    private Label results_screen, log_screen_label;
    @FXML
    private void updateScreen(ActionEvent event) {

    	/*** Variables ***/
    	String btn_text = ((Button)event.getSource()).getText();
    	String screen = results_screen.getText();
    	String log_screen = log_screen_label.getText();
    	
	    switch(btn_text){
	    	case "CC":
	    		results_screen.setText("");
	    		log_screen_label.setText("");
	    		break;
	    	case "DEL":
	    		btn_text = results_screen.getText();
	    		if(!btn_text.isEmpty()){
		    		btn_text = btn_text.substring(0, btn_text.length()-1);
		    		results_screen.setText(btn_text);
		    		//log_screen_label.setText(log_screen + btn_text);
	    		}
	    		break;
	    	case ",":	
	    	case "+":
	    	case "-":
	    	case "/":
	    	case "x":
	    	case "^":
	    	case "sqrt":
	    	case "=":
	    		getOperation(btn_text, screen);
	    		log_screen_label.setText(log_screen + btn_text);
	    		break;
    		default:
    			results_screen.setText(results_screen.getText() + btn_text);
    			log_screen_label.setText(log_screen + btn_text);
    			break;
    	}
    }

    private void getOperation(String btn_text, String screen){

    	double screen_value = Double.parseDouble(screen);
    	double result = 0, x = 0, y = 0;
    	String strResult;
    	char operator = ' ';
    	char last_screen_char = screen.charAt(btn_text.length() - 1);

    	switch(btn_text){
    		case "+":
    			x = screen_value;
    			results_screen.setText("");
    			break;
    		case "-":
    			System.out.println("-");
    			break;
    		case "/":
    			System.out.println("/");
    			break;
    		case "x":
    			System.out.println("*");
    			break;
    		case "^":
    			result = screen_value * screen_value;
    			strResult = Double.toString(result);
    			results_screen.setText(strResult);
    			break;
    		case "sqrt":
    			result = Math.sqrt(screen_value);
    			strResult = Double.toString(result);
    			results_screen.setText(strResult);
    			break;
    		case "=":
    			strResult = Double.toString(result);
    			results_screen.setText(strResult);
    			break;
    		default:
    			results_screen.setText(screen + btn_text);
    			break;	
    	}
    }
}