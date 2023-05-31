window.addEventListener('DOMContentLoaded', function() {

  var form = document.querySelector('form');
  form.addEventListener('submit', function(event) {
	event.preventDefault();

    var username = document.getElementById('username').value;
 
  
       if(username.trim() === '') {
		showAlert('Please enter a username');
		return;
	}
	 sessionStorage.setItem('username', username);
    showAlert('Hello , ' + username + '!');
    setTimeout(function() {
		window.location.href = '/channel';
	},5000);

  });
  
  function showAlert(message) {
	  var alertContainer = document.createElement('div');
	  alertContainer.classList.add('alert-container');
	  
	  var alertElement = document.createElement('h2');
	  alertElement.textContent = message;
	  alertElement.classList.add('alert');
	  
	  alertContainer.appendChild(alertElement);
	  document.body.appendChild(alertContainer);
	  
	  setTimeout(function() {
		  alertContainer.style.opacity = '0';
		  setTimeout(function() { alertContainer.parentNode.removeChild(alertContainer);
		   },1000);
		  
	  },5500);
  }
});