package br.com.activedb.core;

import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public class FieldIndex{
	private TreeMap<Comparable<?>, Set<String>> index;
	private Field field;
	private String storeID;

	public FieldIndex(Field field, String storeID){
		this.index = new TreeMap<Comparable<?>, Set<String>>();
		this.field = field;
		this.storeID = storeID;
	}

	public String getIndexID(){
		return storeID + ":" + field.getName();
	}

	public void addValueToIndex(Comparable<?> value, String recordID){
		Set<String> ids = index.get(value);

		if(ids == null){
			ids = new HashSet<String>();
		}

		ids.add(recordID);
		index.put(value, ids);
	}

	public void removeValueFromIndex(Comparable<?> value, String recordID){
		Set<String> keys = index.get(value);
		
		keys.remove(recordID);
	}

	public int getSize(){
		return index.size();
	}

	public double getDispersion(){
		return index.keySet().size();
	}

	public Set<String> getAllIdsForValueEqualsTo(Comparable<?> value){
		return index.get(value);
	}

	private Set<String> getAllIdsForValueDifferentThan(TreeMap<Comparable<?>, Set<String>> map, Comparable<?> keyValue){
		Set<String> retVal = new HashSet<String>();

		for(Comparable<?> key : map.keySet()){
			if(key.equals(keyValue)){
				continue;
			}

			retVal.addAll(map.get(key));
		}

		return retVal;
	}

	public Set<String> getAllIdsForValueDifferentThan(Comparable<?> value){
		return this.getAllIdsForValueDifferentThan(index, value);
	}
	
	private Set<String> getAllIdsForValueHigherThan(TreeMap<Comparable<?>, Set<String>> map, Comparable<?> keyValue, boolean inclusive){
		NavigableMap<Comparable<?>, Set<String>> subMap = map.tailMap(keyValue, inclusive);
		Set<String> retVal = new HashSet<String>();

		for(Comparable<?> key : subMap.keySet()){
			retVal.addAll(subMap.get(key));
		}

		return retVal;
	}

	public Set<String> getAllIdsForValueHigherThan(Comparable<?> value, boolean inclusive){
		return this.getAllIdsForValueHigherThan(index, value, inclusive);
	}
	
	private Set<String> getAllIdsForValueLowerThan(TreeMap<Comparable<?>, Set<String>> map, Comparable<?> keyValue, boolean inclusive){
		NavigableMap<Comparable<?>, Set<String>> subMap = map.headMap(keyValue, inclusive);
		Set<String> retVal = new HashSet<String>();

		for(Comparable<?> key : subMap.keySet()){
			retVal.addAll(subMap.get(key));
		}

		return retVal;
	}

	public Set<String> getAllIdsForValueLowerThan(Comparable<?> value, boolean inclusive){
		return getAllIdsForValueLowerThan(index, value, inclusive);
	}
}
