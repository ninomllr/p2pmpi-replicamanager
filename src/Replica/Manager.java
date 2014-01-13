package Replica;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import Board.BoardEntry;
import Messages.Message;
import P2P.Messenger;
import Utils.Logger;
import Utils.Settings;
import p2pmpi.mpi.Request;

public abstract class Manager {

	protected static final int WAIT_TIME = 250;

	protected int nodeId;
	// protected ReplicationTimestamp timestamp;
	protected Request[] requests;
	protected Message[][] messages;
	protected ArrayList<BoardEntry> board;
	protected ReplicationTimestamp boardTimestamp;

	public void setBoardTimestamp(ReplicationTimestamp boardTimestamp) {
		this.boardTimestamp = boardTimestamp;
	}

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
		StringBuilder sb = new StringBuilder("######BOARD" + nodeId
				+ "######\n");
		for (int i = 0; i < board.size(); i++) {
			sb.append(printChildren(board.get(i), 0));
		}
		PrintWriter writer;
		try {
			writer = new PrintWriter(Settings.getInstance().getPath()
					+ "board-" + nodeId + ".txt", "UTF-8");
			writer.append(sb.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String printChildren(BoardEntry boardEntry, int level) {
		String result = "";

		for (int i = 0; i < level; i++) {
			result += "--";
		}
		result += (boardEntry.getTitle() + "(" + boardEntry.getUserId() + ")" + "\n");

		if (boardEntry.getChildren().size() > 0) {
			level++;
			for (int i = 0; i < boardEntry.getChildren().size(); i++) {
				result += printChildren(boardEntry.getChild(i), level);
			}
		}

		return result;
	}

	protected boolean addBoardEntry(BoardEntry entry) {
		if (entry.getParent() == null) {
			board.add(entry);
		} else {
			BoardEntry existing = null;
			for (int i = 0; i < board.size(); i++) {
				existing = findEntry(board.get(i), entry.getParent().getTitle());
				if (existing != null)
					break;
			}
			if (existing == null)
				return false;

			existing.addChild(entry);
		}

		return true;
	}

	private BoardEntry findEntry(BoardEntry parent, String title) {
		BoardEntry entry = null;
		if (parent.getTitle().equals(title)) {
			return parent;
		}

		for (int i = 0; i < parent.getChildren().size(); i++) {
			entry = findEntry(parent.getChild(i), title);
			if (entry != null)
				return entry;
		}

		return null;

	}

	protected abstract void executeInterval();
}
