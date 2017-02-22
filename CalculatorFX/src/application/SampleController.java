package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.lang.Math;

public class SampleController{
	@FXML
	private Label main_screen, log_screen_label;

	/*** Global Variables ***/
	Double result = 0.0, x = 0.0, y = 0.0;
	int count_operations = 0;
	char last_operation;
	String strResult;
	boolean clear = false;

	@FXML
	private void onButtonClick(ActionEvent event) {
		String btn_text = ((Button)event.getSource()).getText();
		updateScreen(btn_text);
	}

	@FXML
	private void onKeyReleased(KeyEvent event) {
		System.out.println("Key Pressed: " + event.getCode());
		
		String btn_text;
		
		switch(event.getCode()){
		case DELETE:
		case BACK_SPACE:	
			btn_text = "DEL";
			break;
		case C:
			btn_text = "CC";
			break;
		case PERIOD:
		case DECIMAL:
			btn_text = "-";
			break;
		case PLUS:
		case ADD:	
			btn_text = "+";
			break;
		case MINUS:
		case SUBTRACT:
			btn_text = "-";
			break;
		case MULTIPLY:
		case X:
			btn_text = "x";
			break;
		case EQUALS:
			btn_text = "=";
			break;
		case NUMPAD0:
		case DIGIT0:
			btn_text = "0";
			break;
		case NUMPAD1:
		case DIGIT1:
			btn_text = "1";
			break;
		case NUMPAD2:
		case DIGIT2:
			btn_text = "2";
			break;
		case NUMPAD3:
		case DIGIT3:
			btn_text = "3";
			break;
		case NUMPAD4:
		case DIGIT4:
			btn_text = "4";
			break;
		case NUMPAD5:
		case DIGIT5:
			btn_text = "5";
			break;
		case NUMPAD6:
		case DIGIT6:
			btn_text = "6";
			break;
		case NUMPAD7:
		case DIGIT7:
			btn_text = "7";
			break;
		case NUMPAD8:
		case DIGIT8:
			btn_text = "8";
			break;
		case NUMPAD9:
		case DIGIT9:
			btn_text = "9";
			break;
		default:
			btn_text = "";
			break;
		}
		updateScreen(btn_text);
	}

	private void updateScreen(String btn_text){

		/*** Variables ***/
		String screen_text = main_screen.getText();
		String log_text = log_screen_label.getText();

		// Initial Screen
		if(screen_text.equals("0.0 ")){
			clearScreen();
		} else if(last_operation == '=' && btn_text.chars().allMatch( Character::isDigit ) && !clear){
			clearScreen();
			clear = true;
		}

		screen_text = main_screen.getText();
		log_text = log_screen_label.getText();

		switch(btn_text){
		case "CC":
			clearScreen();
			break;
		case "DEL":
			btn_text = main_screen.getText();

			if(last_operation == '='){
				clearScreen();
			} else if(!btn_text.isEmpty()){
				// Change Main Screen
				btn_text = btn_text.substring(0, btn_text.length()-1);
				main_screen.setText(btn_text);

				// Change Log Screen if the last char is a number 
				char last_char = log_text.charAt(log_text.length()-1);

				if(Character.isDigit(last_char) || last_char == '.'){
					log_text = log_text.substring(0, log_text.length()-1);
					log_screen_label.setText(log_text);
				}
			}
			break;
		case ".":
			// Do not allow multiple dots or dot without numbers
			if(screen_text.indexOf(".") == -1 && !screen_text.isEmpty()){
				main_screen.setText(screen_text + ".");
				log_screen_label.setText(log_text + ".");
			}
			break;
		case "+":
		case "-":
		case "/":
		case "x":
		case "^":
		case "sqrt":
			count_operations++;
			if(!screen_text.isEmpty() && (count_operations <= 1)){
				log_screen_label.setText(log_text + " " + btn_text + " ");
				getOperation(btn_text);
			}
			break;
		case "=":
			if(!screen_text.isEmpty()){
				log_screen_label.setText(log_text + " " + btn_text + " ");
				getOperation(btn_text);
			}
			break;
		default:
			// Limit then number to 16 digits 
			if(screen_text.length() <= 16){
				main_screen.setText(main_screen.getText() + btn_text);
				log_screen_label.setText(log_text + btn_text);
			}
			break;
		}
	}

	private void clearScreen(){
		main_screen.setText("");
		log_screen_label.setText("");
		count_operations = 0;
	}

	private void getOperation(String btn_text){

		String screen_text = main_screen.getText();
		String log_text = log_screen_label.getText();
		Double screen_value = Double.parseDouble(screen_text);

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
			result = x;
			break;
		case "sqrt":
			result = Math.sqrt(screen_value);
			printResult(result, log_text, false);
			break;
		case "=":
			getResult();
			printResult(result, log_text, true);
			break;
		default:
			System.out.println("switch default debug");
			break;
		}
	}

	private double getResult(){
		if(last_operation == '+'){
			result += x;
		} else if(last_operation == '-'){
			result -= x;
		} else if(last_operation == 'x'){
			result *= x;
		} else if(last_operation == '/'){
			result /= x;
		} else if(last_operation == '^'){
			result = Math.pow(result, x);
		} 
		return result;
	}

	private void printResult(Double result, String log_text, boolean equals){
		strResult = Double.toString(result);
		System.out.println(strResult);

		if(!equals){
			log_screen_label.setText(log_text + " = " + strResult);
		} else{
			log_screen_label.setText(log_text + strResult);
		}
		// Update Variables
		last_operation = '=';
		clear = false;
		count_operations = 0;
		result = 0.0;

		// Errors Handling
		if(strResult.equals("Infinity")){
			log_screen_label.setText("Division by zero = Infinity");
			main_screen.setText("");
		} else if(strResult.equals("NaN")){
			log_screen_label.setText("Cannot calculate negative square roots!");
			main_screen.setText("");
		} else{
			main_screen.setText(strResult);
		}
	}
}