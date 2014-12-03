package ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import javax.swing.JTextArea;

import model.FriendListModel;
import model.ListStatisticsModel;
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
	UserModel user;
	DynamicEnum<String> topLevel = new ADynamicEnum<String>();
	DynamicEnum<String> secondLevel = new ADynamicEnum<String>();
	HashMap<String,FriendListModel> groupToMembers;
	HashMap<String, ListStatisticsModel> listStatTable;

	String listStat;
	
	PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	public ListBrowser(UserModel user, int type){
		this.user = user;
		this.type = type;
		listStatTable = user.getListStatistics(type);
		groupToMembers =user.getLists(type);
		groupToMembers.put("All Friends",user.getAllFriends());
		
		for(String s :groupToMembers.keySet()){
			topLevel.addChoice(s);
		}
		topLevel.setValue("All Friends");
		secondLevel.setChoices(groupToMembers.get("All Friends").getMemberList());
		topLevel.addPropertyChangeListener(this);
		listStat = user.getStatistics(type);
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
	@ComponentHeight(200)
	@ComponentWidth(400)
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
			List<String> newChoices = groupToMembers.get(newValue).getMemberList();
			secondLevel.setChoices(newChoices);
			propertyChangeSupport.firePropertyChange("topLevel", " ", newValue);
			
			if(newValue.equals("All Friends")){
				setStat(user.getStatistics(type));
			}else{
				setStat(listStatTable.get(newValue).getStatistics());
			}
		}	
	}
	@Override
	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub
		propertyChangeSupport.addPropertyChangeListener(arg0);
	}
}
