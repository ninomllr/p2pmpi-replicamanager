package Replica;
import Messages.*;
import P2P.*;
import Utils.Logger;
import Utils.Settings;

public abstract class MessageHandler {

	public void handleMessage(Message message, Manager manager) {	
		
		//Logger.getInstance().log("Handle message from " + message.getSender());
		
		switch (message.getMessageType()) {

		case UPDATE:
			handleUpdate((UpdateMessage)message, manager);
			break;
		case GOSSIP:
			handleGossip((GossipMessage)message, manager);
			break;
		case QUERY:
			handleQuery((QueryMessage)message, manager);
			break;
		
		default:
			break;

		}
	}

	public void sendMessage(Message message)
	{
		Messenger.send(message);
	}
	
	protected abstract void handleUpdate(UpdateMessage message, Manager manager);
	protected abstract void handleGossip(GossipMessage message, Manager manager);
	protected abstract void handleQuery(QueryMessage message, Manager manager);

}
