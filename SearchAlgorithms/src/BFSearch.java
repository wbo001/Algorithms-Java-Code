import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
/*BFS Search program
 * Author: Wesley Olinger
 * Date: 10/15/2015
 */

/*This class takes in a file of edges and creates a hashtable
 * it then takes that hashtable and conducts a BFS tree from it.
 * After the BFS Tree has been made it asks the user for a node
 * and uses BFS search to find that particular node. It also prints
 * out the path traversed and the total time elapsed.*/
public class BFSearch {
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		Scanner scan = new Scanner(System.in);
		File file = new File("edges.txt");
		Hashtable<Integer, ArrayList<Integer>> ht = convertToAdj(file);
		int startingNode = getStartingNode(file);
		Hashtable<Integer, ArrayList<Integer>> BFSTree = constructBFSTree(ht, startingNode);
		System.out.print("Enter the node to search for. ");
		int nodeInSearchOf = scan.nextInt();
		System.out.println("Total number of nodes searched: " + getPath(BFSTree, startingNode, nodeInSearchOf));
		long endTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("m:s:S");
		System.out.println("Total time elapsed [min:sec:mill]: " + sdf.format(new Date(endTime - startTime)));
	}
	//Takes in a file and creates a hashtable
	public static Hashtable<Integer, ArrayList<Integer>> convertToAdj(File file){
		Hashtable<Integer, ArrayList<Integer>> ht = new Hashtable<Integer, ArrayList<Integer>>();
		String line = "";
		try{
			BufferedReader BFR = new BufferedReader(new FileReader(file));
			while((line = BFR.readLine()) != null){
				String[] nums = line.split("\\D+");
				int key = Integer.parseInt(nums[0]);
				int key2 = Integer.parseInt(nums[1]);
				if(ht.keySet().contains(key)){
					int value = Integer.parseInt(nums[1]);
					ArrayList<Integer> values = ht.get(key);
					values.add(value);
					ht.put(key,(ArrayList<Integer>)values);
				}
				else{
					ArrayList<Integer> values = new ArrayList<Integer>();
					int value = Integer.parseInt(nums[1]);
					values.add(value);
					ht.put(key, values);
				}
				if(ht.keySet().contains(key2)){
					int value = Integer.parseInt(nums[0]);
					ArrayList<Integer> values = ht.get(key2);
					values.add(value);
					ht.put(key2, values);
				}
				else{
					ArrayList<Integer> values = new ArrayList<Integer>();
					int value = Integer.parseInt(nums[0]);
					values.add(value);
					ht.put(key2, values);
				}
			}
			BFR.close();
		}
		catch(Exception e){
			System.out.print(e.getMessage());
		}
		return ht;
	}
	//Method takes in a graph and a starting node and returns a BFSTree
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
	//Method return the starting node of a file of edges
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
	//Method that returns the path from the starting node to any particular node. 
	public static int getPath(Hashtable<Integer, ArrayList<Integer>> BFSTree, int startNode, int nodeInSearchOf){
		ArrayDeque<Integer> Q = new ArrayDeque<Integer>();
		Q.addFirst(startNode);
		int counter = 1;
		while(!Q.isEmpty()){
			int currentNode = Q.removeFirst();
			if(currentNode == nodeInSearchOf){
				System.out.print(currentNode + " ");
				break;
			}
			else{
				System.out.print(currentNode + " ");
			}
			try{
				for(int v : BFSTree.get(currentNode)){
					Q.addLast(v);
				}
			}
			catch(Exception e){}
			counter++;
		}
		System.out.println();
		return counter;
	}
}