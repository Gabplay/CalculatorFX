package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SampleController{
    @FXML
    private Label results_screen;
    @FXML
    private void updateScreen(ActionEvent event) {
    	String text;

    	text = ((Button)event.getSource()).getText();

	    switch(text){
	    	case "CC":
	    		results_screen.setText("");
	    		break;
	    	case "DEL":
	    		text = results_screen.getText();
	    		if(!text.isEmpty()){
		    		text = text.replace(text.substring(text.length() - 1), "");
		    		results_screen.setText(text);
	    		}
	    		break;
	    	case "+":
	    	case "-":
	    	case "/":
	    	case "*":
	    	case "^":
	    	case "sqrt":
	    		String screen = results_screen.getText();
	    		getOperation(text, screen);
	    		break;
    		default:
    			results_screen.setText(results_screen.getText() + text);
    			break;
    	}
    }

    private void getOperation(String text, String screen){

    	long value = Long.parseLong(screen);
    	long result;

    	switch(text){
    		case "+":
    			System.out.println("+");
    			break;
    		case "-":
    			System.out.println("-");
    			break;
    		case "/":
    			System.out.println("/");
    			break;
    		case "*":
    			System.out.println("*");
    			break;
    		case "^":
    			result = value * value;
    			System.out.println(result);
    			break;
    		case "=":
    			results_screen.setText("");
    			break;
    	}
    }
}