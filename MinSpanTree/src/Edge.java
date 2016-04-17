import java.lang.reflect.Array;


public class Edge implements Comparable<Edge>{
	int first;
	int second;
	int weight;
	public Edge(int first, int second, int weight){
		this.first = first;
		this.second = second;
		this.weight = weight;
	}
	public int[] getEdge(){
		int[] edge = {first, second};
		return edge; 
	}
	public int getFirst(){
		return first;
	}
	public int getSecond(){
		return second;
	}
	public int getWeight(){
		return weight;
	}
	public int compareTo(Edge edge) {
		int factor;
		if(this.weight > edge.getWeight())
			factor = 1;
		else if(this.weight == edge.getWeight())
			factor = 0;
		else
			factor = -1;
		return factor;
	}
}