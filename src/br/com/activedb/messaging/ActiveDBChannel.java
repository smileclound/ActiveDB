package br.com.activedb.messaging;

import java.io.File;
import java.util.List;

import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.ExtendedReceiverAdapter;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.View;

public class ActiveDBChannel extends ExtendedReceiverAdapter implements MessageReceiver {
	private JChannel channel;
	private List<String> topics;
	private File configurationPath;
	private String channelName;

	public ActiveDBChannel(List<String> topics, File configurationPath, String name){
		this.topics = topics;
		this.configurationPath = configurationPath;
		this.channelName = name;
	}

	@Override
	public void createChannel() throws ChannelException {
		if(topics == null){
			throw new RuntimeException("Topic for ActiveDBChannel is null.");
		}else if(configurationPath == null){
			throw new RuntimeException("No configuration file defined for ActiveDBChannel.");
		}
		
		channel = new JChannel(configurationPath.getPath());
		channel.setName(channelName);
		channel.setReceiver(this);
		this.joinTopics();
	}
	
	public void joinTopics() throws ChannelException{
		for(String topic : topics){
			channel.connect(topic);
		}
	}

	@Override
	public void sendMessage(Message message, String topic) throws ChannelNotConnectedException, ChannelClosedException {
		System.out.println(">>>Sending JGroups Message: '" + message.getObject() + "' on channel: '" + this.channelName + "' topic:'" + topic + "'");
		channel.send(message);
	}
	
	@Override
	public void receive(Message msg) {
		if(msg.getSrc() == channel.getAddress()){
			System.out.println("Ignoring message from address: Message was sent from this Node!");
			//return;
		}
		System.out.println(">>>Receiving JGroups Message:" + msg.getObject() + " on channel:" + this.channelName);
		System.out.println(msg.getObject());
	}
	
	@Override
	public void viewAccepted(View new_view) {
		System.out.println("New View accepted>>" + new_view);
	}

}
