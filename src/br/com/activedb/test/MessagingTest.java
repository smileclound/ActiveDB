package br.com.activedb.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.jgroups.Message;
import org.junit.Test;
import br.com.activedb.messaging.ActiveDBChannel;
import static org.junit.Assert.*;

public class MessagingTest {

	@Test
	public void testSendMessageOnTopic(){
		List<String> topics = new ArrayList<String>();
		topics.add("com.activeDB.testTopic");

		File path = new File(".//br//com//activedb//messaging//configuration//BackChannel.xml"); 

		assert(path.exists());

		ActiveDBChannel channel = new ActiveDBChannel(topics, path, "BackChannel");

		try{
			channel.createChannel();
		}catch(Exception e){
			e.printStackTrace();
			assertTrue("Fail to create channel", false);
		}

		Message message = new Message();
		message.setObject(new String("messaging test"));

		try{
			channel.sendMessage(message, topics.get(0));
		}catch(Exception e){
			e.printStackTrace();
			assertTrue("Fail to send message", false);
		}
	}

	@Test
	public void testSendMessageBetweenChannels(){

		List<String> topics = new ArrayList<String>();
		topics.add("com.activeDB.testTopic");

		File path = new File(".//br//com//activedb//messaging//configuration//BackChannel.xml"); 

		assert(path.exists());

		ActiveDBChannel channel = new ActiveDBChannel(topics, path, "BackChannel");
		ActiveDBChannel channel2 = new ActiveDBChannel(topics, path, "BackChannel2");

		try{
			channel.createChannel();
			channel2.createChannel();
		}catch(Exception e){
			e.printStackTrace();
			assertTrue("Fail to create channel", false);
		}

		Message message = new Message();
		message.setObject(new String("From backChannel1 to 2 : messaging test"));

		try{
			channel.sendMessage(message, topics.get(0));
		}catch(Exception e){
			e.printStackTrace();
			assertTrue("Fail to send message", false);
		}
		
		Message message2 = new Message();
		message2.setObject(new String("From backChannel2 to 1 : messaging test"));

		try{
			channel2.sendMessage(message2, topics.get(0));
		}catch(Exception e){
			e.printStackTrace();
			assertTrue("Fail to send message", false);
		}

	}
}
