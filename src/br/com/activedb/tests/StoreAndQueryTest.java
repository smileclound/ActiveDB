package br.com.activedb.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import br.com.activedb.core.*;
import static org.junit.Assert.*;

public class StoreAndQueryTest {

	@Test
	public void storeRecordTest(){
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

		ArrayList<Value<?>> values = new ArrayList<Value<?>>();

		Value<Integer> idValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<Integer> nameValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<Integer> ageValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<Integer> rateValue = new Value<Integer>(new Integer(123405), Integer.class);

		values.add(idValue);
		values.add(nameValue);
		values.add(ageValue);
		values.add(rateValue);

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
	}
	
	@Test
	public void testLookupByRule(){
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

		ArrayList<Value<?>> values = new ArrayList<Value<?>>();

		Value<Integer> idValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<String> nameValue = new Value<String>("Vinicius", String.class);
		Value<Integer> ageValue = new Value<Integer>(new Integer(123405), Integer.class);
		Value<Double> rateValue = new Value<Double>(new Double(4.5), Double.class);

		values.add(idValue);
		values.add(nameValue);
		values.add(ageValue);
		values.add(rateValue);

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
		
		
	}
}
