package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import java.math.BigDecimal;
import java.lang.Math;

public class SampleController{
	@FXML
	private Label main_screen, log_screen_label;

	/*** Global Variables ***/
	BigDecimal result = new BigDecimal("0"), x = new BigDecimal("0");
	int count_operations = 0;
	char last_operation = '=';
	boolean clear = false, error = false;

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
				btn_text = ".";
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
			case DIVIDE:
				btn_text = "/";
				break;
			case E:
				btn_text = "^";
				break;
			case S:
				btn_text = "sqrt";
				break;
			case EQUALS:
			case ENTER:
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
		if(btn_text != ""){
			updateScreen(btn_text);
		}
	}

	private void updateScreen(String btn_text){
		/*** Variables ***/
		String screen_text = main_screen.getText();
		String log_text = log_screen_label.getText();

		// Initial Screen
		if(screen_text.equals("0.0 ")){
			clearScreen();
			clear = true; // Clear after result is shown
		} else if(screen_text.equals("0") && log_text.equals("0")){
			clearScreen(); // Do not show useless zeros to the left
		}
		// Clear screen if typed digit after result is shown
		else if(last_operation == '=' && btn_text.chars().allMatch( Character::isDigit ) && !clear){
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
				deleteLastChar(screen_text, log_text);
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
				if(!log_text.isEmpty() && (count_operations < 1)){
					count_operations++;
					log_screen_label.setText(log_text + " " + btn_text + " ");
					if(!screen_text.isEmpty()){
						getOperation(btn_text);
					}
				}
				break;
			case "=":
				if(!screen_text.isEmpty() && last_operation != '='){
					log_screen_label.setText(log_text + " " + btn_text + " ");
					getOperation(btn_text);
				}
				break;
			default:
				// Limit the number to 16 digits and do not show useless zeros to the left
				if(screen_text.length() <= 16 && !(screen_text.equals("0") && btn_text.equals("0"))){
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

	private void deleteLastChar(String screen_text, String log_text){
		if(last_operation == '=' && !clear){
			clearScreen(); // Clear screen after result is shown
		}
		else if(!log_text.isEmpty()){
			if(!screen_text.isEmpty()){
				// Change Main Screen
				screen_text = screen_text.substring(0, screen_text.length()-1);
				main_screen.setText(screen_text);
			}
			// Change Log Screen if the last char is a number or a period
			char last_char = log_text.charAt(log_text.length()-1);

			// Delete last char if it's a digit or a period
			if(Character.isDigit(last_char) || last_char == '.'){
				log_text = log_text.substring(0, log_text.length()-1);
				log_screen_label.setText(log_text);
			}
			// Delete operation
			else if(last_operation != '='){
				count_operations--;
				log_text = log_text.substring(0, log_text.length()-3);
				log_screen_label.setText(log_text);
			}
		}
	}

	private void getOperation(String btn_text){
		String screen_text = main_screen.getText();
		String log_text = log_screen_label.getText();
		Double screen_value = Double.parseDouble(screen_text);

		main_screen.setText(""); // Clear main screen
		x = BigDecimal.valueOf(screen_value);
		if(!btn_text.equals("=")) {
			last_operation = btn_text.charAt(0);
			result = x;
		}

		switch(btn_text){
			case "sqrt":
				// If x is a negative value
				if(x.compareTo(BigDecimal.ZERO) <= 0){
					log_screen_label.setText("Cannot calculate negative square roots!");
					main_screen.setText("0.0 ");
					last_operation = '=';
				} else{
					result = BigDecimal.valueOf(Math.sqrt(screen_value));
					printResult(result, log_text, true);
				}
				break;
			case "=":
				result = getResult();
				if(error){
					log_screen_label.setText("Cannot divide by zero!");
					main_screen.setText("0.0 ");
					error = false;
					last_operation = '=';
				} else{
					printResult(result, log_text, false);
				}
				break;
			default:
				System.out.println("Other Operation!");
				break;
		}
	}

	private BigDecimal getResult(){
		// Main calculations
		switch(last_operation){
			case '+':
				result = result.add(x);
				break;
			case '-':
				result = result.subtract(x);
				break;
			case 'x':
				result = result.multiply(x);
				break;
			case '/':
				// Cannot divide by zero
				if(x.compareTo(BigDecimal.ZERO) == 0){
					error = true;
				} else{
					result = result.divide(x, 2, BigDecimal.ROUND_HALF_EVEN);
				}
				break;
			case '^':
				result = BigDecimal.valueOf(Math.pow(result.doubleValue(), x.doubleValue()));
				break;
			default:
				result = null;
				break;
		}
		return result;
	}

	private void printResult(BigDecimal result, String log_text, boolean show_equals){
		String strResult = result.toString();
		System.out.println("Final Result = " + strResult);
		if(show_equals){
			log_screen_label.setText(log_text + "= " + strResult);
		} else{
			log_screen_label.setText(log_text + strResult);
		}
		// Update Variables
		last_operation = '=';
		clear = false;
		count_operations = 0;
		result = new BigDecimal("0");
		main_screen.setText(strResult);
	}
}