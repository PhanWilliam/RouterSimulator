import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedServer extends Thread{
	
	private ServerSocket serverSocket;
	private String msg;
	private Router router;
	
	public ThreadedServer(int port, Router router) throws IOException {
		serverSocket = new ServerSocket(port);
		this.router = router;
		System.out.println(serverSocket + " listening on port " + port);		
	}
	
	public void close() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getMsg() {
		return this.msg;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				ThreadedServerSocket tss = new ThreadedServerSocket(serverSocket.accept(), this.router);
				tss.start();
			} catch (IOException e) {
				System.out.println("error");
				e.printStackTrace();
			}
		}
		
		/*try {
			this.serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
