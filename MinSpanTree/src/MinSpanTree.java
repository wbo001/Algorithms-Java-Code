import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;


public class MinSpanTree{
	
	private static Hashtable<Integer, Integer> components = null;	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the node to search for. ");
		int nodeInSearchOf = scan.nextInt();
		long startTime = System.currentTimeMillis();
		File file = new File("edges.txt");
		ArrayList<Edge> sortedEdges = convertToGraph(file);
		ArrayList<Edge> MinSpanTree = constructMinSpanTree(sortedEdges);
		Hashtable<Integer, ArrayList<Integer>> ht = convertArrayListToHashtable(MinSpanTree);
		int startingNode = getStartingNode(file);
		Hashtable<Integer, ArrayList<Integer>> BFSTree = constructBFSTree(ht, startingNode);
		ArrayList<Integer> path = getPath(BFSTree, startingNode, nodeInSearchOf);
		Collections.reverse(path);
		if(nodeInSearchOf != path.get(path.size() - 1))
			System.out.println("Node not found \nWeight: 0");
		else{
			System.out.println("Path: " + path);
			System.out.println("Weight: " + getCost(path, MinSpanTree));
		}
		long endTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("m:s:S");
		System.out.println("Total time elapsed [min:sec:mill]: " + sdf.format(new Date(endTime - startTime)));
	}
	public static int getCost(ArrayList<Integer> path, ArrayList<Edge> edges){
		int totalCost = 0;
		for(Edge x : edges){
			if(path.contains(x.getFirst()) && path.contains(x.getSecond())){
				totalCost = totalCost + x.getWeight();
			}
		}
		return totalCost;
	}
	public static ArrayList<Integer> getPath(Hashtable<Integer, ArrayList<Integer>> BFSTree, int startNode, int nodeInSearchOf){
		ArrayDeque<Integer> Q = new ArrayDeque<Integer>();
		Hashtable<Integer, Integer> parents = new Hashtable<Integer, Integer>();
		ArrayList<Integer> path = new ArrayList<Integer>();
		Q.addFirst(startNode);
		int currentNode = -1;
		while(!Q.isEmpty()){
			currentNode = Q.removeFirst();
			if(currentNode == nodeInSearchOf){
				break;
			}
			try{
				for(int v : BFSTree.get(currentNode)){
					Q.addLast(v);
					parents.put(v, currentNode);
				}
			}
			catch(Exception e){}
		}
		path.add(currentNode);
		while(currentNode != startNode){
			path.add(parents.get(currentNode));
			currentNode = parents.get(currentNode);
		}
		return path;
	}
	public static Hashtable<Integer, ArrayList<Integer>> convertArrayListToHashtable(ArrayList<Edge> minTree){
		Hashtable<Integer, ArrayList<Integer>> minTreeHT = new Hashtable<Integer, ArrayList<Integer>>();
		for(Edge x : minTree){
			if(minTreeHT.containsKey(x.getFirst())){
				ArrayList<Integer> values = minTreeHT.get(x.getFirst());
				values.add(x.getSecond());
				minTreeHT.put(x.getFirst(), values);
			}
			else{
				ArrayList<Integer> values = new ArrayList<Integer>();
				values.add(x.getSecond());
				minTreeHT.put(x.getFirst(), values);
			}
			if(minTreeHT.containsKey(x.getSecond())){
				ArrayList<Integer> values = minTreeHT.get(x.getSecond());
				values.add(x.getFirst());
				minTreeHT.put(x.getSecond(), values);
			}
			else{
				ArrayList<Integer> values = new ArrayList<Integer>();
				values.add(x.getFirst());
				minTreeHT.put(x.getSecond(), values);
			}
		}
		return minTreeHT;
	}
	public static Hashtable<Integer, ArrayList<Integer>> constructBFSTree(Hashtable<Integer, ArrayList<Integer>> graph, int startingNode){
		Hashtable<Integer, Boolean> discovered = new Hashtable<Integer, Boolean>();
		Hashtable<Integer, ArrayList<Integer>> BFSTree = new Hashtable<Integer, ArrayList<Integer>>();
		for(int x : graph.keySet()){
			if(!discovered.contains(x))
				discovered.put(x, false);
			for (int y: graph.get(x)) {
				if(!discovered.contains(y))
					discovered.put(y, false);
			}
		}
		ArrayDeque<Integer> Q = new ArrayDeque<Integer>();
		Q.addFirst(startingNode);
		discovered.replace(startingNode, false, true);
		while(!Q.isEmpty()){
			int currentNode = Q.removeFirst();
			try{
			for(int v: graph.get(currentNode)){
				if(discovered.get(v) == false){
					discovered.replace(v, false, true);
					if(BFSTree.get(currentNode)== null){
						ArrayList<Integer> child = new ArrayList<Integer>();
						child.add(v);
						BFSTree.put(currentNode, child);
					}
					else{
						ArrayList<Integer> children = BFSTree.get(currentNode);
						children.add(v);
						BFSTree.put(currentNode, children);
					}
					Q.addLast(v);				
				}
			}
			}
			catch(Exception e){}
		}
		return BFSTree;
	}
	public static ArrayList<Edge> convertToGraph(File file){
		ArrayList<Edge> edges = new ArrayList<Edge>();
		String line = "";
		try{
			BufferedReader BFR = new BufferedReader(new FileReader(file));
			while((line = BFR.readLine()) != null){
				String[] nums = line.split("\\D+");
				Edge currentEdge = new Edge(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), Integer.parseInt(nums[2]));
				edges.add(currentEdge);
				//currentEdge = new Edge(Integer.parseInt(nums[1]), Integer.parseInt(nums[0]), Integer.parseInt(nums[2]));
				//edges.add(currentEdge);
			}
			
		}catch(Exception e){}
		Collections.sort(edges);
		return edges;
	}
	public static int getStartingNode(File file){
		String line;
		String[] content = null;
		try {
			BufferedReader BFR = new BufferedReader(new FileReader(file));
			line = BFR.readLine();
			content = line.split("\\D+");
		} catch (IOException e) {
			int noStartingNode = 0;
		}
		return Integer.parseInt(content[0]);
	}
	public static ArrayList<Edge> constructMinSpanTree(ArrayList<Edge> edges){
		components = new Hashtable<Integer, Integer>();
		ArrayList<Edge> MinSpanTree = new ArrayList<Edge>();
		for(Edge x : edges){
			if(!components.contains(x.getFirst()))
				components.put(x.getFirst(), x.getFirst());
			if(!components.contains(x.getSecond()))
				components.put(x.getSecond(), x.getSecond());
		}
		for(Edge x : edges){
			if (find(x.getSecond()) != find(x.getFirst())) {
				components.put(find(x.getFirst()), find(x.getSecond()));
				MinSpanTree.add(x);
			}
		}
		return MinSpanTree;
	}
	public static int find(int w){
		Integer node = w;
		while(!node.equals(components.get(node))){
			node = components.get(node);
		}
		return node;
	}
}