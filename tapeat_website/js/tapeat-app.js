function genreateReservationCard (reservations) {
      var waiterCard = $('#tapeat-reservation-card-for-waiter');
      waiterCard.html('');
      for (var reservation in reservations){
        // Card Components
        var cardComponent = '<div class="col s12 m6 waitline-card"> <div class="card white"> <div class="card-content"> <div class="left-panel col s2 m2 center-align"> <img id="profile_picture"class="profile"/> <p>Party Size: <span>2</span></p> </div> <div class="right-panel col s10 m10"> <div class="row"> <div class="col s12 m12"> <p id="customer_name">Customer</p> </div> </div> <div class="card-action"> <div class="col s3 m3"> <a data-option="confirm"><i class="custome mdi-navigation-check"></i></a> </div> <div class="col s3 m3"><a data-option="text"><i class="custome mdi-action-question-answer"></i></a> </div> <div class="col s3 m3"><a data-option="call"><i class="custome mdi-notification-phone-in-talk"></i></a> </div> <div class="col s3 m3"><a data-option="cancel"><i class="custome mdi-navigation-close"></i></a></div> </div> </div> </div>';
        var customerCard = $(cardComponent);
        var specificReservation = reservations[reservation];
        customerCard.first().attr('id', reservation);
        customerCard.find('#profile_picture').attr('data-name',specificReservation.customerName);
        customerCard.find('#customer_name').html(specificReservation.customerName);
        customerCard.find("[data-option='call']").attr("href", "tel:+" + specificReservation.customerPhone);
        waiterCard.append(customerCard);
      };
      $('.profile').initial();
}

function genreateReservationTable(reservations){
	var tableValue = '<tbody>';
	var index = 1;
	$.each(reservations, function(key, reservation){
		tableValue += '<tr>';
		tableValue += '<td>' + index + '</td>';
		tableValue += '<td>' + reservation.customerName + '</td>';
		tableValue += '<td>' + reservation.partySize + '</td>';
		tableValue += '</tr>';
		index++;
	});
	tableValue += '</tbody>';
	$("#tapeat-reservation-table tbody").replaceWith(tableValue);
}



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


function getUserRole(isRestaurant){
	return isRestaurant ? "restaurant" : "customer";
}

function afterUserFirstTimeLogin(isRestaurant, authData){
	var userRef = ref.child("users" + "/" + authData.facebook.id);
	var userData = {
			signInTime: Date.now(),
			userName: authData.facebook.displayName,
			userEmail: authData.facebook.email || null,
			userRole: getUserRole(isRestaurant),
			userProfile: authData.facebook.cachedUserProfile,
			facebookAccessToken: authData.facebook.accessToken
		};
	userRef.update(userData);
}

function hideAll(){
	$("#tapeat-intro-section").hide();
	$("#tapeat-reservation-card-for-waiter").hide();
	$("#tapeat-reservation-table").hide();
}


function showTable(){
	$("#tapeat-reservation-table").show();
}

function showWaiterCard(){
	$("#tapeat-reservation-card-for-waiter").show();
}

function showIntro(){
	$("#tapeat-intro-section").show();
}


function displayReservations(restaurantId, userRoleValue, turnOff){
	//TODO: delete this after change all id to facebook buttonId
	restaurantId = "dtfSeattleUniversityVillage1"

	var restaurantReservation = ref.child("reservations/" + restaurantId);

	if(turnOff){
		hideAll();
		restaurantReservation.off('value', showIntro());
		return;
	}
	restaurantReservation.on('value', function(snapshot) {
		hideAll();
		var reservations = snapshot.val();
		if(userRoleValue === "restaurant"){
			genreateReservationCard(reservations);
			showWaiterCard();
		}else if(userRoleValue === "customer"){
			genreateReservationTable(reservations);
			showTable();
		}else{
			showIntro();
		}
	});
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
  	userInfo.children().map(function(index, element){
  		$(element).html(authData.facebook.displayName);
  	});

  	userInfo.show();
  	logoutLink.show();
  	logoutLink.on("click", function(){
  		ref.unauth();
  	});

  	var userRole = ref.child("users" + "/" + authData.facebook.id + "/userRole");
	userRole.once("value", function(snap) {
		var userRoleValue = snap.val();
		  if(userRoleValue){
		  	displayReservations(authData.facebook.id, userRoleValue, false);
		  }else{
		  	var isRestaurant = getParameterByName("isRestaurant");
		  	afterUserFirstTimeLogin(isRestaurant, authData);
		  	var userRoleValue = getUserRole(isRestaurant);
		  	displayReservations(authData.facebook.id, userRoleValue, false);
		  }
	});

  } else {
  	loginLink.show();

  	displayReservations(null, null, true);

  	$(".login-link").on("click", function(){
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
		  }
		}, {
		  scope: "email, public_profile, user_friends" // the permissions requested
		});
	});
  }
}



var ref;

$(document).ready(function(){

	ref = new Firebase("https://tapeat.firebaseio.com/");
	ref.onAuth(authDataCallback);
});

