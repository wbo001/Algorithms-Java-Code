import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;
/*
 * DFS search program
 * Author: Wesley Olinger
 * Date: 10/15/2015
 */

/*This class takes in a file of edges and creates a hashtable
 * it then takes that hashtable and conducts a DFS tree from it.
 * After the DFS Tree has been made it asks the user for a node
 * and uses DFS search to find that particular node. It also prints
 * out the path traversed and the total time elapsed.
 */
public class DFSearch {
	//Main driver of the program
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		Scanner scan = new Scanner(System.in);
		File file = new File("edges.txt");
		Hashtable<Integer, ArrayList<Integer>> ht = convertToAdj(file);
		int startingNode = getStartingNode(file);
		Hashtable<Integer, ArrayList<Integer>> DFSTree = constructTree(ht, startingNode);
		System.out.println(DFSTree);
		System.out.println("Please enter a node to search for.");
		int nodeInSearchOf = scan.nextInt();
		System.out.println("Total number of nodes searched: " + getPath(DFSTree, startingNode, nodeInSearchOf));
		long endTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("m:s:S");
		System.out.println("Total time elapsed [min:sec:mill]: " + sdf.format(new Date(endTime - startTime)));
	}
	//Method takes in a file of edges and makes a hashtable
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
	public static Hashtable<Integer, ArrayList<Integer>> constructTree(Hashtable<Integer, ArrayList<Integer>> ht, int startingNode){
		Hashtable<Integer, Boolean> discovered = new Hashtable<Integer, Boolean>();
		Hashtable<Integer, ArrayList<Integer>> DFSTree = new Hashtable<Integer, ArrayList<Integer>>();
		Hashtable<Integer, Integer> getParent = new Hashtable<Integer, Integer>();
		Deque<Integer> stack = new LinkedList<Integer>();
		for(int x : ht.keySet()){
			if(!discovered.contains(x))
				discovered.put(x, false);
			for (int y: ht.get(x)) {
				if(!discovered.contains(y))
					discovered.put(y, false);
			}
		}
		stack.add(startingNode);
		while(!stack.isEmpty()){
			int currentNode = stack.pop();
			if(discovered.get(currentNode) == false){
				discovered.replace(currentNode, false, true);
				if(currentNode !=  startingNode){
					int parent = getParent.get(currentNode);
					if(DFSTree.get(parent) == null){
						ArrayList<Integer> children = new ArrayList<Integer>();
						children.add(currentNode);
						DFSTree.put(parent, children);
					}
					else{
						ArrayList<Integer> children = DFSTree.get(parent);
						children.add(currentNode);
						DFSTree.put(parent, children);
					}
				}
				try{
					for (int x: ht.get(currentNode)){
						if(discovered.get(x) == false){
							stack.push(x);;
							getParent.put(x, currentNode);
						}
					}
				}
				catch(Exception e){
					
				}
			}
		
		}
		return DFSTree;
	}
	//Returns the path from a starting node to a particular node in a BFSTree
	public static int getPath(Hashtable<Integer, ArrayList<Integer>> DFSTree, int startNode, int nodeInSearchOf){
		Deque<Integer> stack = new LinkedList<Integer>();
		int counter = 0;
		stack.add(startNode);
		while(!stack.isEmpty()){
			counter++;
			int currentNode = stack.pop();
				if(currentNode == nodeInSearchOf){
					System.out.print(currentNode + " ");
					break;
				}
				else
					System.out.print(currentNode + " ");
				try{
					for(int x : DFSTree.get(currentNode)){
						stack.push(x);
					}
				}
				catch(Exception e){}
		}
		System.out.println();
		return counter;
	}
	//Method takes in a file of edges and gets the starting node
	public static int getStartingNode(File file){
		String line;
		String[] content = null;
		try {
			BufferedReader BFR = new BufferedReader(new FileReader(file));
			line = BFR.readLine();
			content = line.split("\\D+");
		} catch (IOException e) {
			int noStartingNode = -1;
		}
		return Integer.parseInt(content[0]);
	}
}
