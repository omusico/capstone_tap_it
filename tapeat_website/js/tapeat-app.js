$(document).ready(function(){
	$(".modal-trigger").leanModal();

	$("#business-login").on("click", function(){
		var ref = new Firebase("https://tapeat.firebaseio.com/");
		ref.authWithOAuthPopup("facebook", function(error, authData) {
		  if (error) {
		    console.log("Login Failed!", error);
		    alert(error.toString());
		  } else {
		    console.log("Authenticated successfully with payload:", authData);
		    alert(authData.toString());
		  }
		});
	});
});