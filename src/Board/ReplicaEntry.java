package Board;
import java.io.Serializable;

import Replica.ReplicationTimestamp;


public class ReplicaEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReplicationTimestamp timestamp;
	private BoardEntry boardEntry;
	
	public ReplicationTimestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ReplicationTimestamp timestamp) {
		this.timestamp = timestamp;
	}

	public BoardEntry getBoardEntry() {
		return boardEntry;
	}

	public void setBoardEntry(BoardEntry boardEntry) {
		this.boardEntry = boardEntry;
	}

	public ReplicaEntry(ReplicationTimestamp timestamp, BoardEntry boardEntry) {
		setBoardEntry(boardEntry);
		setTimestamp(timestamp);
	}
}
