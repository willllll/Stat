package dataReader;

import java.text.ParseException;

import model.UserStatisticsModel;

public class LogParser {
	
	UserStatisticsModel userStat;
	
	public LogParser(UserStatisticsModel userStat){
		this.userStat = userStat;
	}
	
	public void parse(String in) throws ParseException{
		String[] split = in.split(" ");
		if(split[2].equals("Login")){
			userStat.login(split[1]);
		}else if(split[2].equals("Leave")){
			userStat.leave(split[1]);
		}else if(split[2].equals("Create")){
			String name="";
			for(int i=3;i<split.length;i++){
				name+=split[i]+" ";
			}
			userStat.create(name.substring(0,name.length()-1));
		}else if(split[2].equals("Discard")){
			String name="";
			for(int i=3;i<split.length;i++){
				name+=split[i]+" ";
			}
			userStat.discard(name.substring(0,name.length()-1));
		}else if(split[2].equals("Add")){
			String name="";
			for(int i=3;i<split.length;i++){
				if(!matchID(split[i])){
					name+=split[i]+" ";
				}else{
					if(split[i].charAt(split[i].length()-1)==','){
						userStat.add(name, split[i].substring(0,split[i].length()-1));
					}else{
						userStat.add(name, split[i]);
					}
				}		
			}
		}else if(split[2].equals("Delete")){
			String name="";
			for(int i=3;i<split.length;i++){
				if(!matchID(split[i])){
					name+=split[i]+" ";
				}else{
					if(split[i].charAt(split[i].length()-1)==','){
						userStat.delete(name, split[i].substring(0,split[i].length()-1));
					}else{
						userStat.delete(name, split[i]);
					}
				}		
			}
		}else if(split[2].equals("Click")){
			userStat.setClick(Integer.parseInt(split[3]));
		}else if(split[2].equals("Rename")){
			String oldName ="";
			String newName= "";
			int index=0;
			for(int i=3;i<split.length;i++){
				if(split[i].equals("to")){
					index=i;
					break;
				}
				oldName += split[i]+" ";
			}
			oldName = oldName.substring(0,oldName.length()-1);
			for(int i=index+1;i<split.length;i++){
				newName +=split[i]+" ";
			}
			newName = newName.substring(0,newName.length()-1);
			userStat.rename(oldName, newName);
		}
	}
	
	private boolean matchID(String name){
		return name.matches("^[0-9]{4,40}[,]$")||name.matches("^[0-9]{4,40}$");
	}
	
}
