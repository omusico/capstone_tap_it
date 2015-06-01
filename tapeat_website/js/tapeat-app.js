function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}


function logError(ref, error){
	ref.child("facebook_login_error").child(error.code).push({
		message: error.message,
		time: Date.now()
	});
}


function afterUserFirstTimeLogin(isRestaurant, authData){
	var userRef = ref.child("users" + "/" + authData.facebook.id);
	var userData = {
			signInTime: Date.now(),
			userName: authData.facebook.displayName,
			userEmail: authData.facebook.email || null,
			userRole: isRestaurant ? "restaurant" : "customer",
			userProfile: authData.facebook.cachedUserProfile,
			facebookAccessToken: authData.facebook.accessToken
		};
	userRef.update(userData);
}


function showTable(tableValue){
	$("#index-banner").hide();
	$("#tapeat-intro").hide();
	$("#tapeat-content").hide();
	$("#tapeat-contact").hide();
	$("#tapeat-reservation-table tbody").replaceWith(tableValue);
	$("#tapeat-reservation-table").show();
}

function hideTable(){
	$("#index-banner").show();
	$("#tapeat-intro").show();
	$("#tapeat-content").show();
	$("#tapeat-contact").show();
	$("#tapeat-reservation-table tbody").replaceWith("");
	$("#tapeat-reservation-table").hide();
}


function showReservationWithoutModify(restaurantId, turnOff){
	//TODO: delete this after change all id to facebook id
	restaurantId = "dtfSeattleUniversityVillage1"
	var restaurantReservation = ref.child("reservations/" + restaurantId);

	if(turnOff){
		restaurantReservation.off('value', hideTable());
		return;
	}

	if(restaurantId){
		restaurantReservation.on('value', function(snapshot) {
			var tableValue = '<tbody>';
			var index = 0;
			$.each(snapshot.val(), function(key, reservation){
				index++;
				tableValue += '<tr>';
				tableValue += '<td>' + index + '</td>';
				tableValue += '<td>' + reservation.customerName + '</td>';
				tableValue += '<td>' + reservation.partySize + '</td>';
				tableValue += '</tr>';
			});
			tableValue += '</tbody>';
			showTable(tableValue);
		});
	}
}



// Create a callback which logs the current auth state
function authDataCallback(authData) {
  var userInfo = $('.user-info');
  var loginLink = $('.login-link');
  var logoutLink = $('.logout-link');


  userInfo.hide();
  loginLink.hide();
  logoutLink.hide();
  if (authData) {
  	userInfo.children().first().html(authData.facebook.displayName);
  	userInfo.show();
  	logoutLink.show();
  	logoutLink.on("click", function(){
  		ref.unauth();
  	});
  	$('#login-popup').closeModal();


  	var userRole = ref.child("users" + "/" + authData.facebook.id + "/userRole");
	userRole.once("value", function(snap) {
	  if(snap.val()){
	  	var userRoleValue = snap.val();
	  	if(userRoleValue === "restaurant"){
  			showReservationWithoutModify(authData.facebook.id);
	  	}
	  }
	});

  } else {
  	loginLink.show();

  	showReservationWithoutModify(null, true);

  	var isRestaurant = getParameterByName("isRestaurant");
  	var buttonId = isRestaurant ? "#business-login" : "#customer-login";
  	$(buttonId).on("click", function(){
		ref.authWithOAuthPopup("facebook", function(error, authData) {
		  if (error) {
		    if (error.code === "TRANSPORT_UNAVAILABLE") {
		      // fall-back to browser redirects, and pick up the session
		      // automatically when we come back to the origin page
		      ref.authWithOAuthRedirect("facebook", function(error) {
		      	if(error){
					logError(ref, error);
		      	}
		   	  });
		    }else{
		    	logError(ref, error);
		    }
		  } else{
		  	afterUserFirstTimeLogin(isRestaurant, authData);
		  }
		});
	});
  }
}



var ref;

$(document).ready(function(){
	$(".modal-trigger").leanModal();
	
	ref = new Firebase("https://tapeat.firebaseio.com/");
	ref.onAuth(authDataCallback);
});

