package model;

import java.util.ArrayList;

public class FriendListModel {
	private ArrayList<String> memberList;
	private String name;
	
	public FriendListModel(String name){
		this.name = name;
		memberList = new ArrayList<String>();
	}
	
	public int size(){
		return memberList.size();
	}
	
	public String getListName(){
		return name;
	}
	
	public void setListName(String name){
		this.name = name;
	}
	
	public void addMember(String member){
		memberList.add(member);
	}
	public void deleteMember(String member){
		memberList.remove(member);
	}
	
	public ArrayList<String> getMemberList(){
		return memberList;
	}
}
