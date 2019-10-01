package FinalProject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
/**
 * This Java Class is the Controller class which is responsible
 * for everything that happens inside the window. This is where the
 * FXML objects are defined and modified.
 * 
 * @author Belal Kaoukji
 * 
 *
 */
public class TireManager implements Initializable{

	@FXML BorderPane bp;
	@FXML GridPane gpBottom;
	@FXML TextField txtBrand, txtPrice, txtSize, txtOne, txtTwo, txtThree;
	@FXML TextField txtPDate = new TextField("");
	@FXML TextField txtPPrice = new TextField("");
	@FXML TextField txtPFrom = new TextField("");
	@FXML Button btnAdd, btnRemove, btnSave, btnExit, btnDone, btnDetails, btnOK, btnExtra;
	@FXML RadioButton rbWheels, rbTires;
	@FXML ToggleGroup type;
	@FXML ListView<Item> lv;
	@FXML Alert alertAdd = new Alert(AlertType.ERROR, "Some Text Boxes Are Empty.");
	@FXML Alert alertRem = new Alert(AlertType.ERROR, "Please Select An Item First.");
	@FXML Alert alertNoType = new Alert(AlertType.ERROR, "Please Pick an Item Type.");
	@FXML Alert alertWrongInput = new Alert(AlertType.ERROR, "Price & Size Textboxes can Only Take Positive Numerical Values.");
	@FXML Label lblPDate = new Label("Purchase Date:");
	@FXML Label lblPDateFormat = new Label("(dd/MM/yyyy)");
	@FXML Label lblPPrice;
	@FXML Label lblPFrom = new Label("Purchased From:");
	@FXML Label lblTType, lblTWidth, lblTLeft;
	@FXML ChoiceBox cbOne;
	@FXML ObservableList<Item> data = FXCollections.observableArrayList();
	@FXML ObservableList<String> typeChoice = FXCollections.observableArrayList("All Season", "Summer", "Winter");
	@FXML ObservableList<String> boltChoice = FXCollections.observableArrayList("", "4x100", "4x114.3", "5x120", "5x114.3", "5x100");
	ArrayList<Item> itemList = new ArrayList<Item>();
	String empty = "";
	String itemType = "";
	String address = "C:\\temp\\TireManagerTest.txt";
	Scanner bigArray;
	Item item;
	Tires tire;
	Wheels wheel;
	PrintWriter pw = null;
	FileWriter fw = null;
	BufferedWriter bw = null;
	File file = new File(address);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		readFromFile();
		beforeViewDetails();
	}

	@FXML public String doPick(ActionEvent e) {
		if (e.getSource() == rbWheels) {
			itemType = "Wheels";
		} else {
			itemType = "Tires";
		}
		return itemType;
	}

	@FXML public void doAdd(ActionEvent e) {
		doClear();
		txtBrand.setPromptText("");
		beforeViewDetails();
		if (txtBrand.getText().equals(empty)
		 || txtPrice.getText().equals(empty) 
		 || txtSize.getText().equals(empty)) 
		{
			alertAdd.showAndWait();

		}
		else if (!rbWheels.isSelected() && !rbTires.isSelected()) 
		{
			alertNoType.showAndWait();
		}

		else 
		{
			try {
				if (itemType.equals("Wheels")) {
					item = new Wheels(txtBrand.getText(), 
				   Double.parseDouble(txtPrice.getText()), 
			 (int)(Double.parseDouble(txtSize.getText())),
							          "Wheels");
					 item.setItemType("Wheels");
				}

				else 
				{
					item = new Tires(txtBrand.getText(), 
				  Double.parseDouble(txtPrice.getText()), 
			(int)(Double.parseDouble(txtSize.getText())),
					  				 "Tires");
					item.setItemType("Tires");

				}

				if (item.getSize() <= 0) 
				{ 
					throw new NumberFormatException();
				}
				else 
				{
					itemList.add(item);
					data.add(item);
					lv.setItems(data);
					txtBrand.clear();
					txtPrice.clear();
					txtSize.clear();
					rbWheels.setSelected(false);
					rbTires.setSelected(false);
				}
			}
			catch (NumberFormatException nfe) {
				System.out.println("Price & Size Textboxes can only take Positive Numerical Values.");
				alertWrongInput.showAndWait();
			}
			catch (Exception e1) {
				Alert a = new Alert(AlertType.ERROR, "Something Wrong Happened.");
				a.showAndWait();
				e1.printStackTrace();
			}
		}
	}

	@FXML public void doRemove(ActionEvent e) {
		if (lv.getSelectionModel().isEmpty())
			alertRem.showAndWait();
		else 
		{
			txtBrand.setPromptText("Item Removed!");
			itemList.remove(lv.getSelectionModel().getSelectedItem());
			data.remove(lv.getSelectionModel().getSelectedItem());
		}
	}

	@FXML public void addDetails(ActionEvent e) throws InterruptedException {
		if (lv.getSelectionModel().isEmpty()) {
			alertRem.showAndWait();
		}
		else {
			try {	
				cbOne.valueProperty().set(null);
				gpBottom.setVisible(true);
				cbOne.setMinWidth(73);
				lblTType.setText("Tire Type:");//CHANGES LABELS
				lblTWidth.setText("Tire Width:");//		FROM TIRE-SPECIFIC LABELS
				lblTLeft.setText("Tread Left:");//			TO  WHEEL-SPECIFIC LABELS
				txtPDate.setPromptText("dd/MM/yyyy");
				txtTwo.setPromptText("e.g: 225");
				txtThree.setPromptText("e.g: 7/32");
				
				if (lv.getSelectionModel().getSelectedItem().getItemType().equals("Tires")) { // IF PICKED TIRES
					beforeAddingNew();
					btnOK.setVisible(false);
					cbOne.setItems(typeChoice);
					
					if (lv.getSelectionModel().getSelectedItem().getCounter() != 0) {
						beforeViewDetails();
						
						txtPPrice.setText("" + lv.getSelectionModel().getSelectedItem().getpPrice() + "$");// VIEWS
						txtPDate.setText(lv.getSelectionModel().getSelectedItem().getpDate());//	THE DETAILS
						txtPFrom.setText(lv.getSelectionModel().getSelectedItem().getpFrom());//			THAT WERE ALREADY
						txtOne.setText("" + ((Tires) lv.getSelectionModel().getSelectedItem()).getType());// ENTERETED
						txtTwo.setText("" + ((Tires) lv.getSelectionModel().getSelectedItem()).getWidth() + "mm");//			BY THE
						txtThree.setText("" + ((Tires) lv.getSelectionModel().getSelectedItem()).getTreadLife() + "mm");//			USER
					}	
				} else {																	  // IF PICKED WHEELS
					beforeAddingNew();													  // FOR THE FIRST TIME
					btnOK.setVisible(false);
					cbOne.setItems(boltChoice);
					lblTType.setText("Bolt Pattern:");//CHANGES LABELS
					lblTWidth.setText("Wheel Width:");//		FROM TIRE-SPECIFIC LABELS
					txtTwo.setPromptText("e.g: 8.5");
					lblTLeft.setText("Wheel Offset:");//			TO  WHEEL-SPECIFIC LABELS
					txtThree.setPromptText("e.g: -35");
					
					if (lv.getSelectionModel().getSelectedItem().getCounter() != 0) {		 // FOR THE SECOND TIME
						beforeViewDetails();

						txtPPrice.setText("$" + lv.getSelectionModel().getSelectedItem().getpPrice());// VIEWS
						txtPDate.setText(lv.getSelectionModel().getSelectedItem().getpDate());//	THE DETAILS
						txtPFrom.setText(lv.getSelectionModel().getSelectedItem().getpFrom());//			THAT WERE ALREADY
						txtOne.setText("" + ((Wheels) lv.getSelectionModel().getSelectedItem()).getBoltPattern());// ENTERETED
						txtTwo.setText("" + ((Wheels) lv.getSelectionModel().getSelectedItem()).getWidth() + "\"");//			BY THE
						txtThree.setText("" + ((Wheels) lv.getSelectionModel().getSelectedItem()).getOffset() + "mm");//			USER
					}
				}
			} 
			catch (NullPointerException npe) {
				alertRem.showAndWait();
				npe.printStackTrace();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@FXML public void doSave() { // SAVES ALL INFORMATION TO THE ARRAYLIST

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date pDate = sdf.parse(txtPDate.getText());
			String temp[] = txtPDate.getText().split("/");

			for (int i = 0; i < temp.length; i++) {
				if (i != 2) {
					if (temp[i].length() > 2) {
						throw new ParseException(null, 0);
					}
				} else {
					if (temp[i].length() != 4) {
						throw new ParseException(null, 0);
					}
				}	
			}
			
			lv.getSelectionModel().getSelectedItem().setpDate(txtPDate.getText());
			lv.getSelectionModel().getSelectedItem().setpPrice(Double.parseDouble(txtPPrice.getText()));
			lv.getSelectionModel().getSelectedItem().setpFrom(txtPFrom.getText());
			
			if (cbOne.getSelectionModel().isEmpty() || cbOne.getSelectionModel().getSelectedItem().equals(""))
				throw new EmptyChoiceboxException();
			
			txtPDate.clear();
			txtPPrice.clear();
			txtPFrom.clear();
			
			if (lv.getSelectionModel().getSelectedItem().getItemType().equals("Wheels")) {
				wheel = ((Wheels) lv.getSelectionModel().getSelectedItem());
				wheel.setOffset(Integer.parseInt(txtThree.getText()));
				wheel.setWidth(Double.parseDouble(txtTwo.getText()));
				wheel.setBoltPattern((String)cbOne.getValue());
				wheel.setCounter();
				txtPPrice.clear();
				txtPDate.clear();
				txtPFrom.clear();
				txtOne.clear();
				txtTwo.clear();
				txtThree.clear();

//				for (Item i : itemList) {
//					System.out.println(i);
//					System.out.println(i.getpDate());
//					System.out.println(i.getpFrom()); // FOR TESTING PURPOSES
//					System.out.println(i.getpPrice());
//				}
				
			} else {
				tire = ((Tires) lv.getSelectionModel().getSelectedItem());
				tire.setTreadLife((txtThree.getText()));
				tire.setWidth(Integer.parseInt(txtTwo.getText()));
				tire.setType((String)cbOne.getValue());
				tire.setCounter();
				txtPPrice.clear();
				txtPDate.clear();
				txtPFrom.clear();
				txtOne.clear();
				txtTwo.clear();
				txtThree.clear();

//				for (Item i : itemList) {
//					System.out.println(i);
//					System.out.println(i.getpDate());
//					System.out.println(i.getpFrom()); // FOR TESTING PURPOSES
//					System.out.println(i.getpPrice());
//				}
			}
			gpBottom.setVisible(false);
		}
		catch (ParseException pe) {
			alertWrongInput = new Alert(AlertType.ERROR,"Invalid Date Format. Enter Again With Respect to Required Format.");
			alertWrongInput.showAndWait();
		}
		catch (NumberFormatException nfe) {
			alertAdd.showAndWait();
		}
		catch (EmptyChoiceboxException ece) {
			alertWrongInput = new Alert(AlertType.ERROR, "One or more Choice Boxes are Empty. (Not Selected)");
			alertWrongInput.showAndWait();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class EmptyChoiceboxException extends Exception {

		public EmptyChoiceboxException() {
			super("A ChoiceBox is left empty.");
		}
	}

	@FXML public void doClear() { // WHEN CLICKING ON THE OK BUTTON THIS ACTIVATES AND CLEARS EVERYTHING
		txtPPrice.clear();
		txtPDate.clear();
		txtPFrom.clear();
		txtOne.clear();
		txtTwo.clear();
		txtThree.clear();
		btnOK.setVisible(false);
		gpBottom.setVisible(false);
		cbOne.valueProperty().set(null);
	}
	public void beforeAddingNew() {
		txtPPrice.setDisable(false);//MAKES
		txtPDate.setDisable(false);//	SURE
		txtPFrom.setDisable(false);//		ALL
		txtOne.setVisible(false);//				TEXTFIELDS
		txtTwo.setDisable(false);//					AND
		txtThree.setDisable(false);//					BUTTONS
		btnDone.setVisible(true);//							ARE
		cbOne.setVisible(true);//								ENABLED
	}
	public void beforeViewDetails() {
		btnOK.setVisible(true);// DISABLES
		btnDone.setVisible(false);//	ALL 
		cbOne.setVisible(false);//			TEXTFIELDS
		txtOne.setVisible(true);//				AND
		txtPPrice.setDisable(true);//				CHOICEBOXES
		txtPDate.setDisable(true);//						AND PREPARES
		txtPFrom.setDisable(true);//							TO ADD
		txtOne.setDisable(true);//									DETAILS
		txtTwo.setDisable(true);//										FOR
		txtThree.setDisable(true);//										A NEW
		cbOne.valueProperty().set(null);//										ITEM

	}
	public void saveToFile() {
		Alert alert = new Alert(AlertType.CONFIRMATION, "items will be saved!");
		alert.showAndWait();
		address = "C:\\temp\\TireManagerTest.txt";
		
		try {
			
			if (file.exists()) {
			file.delete();
			}
			file = new File(address);
		
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			
			for (Item i : itemList) {
				i.saveString();
			pw.write(i.saveString());
			pw.println();
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			pw.close();
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void readFromFile() {
		file = new File(address);
		if (!file.exists()) {
			try {
			file.createNewFile();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			bigArray = new Scanner(file);
			//ArrayList<Item> item = new ArrayList<>();
			Item item = null;
			Tires tire;
			Wheels wheel;
			int count = 0;
			while (bigArray.hasNextLine()) {
			String singleItem = bigArray.nextLine();
			String[] singleItemFields = singleItem.split(";");
			if (singleItemFields[4].equals("null")) {
				singleItemFields[4] = "N/A";
				}
			if (singleItemFields[6].equals("null")) {
				singleItemFields[6] = "N/A";
			}
			if (singleItemFields[7].equals("null")) {
				singleItemFields[7] = "N/A";
			}
			if (singleItemFields[9].equals("null")) {
				singleItemFields[9] = "0";
			}
			if (singleItemFields[3].equals("Wheels")) {
				item = new Wheels();
			item.setBrand(singleItemFields[0]);
			item.setPrice(Double.parseDouble(singleItemFields[1]));
			item.setSize(Integer.parseInt(singleItemFields[2]));
			item.setItemType(singleItemFields[3]);
			item.setpDate(singleItemFields[4]);
			item.setpPrice(Double.parseDouble(singleItemFields[5]));
			item.setpFrom(singleItemFields[6]);
			((Wheels) item).setBoltPattern(singleItemFields[7]);
			((Wheels) item).setWidth(Double.parseDouble(singleItemFields[8]));
			((Wheels) item).setOffset(Integer.parseInt(singleItemFields[9]));
			item.setCounter();
			}
			else if (singleItemFields[3].equals("Tires")) {
				item = new Tires();
				item.setBrand(singleItemFields[0]);
				item.setPrice(Double.parseDouble(singleItemFields[1]));
				item.setSize(Integer.parseInt(singleItemFields[2]));
				item.setItemType(singleItemFields[3]);
				item.setpDate(singleItemFields[4]);
				item.setpPrice(Double.parseDouble(singleItemFields[5]));
				item.setpFrom(singleItemFields[6]);
				((Tires) item).setType(singleItemFields[7]);
				((Tires) item).setWidth(Integer.parseInt(singleItemFields[8]));
				((Tires) item).setTreadLife(singleItemFields[9]);
				item.setCounter();
			}
			itemList.add(item);
			data.add(item);
			count++;
			}

			lv.setItems(data);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			bigArray.close();
		}
	}
}
