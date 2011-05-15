package br.com.activedb.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.activedb.core.AssyncIndexUpdater.Action;

public class DataStore {
	private Map<String, Field> metaData;
	private Map<String, Record> store;
	private String storeID;
	private RecordIdGenerator idGen;
	private Map<Field, FieldIndex> indexes;

	public DataStore(List<Field> fields, String storeID, RecordIdGenerator idGen){
		this.metaData = new LinkedHashMap<String, Field>();
		this.storeID = storeID;
		this.store = new HashMap<String, Record>();
		this.indexes = new HashMap<Field, FieldIndex>();
		this.idGen = idGen;

		this.applyMetaData(fields);
		this.startIndexesForFields(fields);
	}

	public Collection<Field> getMetaData(){
		return metaData.values();
	}

	public String getStoreID(){
		return storeID;
	}

	public synchronized String storeRecord(Record record){
		record.setRecordID(idGen.generateId());
		store.put(record.getRecordID(), record);

		updateIndexesOnInsert(record, indexes);

		return record.getRecordID();
	}

	public synchronized List<String> storeRecord(List<Record> records){
		List<String> retVal = new ArrayList<String>();

		for(Record record : records){
			record.setRecordID(idGen.generateId());
		}

		AssyncIndexUpdater updater = new AssyncIndexUpdater(records, this, Action.INSERT);
		new Thread(updater, "AsyncIndexUpdater").start();

		for(Record record : records){
			retVal.add(record.getRecordID());
			store.put(record.getRecordID(), record);
		}

		waitForIndexToUpdate(updater);

		return retVal;
	}

	private void waitForIndexToUpdate(AssyncIndexUpdater updater){
		System.out.println("Waiting for updater to finish updating indexes...");
		if(updater.isFinished){
			System.out.println("Updater has already finisehd working...");
			return;
		}
		try{
			synchronized (updater) {
				while(!updater.isFinished){
					updater.wait();
				}
			}
		}catch(InterruptedException e){
			//TODO: Log exception
			e.printStackTrace();
		}
		System.out.println("Finished waiting...");
	}

	public Record lookupRecordByRecordID(String id){
		return store.get(id);
	}

	public synchronized Collection<Record> lookupRecords(List<Rule<? extends Comparable<?>>> rules){
		Collection<Record> list = store.values();

		for(Rule<? extends Comparable<?>> rule : rules){
			list = validateRuleForRecordList(list, rule);
		}

		return list;		
	}

	public synchronized Collection<Record> lookupRecordsUsingIndexes(List<Rule<? extends Comparable<?>>> rules){
		List<Set<String>> resultForEachRule = new ArrayList<Set<String>>();

		for(Rule<? extends Comparable<?>> rule : rules){
			FieldIndex index = this.indexes.get(this.metaData.get(rule.getFieldID()));

			Set<String> result = rule.getValidKeysFromIndexForValue(index);

			resultForEachRule.add(result);
		}

		Set<String> consolidateKeys = this.consolidateResultFromLookup(resultForEachRule);

		List<Record> retval = getRecords(consolidateKeys);

		return retval;
	}

	private List<Record> getRecords(Collection<String> recordKeys){
		List<Record> list = new ArrayList<Record>();
		for(String key : recordKeys){
			Record record = this.lookupRecordByRecordID(key);

			if(record == null){
				//TODO Looks like our indexes are out of sync. Log it and do something.
				System.out.println("Index out of sync for key " + key);
			}else{
				list.add(record);
			}
		}

		return list;
	}

	private Set<String> consolidateResultFromLookup(List<Set<String>> resultPerRule){		
		/**get the biggest Set and validate to see if all other Collections have that value**/
		Set<String> biggest = null;
		for(Set<String> set : resultPerRule){
			if(biggest == null || set.size() > biggest.size()){
				biggest = set;
			}
		}

		for(String key : biggest){
			for(Set<String> set : resultPerRule){
				if(!set.contains(key)){
					biggest.remove(key);
					break;
				}
			}
		}

		return biggest;
	}

	private Collection<Record> validateRuleForRecordList(Collection<Record> records, Rule<? extends Comparable<?>> rule){
		List<Record> result = new ArrayList<Record>();
		for(Record rec : records){
			if(ruleValidated(rule, rec)){
				result.add(rec);
			}
		}

		return result;
	}

	private boolean ruleValidated(Rule<? extends Comparable<?>> rule, Record rec){
		Field field = metaData.get(rule.getFieldID());

		if(field == null){
			throw new RuntimeException("Unknown fieldID for DataStore. FieldID:" + rule.getFieldID() + " DataStore:" + this.storeID);
		}	

		if(!rule.validateRule(rec.get(field))){
			return false;
		}

		return true;
	}

	private void applyMetaData(List<Field> meta){
		int i = 0;
		for(Field f : meta){
			f.setIndex(i++);

			metaData.put(f.getName(), f);
		}
	}

	private void startIndexesForFields(List<Field> meta){
		for(Field f : meta){
			indexes.put(f, new FieldIndex(f, storeID));
		}
	}

	public synchronized void cleanStore(){
		metaData.clear();
		store.clear();
	}

	public Map<Field, FieldIndex> getIndexes(){
		return this.indexes;
	}

	private void updateIndexesOnInsert(Record record, Map<Field, FieldIndex> indexes){
		for(Field key : record.getValues().keySet()){
			FieldIndex index = indexes.get(key);

			index.addValueToIndex(record.get(key), record.getRecordID());
		}
	}

	private void updateIndexesOnRemove(Record record, Map<Field, FieldIndex> indexes){
		for(Field key : record.getValues().keySet()){
			FieldIndex index = indexes.get(key);

			index.removeValueFromIndex(record.get(key), record.getRecordID());
		}

	}

}
