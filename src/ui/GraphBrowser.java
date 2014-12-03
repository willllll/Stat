package ui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import bus.uigen.ObjectEditor;
import bus.uigen.jung.AJungGraphManager;
import bus.uigen.jung.JungGraphManager;
import bus.uigen.jung.LayoutType;
import bus.uigen.shapes.AStringModel;
import bus.uigen.shapes.OEShapeModel;
import edu.uci.ics.jung.graph.Graph;
import main.User;

public class GraphBrowser extends JPanel implements PropertyChangeListener {
	User user;
	Graph<Object, Integer> aGraph;
	JungGraphManager<Object, Integer> jungGraphManager;
	public GraphBrowser(User user){
		super(new GridLayout(1, 1));
		this.user = user;
		aGraph = user.getListGraph(0);
		jungGraphManager = new AJungGraphManager<Object, Integer>(aGraph,this);
		//OEShapeModel oeShapeModel = new AStringModel("List Graph Browser", 20, 20);
		//jungGraphManager.getJungShapeModelDisplayer().getShapes().add(oeShapeModel);
		jungGraphManager.setLayoutType(LayoutType.Spring);
		ObjectEditor.edit(jungGraphManager);
	}
	
	public void paint(Graphics G){
		super.paint(G);
		G.drawLine(0,0,400,400);
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		String newValue = (String) evt.getNewValue();
		int index = user.getListName().indexOf(newValue);
		//jungGraphManager.setGraph(user.getListGraph(2));
		aGraph.removeVertex("100008343420563");
	}
}
