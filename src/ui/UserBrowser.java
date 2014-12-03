package ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JRadioButton;

import main.User;
import util.annotations.Position;
import util.annotations.PreferredWidgetClass;
import util.models.ADynamicEnum;
import util.models.DynamicEnum;

public class UserBrowser implements PropertyChangeListener{
	DynamicEnum<String> users;
	Map<String, User> userTable;
	ListBrowser browser;
	ArrayList<User> list;
	public UserBrowser(ArrayList<User> list){	
		this.list = list;
		users = new ADynamicEnum<String>();
		userTable = new HashMap<String, User>();
		for(User u : list){
			users.addChoice(u.getId());
			//for multiple user selection
			//userTable.put(u.getId(), list.get(count++));
		}
		users.setValue(users.getChoices().get(0));
		users.setChoices(users.getChoices());
		users.addPropertyChangeListener(this);
		browser = new ListBrowser(list.get(0));
	}
	//@PreferredWidgetClass(JRadioButton.class)
	@Position(0)
	public DynamicEnum<String> getUser() {
		return users;
	}
	
	public void setUser(DynamicEnum<String> newUser){
		users = newUser;
	}
	
	public void setListBrowser(User user){
		browser = new ListBrowser(user);
	}
	@Position(1)
	public ListBrowser getListBrowser(){
		return browser;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == users) {
			String newValue = (String) evt.getNewValue();
			int index = users.getChoices().indexOf(newValue);
			setListBrowser(list.get(index));
		}
	}

}
