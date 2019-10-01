package Testpack;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class Contact {

	private SimpleStringProperty id; // CONVERT TO STRINGGG, then after storing, CONVERT TO PROPERTY
	private SimpleStringProperty fname;
	private SimpleStringProperty lname;
	private SimpleStringProperty num;
	

	public Contact(String id, String fname, String lname, String num) {
		this.id = new SimpleStringProperty(id);
		this.fname = new SimpleStringProperty(fname);
		this.lname = new SimpleStringProperty(lname);
		this.num = new SimpleStringProperty(num);
		
	}
	public String getId() {
		return id.get();
	}
	public void setId(String id) {
		this.id.set(id);
	}
	public String getFname() {
		return fname.get();
	}
	public void setFname(String fname) {
		this.fname.set(fname);
	}
	public String getLname() {
		return lname.get();
	}
	public void setLname(String lname) {
		this.lname.set(lname);
	}
	public String getNum() {
		return num.get();
	}
	public void setNum(String num) {
		this.num.set(num);
	}


}
