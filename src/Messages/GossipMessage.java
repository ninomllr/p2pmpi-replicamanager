package Messages;
import java.util.ArrayList;
import java.util.List;

import Replica.*;



public class GossipMessage extends Message {
	
	
	private static final long serialVersionUID = 1L;
	private ReplicationTimestamp timestamp;
	private ArrayList<UpdateLogEntry> log;

	public ArrayList<UpdateLogEntry> getLog() {
		return log;
	}

	public void setLog(List<UpdateLogEntry> updateLog) {
		this.log = (ArrayList<UpdateLogEntry>) updateLog;
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
