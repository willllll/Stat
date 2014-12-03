package test;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.HashMap;

import javax.swing.JFrame;
import bus.uigen.ObjectEditor;
import main.ListStat;
import main.User;
import ui.GraphBrowser;
import ui.ListBrowser;
import ui.StatBrowser;
import ui.TabPanel;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub
		String user_id="100008343420563";
		User user = new User(user_id);
		user.loadRecommendedLists();
		StatBrowser sb1 = new StatBrowser(user.getStat1().getStat());
		StatBrowser sb2 = new StatBrowser(user.getStat2().getStat());
		StatBrowser sb3 = new StatBrowser(user.getStat3().getStat());
		GraphBrowser gb = new GraphBrowser(user);
		
		HashMap<String, ListStat> table = user.getStat2().getTable();
		
		ListBrowser lb = new ListBrowser(user);
		
		lb.addPropertyChangeListener(gb);
		
		JFrame frame=  new JFrame();
		//frame.setLayout(new BorderLayout());
		frame.getContentPane().setLayout(new BorderLayout());
		TabPanel tab =  new TabPanel();
		//JPanel jungPanel = new JPanel();
		frame.getContentPane().add(tab,BorderLayout.NORTH);
		frame.getContentPane().add(gb,BorderLayout.CENTER);
		
		ObjectEditor.editInMainContainer(lb, tab.getPanel0());
		ObjectEditor.editInMainContainer(sb1, tab.getPanel1());
		ObjectEditor.editInMainContainer(sb2, tab.getPanel2());
		ObjectEditor.editInMainContainer(sb3, tab.getPanel3());

		frame.setSize(1000, 800);
		frame.setVisible(true);
	}

}
