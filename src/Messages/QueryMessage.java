package Messages;
import java.util.ArrayList;

import Board.BoardEntry;
import Replica.ReplicationTimestamp;


public class QueryMessage extends Message {

	private static final long serialVersionUID = 1L;
	private ReplicationTimestamp timestamp;
	private ArrayList<BoardEntry> board;

	public QueryMessage() {
		super(MessageType.QUERY);
		
		setTimestamp(new ReplicationTimestamp());
	}

	public ReplicationTimestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ReplicationTimestamp timestamp) {
		this.timestamp = timestamp;
	}

	public ArrayList<BoardEntry> getBoard() {
		return board;
	}

	public void setBoard(ArrayList<BoardEntry> board) {
		this.board = board;
	}

}
