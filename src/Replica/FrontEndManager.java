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
	
	private boolean waitingForAnswer;
	
	private Random random;

	public FrontEndManager(int nodeId) {
		super(nodeId);
		boardTimestamp = new ReplicationTimestamp();
		handler = new FrontEndMessageHandler();
		random = new Random(Settings.getInstance().getSeed()+nodeId);
		waitingForAnswer = false;
		
	}


	@Override
	protected void executeInterval() {

		// answer handling
		for (int i = 0; i < messages.length; i++) {
			
			if (i == nodeId) continue;

			if (requests[i].Test() != null) {
				
				waitingForAnswer = false;

				Message msg = messages[i][0];
				messages[i][0] = null;

				// start listening again
				requests[i] = Messenger.listen(messages[i], i);

				// handle message
				handler.handleMessage(msg, this);
			}

		}
		
		if (!waitingForAnswer)
		{
			int replica =  getReplicaManager();
			
			// post a new entry
			if (random.nextInt(100)>=95){
	
				Logger.getInstance().log("Create a new entry and send to " + replica);
				
				BoardEntry entry;
				if (board.size()!=0 && random.nextInt(100)<70) { // create an answer
					BoardEntry existing = getExistingBoardEntry();
					entry = new BoardEntry(nodeId, "RE:"+existing.getTitle(), ""+nodeId);
					entry.setParent(existing);
				} else { // create a new thread
					entry = new BoardEntry(nodeId, String.format("%06d", random.nextInt(999999)), ""+nodeId);
				}
				createUpdateMessage(entry,replica);	
				waitingForAnswer=true;
			} else if (random.nextInt(100)>=90) {
				Logger.getInstance().log("Create a new query and send to " + replica);
				createQueryMessage(replica);
				waitingForAnswer=true;
			}
		}
	}

	private BoardEntry getExistingBoardEntry() {
		int thread = random.nextInt(board.size());
		BoardEntry entry = board.get(thread);
		while (entry.getChild()!=null) {
			entry = entry.getChild();
		}
		return entry;
	}
	
	public void createQueryMessage(int receiver) {
		QueryMessage message = new QueryMessage();
		message.setTimestamp(boardTimestamp);
		message.setReceiver(receiver);
		message.setSender(nodeId);
		Messenger.send(message);
	}

	public void createUpdateMessage(BoardEntry entry, int receiver) {
		UpdateMessage message = new UpdateMessage();
		message.setEntry(entry);
		message.setTimestamp(boardTimestamp);
		message.setReceiver(receiver);
		message.setSender(nodeId);
		Messenger.send(message);
	}
	
	public boolean addBoardEntry(BoardEntry entry) {
		return super.addBoardEntry(entry);
	}
	
	public int getReplicaManager() {
		return random.nextInt(Settings.getInstance().getNumberOfReplicaManagers())+Settings.getInstance().getNumberOfFrontends();
	}

	public void setBoard(ArrayList<BoardEntry> board) {
		this.board = board;	
	}

}
