package Messages;
import java.util.ArrayList;
import java.util.List;

import Replica.*;



public class GossipMessage extends Message {
	
	
	private static final long serialVersionUID = 1L;
	private ReplicationTimestamp timestamp;
	private UpdateLog log;

	public UpdateLog getLog() {
		return log;
	}

	public void setLog(UpdateLog updateLog) {
		this.log = (UpdateLog) updateLog;
	}

	public GossipMessage() {
		super(MessageType.GOSSIP);
		setTimestamp(new ReplicationTimestamp());
		setLog(new UpdateLog());
	}

	public ReplicationTimestamp getTimestamp() {
		return timestamp.clone();
	}

	public void setTimestamp(ReplicationTimestamp timestamp) {
		this.timestamp = timestamp.clone();
	}

}
