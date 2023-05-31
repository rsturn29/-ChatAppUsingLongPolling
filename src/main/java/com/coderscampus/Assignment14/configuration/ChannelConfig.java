package com.coderscampus.Assignment14.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.coderscampus.Assignment14.domain.Channel;

import jakarta.annotation.PostConstruct;

@Configuration
public class ChannelConfig {
	private List<Channel> channels;

	@PostConstruct
	public void initChannels() {
		channels = Arrays.asList(
				new Channel( 1, "General"),
				new Channel( 2, "Basics"),
				new Channel( 3, "Advanced")
				);

	}

	public List<Channel> getChannels() {
		return channels;
	}

	public Channel getChannelById(int channelId) {
		for (Channel channel : channels) {
			if(channel.getChannelId()== channelId) {
				return channel;
			}
		}
		return null;
	
		}
	}

