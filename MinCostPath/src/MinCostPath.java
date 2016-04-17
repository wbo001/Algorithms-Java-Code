import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
/* Min Cost Algorithm
 * Author: Wesley Olinger
 * Date 10/29/2015
 * 
 * This algorithm takes in a graph and a cost associated with getting form one node to another. It will
 * then find the shortest path from a specified starting and end node within the graph.   
 */



public class MinCostPath {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the node to start from. ");
		int startNode = scan.nextInt();
		System.out.print("Enter the destination node ");
		int nodeInSearchOf = scan.nextInt();
		long startTime = System.currentTimeMillis();
		File file = new File("Edges.txt");
		ArrayList<Vertex> vertexs = getAllVertex(file);
		ArrayList<Edge> sortedEdges = convertToGraph(file);
		int s = Shortest_Path(vertexs, sortedEdges, (startNode - 1), (nodeInSearchOf - 1));
		System.out.println("The least cost from node " + startNode + " to " + nodeInSearchOf + " : " + s);
		long endTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("m:s:S");
		System.out.println("Total time elapsed [min:sec:mill]: " + sdf.format(new Date(endTime - startTime)));
	}
	//This is where the algorithm actually conducts its search to find the shortest path in the graph
	public static int Shortest_Path(ArrayList<Vertex> V, ArrayList<Edge> E, int startingNode, int targetNode){
		ArrayDeque<Integer> active = new ArrayDeque<Integer>();
		int n = V.size();
		int[] next = new int[n];
		int[] m = new int[n];
		m[targetNode] = 0;
		for(int x = 0; x < m.length; x++){
			if(x != (targetNode))
				m[x] = 500;
		}
		active.addLast(targetNode);
		while(!active.isEmpty()){
			int activeNode = active.removeFirst();
			for(Edge x : E){
				if((x.getSecond() - 1) == activeNode){
					int vChange = m[x.getFirst() - 1];
					int y = m[x.getSecond() - 1];
					m[x.getFirst() - 1] = (min(m[x.getFirst() - 1], (y + x.getWeight())));
					if(m[x.getFirst() - 1] != vChange){
						next[x.getFirst() - 1] = m[x.getFirst() - 1];
						active.addLast(x.getFirst() - 1);
					}
				}
			}
		}
		return m[startingNode];
		/*
		for(int x = 0; x < m.length; x++)
			System.out.println(m[x]);*/
	}
	//Method used for determining the minimum of two integers. 
	public static int min(int left, int right){
		if(left < right)
			return left;
		else
			return right;
	}
	//Method used to reading in the vertices of a given file
	public static <T> ArrayList<Vertex> getAllVertex(File file){
		ArrayList<Vertex> vertexs = new ArrayList<Vertex>();
		ArrayList<Integer> allVertex = new ArrayList<Integer>();
		String line = "";
		try{
			BufferedReader BFR = new BufferedReader(new FileReader(file));
			while((line = BFR.readLine()) != null){
				String[] nums = line.split(" ");
				if(!allVertex.contains(Integer.parseInt(nums[0])))
					allVertex.add(Integer.parseInt(nums[0]));
				if(!allVertex.contains(Integer.parseInt(nums[1])))
					allVertex.add(Integer.parseInt(nums[1]));
			}
		}catch(Exception e){}
		for(Integer v : allVertex){
			Vertex vertex = new Vertex(v);
			vertexs.add(vertex);
		}
		return vertexs;
	}
	//This method reads in the file containing the edges and the respective cost and adds them to an 
	//ArrayList. 
	public static ArrayList<Edge> convertToGraph(File file){
		ArrayList<Edge> edges = new ArrayList<Edge>();
		String line = "";
		try{
			BufferedReader BFR = new BufferedReader(new FileReader(file));
			while((line = BFR.readLine()) != null){
				String[] nums = line.split(" ");
				Edge currentEdge = new Edge(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), Integer.parseInt(nums[2]));
				edges.add(currentEdge);
			}
			
		}catch(Exception e){}
		Collections.sort(edges);
		return edges;
	}
}