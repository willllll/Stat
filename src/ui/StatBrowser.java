package ui;

import javax.swing.JTextArea;

import util.annotations.ComponentHeight;
import util.annotations.ComponentWidth;
import util.annotations.PreferredWidgetClass;

public class StatBrowser {
	String content;
	
	public StatBrowser(String content){
		this.content = content;
	}
	@PreferredWidgetClass(JTextArea.class)
	@ComponentHeight(100)
	@ComponentWidth(400)
	public String getStat(){
		return content;
	}
	
	public void setStat(String content){
		this.content = content;
	}
}
