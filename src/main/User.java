package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;

public class User {
	private final String BASE_PATH="/Users/ziyouw/Desktop/data/";
	//private String name;
	private String id;
	private String folderpath;
	private ArrayList<FriendList> fblist;
	private Statistics stat1,stat2,stat3;
	HashMap<String, ArrayList<String>> relationship;
	
	public User(String id) throws FileNotFoundException{
		this.id=id;
		fblist=new ArrayList<FriendList>();
		folderpath = BASE_PATH+id+"_"+Integer.toString(findFolder(BASE_PATH));
		stat1=new Statistics();
		stat2=new Statistics();
		stat3=new Statistics();
		relationship = new HashMap<String, ArrayList<String>>();
		//loadRecommendedLists();
	}
	
	public Statistics getStat1(){
		return stat1;
	}
	
	public Statistics getStat2(){
		return stat2;
	}
	public Statistics getStat3(){
		return stat3;
	}
	public String getId(){
		return id;
	}
	
	public ArrayList<FriendList> getList(){
		return fblist;
	}
	
	public ArrayList<String> getListName(){
		ArrayList<String> temp = new ArrayList<String>();
		for(FriendList a:fblist){
			temp.add(a.getName());
		}
		return temp;
	}
	
	public Graph<Object, Integer> getListGraph(int index){
		ArrayList<String> names =(ArrayList<String>) fblist.get(index).getNameList();
//		for(String name:names){
//			System.out.println(name);
//		}
		names.add(this.id);
		int id = 0;
		Graph<Object, Integer> aGraph = new UndirectedSparseMultigraph<>();
		for(String s : names){
			aGraph.addVertex(s);
			if(relationship.containsKey(s)){
				for(String value : relationship.get(s)){
					if(names.contains(value)){
						aGraph.addEdge(id++, s, value);
					}	
				}
			}
		}
		return aGraph;
	}
	
	public Graph<Object, Integer> getSocialGraph() throws FileNotFoundException{
		//this.loadSocailGraph();
		int id=0;
		Graph<Object, Integer> aGraph = new UndirectedSparseMultigraph<>();
		for(String key: relationship.keySet()){
			aGraph.addVertex(key);
			for(String value: relationship.get(key)){
				aGraph.addEdge(id++, key, value);
			}
		}
		return aGraph;
	}
	
	private void loadSocailGraph() throws FileNotFoundException{
		String path =folderpath+"/relationship.txt";
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
	
	public void loadRecommendedLists() throws FileNotFoundException, ParseException{
		String logPath1=BASE_PATH+"appdata/step1/"+id+"_creator.txt";
		String logPath2=BASE_PATH+"appdata/step2/"+id+"_editor.txt";
		String logPath3=BASE_PATH+"appdata/step3/"+id+"_creator2.txt";
		String path = folderpath+"/output.txt";
		Scanner scan =new Scanner(new File(path));
		String line;
		while(scan.hasNextLine()){
			line = scan.nextLine();
			if(line.equals("")){
				continue;
			}else if(line.charAt(0)=='C'){
				FriendList list= new FriendList(line);
				String name=line;
				stat2.addAList(line);
				line = scan.nextLine();
				int n=Integer.parseInt(line.split(" ")[2]);
				for(int i=1;i<=n;i++){
					line = scan.nextLine();
					if(!line.equals(id)){
						list.add(new Friend(line));
						stat2.getListStat(name).add(1);
					}
				}
				fblist.add(list);
			}
		}
		stat2.setTotalNumOfListsCreated(fblist.size());
		
		scan = new Scanner(new File(logPath1));
		while(scan.hasNextLine()){
			stat1.parse(scan.nextLine());
		}
		scan.close();
		scan = new Scanner(new File(logPath2));
		while(scan.hasNextLine()){
			stat2.parse(scan.nextLine());
		}
		scan.close();
		scan = new Scanner(new File(logPath3));
		while(scan.hasNextLine()){
			stat3.parse(scan.nextLine());
		}
		scan.close();
		
		this.loadSocailGraph();
	}
	
	private int findFolder(String path){
		int time=0;
		File dir = new File(path);
		File listDir[] = dir.listFiles();
		for (int i = 0; i < listDir.length; i++) {
			String dirName = listDir[i].getName();
			if (dirName.startsWith(id)) {
				File subDir = new File(path + dirName + "/");
				File listFile[] = subDir.listFiles();
				for (int j = 0; j < listFile.length; j++) {
					if (listFile[j].getName().equals("complete.txt")) {
						String[] tmp = dirName.split("_");
						if(Integer.parseInt(tmp[1])>time){
							time=Integer.parseInt(tmp[1]);
							break;
						}
					}
				}
			}
		}
		return time;
	}
}
