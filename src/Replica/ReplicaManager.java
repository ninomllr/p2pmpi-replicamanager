package Replica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import p2pmpi.mpi.Request;

import Board.BoardEntry;
import Utils.Logger;
import Utils.Settings;
import Utils.SortedList;
import Messages.*;
import P2P.Messenger;

public class ReplicaManager extends Manager {

	private ReplicationMessageHandler handler;
	private SortedList<UpdateLogEntry> updateLog;
	private ArrayList<QueryMessage> pendingQueries;
	private Random random;

	private ArrayList<ReplicationTimestamp> executedLog;

	public ReplicaManager(int nodeId) {
		super(nodeId);
		handler = new ReplicationMessageHandler();
		setTimestamp(new ReplicationTimestamp());
		updateLog = new SortedList<UpdateLogEntry>();
		executedLog = new ArrayList<ReplicationTimestamp>();
		pendingQueries = new ArrayList<QueryMessage>();
		random = new Random(Settings.getInstance().getSeed()+nodeId);
	}

	public ReplicationTimestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ReplicationTimestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void increment() {
		timestamp.increment(nodeId);
	}

	public int getLocalTime() {
		return (timestamp.getTimestamps())[nodeId];
	}

	public void addBoardEntry(BoardEntry entry,
			ReplicationTimestamp timestampUpdate,
			ReplicationTimestamp timestampMessage) {
		if (!executedLog.contains(timestampMessage)) {
			board.add(entry);
			boardTimestamp = timestampUpdate.maximize(boardTimestamp);
			executedLog.add(timestampMessage);
		}
	}

	public void addUpdate(UpdateLogEntry entry) {
		updateLog.add(entry);
	}

	public void maximizeTimestamp(ReplicationTimestamp other) {
		timestamp = timestamp.maximize(other);
	}

	public void addLogEntries(ArrayList<UpdateLogEntry> log) {
		updateLog.addAll(log);
	}

	public void executeUpdateLog() {
		for (int i = 0; i < updateLog.size(); i++) {
			UpdateLogEntry entry = updateLog.get(i);
			if (boardTimestamp.compareTo(entry.getTimestamp()) < 1) {
				addBoardEntry(entry.getEntry().getBoardEntry(),
						entry.getTimestamp(), entry.getEntry().getTimestamp());
			}
		}
	}

	public void cleanUpdateLog() {

		// @TODO löschen von alten nachrichten

		ArrayList<UpdateLogEntry> delete = new ArrayList<UpdateLogEntry>();

		for (int i = 0; i < updateLog.size(); i++) {
			UpdateLogEntry entry = updateLog.get(i);
			if (boardTimestamp.compareTo(entry.getEntry().getTimestamp()) < 1) {
				if (executedLog.contains(entry.getEntry().getTimestamp()))
					delete.add(entry);
			}
		}

		updateLog.removeAll(delete);
	}

	public void addQuery(QueryMessage message) {
		pendingQueries.add(message);
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

		// check if we can handle another query
		ArrayList<QueryMessage> pending = (ArrayList<QueryMessage>) pendingQueries.clone();
		pendingQueries.clear();
		for (int i = 0; i < pending.size(); i++) {
			handler.handleMessage(pending.get(i), this);
		}

		// gossips
		// lazy implementieren

		
		if (random.nextInt(100)>=95){
			Logger.getInstance().log("Send Gossips");
			
			for (int i = Settings.getInstance().getNumberOfFrontends(); i<Settings.getInstance().getSize(); i++) {
				if (i == nodeId) continue;
				sendGossip(i);
			}
		}
		

		// Output

	}
	
	public void sendGossip(int receiver) {
		GossipMessage message = new GossipMessage();
		message.setLog(updateLog.toArrayList());
		message.setTimestamp(timestamp);
		message.setReceiver(receiver);
		message.setSender(nodeId);
		Messenger.send(message);
	}

}
