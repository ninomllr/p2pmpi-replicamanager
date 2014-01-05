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

	/**
	 * 
	 * @param other
	 * @return 1 if this happened before other, 0 if this did not happen before other, -1 if no causal connection
	 */
	public boolean equals(ReplicationTimestamp other) {
		int[] values = other.timestamps;
		
		for (int i = 0; i < values.length; i++) {

			if (values[i] != timestamps[i]) {
				return false;
			}			
		}
		
		return true;
		
	}
	
	/**
	 * 
	 * @param other
	 * @return 1 if this happened before other, 0 if this did not happen before other, -1 if no causal connection
	 */
	public int happenedBefore(ReplicationTimestamp other) {
		int[] values = other.timestamps;
		
		int otherCount = 0;
		int thisCount = 0;
		int sameCount = 0;
		
		// this < other then bigger = true
		
		for (int i = 0; i < values.length; i++) {
			if (values[i] <= timestamps[i]) {
				thisCount++;
			}
			
			if (values[i] >= timestamps[i]){
				otherCount++;
			} 
			
			if (values[i] == timestamps[i]) {
				sameCount++;
			}
				
		}
		
		if (otherCount-sameCount == 0)
			return 0;
		if (thisCount-sameCount == 0)
			return 1;
		
		// no causal connection
		return -1;
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
