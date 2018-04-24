import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

import org.omg.CosNaming.NamingContextPackage.NotEmpty;

public class Router{
	
	public static int id_counter = 0;
	
	/**
	 * Router id used to uniquely identifies the router.
	 */
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	private int counter=0;
	private RoutingTable routingTable;
	
	public RoutingTable getRoutingTable() {
		return routingTable;
	}

	public void setRoutingTable(RoutingTable routingTable) {
		this.routingTable = routingTable;
	}
	/**
	 * Each router know about the other router
	 * by having the objects in their class.
	 * 
	 * TODO include this router too, to ease the RoutingTable
	 * process.
	 */
	private Router neighbors[];
	
	public Router[] getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Router[] neighbors) {
		this.neighbors = neighbors;
	}
	private int distance[]=new int [5];
	
	/**
	 * Host is stored 'virtually'
	 * which means, host will contain only
	 * socket address, without actual threading.
	 */
	private Vector<Host> host;	
	
	public Vector<Host> getHost() {
		return host;
	}

	/**
	 * Create new objects contained in the 
	 * router class.
	 * @throws IOException 
	 */
	
	public Router(){
		// TODO router amount is fix into 5, 
		// maybe there are better ways to do this though		
		this.neighbors = new Router[5];
		this.host = new Vector<Host>();
		this.id = Router.id_counter++;
		
		initializeDistance();
		readFile();
		this.routingTable=new RoutingTable(initializeRoutingTable());
	}

	/**
	 * Enable the router to receive message
	 * from other router.
	 * 
	 * TODO make sure that the router receive function
	 * can do everything of the following:
	 * - Decode the message.
	 * - Acknowledge if the packet is sent.
	 * - Know the sender of the message.
	 * - If the destination of the msg is not this 
	 *   router, then:
	 *   > Do routing function to send the msg to another
	 *     router.
	 */
	public void receive(Message msg)
	{
		if (this.id == findMessageDestination(msg)) {
			System.out.println(this+ "("+ this.id +")" + " receive message");
			System.out.println("From: "+msg.getSender().getIpAddress());
			System.out.println("Message: "+msg.getMsg());
			return;
		}
		
		try {
			System.out.println(this + "("+ this.id +")" + " sending...");
			this.send(msg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Enable the router to send message
	 * to other router.
	 * 
	 * TODO make sure that the router receive function
	 * can do everything of the following:
	 * - Decode the message.
	 * - Acknowledge if the packet is sent.
	 * - Know the destination of the message.
	 * - Able to make another router to receive the msg.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public void send(Message msg) throws UnknownHostException, IOException {
		int next_hop_index = this.routing(msg);
		
		// System.out.println("next_hop_index(1) = " + next_hop_index);
		
		if (next_hop_index == this.id) {			
			if(this.id == findMessageDestination(msg)) {				
				this.receive(msg);
				return;
			}
			next_hop_index = findMessageDestination(msg);
		}
			
		
			
		// System.out.println("next_hop_index(2) = " + next_hop_index);
		
		int portNumber = msg.getReceiver().getPortNumber();
		boolean portAvailable = false;
		
		do{
			try {				
				neighbors[next_hop_index].listen(portNumber);
				portAvailable = true;
			} catch (Exception e) {
				portAvailable = false;
				portNumber++;
				// e.printStackTrace();
			}
		}while(!portAvailable);
		
		ThreadedClientSocket threadedSocket = new ThreadedClientSocket(
				"127.0.0.1", portNumber, msg.EncodeMessage());
		threadedSocket.start();
		
		System.out.println(this + "("+ this.id +")" + " send message");
	}
	public int[][] initializeRoutingTable() {
		int [][]graph=new int[5][5];
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				if(i==j) {
					graph[i][j]=0;
				}
				else if(i==this.id) {
					graph[i][j]=distance[j];
					
				}
				else if(j==this.id) {
					graph[i][j]=distance[i];
				}
				else {
					graph[i][j]=Integer.MAX_VALUE;
				}
			}
		}
		
		return graph;
	}
	
	/**
	 * Initialize all the distance to possible neighbor to
	 * integer max value except to itself.
	 * 
	 * NOTE the index is the id for each router
	 */
	public void initializeDistance() {
		for(int i=0;i<5;i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		distance[this.id]=0;
	}
	
	/**
	 * Enable the router to read a file and extracting
	 * the router next hop and distance.
	 * 
	 * TODO make sure that the router could do everything 
	 * of the following:
	 * - File the RoutingTable with the value from the file.
	 */
	public void readFile() {
		Vector<String> content = new Vector<String>();
		try {
			Scanner in = new Scanner(new FileReader("RoutersFile2/"+this.id+".txt"));
			while(in.hasNextLine()) {
				content.add(in.nextLine());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String string : content) {
			if(string.startsWith("R")) {
				String temp[] ;
				temp = string.substring(1,string.length()).split("#");
				distance[Integer.parseInt(temp[0])]=Integer.parseInt(temp[1]);
			}
			else if(string.startsWith("H")) {
				String temp[];
				temp = string.substring(1,string.length()).split("#");
				this.host.add(new Host(temp[0],Integer.parseInt(temp[1])));
				
			}
		}
		
	}
	
	
	
	/**
	 * Enable the router to check which router is linked into
	 * the Host. The idea is to acknowledge that this router
	 * contain the following Host or not. This will help
	 * other router to determine the final destination of the
	 * message.
	 * 
	 * TODO make sure that the router could do everything
	 * of the following:
	 * - Determine whether a host with such IP and port is
	 *   connected directly to the router.
	 * - Return the boolean value.
	 */
	public boolean checkHost(int routerId) {	
		if(routerId==this.id) {
			return true;
		}
		else {
			return false;
			
		}
	}
	
	/**
	 * Enable the router to determine the path taken to send the
	 * message to the destination.
	 * 
	 * TODO make sure that the router could do everything of
	 * the following:
	 * - If the host is known
	 * - If the host of destination address is unknown to 
	 * 	 this router, then:
	 * 	 > Check each neighbor's host for the destination address
	 *     using the checkHost function.
	 *   > Return the neighbor's router to this function.
	 * - Do routing by using the RoutingTable algorithm, to 
	 *   determine the next hop.
	 * - Send the message to another router.
	 */

	public int routing(Message msg) {
		if(checkHost(findMessageDestination(msg))==true) {
			// if host is known
			return this.id;
		}

		else {
			//finding next hop
			if(counter==0) {
				broadCast();
				counter++;
			}
			int [] djikstraResult = new int [5];
			djikstraResult=this.routingTable.dijkstra(this.id);
			
			// System.out.println("Dijkstra Result: ");
//			for (int i = 0; i < djikstraResult.length; i++) {
//				System.out.print(djikstraResult[i] + " ");
//			}
//			System.out.println("");
			
			int temp=djikstraResult[findMessageDestination(msg)];
			int nextHop=this.id;
			while(temp!=this.id) {
				nextHop = temp;
				temp=djikstraResult[temp];
			//	System.out.println(nextHop);
			}
			
			//System.out.println("nextHop " + nextHop);
			// System.out.println("temp " + temp);
			return nextHop;			
		}
		
	}
	
	public int findMessageDestination(Message msg) {
		for (Router i : neighbors) {
			for (Host j : i.getHost()) {
				if(j.getIpAddress().equals(msg.getReceiver().getIpAddress())) {
					return i.getId();
				}
			}
		}
		return -1;

	}
	/**
	 * Enable the router to fill its own routing table graph
	 * 
	 */
	public void broadCast() {
		int temp[][] = new int[5][5];
		for (Router i : neighbors) {
			temp = this.routingTable.getGraph();
			
			for(int j=0;j<5;j++) {
				for(int k=0;k<5;k++) {
					if(temp[j][k]>i.getRoutingTable().getGraph()[j][k]) {
						temp[j][k]=i.getRoutingTable().getGraph()[j][k];
						
					}
					
				}
			}
			this.routingTable.setGraph(temp);
		}
		
	}
	public void listen(int port) throws IOException {		
		ThreadedServer server = new ThreadedServer(port, this);
		server.start();	
	}

}
