package main;

import java.text.ParseException;

public class Parser {
	private Statistics stat;
	
	public Parser(Statistics stat){
		this.stat=stat;
	}
	
	public void parse(String in) throws ParseException{
		String[] split = in.split(" ");
		if(split[2].equals("Login")){
			stat.login(split[1]);
		}else if(split[2].equals("Leave")){
			stat.leave(split[1]);
		}else if(split[2].equals("Create")){
			stat.create();
		}else if(split[2].equals("Discard")){
			stat.discard();
		}else if(split[2].equals("Add")){
			int count=countMember(split);
			stat.add(count, split[2]);
		}else if(split[2].equals("Delete")){
			stat.delete(countMember(split),split[2]);
		}else if(split[2].equals("Click")){
			stat.click(Integer.parseInt(split[3]));
		}else if(split[2].equals("rename")){
			
		}
	}
	
	private int countMember(String[] in){
		int count=0;
		for(int i=in.length-1;i>3;i--){
			if(in[i].matches("^[0-9]{1,40}[,]$")){
				count++;
			}else if(in[i].matches("^[0-9]{1,40}$")){
				count++;
			}
		}
		//System.out.println(count);
		return count;
	}
}
