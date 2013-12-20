package Replica;
import java.io.Serializable;

import Board.ReplicaEntry;


public class UpdateLogEntry implements Comparable<UpdateLogEntry>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReplicationTimestamp timestamp;
	public ReplicationTimestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ReplicationTimestamp timestamp) {
		this.timestamp = timestamp;
	}

	private ReplicaEntry entry;

	public UpdateLogEntry(ReplicationTimestamp identifier, ReplicaEntry data) {
		this.timestamp = identifier;
		this.setEntry(data);
	}

	@Override
	public int compareTo(UpdateLogEntry o) {
		return timestamp.compareTo(o.timestamp);
	}

	public ReplicaEntry getEntry() {
		return entry;
	}

	private void setEntry(ReplicaEntry entry) {
		this.entry = entry;
	}

}
