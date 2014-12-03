package ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import bus.uigen.ObjectEditor;
import bus.uigen.jung.AJungGraphManager;
import bus.uigen.jung.JungGraphManager;
import bus.uigen.jung.LayoutType;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import model.FriendListModel;
import model.UserModel;

public class GraphBrowser extends JPanel implements PropertyChangeListener {
	UserModel user;
	Graph<Object, Integer> aGraph;
	JungGraphManager<Object, Integer> jungGraphManager;
	public GraphBrowser(UserModel user){
		super(new GridLayout(1, 1));
		this.user = user;
		aGraph = buildGraph(user.getRelationship());
		jungGraphManager = new AJungGraphManager<Object, Integer>(aGraph,this);
		jungGraphManager.setVertexFillColor(user.getId(),Color.BLUE);
		//OEShapeModel oeShapeModel = new AStringModel("List Graph Browser", 20, 20);
		//jungGraphManager.getJungShapeModelDisplayer().getShapes().add(oeShapeModel);
		jungGraphManager.setLayoutType(LayoutType.Spring);
		ObjectEditor.edit(jungGraphManager);
	}
	
	private Graph<Object, Integer> buildGraph(HashMap<String, ArrayList<String>> relationship){
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
	
	private void updateGraph(FriendListModel flm){
		resetGraph();
		jungGraphManager.setVertexFillColor(user.getId(),Color.BLUE);
		for(String s: flm.getMemberList()){
			jungGraphManager.setVertexFillColor(s,Color.GREEN);
			jungGraphManager.setVertexFillColor(user.getId(),Color.BLUE);
		}
		for(Object s:jungGraphManager.getGraph().getVertices()){
			if(!flm.getMemberList().contains((String) s)){
				jungGraphManager.setVertexVisibile((String) s, false);
			}
		}
		jungGraphManager.setVertexVisibile(user.getId(), true);
	}
	
	private void resetGraph(){
		for(Object s:jungGraphManager.getGraph().getVertices()){
			jungGraphManager.setVertexFillColor((String) s,Color.RED);
			jungGraphManager.setVertexVisibile((String) s, true);
			jungGraphManager.setVertexFillColor(user.getId(),Color.BLUE);
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getPropertyName()=="topLevel"){
			String newValue = (String) evt.getNewValue();
			System.out.println(newValue);
			if(newValue.equals("All Friends")){
				resetGraph();
			}else{
				updateGraph(user.getLists(1).get(newValue));
			}
			
			
		}
		
		//int index = user.getListName().indexOf(newValue);
		//jungGraphManager.setGraph(user.getListGraph(2));
		//aGraph.removeVertex("100008343420563");
		//System.out.println(newValue);
		//updateGraph(user.getLists(1).get(newValue));
	}
}
