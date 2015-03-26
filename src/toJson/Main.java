package toJson;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import model.FriendListModel;
import model.UserModel;

public class Main {

	public static void main(String[] args) throws ParseException, IOException {
		// TODO Auto-generated method stub
		String id="100002437465375";
		UserModel um = new UserModel(id);
		FriendListModel allFriend = um.getAllFriends();
		HashMap<String, ArrayList<String>>relationship = um.getRelationship();
		System.out.println(allFriend.size());
		allFriendsToJson(id,allFriend,relationship, "/Users/ziyouw/Desktop/Visualization/"+id+"_Json.json");
	}
	
	private static void allFriendsToJson(String id,FriendListModel allFriend,HashMap<String, ArrayList<String>>relationship, String path) throws IOException{
		Hashtable<String, Integer> IdMap = new Hashtable<String, Integer>();
		PrintWriter prin = new PrintWriter(new File(path));
		prin.println("{");
		prin.println("\"nodes\":[");
		int count = 0;
		for(String member:allFriend.getMemberList()){
			IdMap.put(member,count++);
			prin.println("{\"name\":"+"\""+member+"\",\"group\":0},");
		}
		IdMap.put(id,count++);
		prin.println("{\"name\":"+"\""+id+"\",\"group\":10}");
		prin.println("],");
		prin.println("\"links\":[");
		for(String source: relationship.keySet() ){
			System.out.println("source: "+source);
			for(String target : relationship.get(source)){
				System.out.println(target);
				prin.flush();
				prin.println("{\"source\":"+IdMap.get(source).toString()+",\"target\":"+IdMap.get(target).toString()+",\"value\":0},");
			}
		}
		prin.println("]");
		prin.println("}");
		prin.close();
	}

}
