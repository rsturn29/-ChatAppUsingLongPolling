window.addEventListener('DOMContentLoaded', function() {

var username = sessionStorage.getItem('username');
var channelId = window.location.pathname.split('/').pop();

if(username){
	var welcomeMessage = document.querySelector('h2');
	welcomeMessage.textContent = 'Welcome,  ' + username;
	
	var usernameInput = document.querySelector('input[name="username"]');
	usernameInput.value = username;
}
var messageBox = document.querySelector('.messageBox');


setInterval(getMessages, 2000);
getMessages();

messageBox.addEventListener('submit', function(e){
	e.preventDefault();
		var message = {
			"content": messageBox.value,
			"channelId": channelId,
			"username":username,
			"timestamp": new Date()
			
		};
		var messageText = messageBox.value;
		console.log('Send message' + messageText);
		
		messageBox.value = '';
		
		fetch('/messages',{
			method: 'POST',
			headers: {
				'Content-Type' : 'application/json; charset=utf-8'
			}, 
			body: JSON.stringify(message)
		}).then(function(response){
			getMessages();
		});
		return false;
	}
);
function getMessages(){
	var messageContainer = document.querySelector('.message-container');
	fetch ('/messages/' + channelId)
	.then(function(response){
		return response.json();
	})
	.then(function(messages){
		messageContainer.innerHTML = ''; 
		messages.forEach(message =>{
			messageContainer.innerHTML += '<div>' + '<span class="username"> ' + message.username  
			   +  ' </span> '+ ' : ' + ' <span class="message"> ' +  message.content  + '</span>' + 
			 '<span class="timestamp"> ' +' sent :' + message.timestamp + '</span>' + 
			 '</div>'
		});
	}).catch(function(error){
		console.log('Error: ' + error.message)
	});
}

setInterval(getMessages, 2000);
});


  