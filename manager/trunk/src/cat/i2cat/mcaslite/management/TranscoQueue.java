//package cat.i2cat.mcaslite.management;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cat.i2cat.mcaslite.config.model.TranscoRequest;
//import cat.i2cat.mcaslite.config.model.TranscoRequest.Status;
//import cat.i2cat.mcaslite.exceptions.MCASException;
//
//public class TranscoQueue {
//
//	private List<TranscoRequest> queue;
//	private static final TranscoQueue INSTANCE = new TranscoQueue();
//
//	public static TranscoQueue getInstance(){
//		return INSTANCE;
//	}
//
//	private TranscoQueue() {
//		this.queue = new ArrayList<TranscoRequest>();
//	}
//	
//	synchronized public int indexOf(TranscoRequest r){
//		return queue.indexOf(r);
//	}
//	
//	synchronized public void update(TranscoRequest r) {
//		int index = queue.indexOf(r);
//		if (index >= 0){
//			queue.remove(index);
//			queue.add(index, r);
//		}
//	}
//	
//	synchronized public boolean removeRequest(TranscoRequest r) {
//		return queue.remove(r);
//	}
//
//	synchronized public TranscoRequest get(Status state) {
//		for (TranscoRequest r : queue) {
//			if (r.getState().equals(state)) {
//				return r;
//			}
//		}
//		return null;
//	}
//	
//	synchronized public int count(Status state) {
//		int i = 0;
//		for (TranscoRequest r : queue) {
//			if (r.getState().equals(state)) {
//				i++;
//			}
//		}
//		return i;
//	}
//	
//	synchronized public Status getState(TranscoRequest r) {
//		if (queue.contains(r)){
//			return queue.get(queue.indexOf(r)).getState();
//		}
//		return null;
//	}
//	
//	synchronized public TranscoRequest getRequest(TranscoRequest r) {
//		if (queue.contains(r)){
//			return queue.get(queue.indexOf(r));
//		}
//		return null;
//	}
//
//	synchronized public void put(TranscoRequest r) throws MCASException {
//		if (queue.contains(r)) {
//			throw new MCASException(); 
//		} else {
//			this.queue.add(r);
//		}
//	}
//	
//	synchronized public void clearQueue(){
//		this.queue.clear();
//	}
//
//	synchronized public boolean contains(TranscoRequest r) {
//		return this.queue.contains(r);
//	}
//
//	synchronized public boolean isEmpty() {
//		return this.queue.isEmpty();
//	}
//	
//	synchronized public int size() {
//		return this.queue.size();
//	}
//	
//	synchronized public boolean isEmpty(Status state) {
//		if (queue.isEmpty()) {
//			return true;
//		} else {
//			for (TranscoRequest r : queue) {
//				if (r.getState().equals(state)) {
//					return false;
//				}
//			}
//		}
//		return true;
//	}
//
//	synchronized public List<TranscoRequest> getElements() {
//		List<TranscoRequest> list = new ArrayList<TranscoRequest>();
//		for (TranscoRequest r : queue) {
//			list.add(r);
//		}
//		return list;
//	}
//	
//}
