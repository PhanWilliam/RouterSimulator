import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedServer extends Thread{
	
	private ServerSocket serverSocket;
	
	public ThreadedServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println(serverSocket + " listening on port " + port);		
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				new ThreadedServerSocket(serverSocket.accept()).start();
				System.out.println("connection established");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*try {
			Socket socket = serverSocket.accept();
			Scanner scan = new Scanner(socket.getInputStream());
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			
			String s = scan.nextLine();
			System.out.println(s);
			printWriter.write("Hello");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
		
	}
}
