import java.io.IOException;
import java.net.UnknownHostException;

public class William2 {

	public William2() {
		Router[] routers = new Router[5];
		
		for (int i = 0; i < routers.length; i++) {
			routers[i] = new Router();
		}
		
		for (int i = 0; i < routers.length; i++) {
			routers[i].setNeighbors(routers);
		}
		
		Host h1 = new Host("192.168.1.2", 80);
		Host h2 = new Host("192.168.4.2", 80);
		Message msg = new Message("Hello World", h1, h2);
		
		try {
			routers[0].send(msg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new William2();
	}

}
