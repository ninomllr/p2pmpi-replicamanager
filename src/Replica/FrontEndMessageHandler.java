package Replica;

import Messages.*;
import Utils.Logger;

public class FrontEndMessageHandler extends MessageHandler {
	
	protected void handleUpdate(UpdateMessage message, FrontEndManager manager) {
		Logger.getInstance().log("Got Update answer from " + message.getSender());
		manager.createQueryMessage(message.getSender());
	}

	protected void handleGossip(GossipMessage message, FrontEndManager manager) {
		Logger.getInstance().log("Got Gossip answer from " + message.getSender());
		// should never happen
	}

	protected void handleQuery(QueryMessage message, FrontEndManager manager) {
		Logger.getInstance().log("Got Query answer from " + message.getSender());	
		
		manager.setBoard(message.getBoard());
		
		ReplicationTimestamp ts = message.getTimestamp().clone();
		ts = ts.maximize(manager.getBoardTimestamp());
		manager.setBoardTimestamp(ts);
		manager.printBoard();
	}

	@Override
	protected void handleUpdate(UpdateMessage message, Manager manager) {
		handleUpdate(message,(FrontEndManager)manager);
	}

	@Override
	protected void handleGossip(GossipMessage message, Manager manager) {
		handleGossip(message, (FrontEndManager)manager);
	}

	@Override
	protected void handleQuery(QueryMessage message, Manager manager) {
		handleQuery(message, (FrontEndManager)manager);		
	}

}
