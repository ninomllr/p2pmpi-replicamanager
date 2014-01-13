import Utils.Logger;
import Utils.Settings;
import p2pmpi.mpi.MPI;
import Replica.*;

public class Replica {

	static int rank, size;
	static Manager instance;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		MPI.Init(args);

		Settings.getInstance().setPath(args[0]);

		// number of processors
		size = MPI.COMM_WORLD.Size();
		Settings.getInstance().setSize(size);
		// current processor id
		rank = MPI.COMM_WORLD.Rank();
		Settings.getInstance().setRank(rank);

		Logger.getInstance().setRank(rank);

		if (Settings.getInstance().getNumberOfFrontends() > size) {
			throw new Exception(
					"Fehlerhafte Parameter! Es muss mit mindestens "
							+ (Settings.getInstance().getNumberOfFrontends() + 1)
							+ " gestartet werden.");
		} else {

			if (rank < Settings.getInstance().getNumberOfFrontends()) {
				Logger.getInstance().log("FrontEnd");
				instance = new FrontEndManager(rank);
			} else {
				Logger.getInstance().log("ReplicaManager");
				instance = new ReplicaManager(rank);
			}

			// init files
			instance.printBoard();

			instance.run();
		}

		MPI.Finalize();
	}

}
