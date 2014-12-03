package main;

import java.util.ArrayList;
import java.util.List;

public class FriendList {
	private ArrayList<Friend> list;
	private String name;
	//private 
	
	public FriendList(String name){
		list = new ArrayList<Friend>();
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public List<String> getNameList(){
		List<String> temp = new ArrayList<String>();
		for (Friend f : list){
			temp.add(f.getId());
		}
		return temp;
	}
	
	public void add(Friend friend){
		list.add(friend);
	}
}
