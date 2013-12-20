package Replica;

import java.util.ArrayList;

import Board.BoardEntry;
import Messages.Message;
import P2P.Messenger;
import Utils.Logger;
import Utils.Settings;
import p2pmpi.mpi.Request;

public abstract class Manager {

	protected static final int WAIT_TIME = 1000;

	protected int nodeId;
	protected ReplicationTimestamp timestamp;
	protected Request[] requests;
	protected Message[][] messages;
	protected ArrayList<BoardEntry> board;
	protected ReplicationTimestamp boardTimestamp;
	
	
	public ArrayList<BoardEntry> getBoard() {
		return board;
	}
	
	public ReplicationTimestamp getBoardTimestamp() {
		return boardTimestamp;
	}

	protected Manager(int nodeId) {
		
		messages = new Message[Settings.getInstance().getSize()][1];
		requests = new Request[Settings.getInstance().getSize()];
		boardTimestamp = new ReplicationTimestamp();
		board = new ArrayList<BoardEntry>();

		Logger.getInstance().log("Start listening.");
		
		// listening to everyone
		for (int i = 0; i < Settings.getInstance().getSize(); i++) {
			if (i == nodeId)
				continue;
			
			Logger.getInstance().log("Start listening to " + i);

			requests[i] = Messenger.listen(messages[i], i);
		}
		
		this.nodeId = nodeId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public ReplicationTimestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ReplicationTimestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public void run() {
		while (true) {
			executeInterval();
			try {
				Thread.sleep(WAIT_TIME);
			} catch (Exception ex) {
				//
			}
		}
	}
	
	public void printBoard() {
		StringBuilder sb=new StringBuilder("Board: ");
		for (int i = 0 ; i<board.size(); i++) {
			sb.append(board.get(i).getTitle()+" by "+board.get(i).getUserId()+", ");
		}
		Logger.getInstance().log(sb.toString());
	}

	protected abstract void executeInterval();
}
