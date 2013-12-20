package Messages;
import Board.BoardEntry;
import Replica.ReplicationTimestamp;


public class UpdateMessage extends Message {

	private static final long serialVersionUID = 1L;
	private ReplicationTimestamp timestamp;
	private BoardEntry entry;
	
	public UpdateMessage() {
		super(MessageType.UPDATE);
	}

	public ReplicationTimestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ReplicationTimestamp timestamp) {
		this.timestamp = timestamp;
	}

	public BoardEntry getEntry() {
		return entry;
	}

	public void setEntry(BoardEntry entry) {
		this.entry = entry;
	}

}
