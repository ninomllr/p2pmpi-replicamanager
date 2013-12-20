package Replica;
import java.io.Serializable;

import Utils.Settings;


public class ReplicationTimestamp implements Comparable<ReplicationTimestamp>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] timestamps;

	public ReplicationTimestamp() {
		timestamps = new int[Settings.getInstance().getSize()];
	}
	
	public void increment(int node) {
		timestamps[node]++;
	}
	
	public int[] getTimestamps() {
		return timestamps;
	}
	
	public void setTimestamp(int node, int time) {
		timestamps[node] = time;
	}
	
	public int getTimestamp(int node) {
		return timestamps[node];
	}
	
	public ReplicationTimestamp maximize(ReplicationTimestamp other) {
		
		ReplicationTimestamp val = new ReplicationTimestamp();
		
		for (int i = 0; i < timestamps.length; i++) {
			val.setTimestamp(i, Math.max(timestamps[i],other.getTimestamp(i)));
		}
			
		return val;
	}

	@Override
	public int compareTo(ReplicationTimestamp arg0) {
		int[] values = arg0.timestamps;
		
		for (int i = 0; i < values.length; i++) {
			if (values[i] > timestamps[i])
				return 1;
		}
		
		return -1;
	}

}
