	 package Testpack;
	import java.beans.EventHandler;
import java.sql.Connection;
	import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.ArrayList;
	import java.util.Collection;
import java.util.Scanner;

import javafx.beans.binding.StringExpression;
	import javafx.beans.property.SimpleStringProperty;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
	import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
	import javafx.scene.layout.BorderPane;
	
	public class Assignment1Controller implements Assignment1_Vars {
	
		@FXML private Button btnView, btnOk;
		@FXML private RadioButton rbAdd, rbSearch, rbModify, rbDelete;
		@FXML private ToggleGroup tg;
		@FXML private TableView tblContacts;
		@FXML private TableColumn colId, colFirstName, colLastName, colPhoneNumber;
		@FXML private TextField txtFName, txtLName, txtNum, txtId;
		@FXML Alert a = new Alert(AlertType.ERROR);
		ArrayList<Contact> all;
		private Connection con;
		private Statement st;
		private String sql = "SELECT * FROM assignment1";
		private String first_name = "";
		private String last_name = "";
		private String num = "";
		int i = 0;
		int id = 0;
		int check = 0;
		int pass;
		ResultSet rs;
		PreparedStatement pst;
		
		@FXML public void initialize() {
			txtId.setVisible(false);
			doView();
		}
		
		public Assignment1Controller() {
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, uName, uPass);
				st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				//getAllData();
			}
			 catch (Exception e) {
				 a.setContentText("Error Connecting to Database");
				 a.showAndWait();
				e.printStackTrace();
			}
		}
		
		public ArrayList<Contact> getAllData() {
		all = new ArrayList<>();
				//String str = "SELECT * FROM assignment1";
		
				try {
					rs = st.executeQuery(sql);
					while (rs.next()) {
						Contact c = new Contact(rs.getInt(1)+"",
												rs.getString(2),
												rs.getString(3),
												rs.getString(4));
						all.add(c);
						}
				
				} catch (SQLException e) {
					a.setContentText("Failed Gathering Data From Database");
					 a.showAndWait();
					e.printStackTrace();
				}
				return all;
		}
		
		@FXML public void doAdd() {
	
//			final ObservableList<Contact> data = FXCollections.observableArrayList(
//					new Contact("1", "2", "3", "4"),
//					new Contact("555", "9", "7", "8"));
			String first_name = txtFName.getText();
			System.out.println("first name " + first_name);
			
			String last_name = txtLName.getText();
			System.out.println("last name " + last_name);
			
			String num = txtNum.getText();
//			if (Integer.parseInt(num) >= 2147483647) {
//				a.setContentText("Phone # Too Big, must be smaller than 2147483647");
//				a.showAndWait();
//				return;
//			}
			System.out.println("number " + num);
			
			if (first_name.equals("") || last_name.equals("") || num.equals("")) {
				a.setContentText("The Text Field(s) are empty");
				 a.showAndWait();
				 return;
			}
			
			Contact c = new Contact("-1", first_name, last_name, num);
			
			setSql("INSERT INTO assignment1 (first_name, last_name, phone_number)" +
					" VALUES ('"+ c.getFname()+"', '"+c.getLname()+
						"', "+c.getNum()+")");
			doView();
		}
		@FXML public void doSearch() {
			first_name = txtFName.getText();
			last_name = txtLName.getText();
			num = txtNum.getText();
			
			if (!first_name.equals("") && !last_name.equals("") && !num.equals("")) {
			a.setContentText("When Searching, enter only 1 value in only 1 of the Text Fields");
			 a.showAndWait();
			 return;
			}
			
			if (!first_name.equals("")) {
				setSql("SELECT id, first_name, last_name, phone_number "
							+ " FROM assignment1 "
							+ " WHERE first_name LIKE '%"+first_name+"%'");
				doView();
				
			} else if (!last_name.equals("")) {
				setSql("SELECT id, first_name, last_name, phone_number "
						+ " FROM assignment1 "
						+ " WHERE last_name LIKE '%"+last_name+"%'");
				doView();
				
			} else if (!num.equals("")) {
				setSql("SELECT id, first_name, last_name, phone_number "
						+ " FROM assignment1 "
						+ " WHERE phone_number LIKE '%"+num+"%'");
				doView();
			}
		}
		public void setSql(String sql) {
			this.sql = sql;
			i = 1;
		}
		public String getSql() {
			return sql;
		}
		
		@FXML public void doModify() {
			first_name = txtFName.getText();
			last_name = txtLName.getText();
			num = txtNum.getText();
			if (!txtId.getText().equals("")) id = (Integer.parseInt(txtId.getText()));
			
			if (first_name.equals("") || last_name.equals("") || num.equals("") || id == 0) {
				a.setContentText("One of the Text Fields is empty, Please Make sure you type into all text fields");
				 a.showAndWait();
				 return;
			}
			
			setSql("UPDATE assignment1 "
					+ " SET first_name = '"+first_name+"'"
						+ ", last_name = '"+last_name+"'"
						+ ", phone_number = '"+num+"'"
						+ " WHERE id = "+id+"");
			doView();
		}
		@FXML public void doDelete() {
			
//		if (id == 0) {
//			a.setContentText("Text Field is empty, enter a contact ID # to delete");
//			 a.showAndWait();
//			 return;
//		}
			if (!txtLName.getText().equals("")) id = (Integer.parseInt(txtLName.getText()));
			else {
				a.setContentText("Contact ID # Field is empty");
				a.showAndWait();
				return;
			}
//		for (Contact c : all) {
//			if (Integer.toString(id).equals(c.getId())) {
//				check = 1;
//				break;
//			} 
//		}
//		if (check == 0) {
//				a.setContentText("Incorrect ID #");
//				a.showAndWait();
//				return;
//		}
			
		
			
			
			setSql("DELETE FROM assignment1"
					+ " WHERE id = " + id);
			doView();
		}
		@FXML public void doView() {
			
			txtId.setVisible(false);
			txtFName.setVisible(true);
			txtNum.setVisible(true);
			txtFName.setPromptText("First Name");
			txtLName.setPromptText("Last Name");
			txtNum.setPromptText("Phone Number");
			txtFName.setText("");
			txtLName.setText("");
			txtNum.setText("");
			
			ObservableList<Contact> data = FXCollections.observableArrayList();
			
			if (btnView.isFocused()) {
				rbSearch.setSelected(false);
				rbDelete.setSelected(false);
				rbModify.setSelected(false);
				rbAdd.setSelected(false);
			}
			if (i == 0 || (!rbAdd.isSelected() && !rbSearch.isSelected() && !rbModify.isSelected() && !rbDelete.isSelected())) {
			sql = "SELECT * FROM assignment1";
			}
			try {
				System.out.println("i is: " + i);
				System.out.println(sql);
				
				if (getSql().toUpperCase().contains("SELECT")) rs = st.executeQuery(sql);
				else if (getSql().toUpperCase().contains("DELETE")) {
					
					pass = st.executeUpdate(sql);
					if (pass == 0) {
						a.setContentText("Invalid Contact ID #");
						a.showAndWait();
						return;
					}
				}
				else st.executeUpdate(sql);
				
				while (!rs.isClosed() && rs.next()) { // Without isClosed(), i'd get an error, cannot do rs.next() after ResultSet is Closed.
					data = FXCollections.observableArrayList(
							getAllData());
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			colId.setCellValueFactory(new PropertyValueFactory<>("id"));
			colFirstName.setCellValueFactory(new PropertyValueFactory<>("fname"));
			colLastName.setCellValueFactory(new PropertyValueFactory<>("lname"));
			colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("num"));
			tblContacts.setItems(data);
		}
		
		@FXML public void changePromptText(ActionEvent e) {
			
			if (rbAdd.isFocused()) {
				txtFName.setPromptText("First Name");
				txtLName.setPromptText("Last Name");
				txtNum.setPromptText("Phone Number");
				txtId.setVisible(false);
				txtFName.setVisible(true);
				txtNum.setVisible(true);
				
			} else if (rbSearch.isFocused()) {
				txtFName.setPromptText("First Name");
				txtLName.setPromptText("Last Name");
				txtNum.setPromptText("Phone Number");
				txtId.setVisible(false);
				txtFName.setVisible(true);
				txtNum.setVisible(true);
				
			} else if (rbModify.isFocused()) {
				txtFName.setPromptText("New First Name");
				txtLName.setPromptText("New Last Name");
				txtNum.setPromptText("New Phone Number");
				txtId.setVisible(true);
				txtFName.setVisible(true);
				txtNum.setVisible(true);
				
			} else if (rbDelete.isFocused()) {
				txtLName.setPromptText("Contact ID#");
				txtId.setVisible(false);
				txtFName.setVisible(false);
				txtNum.setVisible(false);	
			}
		}
		
		@FXML public void doPick(ActionEvent e) {
			
			txtFName.setVisible(true);
			txtNum.setVisible(true);
			txtLName.setPromptText("Last Name");
			
			if (rbAdd.isSelected()) {
				doAdd();
	
			} else if (rbSearch.isSelected()) {
				doSearch();

			} else if (rbModify.isSelected()) {
				doModify();
	
			} else if (rbDelete.isSelected()) {
				
				doDelete();

			}	
		}
		
	}
