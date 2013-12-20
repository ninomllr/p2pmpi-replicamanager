package Replica;

import java.util.ArrayList;
import java.util.Random;

import Board.BoardEntry;
import Messages.*;
import Messages.Message;
import P2P.Messenger;
import Utils.*;

public class FrontEndManager extends Manager {

	private FrontEndMessageHandler handler;
	
	private Random random;

	public FrontEndManager(int nodeId) {
		super(nodeId);
		setTimestamp(new ReplicationTimestamp());
		handler = new FrontEndMessageHandler();
		random = new Random(Settings.getInstance().getSeed()+nodeId);
		
	}

	@Override
	protected void executeInterval() {

		// answer handling
		for (int i = 0; i < messages.length; i++) {
			
			if (i == nodeId) continue;

			if (requests[i].Test() != null) {

				Message msg = messages[i][0];
				messages[i][0] = null;

				// start listening again
				requests[i] = Messenger.listen(messages[i], i);

				// handle message
				handler.handleMessage(msg, this);
			}

		}

		// post a new entry
		if (random.nextInt(100)>=95){
			int replica =  getReplicaManager();
			Logger.getInstance().log("Create a new entry and send to " + replica);
			BoardEntry entry = new BoardEntry(nodeId, "a", "a");
			createEntry(entry,replica);	
		}
		
		// random rm auswählen

		// ask queries

		// get updates

	}
	
	public void createEntry(BoardEntry entry, int receiver) {
		UpdateMessage message = new UpdateMessage();
		message.setEntry(entry);
		message.setTimestamp(timestamp);
		message.setReceiver(receiver);
		message.setSender(nodeId);
		Messenger.send(message);
	}
	
	public int getReplicaManager() {
		return random.nextInt(Settings.getInstance().getNumberOfReplicaManagers())+Settings.getInstance().getNumberOfFrontends();
	}

}
