package Replica;

import Board.*;
import Messages.*;
import Utils.Logger;

public class ReplicationMessageHandler extends MessageHandler {
	
	private void handleQuery(QueryMessage message, ReplicaManager replicaManager) {
		
		if (replicaManager.getBoardTimestamp().happenedBefore(message.getTimestamp())) {
			Logger.getInstance().log("Handle Query message from " + message.getSender());
			// wohoo, i can answer it already!
			QueryMessage msg = new QueryMessage();
			msg.setReceiver(message.getSender());
			msg.setSender(message.getReceiver());
			msg.setBoard(replicaManager.getBoard());
			msg.setTimestamp(replicaManager.getBoardTimestamp());
			sendMessage(msg);
		} else {
			// can not answer at the moment, i have to wait
			replicaManager.addQuery(message);
		}
	}

	private void handleGossip(GossipMessage message, ReplicaManager replicaManager) {
		
		Logger.getInstance().log("Handle Gossip message from " + message.getSender());
		
		// add update log and refresh timestamp
		replicaManager.addLogEntries(message.getLog());
		replicaManager.maximizeTimestamp(message.getTimestamp());
		
		// check update log for execution
		replicaManager.executeUpdateLog();
		
		// clean all update log messages
		replicaManager.cleanUpdateLog();
		
		// print what we have in our board
		replicaManager.printBoard();
		
	}

	private void handleUpdate(UpdateMessage message, ReplicaManager replicaManager) {
		
		Logger.getInstance().log("Handle Update message from " + message.getSender() + " with message " + message.getEntry().getTitle());
		
		// increase replicaTS
		replicaManager.increment();
		
		// generate unique Identifier TS
		ReplicationTimestamp timestamp = message.getTimestamp().clone();
		timestamp.setTimestamp(replicaManager.getNodeId(),replicaManager.getLocalTime());
		
		// add to update log
		UpdateLogEntry entry = new UpdateLogEntry(timestamp, new ReplicaEntry(message.getTimestamp(), message.getEntry()));
		replicaManager.addUpdate(entry);
		
		// inform front end
		UpdateMessage msg = new UpdateMessage();
		msg.setSender(message.getReceiver());
		msg.setReceiver(message.getSender());
		
		sendMessage(msg);
		
		// Update
		replicaManager.executeUpdateLog();
		
		// print what we have in our board
		replicaManager.printBoard();
	}

	@Override
	protected void handleUpdate(UpdateMessage message, Manager manager) {
		handleUpdate(message, (ReplicaManager)manager);
		
	}

	@Override
	protected void handleGossip(GossipMessage message, Manager manager) {
		handleGossip(message, (ReplicaManager)manager);
	}

	@Override
	protected void handleQuery(QueryMessage message, Manager manager) {
		handleQuery(message, (ReplicaManager)manager);
	}

}
