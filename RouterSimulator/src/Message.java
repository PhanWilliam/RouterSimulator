
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

	public Message(String msg, Host sender, Host receiver) {
		super();
		this.msg = msg;
		this.sender = sender;
		this.receiver = receiver;
	}
	public String EncodeMessage()
	{
		String s = msg + "#" + sender + "#" + receiver;
		return s;
	}
	
	public Message DecodeMessage(String s)
	{
		String[] output = s.split("#");
		msg = output[0];
		String hostSenderIp = sender.getIpAddress();
		String hostSenderPort = Integer.toString(sender.getPortNumber());
		String hostReceiverIp = receiver.getIpAddress();
		String hostReceiverPort = Integer.toString(receiver.getPortNumber());
		return new Message(s, sender, receiver);
	}
}
