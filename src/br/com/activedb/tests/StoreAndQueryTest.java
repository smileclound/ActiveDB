package br.com.activedb.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import br.com.activedb.core.*;
import static org.junit.Assert.*;

public class StoreAndQueryTest {

	@Test
	public void storeRecordTest(){
		List<Field> list = new ArrayList<Field>();

		Field id = new Field(Integer.class);
		Field name = new Field(String.class);
		Field age = new Field(Integer.class);
		Field rate = new Field(Double.class);

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

		String generateID = store.storeRecord(record);
		
		assertNotNull(generateID);
		assertNotNull(store.lookupRecordByRecordID(generateID));
	}
}
