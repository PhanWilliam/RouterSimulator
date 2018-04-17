
public class RoutingTable {

	/**
	 * To store the distance value from each router,
	 * a graph matrix is used to pinpoint the distance.
	 */
	private int graph[][];
	
	/**
	 * For a router graph ABCDE, the path will be return
	 * based on the order ABCDE given on the graph.
	 */
	private int path[];
	
	/**
	 * Initialize the variables needed to make the algorithm
	 * works.
	 * 
	 * @param graph will be used to initialize the graph.
	 */
	public RoutingTable(int graph[][]) {
		this.graph = graph;
		this.path = new int[graph.length];
	}
	
	/**
	 * Evaluate the graph to find the shortest path.
	 * 
	 * @return the corresponding path based on the order.
	 */
	public int[] dijkstra(int source) {
		// TODO code dijkstra here..
		return path;
	}
}
