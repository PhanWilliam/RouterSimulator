import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedServer extends Thread{
	
	private ServerSocket serverSocket;
	private String msg;
	
	public ThreadedServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
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
				ThreadedServerSocket tss = new ThreadedServerSocket(serverSocket.accept());
				tss.start();
				
				this.msg = tss.getMsg();
				break;
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
