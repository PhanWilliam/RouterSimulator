import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Jonathan3{
	
	Scanner sc = new Scanner(System.in);
	Router router = new Router();
	Router[] routers = new Router[5];
	
	
	private void initialize()
	{
		for (int i = 0; i < routers.length; i++) {
			routers[i] = new Router();
		}
		for (int i = 0; i < routers.length; i++) {
			routers[i].setNeighbors(routers);
		}
	}
	
	private void clrscreen()
	{
		for (int i = 0; i < 500; i++) {
			System.out.println();
		}
	}
	
	public Jonathan3() {
		initialize();
		viewRouters();
		viewStartMenu();
		sc.nextLine();
		clrscreen();
		viewRouters();
		int menuChoice = 0;
		do{
			ShowMainMenu();
			do{
				System.out.println("Enter Menu: ");
				menuChoice = sc.nextInt();
				sc.nextLine();
			}while(menuChoice <1 || menuChoice >3);
			
			clrscreen();
			switch(menuChoice)
			{
			case 1: 
				TurnOnRouter();
				break;
			case 2:
				sendMessage();
			}
		}while(menuChoice!=3);
	}
	
	
	public void sendMessage()
	{
		String msg = "";
		int port = 0;
		System.out.println("Input the port number: ");
		port = sc.nextInt();
		sc.nextLine();
		System.out.println("Input the message: ");
		msg = sc.nextLine();
		
		ThreadedClientSocket threadedSocket =new ThreadedClientSocket("127.0.0.1", port, msg);
		threadedSocket.start();
	}

	public static void main(String[] args) {
		new Jonathan3();
	}
	
	private void TurnOnRouter()
	{
		for (int i = 0; i < routers.length; i++) {
			System.out.println(routers[i].getId() + " is on!");
		}
		
		int id = 0;
		int port = 0;
		System.out.println("Pick Routers[id]: ");
		id = sc.nextInt();
		sc.nextLine();
		
		System.out.println("Pick port number[id]: ");
		port = sc.nextInt();
		sc.nextLine();
		
		try {
			routers[id].listen(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void viewRouters()
	{
		for (int i = 0; i < routers.length; i++) {
			System.out.println("Router " + routers[i].getId() + "   " + "Host = " + routers[i].getHost());
		}		
	}
	
	private void ShowMainMenu()
	{
		System.out.println("1. Start router [insert port]: ");
		System.out.println("2. Send Message: ");
		System.out.println("3. Exit");
	}
	
	private void viewStartMenu()
	{
		System.out.println("Welcome to Routers Simulator");
		System.out.println("==============================");
		System.out.println("Press enter to send a message!");
	}	
}
