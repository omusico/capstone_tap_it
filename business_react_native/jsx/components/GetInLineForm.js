/* @flow */
'use strict';

var React = require('react-native');
var t = require('tcomb-form-native');
var Form = t.form.Form;
var Firebase = require('firebase-react-native');



var {
  StyleSheet, 
  Text, 
  View, 
  TouchableHighlight
} = React;


// here we are: define your domain model
var Person = t.struct({
  firstName: t.Str,              // a required string
  lastName: t.Str,
  howManyPeople: t.Num,  			// an optional string using t.maybe(t.str)
  phone: t.Num,               // a required number
  preference: t.maybe(t.Str)
});

var options = {}; // optional rendering options (see documentation)


var GetInLineForm = React.createClass({

	statics: {
        restaurantId: "dtfSeattleUniversityVillage1" //this value can be filled by parse.
    },

  onPress: function () {

  	var restaurantId = "dtfSeattleUniversityVillage1";  //this value can be filled by parse.
    var value = this.refs.form.getValue();
    if (value) { // if validation fails, value will be null
      console.log(value); // value here is an instance of Person
  		var TapEatFireBase = new Firebase("https://tapeat.firebaseio.com/");
  		TapEatFireBase.child("reservations/" + restaurantId).push({
  			customerName: value.firstName + " " + value.lastName,
 			customerPhone: value.phone,
			customerUsername: value.firstName + value.lastName,
			partySize: value.howManyPeople,
			peference: value.preference,
			restaurantName: restaurantId
  		});
    }
  },

  render: function() {
    return (
      <View style={styles.container}>
        {/* display */}
        <Form
          ref="form"
          type={Person}
          options={options}
        />
        <TouchableHighlight style={styles.button} onPress={this.onPress} underlayColor='#99d9f4'>
          <Text style={styles.buttonText}>Save</Text>
        </TouchableHighlight>
      </View>
    );
  }
});


var styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    padding: 20,
    backgroundColor: '#ffffff',
  },
  title: {
    fontSize: 30,
    alignSelf: 'center',
    marginBottom: 30
  },
  buttonText: {
    fontSize: 18,
    color: 'white',
    alignSelf: 'center'
  },
  button: {
    height: 36,
    backgroundColor: '#48BBEC',
    borderColor: '#48BBEC',
    borderWidth: 1,
    borderRadius: 8,
    marginBottom: 10,
    alignSelf: 'stretch',
    justifyContent: 'center'
  }
});

module.exports = GetInLineForm;
