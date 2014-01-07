package Replica;

import java.util.*;

public class UpdateLog extends LinkedList<UpdateLogEntry>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean add(UpdateLogEntry arg0) {
		return super.add(arg0);
	}

	@Override
	public void add(int arg0, UpdateLogEntry arg1) {
		super.add(arg0, arg1);
	}

	@Override
	public boolean addAll(Collection<? extends UpdateLogEntry> arg0) {
		Iterator<? extends UpdateLogEntry> iterator = arg0.iterator();

		while (iterator.hasNext()){
		
			Iterator<? extends UpdateLogEntry> iterator2 = this.iterator();
			
			UpdateLogEntry entry = iterator.next();
			int i = 0;
			while (iterator2.hasNext()) {
				UpdateLogEntry entry2 = iterator2.next();
				if (entry.happenedBefore(entry2)) {
					break;
				}
				i++;
			}

			this.add(i, entry);
		}
		return true;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends UpdateLogEntry> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		super.clear();
		
	}

	@Override
	public boolean contains(Object arg0) {
		return super.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return super.contains(arg0);
	}

	@Override
	public UpdateLogEntry get(int arg0) {
		return super.get(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		return super.indexOf(arg0);
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}

	@Override
	public Iterator<UpdateLogEntry> iterator() {
		return super.iterator();
	}

	@Override
	public int lastIndexOf(Object arg0) {
		return super.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<UpdateLogEntry> listIterator() {
		return super.listIterator();
	}

	@Override
	public ListIterator<UpdateLogEntry> listIterator(int arg0) {
		return super.listIterator(arg0);
	}

	@Override
	public boolean remove(Object arg0) {
		return super.remove(arg0);
	}

	@Override
	public UpdateLogEntry remove(int arg0) {
		return super.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return super.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return super.retainAll(arg0);
	}

	@Override
	public UpdateLogEntry set(int arg0, UpdateLogEntry arg1) {
		return super.set(arg0, arg1);
	}

	@Override
	public int size() {
		return super.size();
	}

	@Override
	public List<UpdateLogEntry> subList(int arg0, int arg1) {
		return super.subList(arg0, arg1);
	}

	@Override
	public Object[] toArray() {
		return super.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return super.toArray(arg0);
	}

}
