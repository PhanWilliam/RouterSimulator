import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

import org.omg.CosNaming.NamingContextPackage.NotEmpty;

public class Router implements Runnable{
	
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

	/**
	 * Each router know about the other router
	 * by having the objects in their class.
	 * 
	 * TODO include this router too, to ease the RoutingTable
	 * process.
	 */
	private Router neighbors[];
	private int distance[]=new int [5];
	
	/**
	 * Host is stored 'virtually'
	 * which means, host will contain only
	 * socket address, without actual threading.
	 */
	private Vector<Host> host;
	
	/**
	 * Each router will run its own using their
	 * own thread.
	 * */
	private Thread thread;
	
	/**
	 * Create new objects contained in the 
	 * router class.
	 */
	public Router() {
		// TODO router amount is fix into 5, 
		// maybe there are better ways to do this though		
		this.neighbors = new Router[5];
		this.host = new Vector<Host>();
		this.thread = new Thread(this);		
		
		initializeDistance();
		readFile();
	}
	
	/**
	 * Start the router by starting the thread.
	 */
	public void startRouter() {
		this.thread.start();
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
	public void receive(Message msg) {}
	
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
	 */
	public void send(Message msg) {}
	
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
	 * - 
	 */
	
	public void readFile() {
		Vector<String> content = new Vector<String>();
		try {
			Scanner in = new Scanner(new FileReader("RoutersFile/"+this.id+".txt"));
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
				String temp;
				temp = string.substring(1,string.length());
				this.host.add(new Host(temp,80));
				
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
	public boolean checkHost(Host host) {		
		// TODO change the return value according to the logic
		return false;
	}
	
	/**
	 * Enable the router to determine the path taken to send the
	 * message to the destination.
	 * 
	 * TODO make sure that the router could do everything of
	 * the following:
	 * - If the host of destination address is unknown to 
	 * 	 this router, then:
	 * 	 > Check each neighbor's host for the destination address
	 *     using the checkHost function.
	 *   > Return the neighbor's router to this function.
	 * - Do routing by using the RoutingTable algorithm, to 
	 *   determine the next hop.
	 * - Send the message to another router.
	 */
	public void routing(Message msg) {}
	
	@Override
	public void run() {
		// keep router alive
		while(true) {			
			// TODO router logics
		}
	}
	
}
