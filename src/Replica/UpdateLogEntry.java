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
		return timestamp.clone();
	}

	public void setTimestamp(ReplicationTimestamp timestamp) {
		this.timestamp = timestamp.clone();
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
	
	public boolean equals(Object obj) {
		return equals((UpdateLogEntry)obj);
	}


	public boolean equals(UpdateLogEntry other) {
		return this.getTimestamp().equals(other.getTimestamp());
	}
	
	public String toString() {
		return timestamp.toString();
	}
		

}
