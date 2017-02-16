package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.lang.Math;

public class SampleController{
    @FXML
    private Label main_screen, log_screen_label;

    /*** Global Variables ***/
    Double result = 0.0, x = 0.0, y = 0.0;
    char last_operation;

    @FXML
    private void updateScreen(ActionEvent event) {

    	/*** Variables ***/
    	String btn_text = ((Button)event.getSource()).getText();
    	String screen_text = main_screen.getText();
    	String log_text = log_screen_label.getText();

    	if(screen_text.equals("0.0 ")){
    		main_screen.setText("");
    		screen_text = "";
    		log_screen_label.setText("");
    		log_text = "";
    	}

	    switch(btn_text){
	    	case "CC":
	    		main_screen.setText("");
	    		log_screen_label.setText("");
	    		result = 0.0;
	    		break;
	    	case "DEL":
	    		btn_text = main_screen.getText();
	    		if(!btn_text.isEmpty()){
		    		btn_text = btn_text.substring(0, btn_text.length()-1);
		    		main_screen.setText(btn_text);
		    		//log_text_label.setText(log_text + btn_text);
	    		}
	    		break;
	    	case ".":
	    		// Do not allow multiple dots or dot without numbers
	    		if(screen_text.indexOf(".") == -1 && !screen_text.isEmpty()){
	    			main_screen.setText(screen_text + ".");
	    			log_screen_label.setText(log_screen_label + ".");
	    		}
	    		break;
	    	case "+":
	    	case "-":
	    	case "/":
	    	case "x":
	    	case "^":
	    	case "sqrt":
	    	case "=":
	    		if(!screen_text.isEmpty()){
	    			log_screen_label.setText(log_text + " " + btn_text + " ");
	    			getOperation(btn_text);
	    		}
	    		break;
    		default:
    			main_screen.setText(main_screen.getText() + btn_text);
    			log_screen_label.setText(log_text + btn_text);
    			break;
    	}
    }

    // TODO: fix log_screen repeating signs and not deleting

    private void getOperation(String btn_text){

    	String screen_text = main_screen.getText();
    	String log_text = log_screen_label.getText();
    	Double screen_value = Double.parseDouble(screen_text);
    	String strResult;

    	main_screen.setText(""); // Clear main screen

    	if(screen_value != null){
			x = screen_value;
		}

    	switch(btn_text){
    		case "+":
    			last_operation = '+';
    			result = x;
    			break;
    		case "-":
    			last_operation = '-';
    			result = x;
    			break;
    		case "/":
    			last_operation = '/';
    			result = x;
    			break;
    		case "x":
    			last_operation = 'x';
    			result = x;
    			break;
    		case "^":
    			last_operation = '^';
    			result = screen_value * screen_value;
    			break;
    		case "sqrt":
    			last_operation = 's';
    			result = Math.sqrt(screen_value);
    			break;
    		case "=":

    			if(last_operation == '+'){
	    			result += x;
    			} else if(last_operation == '-'){
	    			result -= x;
    			} else if(last_operation == '*'){
	    			result *= x;
    			} else if(last_operation == '/'){
	    			result /= x;
    			} else{
    				System.out.println("other operation");
    			}
    			if(log_text.charAt(log_text.length() - 1) != '+'){
    				//System.out.println("debug");
    			}
    			// Print Result
    	    	strResult = Double.toString(result);
    	    	System.out.println(strResult);
    			log_screen_label.setText(log_text + strResult);
    			main_screen.setText("0.0 ");
    			break;
    		default:
    			System.out.println("switch default debug");
    			break;
    	}
    }
}