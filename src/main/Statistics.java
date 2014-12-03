package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Statistics {
	
	private int numofList;
	private int totalPeople;
	private long timeStep;
	private int add;
	private int delete;
	private int click;
	private HashMap<String, ListStat> table; 
	private Parser parse;
	
	public Statistics(){
		click=0;
		parse = new Parser(this);
		numofList=0;
		totalPeople=0;
		timeStep=0;
		add=0;
		delete=0;
		table = new HashMap<String, ListStat>();
	}
	
	public double getAverageClick(){
		return click/numofList;
	}
	
	public long getTotalTime(){
		return timeStep/1000;
	}
	
	public void setTotalNumOfListsCreated(int num){
		numofList+=num;
	}
	
	public int getTotalNumOfListsCreated(){
		return numofList;
	}
	
	public int getNumOfAdd(){
		return add;
	}
	
	public int getNumOfDel(){
		return delete;
	}
	
	public String getStat(){
		String s = "The total time spent on this step is "+ timeStep+".\n"+
		"The total number of lists created is "+ numofList+".\n"+
		"The total number of adding is "+ add+".\n"+
		"The total number of deleting is "+ delete+".\n";
		if(numofList!=0){
			s=s+"The average time spent on a list is "+ timeStep/numofList+".\n"+
			"The average number of people added to a list "+ (double)totalPeople/numofList+".\n";	
		}
		return s;
	}
	
	public void parse(String in) throws ParseException{
		parse.parse(in);
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
	
	public void create() {
		numofList++;
	}
	
	public void discard() {
		numofList--;
	}
	
	public void add(int count, String name) {
		totalPeople=totalPeople+count;
		add+=count;
		if(table.containsKey(name)){
			table.get(name).add(count);
		}else{
			table.put(name, new ListStat(name).add(count));
		}
	}
	
	public void delete(int count,String name) {
		totalPeople=totalPeople-count;
		delete+=count;
		if(table.containsKey(name)){
			table.get(name).delete(count);
		}else{
			table.put(name, new ListStat(name).delete(count));
		}
	}
	
	public void addAList(String name){
		table.put(name,new ListStat(name));
	}

	public ListStat getListStat(String name){
		return table.get(name);
	}
	
	public void click(int click){
		this.click=click;
	}
	
	public void rename(String old, String ne){
		table.put(ne, table.get(old));
		table.remove(old);
	}
	
	public HashMap<String, ListStat> getTable(){
		return table;
	}
}