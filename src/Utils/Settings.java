package Utils;

public  class Settings {
	
	private int rank;
	private int size;
	private final int numberOfFrontends=2;
	private final int seed = 123456;

	private static Settings instance;

	private Settings() {
		rank = 0;
	}
	
	public static Settings getInstance() {
		if (instance == null) instance = new Settings();
		return instance;
	}
	
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumberOfFrontends() {
		return numberOfFrontends;
	}
	
	public int getNumberOfReplicaManagers() {
		return size-numberOfFrontends;
	}

	public int getSeed() {
		return seed;
	}	
	

}
