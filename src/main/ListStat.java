package main;

public class ListStat {
	
	private int totalPeople;
	private int add;
	private int delete;
	private String name;
	
	public String getStat(){
		String s = "The total number of members is " + totalPeople + ".\n"+
				   "The total number of adding is " + add + ".\n"+
				   "The total number of deleting is " + delete + ".\n";
		return s;
	}
	
	public ListStat(String name){
		this.name=name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	
	public ListStat add(int count) {
		totalPeople=totalPeople+count;
		add+=count;
		return this;
	}
	
	public ListStat delete(int count) {
		totalPeople=totalPeople-count;
		delete+=count;
		return this;
	}

}	
