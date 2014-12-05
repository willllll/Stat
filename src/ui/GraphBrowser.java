package ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import bus.uigen.ObjectEditor;
import bus.uigen.jung.AJungGraphManager;
import bus.uigen.jung.JungGraphManager;
import bus.uigen.jung.LayoutType;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import model.FriendListModel;
import model.UserModel;

public class GraphBrowser extends JPanel implements PropertyChangeListener, ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserModel user;
	Graph<Object, Integer> aGraph;
	JungGraphManager<Object, Integer> jungGraphManager;
	int currentStep;
	String[] currentSelected = new String[3];
	public GraphBrowser(UserModel user){
		super(new GridLayout(1, 1));
		for(int i=0;i<3;i++){
			currentSelected[i] = "All Friends";
		}
		currentStep=0;
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
				aGraph.addEdge(id, key, value);
				id++;
			}
		}
		return aGraph;
	}
	
	private void updateGraph(FriendListModel flm,FriendListModel creation, ArrayList<String> deletion){
		updateGraph(flm);
		//try{
		
		if(creation!=null){
			for(String s: creation.getMemberList()){
				jungGraphManager.setVertexFillColor(s,Color.YELLOW);
				for(String b:flm.getMemberList()){
					if(!deletion.contains(b)){
						if(user.getRelationship().get(s)!=null&&user.getRelationship().get(s).contains(b)){
							Integer edge =jungGraphManager.getGraph().findEdge(s, b);
							jungGraphManager.setEdgeDrawColor(edge, Color.YELLOW);
							//System.out.println("here");
						}
						else if(user.getRelationship().get(b)!=null&&user.getRelationship().get(b).contains(s)){
							Integer edge =jungGraphManager.getGraph().findEdge(s, b);
							jungGraphManager.setEdgeDrawColor(edge, Color.YELLOW);
						}
					}
				}
			}
			for(String s: deletion){
				jungGraphManager.setVertexFillColor(s,Color.BLACK);
				for(String b: flm.getMemberList()){
					if(user.getRelationship().get(s)!=null&&user.getRelationship().get(s).contains(b)){
						Integer edge =jungGraphManager.getGraph().findEdge(s, b);
						jungGraphManager.setEdgeDrawColor(edge, Color.BLACK);
					}
					else if(user.getRelationship().get(b)!=null&&user.getRelationship().get(b).contains(s)){
						Integer edge =jungGraphManager.getGraph().findEdge(s, b);
						jungGraphManager.setEdgeDrawColor(edge, Color.BLACK);
					}
				}
			}
		}
		//}catch(NullPointerException e){
			
		//}
	}
	
	private void updateGraph(FriendListModel flm){
		resetGraph();
		setEdge();
		jungGraphManager.setVertexFillColor(user.getId(),Color.BLUE);
		//jungGraphManager.setIntegerTransformer(arg0);
		HashMap<String,ArrayList<String>> relationship = user.getRelationship();
		for(String s: flm.getMemberList()){
			
			jungGraphManager.setVertexFillColor(s,Color.GREEN);
			jungGraphManager.setVertexFillColor(user.getId(),Color.BLUE);
			try{
				for(String b: relationship.get(s)){
					if(flm.getMemberList().contains(b)){
						Integer edge = jungGraphManager.getGraph().findEdge(s, b);
						jungGraphManager.setEdgeDrawColor(edge, Color.GREEN);
					}
				}
			}catch(NullPointerException e){
				
			}
			
			if(relationship.get(user.getId()) != null&&relationship.get(user.getId()).contains(s)){
				if(flm.getMemberList().contains(s)){
					Integer edge = jungGraphManager.getGraph().findEdge(s, user.getId());
					jungGraphManager.setEdgeDrawColor(edge, Color.GREEN);
				}
			}
		}
	}
	
	private void setEdge(){
		for(Integer s:jungGraphManager.getGraph().getEdges()){
			jungGraphManager.setEdgeDrawColor(s, Color.LIGHT_GRAY);
		}
		for(Object s:jungGraphManager.getGraph().getVertices()){
			jungGraphManager.setVertexFillColor((String) s,Color.LIGHT_GRAY);
		}
	}
	
	private void resetGraph(){
		for(Object s:jungGraphManager.getGraph().getVertices()){
			jungGraphManager.setVertexFillColor((String) s,Color.RED);
			jungGraphManager.setVertexVisibile((String) s, true);
		}
		jungGraphManager.setVertexFillColor(user.getId(),Color.BLUE);
		for(Integer s:jungGraphManager.getGraph().getEdges()){
			jungGraphManager.setEdgeDrawColor(s, Color.BLACK);
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getPropertyName()=="topLevel"){
			String newValue = (String) evt.getNewValue();
			System.out.println(newValue);
			currentSelected[currentStep]=newValue;
			if(currentStep==1){
				if(newValue.equals("All Friends")){
					resetGraph();
				}else{
					updateGraph(user.getRecommendation().get(newValue),user.getLists(currentStep+1).get(newValue), user.getStatisticsModel(currentStep+1).getDeletion().get(newValue));
				}
			}else{
				if(newValue.equals("All Friends")){
					resetGraph();
				}else{
					updateGraph(user.getLists(currentStep+1).get(newValue));
				}
			}
		}
		//int index = user.getListName().indexOf(newValue);
		//jungGraphManager.setGraph(user.getListGraph(2));
		//aGraph.removeVertex("100008343420563");
		//System.out.println(newValue);
		//updateGraph(user.getLists(1).get(newValue));
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		currentStep= ((JTabbedPane) e.getSource()).getSelectedIndex();
		if(currentSelected[currentStep].equals("All Friends")){
			resetGraph();
		}else{
			updateGraph(user.getLists(currentStep+1).get(currentSelected[currentStep]));
		}
	}
	
	
}
