import Utils.Logger;
import Utils.Settings;
import p2pmpi.mpi.MPI;
import Replica.*;


public class Replica {
	
	static int rank, size;
	static Manager instance;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MPI.Init(args);

		// number of processors
		size = MPI.COMM_WORLD.Size();
		Settings.getInstance().setSize(size);
		// current processor id
		rank = MPI.COMM_WORLD.Rank();
		Settings.getInstance().setRank(rank);
		
		Logger.getInstance().setRank(rank);
		
		if (rank < Settings.getInstance().getNumberOfFrontends()) {
			Logger.getInstance().log("FrontEnd");
			instance = new FrontEndManager(rank);
		}
		else {
			Logger.getInstance().log("ReplicaManager");
			instance = new ReplicaManager(rank);
		}
		
		instance.run();
		
		MPI.Finalize();
	}

}
