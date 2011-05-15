package br.com.activedb.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import br.com.activedb.core.*;
import static org.junit.Assert.*;

public class StoreAndQueryTest {

	@Test
	public void storeRecordTest(){
		System.out.println("\n\nStarting storeRecordTest...");
		List<Field> list = new ArrayList<Field>();

		Field id = new Field(Integer.class, "id");
		Field name = new Field(Integer.class, "name");
		Field age = new Field(Integer.class, "age");
		Field rate = new Field(Integer.class, "rate");

		list.add(id);
		list.add(name);
		list.add(age);
		list.add(rate);

		Orchestrator server = new Orchestrator();

		DataStore store = null;

		try{	
			store = server.createStore("com.br.person", list);
		}catch(Exception e){
			e.printStackTrace();
		}

		Map<Field, Value<?>> values = new HashMap<Field, Value<?>>();

		Value<Integer> idValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<Integer> nameValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<Integer> ageValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<Integer> rateValue = new Value<Integer>(new Integer(123405), Integer.class);

		values.put(id, idValue);
		values.put(name, nameValue);
		values.put(age, ageValue);
		values.put(rate, rateValue);

		Record record = new Record(values);

		List<String> ids = new ArrayList<String>();
		
		int numRecords = 10000;
		long time = System.currentTimeMillis();
		
		
		for(long i = 0; i < numRecords; i++){
			 ids.add(store.storeRecord(record));
		}
		
		System.out.println("Took " + (System.currentTimeMillis() - time) + " to store " + numRecords + " records");

		assertTrue(ids.size() == numRecords);
		
		time = System.currentTimeMillis();
		Record recordLookUp = store.lookupRecordByRecordID(ids.get(0));
		System.out.println("Took " + (System.currentTimeMillis() - time) + " to retrieve a item");
		
		assertNotNull(recordLookUp);
		System.out.println("Finished storeRecordTest...");
	}
	
	@Test
	public void storeMultipleRecordsUsingAssyncIndexUpdateTest(){
		System.out.println("\n\nStarting storeMultipleRecordsUsingAssyncIndexUpdateTest...");
		List<Field> list = new ArrayList<Field>();

		Field id = new Field(Integer.class, "id");
		Field name = new Field(Integer.class, "name");
		Field age = new Field(Integer.class, "age");
		Field rate = new Field(Integer.class, "rate");

		list.add(id);
		list.add(name);
		list.add(age);
		list.add(rate);

		Orchestrator server = new Orchestrator();

		DataStore store = null;

		try{	
			store = server.createStore("com.br.person", list);
		}catch(Exception e){
			e.printStackTrace();
		}

		Map<Field, Value<?>> values = new HashMap<Field, Value<?>>();

		Value<Integer> idValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<Integer> nameValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<Integer> ageValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<Integer> rateValue = new Value<Integer>(new Integer(123405), Integer.class);

		values.put(id, idValue);
		values.put(name, nameValue);
		values.put(age, ageValue);
		values.put(rate, rateValue);

		Record record = new Record(values);
		
		int numRecords = 10000;
		List<Record> recordsToInsert = new ArrayList<Record>();
		
		for(long i = 0; i < numRecords; i++){
			 recordsToInsert.add(record);
		}
		
		long time = System.currentTimeMillis();
		
		List<String> keys = store.storeRecord(recordsToInsert);
		
		System.out.println("Took " + (System.currentTimeMillis() - time) + " to store " + numRecords + " records");

		assertTrue(keys.size() == numRecords);
		
		time = System.currentTimeMillis();
		Record recordLookUp = store.lookupRecordByRecordID(keys.get(0));
		System.out.println("Took " + (System.currentTimeMillis() - time) + " to retrieve a item");
		
		assertNotNull(recordLookUp);
		System.out.println("Finished storeMultipleRecordsUsingAssyncIndexUpdateTest...");
	}
	
	@Test
	public void testLookupByRule(){
		System.out.println("\n\nStarting testLookupByRule...");
		List<Field> list = new ArrayList<Field>();

		Field id = new Field(Integer.class, "id");
		Field name = new Field(String.class, "name");
		Field age = new Field(Integer.class, "age");
		Field rate = new Field(Double.class, "rate");

		list.add(id);
		list.add(name);
		list.add(age);
		list.add(rate);

		Orchestrator server = new Orchestrator();

		DataStore store = null;

		try{	
			store = server.createStore("com.br.person", list);
		}catch(Exception e){
			e.printStackTrace();
		}

		Map<Field, Value<?>> values = new HashMap<Field, Value<?>>();

		Value<Integer> idValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<String> nameValue = new Value<String>("Vinicius", String.class);
		Value<Integer> ageValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<Double> rateValue = new Value<Double>(new Double(4.5), Double.class);

		values.put(id, idValue);
		values.put(name, nameValue);
		values.put(age, ageValue);
		values.put(rate, rateValue);
		
		Record record = new Record(values);

		List<String> ids = new ArrayList<String>();
		
		int numRecords = 10000;
		
		for(long i = 0; i < numRecords; i++){
			 ids.add(store.storeRecord(record));
		}
		
		Value<Integer> value = new Value<Integer>(new Integer(123405), Integer.class);
		
		Rule<Integer> rule = new Rule<Integer>(Rule.BooleanRule.EQUALS, value, id.getName());
		
		List<Rule<? extends Comparable<?>>> rules = new ArrayList<Rule<? extends Comparable<?>>>();
		rules.add(rule);
		
		long time = System.currentTimeMillis();	
		Collection<Record> result = store.lookupRecords(rules);
		System.out.println("Took " + (System.currentTimeMillis() - time) + " to retrieve " + result.size() + "items");
		
		System.out.println("Finished testLookupByRule...");
	}
	
	@Test
	public void testLookupByRuleUsingIndexes(){
		System.out.println("\n\nStarting testLookupByRuleUsingIndexes...");
		List<Field> list = new ArrayList<Field>();

		Field id = new Field(Integer.class, "id");
		Field name = new Field(String.class, "name");
		Field age = new Field(Integer.class, "age");
		Field rate = new Field(Double.class, "rate");

		list.add(id);
		list.add(name);
		list.add(age);
		list.add(rate);

		Orchestrator server = new Orchestrator();

		DataStore store = null;

		try{	
			store = server.createStore("com.br.person", list);
		}catch(Exception e){
			e.printStackTrace();
		}

		Map<Field, Value<?>> values = new HashMap<Field, Value<?>>();

		Value<Integer> idValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<String> nameValue = new Value<String>("Vinicius", String.class);
		Value<Integer> ageValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<Double> rateValue = new Value<Double>(new Double(4.5), Double.class);

		values.put(id, idValue);
		values.put(name, nameValue);
		values.put(age, ageValue);
		values.put(rate, rateValue);
		
		Record record = new Record(values);

		List<String> ids = new ArrayList<String>();
		
		int numRecords = 10000;
		
		for(long i = 0; i < numRecords; i++){
			 ids.add(store.storeRecord(record));
		}
		
		Value<Integer> value = new Value<Integer>(new Integer(123405), Integer.class);
		
		Rule<Integer> rule = new Rule<Integer>(Rule.BooleanRule.EQUALS, value, id.getName());
		
		List<Rule<? extends Comparable<?>>> rules = new ArrayList<Rule<? extends Comparable<?>>>();
		rules.add(rule);
		
		long time = System.currentTimeMillis();	
		Collection<Record> result = store.lookupRecordsUsingIndexes(rules);
		System.out.println("Took " + (System.currentTimeMillis() - time) + " to retrieve " + result.size() + "items");
		
		System.out.println("Finished testLookupByRule...");
	}
}