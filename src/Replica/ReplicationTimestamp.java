package Replica;
import java.io.Serializable;

import Utils.Logger;
import Utils.Settings;


public class ReplicationTimestamp implements Serializable, Cloneable {
	
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
	
	public boolean equals(Object obj) {
		return equals((ReplicationTimestamp)obj);
	}


	public boolean equals(ReplicationTimestamp other) {
		int[] values = other.timestamps;
		
		for (int i = 0; i < values.length; i++) {

			if (values[i] != timestamps[i]) {
				return false;
			}			
		}
		
		return true;
		
	}
	
	
	public boolean happenedBefore(ReplicationTimestamp other) {
		int[] values = other.timestamps;
		
		for (int i = 0; i < values.length; i++) {
			if (values[i] > timestamps[i]) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
		String output="";
		
		for (int i = 0; i< timestamps.length; i++)
			output+=timestamps[i]+", ";
		
		return output;
	}
	
	public ReplicationTimestamp clone() {
		ReplicationTimestamp obj = new ReplicationTimestamp();
		
		for (int i = 0; i < timestamps.length; i++) {
			obj.setTimestamp(i, timestamps[i]);
		}
		
		return obj;
		
	}

}
