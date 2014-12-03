package ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import main.FriendList;
import main.ListStat;
import main.User;
import model.FriendListModel;
import model.UserModel;
import util.annotations.Column;
import util.annotations.ComponentHeight;
import util.annotations.ComponentWidth;
import util.annotations.PreferredWidgetClass;
import util.annotations.Row;
import util.models.ADynamicEnum;
import util.models.DynamicEnum;
import util.models.PropertyListenerRegistrar;

public class ListBrowser implements PropertyChangeListener, PropertyListenerRegistrar{
	int type;
	DynamicEnum<String> topLevel = new ADynamicEnum<String>();
	DynamicEnum<String> secondLevel = new ADynamicEnum<String>();
	Map<String, FriendList> groupToMembers = new HashMap<String, FriendList>();
	String listStat;
	HashMap<String, ListStat> table;
	
	PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	public ListBrowser(UserModel user, HashMap<String, ListStat> table, int type){
		this.type = type;
		this.table = table;
		
		HashMap<String,FriendListModel> temp =user.getLists(type);
		
		for(FriendList fl :user.getLists(type)){
			String name = fl.getName();
			groupToMembers.put(name, fl);
			topLevel.addChoice(name);
		}
		topLevel.setValue("Clique: 1");
		secondLevel.setChoices(groupToMembers.get("Clique: 1").getNameList());
		topLevel.addPropertyChangeListener(this);
		listStat = table.get("Clique: 1").getStat();
		
	}
	//@PreferredWidgetClass(JRadioButton.class)
	@Row(0)
	@Column(0)
	public DynamicEnum<String> getLists() {
		return topLevel;
	}

	public void setLists(DynamicEnum<String> topLevel) {
		this.topLevel = topLevel;
	}

	@Row(0)
	@Column(1)
	public DynamicEnum<String> getMembers() {
		return secondLevel;
	}

	public void setMembers(DynamicEnum<String> secondLevel) {
		this.secondLevel = secondLevel;
	}
	@Row(1)
	@PreferredWidgetClass(JTextArea.class)
	@ComponentHeight(100)
	@ComponentWidth(300)
	public String getStat(){
		return listStat;
	}
	
	public void setStat(String newValue){
		 listStat= newValue;
		 propertyChangeSupport.firePropertyChange("Stat", " ", newValue);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == topLevel) {
			String newValue = (String) evt.getNewValue();
			List<String> newChoices = groupToMembers.get(newValue).getNameList();
			secondLevel.setChoices(newChoices);
			propertyChangeSupport.firePropertyChange("topLevel", " ", newValue);
			
			setStat(table.get(newValue).getStat());
		}	
	}
	@Override
	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub
		propertyChangeSupport.addPropertyChangeListener(arg0);
	}
}
