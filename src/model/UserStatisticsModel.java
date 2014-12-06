package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import dataReader.LogParser;

public class UserStatisticsModel {
	private HashMap<String, FriendListModel> creation;
	private HashMap<String, ArrayList<String>> deletion;
	private HashMap<String, ListStatisticsModel> listStats;
	private ArrayList<String> discard;
	private String name;
	private LogParser parser;
	private int click, add,delete, existingList;
	private long timeStep;
	
	public UserStatisticsModel(String name){
		this.name = name;
		creation = new HashMap<String, FriendListModel>();
		listStats = new HashMap<String, ListStatisticsModel>();
		discard = new ArrayList<String>();
		
		parser = new LogParser(this);
		click=0;
		add=0;
		delete=0;
		existingList=0;
	}
	
	public HashMap<String, ArrayList<String>> getDeletion(){
		return deletion;
	}
	public HashMap<String, ListStatisticsModel> getListStatistics(){
		return listStats;
	}
	public HashMap<String, FriendListModel> getLists(){
		HashMap<String, FriendListModel> temp = new HashMap<String, FriendListModel>();
		for(String s:creation.keySet()){
			if(!discard.contains(s)){
				temp.put(s, creation.get(s));
			}
		}
		return temp;
	}
	
	public String getName(){
		return name;
	}
	
	public String getStatistics(){
		int listLeft= creation.keySet().size()+existingList-discard.size();
		String s ="Total time: "+timeStep/1000 +"s;  "+"Total clicks: " + click + ";  " +"Total additions: " + add + ";  Total deletions: " + delete +"\n"+
				  "Total number of lists created: " + (creation.keySet().size()+existingList)+";   "+"Total number of lists discarded: " + discard.size() +"\n"+
				  "Total number of remining lists: " + listLeft;
		if(listLeft!=0){
			s=s+"\n"+"Average editing time per list: "+ (timeStep/1000)/listLeft+"s;  "+"Average number of clciks per list: "+ (double)click/listLeft+".\n"+
			"Average number of additions per list: "+ (double)add/listLeft+";  "+"Average number of deletions per list: " +(double)delete/listLeft;
		}
		return s;
	}
	private void setExistingList(int num){
		existingList = num;
	}
	public void parse(String in) throws ParseException{
		parser.parse(in);
	}
	
	public void create(String name){
		creation.put(name, new FriendListModel(name));
		listStats.put(name, new ListStatisticsModel(name));
	}
	public void discard(String name){
		if(name.matches("Clique:_[0-9]{1,2}")){
			String[] split = name.split("_");
			name=split[0]+" "+split[1];
		}
		discard.add(name);
		listStats.get(name).setDiscard(true);
	}
	
	public void login(String time) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = format.parse(time);
		timeStep = date1.getTime();
	}
	
	public void leave(String time) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = format.parse(time);
		timeStep = date1.getTime() - timeStep;
	}
	
	public void setClick(int click){
		this.click = click;
	}
	
	public void rename(String oldName, String newName){
		creation.put(newName, creation.get(oldName));
		creation.remove(oldName);
		listStats.put(newName, listStats.get(oldName));
		listStats.remove(oldName);
	}
	
	public void add(String name, String member){
		name = name.substring(0,name.length()-1);
		if(name.matches("Clique:_[0-9]{1,2}")){
			String[] split = name.split("_");
			name=split[0]+" "+split[1];
		}
		creation.get(name).addMember(member);
		add+=1;
		listStats.get(name).add(1);
	}
	
	public void delete(String name, String member){
		System.out.println(name);
		name = name.substring(0,name.length()-1);
		if(name.matches("Clique:_[0-9]{1,2}")){
			String[] split = name.split("_");
			name=split[0]+" "+split[1];
		}
		if(deletion!=null){
			deletion.get(name).add(member);
		}
			creation.get(name).deleteMember(member);
			delete+=1;
			listStats.get(name).delete(1);		
	}
	
	public void preLoad(HashMap<String,FriendListModel> recommendation){
		deletion =new HashMap<String, ArrayList<String>>();
		for(String name : recommendation.keySet()){
			creation.put(name, new FriendListModel(name));
			deletion.put(name, new ArrayList<String>());
		}
		this.setExistingList(recommendation.keySet().size());
		for(String s:recommendation.keySet()){
			listStats.put(s, new ListStatisticsModel(s));
			listStats.get(s).setTotalPeople(recommendation.get(s).size());
		}
		
	}
}