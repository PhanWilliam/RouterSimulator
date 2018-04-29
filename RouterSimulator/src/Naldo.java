import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Vector;

public class Naldo {

	public static void main(String[] args) {
		Router[] routers = new Router[5];
		
		for (int i = 0; i < routers.length; i++) {
			routers[i] = new Router();
		}
		
		for (int i = 0; i < routers.length; i++) {
			routers[i].setNeighbors(routers);
		}
		for(int i=0;i<5;i++) {
			try {
				Thread.sleep(2000);
				routers[i].on();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		Host h1 = new Host("192.168.1.2", 80);
		Host h2 = new Host("192.168.4.2", 80);
		Message msg = new Message("Hello World", h1, h2);
		Scanner in = new Scanner(System.in);
		in.nextLine();
		try {
			
			routers[0].send(msg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		h1 = new Host("192.168.5.2", 80);
		h2 = new Host("192.168.1.2", 80);
		msg = new Message("Hello World2", h1, h2);
		
		
		in.nextLine();
		try {
			
			routers[4].send(msg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		for(int i=0;i<5;i++) {
//			for(int j=0;j<5;j++) {
//				System.out.print(router0.getRoutingTable().getGraph()[i][j]+" ");
//				if(j==4) {
//					System.out.println();
//				}
//				
//			}
//			
//		}
	}
	
}
