package br.com.activedb.messaging;

import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.Message;

public interface MessageReceiver{
	public void createChannel() throws ChannelException;
	public void sendMessage(Message message, String topic) throws ChannelNotConnectedException, ChannelClosedException;
}
