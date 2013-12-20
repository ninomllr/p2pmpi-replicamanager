package Messages;
import java.io.Serializable;


public abstract class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MessageType messageType;
	private int sender;
	private int receiver;
	
	protected Message(MessageType messageType) {
		this.setMessageType(messageType);
	}

	public MessageType getMessageType() {
		return messageType;
	}

	protected void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	
	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	
}