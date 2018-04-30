
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Jonathan4 {
	
	Scanner sc = new Scanner(System.in);
	Router[] routers = new Router[5];
	
	
	private void initialize()
	{
		for (int i = 0; i < routers.length; i++) {
			routers[i] = new Router();
		}
		for (int i = 0; i < routers.length; i++) {
			routers[i].setNeighbors(routers);
		}
		
		
//		router = new Router();
	}
	
	public Jonathan4() throws UnknownHostException, IOException {
		initialize();
		//viewRouters();
		viewStartMenu();
		sc.nextLine();
		for (int i = 0; i < 500; i++) {
			System.out.println();
		}
		viewRouters();
		sendMessage();
	}
	
	
	public void sendMessage() throws UnknownHostException, IOException
	{
		
		
		System.out.println("Choose sender host Id: ");
		int SenderID = sc.nextInt();
		sc.nextLine();
		for(int i=0;i<5;i++){
			if(routers[i].getId() != SenderID){
				
				System.out.println("Router " + routers[i].getId() + "   " + "Host = " + routers[i].getHost().get(0).getIpAddress());
			}
		}
		System.out.println();
		System.out.println("Choose receiver host Id: ");
		int ReceiverID = sc.nextInt();
		sc.nextLine();
		
		System.out.println("Please Insert your message: ");
		String msg = sc.nextLine();
		
		try {
			routers[SenderID].send(new Message(msg, routers[SenderID].getHost().get(0), routers[ReceiverID].getHost().get(0)));			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// routers[SenderID].send(new Message(msg, routers[SenderID].getHost().get(0), routers[ReceiverID].getHost().get(0)));
		
//		if(SenderID == router.getId())
//		{
//			System.out.println("Choose receiver host id: ");
//			int receiverID = sc.nextInt();
//			for (int i = 0; i < 5; i++) {
//				if(routers[i].getId() != receiverID){
//				System.out.println("Router " + routers[i].getId() + "   " + "Host = " + routers[i].getHost());
//				}
//			}
//		}
//		else
//		{
//			System.out.println("Router id not found!");
//		}
		
		//ThreadedClientSocket threadedSocket = new ThreadedClientSocket("127.0.0.1", , msg);
		//threadedSocket.start();
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		new Jonathan4();
	}
	
	private void viewRouters()
	{
		System.out.println("       id");
		System.out.println("================================");
		for (int i = 0; i < routers.length; i++) {
			System.out.println("Router [" + routers[i].getId() + "]" + "   " + "Host = " + routers[i].getHost().get(0).getIpAddress());
		}		
	}
	
	private void viewStartMenu()
	{
		System.out.println("Welcome to Routers Simulator");
		System.out.println("==============================");
		System.out.println("Press [ENTER] to send a message!");
	}	
}

