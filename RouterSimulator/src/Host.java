
public class Host {

	private String ipAddress;
	private int portNumber;
	
	public Host(String ipAddress, int portNumber) {
		this.ipAddress=ipAddress;
		this.portNumber=portNumber;
	}
	
	/**
	 * Enable the Host class to decode a message
	 * and determine the Host in the message.
	 * 
	 * By assuming there are 2 Host in the message,
	 * please set the Host[0] to sender and Host[1]
	 * to receiver.
	 */
	
	public Host[] decode(Message msg) {
		// TODO change the return to the host values.
		return null;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

}
