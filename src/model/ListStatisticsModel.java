package model;

public class ListStatisticsModel {
	private int totalPeople;
	private int add;
	private int delete;
	private String name;
	
	public ListStatisticsModel(String name){
		this.name=name;
		totalPeople=0;
		add=0;
		delete=0;
	}
	
	public String getName(){
		return name;
	}
	
	public String getStatistics(){
		String s = "Total number of members: " + totalPeople + ".\n"+
				   "Additions: " + add + ".\n"+
				   "Deletions: " + delete + ".\n";
		return s;
	}
	//need to load the number of members for lists in step 2
	public void setTotalPeople(int num){
		totalPeople+=num;
	}
	
	public ListStatisticsModel add(int count) {
		totalPeople=totalPeople+count;
		add+=count;
		return this;
	}
	
	public ListStatisticsModel delete(int count) {
		totalPeople=totalPeople-count;
		delete+=count;
		return this;
	}

}
