package ui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabPanel extends JPanel {
	
	JPanel panel0,panel1,panel2,panel3;
	
	public TabPanel() {
		 super(new GridLayout(1, 1));
		 
		 JTabbedPane tabbedPane = new JTabbedPane();
		 
		 panel0=new JPanel();
		 tabbedPane.addTab("Undirected Manual Creation", panel0);
		 
		 panel1=new JPanel();
		 tabbedPane.addTab("Recommendation", panel1);
		 panel2=new JPanel();
		 tabbedPane.addTab("Directed Manual Creation", panel2);
//		 panel3=new JPanel();
//		 tabbedPane.addTab("Stats-Step3", panel3);
		 
		 add(tabbedPane);
	}
	
	public JPanel getPanel0(){
		return panel0;
	}
	
	public JPanel getPanel1(){
		return panel1;
	}
	public JPanel getPanel2(){
		return panel2;
	}
	public JPanel getPanel3(){
		return panel3;
	}
}
