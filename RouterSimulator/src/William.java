import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class William {
	
	private Scanner scan = new Scanner(System.in);
	private Router routers[];
	
	private void cls() {
		for (int i = 0; i < 25; i++) {
			System.out.println("");
		}
	}
	
	private void sendMessage() throws UnknownHostException, IOException {		
//		Scanner input = new Scanner(socket.getInputStream());
//		PrintWriter output = new PrintWriter(socket.getOutputStream());
		
//		output.write("DIE");
//		String result = input.nextLine();
//		System.out.println(result);
		String message = "";
		int port = 0;
		System.out.print("Input port: ");
		port = scan.nextInt();
		scan.nextLine();
		System.out.print("Input message: ");
		message = scan.nextLine();
		
		ThreadedClientSocket threadedSocket = new ThreadedClientSocket("127.0.0.1", port, message);
		threadedSocket.start();
	}
	
	private void startRouter() {
		for (int i = 0; i < routers.length; i++) {
			System.out.println(routers[i].getId() + " is running");
		}
		
		int id = 0, port = 0;
		System.out.print("Choose routers [id] >> ");
		id = scan.nextInt();
		scan.nextLine();
		
		System.out.print("Choose port number >> ");
		port = scan.nextInt();
		scan.nextLine();
		
		try {
			routers[id].listen(port);
//			String s[] = new String[2];
//			s[0] = port + "";
//			Router router = new Router();
			// router.main(s);
			// router.listen(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		routers = new Router[5];
		
		for (int i = 0; i < routers.length; i++) {
			routers[i] = new Router();			
		}
	}
	
	private void showMenu() {
		System.out.println("Router Simulator");
		System.out.println("====================================");
		System.out.println("1. Start router based on port number");
		System.out.println("2. Send message");
		System.out.println("3. Exit");
	}
	
	public William() {
		init();
		
		int choice = 0;
		do {
			cls();
			showMenu();
			do {
				try {
					System.out.print("Choose >> ");
					choice = scan.nextInt();					
				} catch (Exception e) {
					choice = 0;
				}
				scan.nextLine();
			} while (choice < 1 || choice > 3);
			
			cls();
			switch (choice) {
			case 1:
				startRouter();
				break;
			case 2:
				try {
					sendMessage();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			default:
				break;
			}
			
		} while (choice != 3);
	}

	public static void main(String[] args) {
		new William();
	}

}
