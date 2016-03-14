package com.chrisali.peopledatabase.model.messagedatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MessageServer implements Iterable<Message> {
	private List<Message> selected;
	private Map<Integer, List<Message>> messages;
	
	public MessageServer() {
		selected = new ArrayList<Message>();
		messages = new TreeMap<Integer, List<Message>>();
		
		for (int i=0; i<6; i++) {
			List<Message> list = new ArrayList<Message>();
			for (int j=0; j<5; j++) {
				list.add(new Message("Title " + i,"Contents " + j));
			}
			messages.put(i, list);
		}
	}
	
	public void setSelectedServers(Set<Integer> servers) {
		selected.clear();
		
		for(Integer id : servers) {
			if(messages.containsKey(id))
				selected.addAll(messages.get(id));
		}
	}
	
	public int getMessageCount() {
		return selected.size();
	}

	@Override
	public Iterator<Message> iterator() {
		return new MessageIterator(selected);
	}
}

class MessageIterator implements Iterator<Message> {
	
	private Iterator<Message> iterator;
	
	public MessageIterator(List<Message> messages) {
		iterator = messages.iterator();
	}
	
	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Message next() {
		try {Thread.sleep(62);} 
		catch (InterruptedException e) {}
		
		return iterator.next();
	}
	
}