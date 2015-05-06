'use strict'

var restaurantId = getParameterByName("restaurant");

if(restaurantId){
	var TapEatFireBase = new Firebase("https://tapeat.firebaseio.com/");

	TapEatFireBase.child("reservations/" + restaurantId).on('value', function(snapshot) {
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

		$("#index-banner").hide();
		$("#tapeat-intro").hide();
		$("#tapeat-content").hide();
		$("#tapeat-contact").hide();
		$("#tapeat-reservation-table tbody").replaceWith(tableValue);
		$("#tapeat-reservation-table").show();
		console.log(tableValue);
	});
}else{
	$("#tapeat-reservation-table").hide();
}



function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}