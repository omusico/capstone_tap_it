function sendSMS(event) {
	var phone = event.data.phone;
	var message = "TapEat: Your table is ready!\nhttp://tapeat.herokuapp.com/";

	var data = {
		"dst" : phone,
		"msg" : message
	};

	console.log(phone);

	var request = $.ajax({
		type: "POST",
		url: "/SMS",
		crossDomain: true,
		data: JSON.stringify(data),
		contentType: "application/json"
	});

	request.done(function(data) {
		console.log(data);
		// Flip the card: back, then forth after delay
		var card = $(event.target).closest(".waitline-card");
		$(card).find('#card_text').html('Message has been sent!');
		card.hasClass('flipped') ? card.removeClass("flipped") : card.addClass("flipped");

		setTimeout(function() {
			card.hasClass('flipped') ? card.removeClass("flipped") : card.addClass("flipped");
		}, 3000);
	});

	request.fail(function(jqXHR, textStatus) {
		console.log("Request failed: " + textStatus);
	});
}



function removeCustomer(e){
	  var card = $(e.target).closest(".waitline-card");
	  var firebaseRemove = ref.child('reservations/' + restaurantId + '/'+ card.attr('id'));
	  $(card).find('#card_text').html('Successfully Canceled');
	  
	  card.hasClass('flipped') ? card.removeClass("flipped") : card.addClass("flipped");

	  $(card).fadeOut(1000,'easeInCirc', function(){
	    firebaseRemove.remove();
	  });
}

function genreateReservationCard (reservations) {
      var waiterCard = $('#tapeat-reservation-card-for-waiter');
      waiterCard.html('');
      for (var reservation in reservations){
        // Card Components
        var cardComponent = '<div class="col s12 m6 waitline-card effect_click"> <div class="card white"> <div class="card-content card_front"> <div class="left-panel col s2 m2 center-align"> <img id="profile_picture"class="profile"/> <p>Party Size: <span id="party_size">2</span></p> </div> <div class="right-panel col s10 m10"> <div class="row"> <div class="col s12 m12"> <p id="customer_name">Customer</p> </div> </div> <div class="card-action"> <div class="col s3 m3"> <a data-option="confirm"><i class="custome mdi-navigation-check"></i></a> </div> <div class="col s3 m3"><a data-option="text"><i class="custome mdi-action-question-answer"></i></a> </div> <div class="col s3 m3"><a data-option="call"><i class="custome mdi-notification-phone-in-talk"></i></a> </div> <div class="col s3 m3"><a class="cancel" data-option="cancel"><i class="custome mdi-navigation-close"></i></a></div> </div> </div> </div> <div class="card_back"> <span id="card_text">back</span> </div> </div> </div>';
        var customerCard = $(cardComponent);
        var specificReservation = reservations[reservation];	
        customerCard.first().attr('id', reservation);
        customerCard.find('#party_size').html(specificReservation.partySize);
        customerCard.find('#profile_picture').attr('data-name',specificReservation.customerName);
        customerCard.find('#customer_name').html(specificReservation.customerName);
        customerCard.find("[data-option='call']").attr("href", "tel:+" + specificReservation.customerPhone);
        customerCard.find("a[data-option='cancel']").click(removeCustomer);
        customerCard.find("a[data-option='text']").click({"phone": specificReservation.customerPhone}, sendSMS);
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


function getUserRole(){
	var isRestaurant = getParameterByName("isRestaurant");
	return isRestaurant ? "restaurant" : "customer";
}

function afterUserFirstTimeLogin(authData){
	var userRef = ref.child("users" + "/" + authData.facebook.id);
	var userData = {
			signInTime: Date.now(),
			userName: authData.facebook.displayName,
			userEmail: authData.facebook.email || null,
			userRole: getUserRole(),
			userProfile: authData.facebook.cachedUserProfile,
			facebookAccessToken: authData.facebook.accessToken
		};
	userRef.update(userData);
}

function hideAll(){
	$("#tapeat-intro-section").hide();
	$("#tapeat-reservation-card-for-waiter").hide();
	$("#tapeat-reservation-table").hide();
	$("#tapeat-add-customer-button").hide();
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

function showAddBtn(){
	$("#tapeat-add-customer-button").hide();
}


function displayReservations(userRoleValue, turnOff){

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
			showAddBtn();
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
		  	displayReservations(userRoleValue, false);
		  }else{
		  	afterUserFirstTimeLogin( authData);
		  	var userRoleValue = getUserRole();
		  	displayReservations(userRoleValue, false);
		  }
	});

  } else {
  	loginLink.show();

  	displayReservations(null, true);

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
var restaurantId;
$(document).ready(function(){
	restaurantId = "dtfSeattleUniversityVillage1"
	ref = new Firebase("https://tapeat.firebaseio.com/");
	ref.onAuth(authDataCallback);
	$(".modal-trigger").leanModal();
});

