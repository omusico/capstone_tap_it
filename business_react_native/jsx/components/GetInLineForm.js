/* @flow */
'use strict';

var React = require('react-native');
var t = require('tcomb-form-native');
var Form = t.form.Form;
var Firebase = require('firebase-react-native');
var KeyboardEvents = require('react-native-keyboardevents');
var KeyboardEventEmitter = KeyboardEvents.Emitter;


var {
  StyleSheet, 
  Text, 
  View, 
  TouchableHighlight
} = React;


// here we are: define your domain model
var Person = t.struct({
  fullName: t.Str,              // a required string
  howManyPeople: t.Num,  			// an optional string using t.maybe(t.str)
  phone: t.Num,               // a required number
});

var options = {
  auto: 'placeholders'
}; // optional rendering options (see documentation)


var GetInLineForm = React.createClass({

    getInitialState: function() {
        //Initialzie firebase, open up websocket
        var that = this;
        var restaurantId = "dtfSeattleUniversityVillage1";
        var TapEatFireBase = new Firebase("https://tapeat.firebaseio.com/");
        TapEatFireBase.child("reservations/" + restaurantId).on('value', function(snapshot) {
            that._processingReservations(snapshot);
        });

        KeyboardEventEmitter.on(KeyboardEvents.KeyboardDidShowEvent, (frames) => {
	      this.setState({keyboardSpace: frames.end.height});
	    });
	    KeyboardEventEmitter.on(KeyboardEvents.KeyboardWillHideEvent, (frames) => {
	      this.setState({keyboardSpace: 0});
	    });

        //genreate datasource
        return {
        	keyboardSpace: 0,
          	data: []
        };
    },

    _processingReservations: function(snapshot: object){
        var tempData = [];
        if(snapshot){
          // var dataSource = new ListView.DataSource({ rowHasChanged: (row1, row2) => row1 !== row2 });
          var reservations = snapshot.val();
          for(var key in reservations){
            tempData.push(reservations[key]);  
          }
          this.setState({data: tempData});
        }
    },

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
  			createTime: Date.now(),
  			customerName: value.fullName,
 			customerPhone: value.phone,
			customerUsername: value.fullName,
			partySize: value.howManyPeople,
			restaurantName: restaurantId
  		});
    }
  },

  render: function() {
    return (
      <View style={styles.container}>

        <View style={styles.circle}>
          <Text style={styles.waitTime}>
            <Text style={styles.minute}>{this.state.data.length}</Text> minutes
          </Text>
          <Text style={styles.waitTime}>
            Estimated Wait Time
          </Text>
        </View>

        <View style={styles.formWrapper}>
          <Form
            ref="form"
            type={Person}
            options={options}
            style={styles.form}
          />
        </View>
        <TouchableHighlight style={styles.button} onPress={this.onPress} underlayColor='#99d9f4'>
          <Text style={styles.buttonText}>Get in line</Text>
        </TouchableHighlight>
        <View style={{height: this.state.keyboardSpace}}></View>
      </View>
    );
  }
});


var styles = StyleSheet.create({
  minute: {
    fontSize: 25
  },
  formWrapper: {
    marginLeft: 20,
    marginRight: 20
  },
  waitTime: {
    alignSelf: 'center',
    justifyContent: 'center',
    color: '#15D3A4'
  },
  circle: {
    marginTop: 20,
    marginBottom: 20,
    borderRadius: 100,
    borderWidth: 5,
    borderColor: '#15D3A4',
    width: 175,
    height: 175,
    alignSelf: 'center',
    justifyContent: 'center'
  },
  container: {
    flex: 0.93,
    backgroundColor: '#F8F8F8'
  },
  title: {
    fontSize: 30,
    alignSelf: 'center',
    marginBottom: 30
  },
  buttonText: {
    fontSize: 18,
    color: 'white',
    alignSelf: 'center',
  },
  button: {
    height: 36,
    borderRadius: 8,
    marginBottom: 10,
    alignSelf: 'stretch',
    justifyContent: 'center',
    backgroundColor: '#15D3A4',
    marginRight: 20,
    marginLeft: 20
  }
});

module.exports = GetInLineForm;
