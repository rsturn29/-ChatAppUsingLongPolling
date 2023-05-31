package com.coderscampus.Assignment14.web;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coderscampus.Assignment14.configuration.ChannelConfig;
import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.ChatMessage;
import com.coderscampus.Assignment14.domain.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChannelController {

	@Autowired
	private ChannelConfig channelConfig;

	@GetMapping("/")
	public String getWelcome() {
		return "welcome";
	}

	@PostMapping("/welcome")
	public String getWelcome(ModelMap model, HttpSession session) {
		String username = (String) session.getAttribute("username");
		model.addAttribute("username", username);
		return "welcome";
	}

	@GetMapping("/channel")
	public String getChannels(ModelMap model, HttpSession session) {

		String username = (String) session.getAttribute("username");
		List<Channel> channels = channelConfig.getChannels();

		model.addAttribute("channels", channels);
		model.addAttribute("username", username);
		return "channel";
	}

	@PostMapping("/channel")
	public String joinChat(@RequestParam("username") String username, HttpSession session) {
		session.setAttribute("username", username);

		return "redirect:/channel";
	}

	@GetMapping("/channel/{id}")
	public String joinChannel(@PathVariable("id") int id, @RequestParam("username") String username,
			@RequestParam(value = "message", required = false) String content, HttpSession session, ModelMap model) {
		User user = new User();
		user.setUsername(username);
		session.setAttribute("user", user);
		session.setAttribute("username", username);

		Channel channel = channelConfig.getChannelById(id);
		channel.addUser(user);
		session.setAttribute("channel", channel);

		if (content != null && !content.isEmpty()) {
			ChatMessage message = new ChatMessage();
			message.setContent(content);
			message.setTimestamp(LocalDateTime.now());
			message.setUser(user);
			message.setUsername(username);
			message.setChannelId(channel.getChannelId());
			channel.addMessage(message);
		}
		
		model.addAttribute("user", user);
		model.addAttribute("channel", channel);
		model.addAttribute("messages", channel.getMessages());

		return "channel";
	}

	@PostMapping("/channel/{id}")
	public String postMessage(@PathVariable("id") String channelId, @RequestParam("message") String message,
		 @RequestParam("username") String username,
			HttpSession session) {

		String storedUsername = (String) session.getAttribute("username");
		Channel channel = (Channel) session.getAttribute("channel");
		if(storedUsername == null) {
			return "redirect:/welcome";
		}
		
		User user = new User();
		user.setUsername(storedUsername);
		if (channel == null) {
			return "redirect:/";
		}
		ChatMessage messages = new ChatMessage();
		messages.setContent(message);
		messages.setTimestamp(LocalDateTime.now());
		messages.setUser(user);
		messages.setChannelId(Integer.parseInt(channelId));
		messages.setUsername(username);

		channel.addMessage(messages);
		channel.addUser(user);
		
		return "redirect:/channel/" + channelId;
	}
	@GetMapping("/messages/{channelId}")
	@ResponseBody
	public List<ChatMessage> getMessages(@PathVariable("channelId") String channelId) {
	    
	    
	 if(channelId == null || channelId.equals("null")) {
		 return Collections.emptyList();
	 }
	 int channelIdChange;
	 try {
		  channelIdChange = Integer.parseInt(channelId);
	 }catch (NumberFormatException e) {
		 return Collections.emptyList();
	 }
	 Channel channel = channelConfig.getChannelById(channelIdChange);
	 if(channel == null) {
		 return Collections.emptyList();
	 }
	    List<ChatMessage> messages = channel.getMessages();
	    
	    return messages;
	}

}
