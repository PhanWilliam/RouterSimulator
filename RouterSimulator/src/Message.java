
public class Message {
	private String msg;
	/**
	 * Specify which host that sends the message.
	 */	
	private Host sender;
	/**
	 * Specify which host that receive the message.
	 */	
	private Host receiver;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Host getSender() {
		return sender;
	}

	public void setSender(Host sender) {
		this.sender = sender;
	}

	public Host getReceiver() {
		return receiver;
	}

	public void setReceiver(Host receiver) {
		this.receiver = receiver;
	}

	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	public Message(String msg, Host sender, Host receiver) {
		super();
		this.msg = msg;
		this.sender = sender;
		this.receiver = receiver;
	}
	public String EncodeMessage()
	{
		String s = msg + "#" + sender.getIpAddress() + "#" + sender.getPortNumber() + "#"
				+ receiver.getIpAddress() + "#" + receiver.getPortNumber();
		return s;
	}
	
	public Message DecodeMessage(String s)
	{
		String[] output = s.split("#");
		this.msg = output[0];
		
		//Host h = new Host(ipAddress, portNumber);
		this.sender = new Host(output[1], Integer.parseInt(output[2]));
		this.receiver = new Host(output[3], Integer.parseInt(output[4]));
		
		
		
		
//		String hostSenderIp = sender.getIpAddress();
//		String hostSenderPort = Integer.toString(sender.getPortNumber());
//		String hostReceiverIp = receiver.getIpAddress();
//		String hostReceiverPort = Integer.toString(receiver.getPortNumber());
		return new Message(msg, sender, receiver);
	}
}
