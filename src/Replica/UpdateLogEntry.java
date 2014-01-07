package Replica;
import java.io.Serializable;

import Board.ReplicaEntry;


public class UpdateLogEntry implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReplicationTimestamp timestamp;
	private ReplicaEntry entry;
	private boolean isGossiped;
	
	public ReplicationTimestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ReplicationTimestamp timestamp) {
		this.timestamp = timestamp;
	}

	public UpdateLogEntry(ReplicationTimestamp identifier, ReplicaEntry data) {
		this.timestamp = identifier;
		this.setEntry(data);
		this.setGossiped(false);
	}

	public boolean isGossiped() {
		return isGossiped;
	}

	public void setGossiped(boolean isGossiped) {
		this.isGossiped = isGossiped;
	}

	public boolean happenedBefore(UpdateLogEntry o) {
		return timestamp.happenedBefore(o.timestamp);
	}

	public ReplicaEntry getEntry() {
		return entry;
	}

	private void setEntry(ReplicaEntry entry) {
		this.entry = entry;
	}

}
