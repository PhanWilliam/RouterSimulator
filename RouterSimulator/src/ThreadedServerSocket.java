import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedServerSocket extends Thread{

	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private String msg;
	
	public ThreadedServerSocket(Socket socket) throws IOException {		
		this.socket = socket;		
		// System.out.println(this + " socket created");
	}
	
	public String getMsg() {
		return this.msg;
	}
	
	@Override
	public void run() {		
		String incomingMessage = "";
		try {
			this.output = new PrintWriter(socket.getOutputStream(), true);
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));			
						
			String inputLine;
			while ((inputLine = input.readLine()) != null) {
				if(inputLine.equals("Goodbye")) break;
				output.println("Server give this message: " + inputLine);
				System.out.println("Server received this message: " + inputLine);
			}
			
			input.close();
			output.close();		
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
}
