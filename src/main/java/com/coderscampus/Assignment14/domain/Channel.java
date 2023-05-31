package com.coderscampus.Assignment14.domain;

import java.util.ArrayList;
import java.util.List;

public class Channel {

	private int channelId;
	private String name;
	private List<User> users;
	private List<ChatMessage> messages;

	public Channel(int channelId, String name) {
		super();
		this.channelId = channelId;
		this.name = name;
		this.users = new ArrayList<User>();
		this.messages = new ArrayList<ChatMessage>();
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		users.remove(user);
	}

	public User getUserByName(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public List<ChatMessage> getMessages() {
		for(ChatMessage message : messages) {
			message.setUsername(message.getUser().getUsername());
		}
		return messages;
	}

	public void setMessages(List<ChatMessage> messages) {
		this.messages = messages;
	}

	public void addMessage(ChatMessage message) {
		message.setChannelId(channelId);
		messages.add(message);

	}

}
