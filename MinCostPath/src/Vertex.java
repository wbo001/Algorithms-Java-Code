
public class Vertex {
	int vertex;
	public Vertex(int vertex){
		this.vertex = vertex;
	}
	public int getVertex(){
		return vertex;
	}
	public int compareTo(Vertex vertex){
		int factor;
		if(this.vertex > vertex.getVertex())
			factor = 1;
		else if(this.vertex == vertex.getVertex())
			factor = 0;
		else
			factor = -1;
		return factor;
	}
}
