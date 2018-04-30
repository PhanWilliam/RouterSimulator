import java.io.IOException;
import java.net.UnknownHostException;

public class Jona2 {
	
	public Jona2()
	{
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
		Host h1 = new Host("190.160.1.2",80);
		Host h2 = new Host("190.160.2.2", 80);
		Message msg = new Message("Coba kirim pesan", h1, h2);
		
		try {
			routers[0].send(msg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		new Jona2();

	}

}
