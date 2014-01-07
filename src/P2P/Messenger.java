package P2P;
import Messages.Message;
import Utils.Logger;
import p2pmpi.mpi.MPI;
import p2pmpi.mpi.Request;

public class Messenger {

	public static Request send(Message message) {

		Message[] buffer = new Message[1];
		buffer[0] = message;
		Request request = MPI.COMM_WORLD.Isend(buffer, 0, 1, MPI.OBJECT, message.getReceiver(), 0);
		
		return request;

	}
	
	public static Request listenAny(Message[] buffer) {
		
		Request request = MPI.COMM_WORLD.Irecv(buffer, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, 0);
		return request;
	}
	
	public static Request listen(Message[] buffer, int sender) {
		
		Request request = MPI.COMM_WORLD.Irecv(buffer, 0, 1, MPI.OBJECT, sender, 0);
		return request;
	}

}
