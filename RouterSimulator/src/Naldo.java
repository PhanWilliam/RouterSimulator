import java.util.Vector;

public class Naldo {

	public static void main(String[] args) {
		Router router0 = new Router();
		Router router1 = new Router();
		Router router2 = new Router();
		Router router3 = new Router();
		Router router4 = new Router();
		
		Router [] listRouter = new Router[5];
		listRouter[0]=router0;
		listRouter[1]=router1;
		listRouter[2]=router2;
		listRouter[3]=router3;
		listRouter[4]=router4;
		for (Router router : listRouter) {
			router.setNeighbors(listRouter);
		}
		
		router0.broadCast();
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				System.out.print(router0.getRoutingTable().getGraph()[i][j]+" ");
				if(j==4) {
					System.out.println();
				}
				
			}
			
		}
	}
	
}
