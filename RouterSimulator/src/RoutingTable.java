
public class RoutingTable {

	/**
	 * To store the distance value from each router,
	 * a graph matrix is used to pinpoint the distance.
	 */
	private int graph[][];
	
	
	public int[][] getGraph() {
		return graph;
	}

	public void setGraph(int[][] graph) {
		this.graph = graph;
	}

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
	 * Just for debugging and stuff..
	 * @param dist
	 * @param prev
	 */
	public void printDistAndPrev(int dist[], int prev[]) {
		for (int i = 0; i < prev.length; i++) {
			System.out.print(dist[i] + "("+ prev[i] +") ");
		}
		System.out.println("");
	}
	
	public int[] convertResultAccordingToSource(int arr[], int source) {
		for (int i = 0; i < arr.length; i++) {
			if(arr[i] == 0)
				arr[i] = source;
			else if(arr[i] <= source)
				arr[i]--;			
		}
		
		return arr;
	}
	
	/**
	 * Change the location of the source to be the most
	 * left of the graph.
	 * 
	 * @param graph
	 * @param source
	 * @return new ordered graph
	 */
	public int[][] moveGraph(int graph[][], int source){
		
		for (int i = 0; i < graph.length; i++) {
			int x = graph[i][source];
			
			for (int j = source; j > 0; j--) {
				graph[i][j] = graph[i][j-1];
			}
			
			graph[i][0] = x;
		}
		
		for (int i = 0; i < graph.length; i++) {
			int x = graph[source][i];
			
			for (int j = source; j > 0; j--) {
				graph[j][i] = graph[j-1][i];
			}
			
			graph[0][i] = x;
		}
		
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				// System.out.print(graph[i][j] + " ");
			}
			// System.out.println("");
		}
		
		return graph;
	}
	
	/**
	 * Return the path to the origin source of order.
	 * 
	 * @param arr
	 * @param source
	 * @return path
	 */
	public int[] moveResult(int arr[], int source) {
		
		int temp = arr[0];
		
		for (int i = 0; i < source; i++) {
			arr[i] = arr[i+1];
		}
		
		arr[source] = temp;
		
		return arr;
	}
	//rtie = routing table information exchange
	public String encodeRoutingTable() {
		String rtie = "RTIE";
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				rtie+="_"+graph[i][j];
			}	
		}
			
		return rtie;
	}
	public void decodeUpdateRoutingTable(String rtie) {
		
		String [] temp = rtie.split("_");
		int counter=1;
		
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				if(graph[i][j]>Integer.parseInt(temp[counter])) {
					graph[i][j]=Integer.parseInt(temp[counter]);
				}
				counter++;
			}
		}
	}
	
	/**
	 * Evaluate the graph to find the shortest path.
	 * 
	 * @return the corresponding path based on the order.
	 */
	public int[] dijkstra(int source) {
		// TODO code dijkstra here..
		int originalSource = source;
		graph = moveGraph(graph, source);
		source = 0;
		
		int dist[] = new int[graph.length];
		int prev[] = new int[graph.length];
		
		for (int i = 0; i < graph.length; i++) {
			dist[i] = Integer.MAX_VALUE;
			prev[i] = -1;
		}
		
		dist[source] = 0;
		prev[source] = source;
		
		int Q[] = new int[graph.length];
		for (int i = 0; i < graph.length; i++) {
			
			Q[i] = graph[0][i];
			
			// System.out.print(Q[i] + " ");
		}
		// System.out.println("");
		
		int nodesTraversed = 0;
		while (nodesTraversed < graph.length) {			
			int min = Integer.MAX_VALUE;
			int nextVertexIndex = 0;
			
			for (int i = 0; i < Q.length; i++) {
				if (Q[i] < min) {
					min = Q[i];
					nextVertexIndex = i;
				}
				// System.out.print(Q[i] + " ");
			}
			// System.out.println("");
			
			Q[nextVertexIndex] = Integer.MAX_VALUE;
			nodesTraversed++;
			// System.out.println("next vertex index: " + nextVertexIndex);
			
			for (int i = 0; i < graph.length; i++) {
				if (i == nextVertexIndex) continue;
				
				int alt = (dist[nextVertexIndex] + graph[nextVertexIndex][i] > Integer.MAX_VALUE) ? 
									Integer.MAX_VALUE : dist[nextVertexIndex] + graph[nextVertexIndex][i];				
				
//				System.out.println("IMV: " + Integer.MAX_VALUE);
//				System.out.println("SUM: " + (dist[nextVertexIndex] + graph[nextVertexIndex][i]));
//				System.out.println("");
				
				if (alt < dist[i] && alt > 0) {
//					System.out.println("dist: " + dist[nextVertexIndex]);
//					System.out.println("graph: " + graph[nextVertexIndex][i]);
//					System.out.println("");
					
					dist[i] = alt;
					prev[i] = nextVertexIndex;
					Q[i] = alt;
				}
			}
			
//			 printDistAndPrev(dist, prev);
//			 System.out.println("==================================");
		}
		
		prev = moveResult(prev, originalSource);
		dist = moveResult(dist, originalSource);
		prev = convertResultAccordingToSource(prev, originalSource);
		
		return prev;
	}
	
}
