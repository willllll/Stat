//package ui;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.io.FileNotFoundException;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//import main.User;
//import edu.uci.ics.jung.graph.Graph;
//import edu.uci.ics.jung.graph.ObservableGraph;
//import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
//import bus.uigen.ObjectEditor;
//import bus.uigen.jung.AJungGraphManager;
//import bus.uigen.jung.JungGraphManager;
//import bus.uigen.jung.LayoutType;
//import bus.uigen.shapes.AStringModel;
//import bus.uigen.shapes.OEShapeModel;
//import bus.uigen.test.SquaringCounterWithButtons;
//
//public class UITest {
//	public static void main (String[] args) throws FileNotFoundException, ParseException {
//		User user = new User("100008343420563");
//		ArrayList<User> UList = new ArrayList<User>();
//		user.loadRecommendedLists();
//		UList.add(user);
//		User user2 = new User("100004642241103");
//		UList.add(user2);
//		user2.loadRecommendedLists();
//		UserBrowser counter = new UserBrowser(UList);
//		//ListBrowser browser = new ListBrowser(user);
//		JPanel counterPanel = new JPanel();
//		JPanel statPanel = new JPanel();
//		//JPanel browserPanel = new JPanel();
//		JPanel jungPanel = new JPanel();
//		JFrame frame=  new JFrame();
//		frame.setLayout(new BorderLayout());
////		JTextField textField = new JTextField("foo");
////		JPanel textPanel = new JPanel();
//		frame.add(counterPanel, BorderLayout.NORTH);
//		frame.add(jungPanel, BorderLayout.CENTER);
//		//frame.add(statPanel, BorderLayout.SOUTH);
//		
//		Graph<Object, Integer> aGraph = user.getSocialGraph();
////		Graph<Object, Integer> aGraph = new UndirectedSparseMultigraph<>();
////		Double aNode = 5.44;
////		aGraph.addEdge(1, aNode, aNode );		
//		ObservableGraph anObservableGraph = new ObservableGraph<>(aGraph);
//		JungGraphManager jungGraphManager = new AJungGraphManager<>(aGraph,jungPanel);
//	
////		Transformer<String, Shape> vertexTransformer = jungGraphManager.getVertexShapeTransformer();
////		Transformer<Context<Graph<String, String>, String>, Shape> edgeTransformer = jungGraphManager.getEdgeShapeTransformer();
//		OEShapeModel oeShapeModel = new AStringModel("Rings Graph Demo", 20, 20);
//		
////		oeShapeModel.setBounds(new Rectangle(0, 0, 100, 100));
//		System.out.println("Adding text to jung graph");
//		jungGraphManager.getJungShapeModelDisplayer().getShapes().add(oeShapeModel);
//	
////		shapes.add(oeShapeModel);
//		jungGraphManager.setLayoutType(LayoutType.Spring);
//		ObjectEditor.editInMainContainer(counter, counterPanel);
//		//ObjectEditor.editInMainContainer(user.getStat(), statPanel);
//		//ObjectEditor.editInMainContainer(counter.getListBrowser(), browserPanel);
//		frame.setSize(600, 800);
//		frame.setVisible(true);
//
//		ObjectEditor.edit(jungGraphManager);
//	}
//
//}
