package dataReader;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.text.ParseException;

import javax.swing.JFrame;

import bus.uigen.ObjectEditor;
import ui.GraphBrowser;
import ui.ListBrowser;
import ui.StatBrowser;
import ui.TabPanel;
import model.UserModel;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub
		String id="100008343420563";
		UserModel um = new UserModel(id);
		
		ListBrowser lb1 = new ListBrowser(um,1);
		ListBrowser lb2 = new ListBrowser(um,2);
		ListBrowser lb3 = new ListBrowser(um,3);
		GraphBrowser gb = new GraphBrowser(um);
		
		lb1.addPropertyChangeListener(gb);
		
		JFrame frame=  new JFrame();
		frame.setLayout(new BorderLayout());
		
		TabPanel tab =  new TabPanel();
		frame.getContentPane().add(tab,BorderLayout.NORTH);
		frame.getContentPane().add(gb,BorderLayout.CENTER);
		
		ObjectEditor.editInMainContainer(lb1, tab.getPanel0());
		ObjectEditor.editInMainContainer(lb2, tab.getPanel1());
		ObjectEditor.editInMainContainer(lb3, tab.getPanel2());
		
		frame.setSize(1000, 800);
		frame.setVisible(true);
	}

}
