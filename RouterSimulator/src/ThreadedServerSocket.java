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
	private Router router;
	
	public ThreadedServerSocket(Socket socket, Router router) throws IOException {		
		this.socket = socket;		
		this.router = router;
		// System.out.println(this + " socket created");
	}
	
	@Override
	public void run() {		
		//String incomingMessage = "";
		try {
			this.output = new PrintWriter(socket.getOutputStream(), true);
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));			
						
			String inputLine;
			while ((inputLine = input.readLine()) != null) {
				if(inputLine.equals("Goodbye")) break;
				output.println("Server give this message: your message have been received well");
				System.out.println("Server received this message: " + inputLine);
				
				router.receive(new Message().DecodeMessage(inputLine));
			}
			
			input.close();
			output.close();		
			socket.close();
		} catch (IOException e) {
			System.out.println("error 2");
			e.printStackTrace();
		}
			
	}
	
}
