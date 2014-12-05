package ui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import util.models.PropertyListenerRegistrar;

public class TabPanel extends JPanel implements PropertyListenerRegistrar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panel0,panel1,panel2,panel3;
	final JTabbedPane tabbedPane;
	
	public TabPanel() {
		 super(new GridLayout(1, 1));
		 tabbedPane = new JTabbedPane();
		 
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
	
	public void addChangeListener(ChangeListener listen){
		tabbedPane.addChangeListener(listen);
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
	
}
