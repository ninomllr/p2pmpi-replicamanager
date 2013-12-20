package Messages;
import java.util.ArrayList;

import Replica.*;



public class GossipMessage extends Message {
	
	
	private static final long serialVersionUID = 1L;
	private ReplicationTimestamp timestamp;
	private ArrayList<UpdateLogEntry> log;

	public ArrayList<UpdateLogEntry> getLog() {
		return log;
	}

	public void setLog(ArrayList<UpdateLogEntry> log) {
		this.log = log;
	}

	public GossipMessage() {
		super(MessageType.GOSSIP);
		setTimestamp(new ReplicationTimestamp());
		setLog(new ArrayList<UpdateLogEntry>());
	}

	public ReplicationTimestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ReplicationTimestamp timestamp) {
		this.timestamp = timestamp;
	}

}
