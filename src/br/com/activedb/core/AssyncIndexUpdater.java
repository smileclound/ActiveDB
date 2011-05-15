package br.com.activedb.core;

import java.util.List;
import java.util.Map;

public class AssyncIndexUpdater implements Runnable{
	public List<Record> records;
	public DataStore store;
	public Action action;
	public enum Action {INSERT, DELETE};
	public boolean isFinished = false;

	public AssyncIndexUpdater(List<Record> records, DataStore store, Action action){
		this.records = records;
		this.store = store;
		this.action = action;
	}

	@Override
	public void run() {

		System.out.print("AssyncIndexUpdater started running. Updating indexes..");
		switch (action) {
		case INSERT:
			this.updateIndexesOnInsert(records, store.getIndexes());
			break;
		case DELETE:
			this.updateIndexesOnRemove(records, store.getIndexes());
			break;
		default:
			//TODO: LOG or Something;
			throw new RuntimeException("Not a valid action on ASsyncIndexUpdater");
		}

		System.out.print("Finished updating... Notifying all!");
		
		synchronized (this) {
			this.notifyAll();
			isFinished = true;
		}
	}

	private void updateIndexesOnInsert(List<Record> records, Map<Field, FieldIndex> indexes){
		for(Record record : records){
			for(Field key : record.getValues().keySet()){
				FieldIndex index = indexes.get(key);

				index.addValueToIndex(record.get(key), record.getRecordID());
			}
		}
	}

	private void updateIndexesOnRemove(List<Record> records, Map<Field, FieldIndex> indexes){
		for(Record record : records){
			for(Field key : record.getValues().keySet()){
				FieldIndex index = indexes.get(key);

				index.removeValueFromIndex(record.get(key), record.getRecordID());
			}
		}
	}
}
