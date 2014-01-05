package Utils;

public class Logger {
	
	private static Logger instance;
	private int rank;
	
	public static Logger getInstance() {
		if (instance == null) instance = new Logger();
		return instance;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public void log(String message) {
		System.out.println("[" + rank + "] "+message );
	}
	
	public void logRank(String message, int rank) {
		if (Settings.getInstance().getRank() == rank)
			log(message);
	}
	
}
