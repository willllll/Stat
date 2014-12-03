package dataReader;

import java.io.File;
import java.util.HashMap;

public class DataReader {
	
	public static HashMap<String,Integer> getAllUsers(String BasePath){
		String regex = "[0-9]+_[0-9]+";
		HashMap<String,Integer> userMap = new HashMap<String,Integer>();
		File dir = new File(BasePath);
		File listDir[] = dir.listFiles();
		for (int i = 0; i < listDir.length; i++) {
			String dirName = listDir[i].getName();
			if(dirName.matches(regex)){
				String[] split = dirName.split("_");
				String id = split[0];
				int time = Integer.parseInt(split[1]);
				
				File subDir = new File(BasePath + dirName + "/");
				File listFile[] = subDir.listFiles();
				for (int j = 0; j < listFile.length; j++) {
					if (listFile[j].getName().equals("complete.txt")) {
						if(userMap.containsKey(id)){
							if(userMap.get(id) != null && userMap.get(id)<time){
								userMap.put(id, time);
							}
						}else{
							userMap.put(id, time);
						}
					}
				}
			}
		}
		return userMap;
	}
	
	public static String findFolder(String name, String path){
		int time=0;
		File dir = new File(path);
		File listDir[] = dir.listFiles();
		for (int i = 0; i < listDir.length; i++) {
			String dirName = listDir[i].getName();
			if (dirName.startsWith(name)) {
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
		return path+name+"_"+time;
	}
}
