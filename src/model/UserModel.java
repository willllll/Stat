package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import dataReader.DataReader;

public class UserModel {
	private final String BASE_PATH="data/";
	private FriendListModel allFriends;
	private HashMap<String,FriendListModel> step1, step2, recommendation, step3;
	private String name,folderPath;
	private HashMap<String, ArrayList<String>> relationship;
	private UserStatisticsModel step1Stat,step2Stat,step3Stat;
	
	public UserModel(String name) throws FileNotFoundException, ParseException{
		this.name = name;
		allFriends = new FriendListModel("All Friends");
		folderPath = DataReader.findFolder(name, BASE_PATH);
		relationship = new HashMap<String, ArrayList<String>>();
		recommendation = new HashMap<String,FriendListModel>();
		//comment out for gathering json 3/26
//		step1Stat = new UserStatisticsModel(name);
//		step2Stat = new UserStatisticsModel(name);
//		step3Stat = new UserStatisticsModel(name);
		
		loadAllData();
//		
//		for(String flm: recommendation.keySet()){
//			FriendListModel tmp =recommendation.get(flm);
//			System.out.println(tmp.getListName());
//			for(String s:tmp.getMemberList()){
//				System.out.println(s);
//			}
//			System.out.println("");
//		}
//		
//		for(String flm: step2Stat.getLists().keySet()){
//			FriendListModel tmp =step2Stat.getLists().get(flm);
//			System.out.println(tmp.getListName());
//			for(String s:tmp.getMemberList()){
//				System.out.println(s);
//			}
//			System.out.println("");
//		}
//		
//		for(String flm: step2Stat.getDeletion().keySet()){
//			ArrayList<String> tmp =step2Stat.getDeletion().get(flm);
//			System.out.println(flm);
//			for(String s:tmp){
//				System.out.println(s);
//			}
//			System.out.println("");
//		}
	}
	
	public HashMap<String,FriendListModel> getRecommendation(){
		return recommendation;
	}
	
	public String getId(){
		return name;
	}
	
	public HashMap<String, ArrayList<String>> getRelationship(){
		return relationship;
	}
	
	public HashMap<String, ListStatisticsModel> getListStatistics(int step){
		if(step==1){
			return step1Stat.getListStatistics();
		}else if(step==2){
			return step2Stat.getListStatistics();
		}else if(step==3){
			return step3Stat.getListStatistics();
		}else{
			return null;
		}
	}
	public String getStatistics(int step){
		if(step==1){
			return step1Stat.getStatistics();
		}else if(step==2){
			return step2Stat.getStatistics();
		}else if(step==3){
			return step3Stat.getStatistics();
		}else{
			return "";
		}
	}
	
	public UserStatisticsModel getStatisticsModel(int step){
		if(step==1){
			return step1Stat;
		}else if(step==2){
			return step2Stat;
		}else if(step==3){
			return step3Stat;
		}else{
			return null;
		}
	}
	
	public FriendListModel getAllFriends(){
		return allFriends;
	}
	
	public HashMap<String,FriendListModel> getLists(int step){
		if(step==1){
			return step1;
		}else if(step==2){
			return step2;
		}else if(step==3){
			return step3;
		}else{
			return null;
		}
	}
	
	private void loadAllData() throws FileNotFoundException, ParseException{
		loadRelationship();
		loadRecommendationResult();
		//comment out for gathering json 3/26
//		step2Stat.preLoad(recommendation);
//		loadLogFile(BASE_PATH+"appdata/step1/"+name+"_creator.txt",step1Stat);
//		loadLogFile(BASE_PATH+"appdata/step2/"+name+"_editor.txt",step2Stat);
//		loadLogFile(BASE_PATH+"appdata/step3/"+name+"_creator2.txt",step3Stat);	
//		step1 =step1Stat.getLists();
//		step2 =step2Stat.getLists();
//		step3 =step3Stat.getLists();
		loadAllFriends();
		
	}
	
	private void loadAllFriends() throws FileNotFoundException{
		String path = folderPath+"/friendinfo.txt";
		Scanner scan =new Scanner(new File(path));
		allFriends.addMember(scan.nextLine());
		while(scan.hasNextLine()){
			String line = scan.nextLine();
			if(line.isEmpty()){
				if(!scan.hasNextLine()){
					break;
				}
				allFriends.addMember(scan.nextLine());
			}
		}
		scan.close();
	}
	
	private void loadLogFile(String path, UserStatisticsModel Stat) throws FileNotFoundException, ParseException{
		Scanner scan =new Scanner(new File(path));
		while(scan.hasNextLine()){
			Stat.parse(scan.nextLine());
		}
		scan.close();
	}
	
	private void loadRelationship() throws FileNotFoundException{
		String path =folderPath+"/relationship.txt";
		Scanner scan =new Scanner(new File(path));
		String line;
		StringTokenizer st;
		while(scan.hasNextLine()){
			line = scan.nextLine();
			st = new StringTokenizer(line);
			String t1 = st.nextToken();
			String t2 = st.nextToken();
			if(relationship.containsKey(t1)){
				if(!relationship.containsKey(t2)){
					if(!relationship.get(t1).contains(t2)){
						relationship.get(t1).add(t2);
					}
				}else{
					if(!relationship.get(t2).contains(t1)){
						if(!relationship.get(t1).contains(t2)){
							relationship.get(t1).add(t2);
						}
					}
				}
			}
			else{
				relationship.put(t1,new ArrayList<String>());
				if(!relationship.containsKey(t2)){
					if(!relationship.get(t1).contains(t2)){
						relationship.get(t1).add(t2);
					}
				}else{
					if(!relationship.get(t2).contains(t1)){
						if(!relationship.get(t1).contains(t2)){
							relationship.get(t1).add(t2);
						}
					}
				}
			}
		}
		scan.close();
	}
	
	private void loadRecommendationResult() throws FileNotFoundException{
		String recommendationPath = folderPath+"/output.txt";
		Scanner scan =new Scanner(new File(recommendationPath));
		String line;
		while(scan.hasNextLine()){
			line = scan.nextLine();
			if(line.equals("")){
				continue;
			}else if(line.charAt(0)=='C'||line.charAt(0)=='G'){
				FriendListModel list =new FriendListModel(line);
				String name = line;
				line = scan.nextLine();
				int n=Integer.parseInt(line.split(" ")[2]);
				for(int i=1;i<=n;i++){
					list.addMember(scan.nextLine());
				}
				recommendation.put(name,list);
			}	
		}
		scan.close();
	}
}
