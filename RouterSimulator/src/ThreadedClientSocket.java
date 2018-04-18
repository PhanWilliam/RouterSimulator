import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadedClientSocket extends Thread{

	private Socket socket;
	private BufferedReader input;
	private PrintStream output;
	private String message = "";
	
	public ThreadedClientSocket(String host, int port, String message) {
		try {
			this.socket = new Socket(host, port);
			this.message = message;
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	@Override
	public void run() {
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream());
						
			output.println(this.message);			
			String inputLine = input.readLine();
			System.out.println(inputLine);
			
			output.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
