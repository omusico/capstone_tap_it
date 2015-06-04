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
  TextInput,
  SliderIOS, 
  View, 
  AlertIOS,
  TouchableHighlight
} = React;


// here we are: define your domain model
var Person = t.struct({
  fullName: t.Str,              // a required string
  howManyPeople: t.Num,  			// an optional string using t.maybe(t.str)
  phone: t.Num,               // a required number
});

var options = {
  fields: {
    fullName: {
      keyboardType: 'default'
    },
    howManyPeople: {
      keyboardType: 'numeric',
    },
    phone: {
      keyboardType: 'phone-pad',
    }

  },
    auto: 'placeholders'


}; // optional rendering options (see documentation)

  // auto: 'placeholders'

var GetInLineForm = React.createClass({

    getInitialState: function() {
        //Initialzie firebase, open up websocket


        //genreate datasource
        return {
        	keyboardSpace: 0,
          	data: [],
            name:'',
            phone:'',
            partySize:'',
            nameValid: true,
            phoneValid: true,
            partySizeValid:true
        };
    },
    componentDidMount: function() {
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
    console.log(this.state);
    console.log(this.isValidName(this.state.name));

    if(this.isValidName(this.state.name)){
      this.setState({
        nameValid: true
      })
    }else{
      this.setState({
        nameValid: false
      })
    }

    if(this.isValidPhone(this.state.phone)){
      this.setState({
        phoneValid: true
      })
    }else{
      this.setState({
        phoneValid: false
      })
    }

    if(this.isValidPartySize(this.state.partySize)){
      this.setState({
        partySizeValid: true
      })
    }else{
      this.setState({
        partySizeValid: false
      })
    }


  	var restaurantId = "dtfSeattleUniversityVillage1";  //this value can be filled by parse.
    if (this.isValidName(this.state.name)&&this.isValidPhone(this.state.phone)&&this.isValidPartySize(this.state.partySize)) { // if validation fails, value will be null
  		var TapEatFireBase = new Firebase("https://tapeat.firebaseio.com/");
  		TapEatFireBase.child("reservations/" + restaurantId).push({
  			createTime: Date.now(),
  			customerName: this.state.name,
 			customerPhone: this.state.phone,
			customerUsername: this.state.name,
			partySize: this.state.partySize,
			restaurantName: restaurantId
  		});

      var succeedMessage = 'Checked in name: '+ this.state.name + "\n" +
            'Party size: ' + this.state.partySize + "\n" + 
            'We will contact you at ' + this.state.phone;

      console.log(succeedMessage);

      AlertIOS.alert(
            'Check in succeed!!',
            succeedMessage
          )
      var request = new XMLHttpRequest();
      request.setRequestHeader("Content-type","application/json");

      request.onreadystatechange = (e) => {
        if (request.readyState !== 4) {
          return;
        }

        if (request.status === 200) {
          console.log('success', request.responseText);
          this.setState({
            name: '',
            phone: '',
            partySize: ''
          })
        } else {
          console.warn('error');
        }
      };
      var data = {
        "dst": this.state.phone,
        "msg": 'Thank you for checking in! Check your wait time at https://tapeat.herokuapp.com/ We will text you again when the table is ready'
      }
      console.log(data);
      request.open('POST', 'https://tapeat.herokuapp.com/SMS');
      request.send(JSON.stringify(data));


    }
  },

  isValidName: function(name) {
    if(!name || name.length == 0 || name.length > 20){
      return false;
    }else{
      return true;
    }
  },

  isValidPartySize: function(partySize) {
    var size = parseInt(partySize);
    if(isNaN(size) || size <=0 || size>=16){
      return false;
    }else{
      return true;
    }


  },

  isValidPhone: function(phone) {
    if(!phone.match(/\d/g)){
      return false;
    }else{
      if(phone.match(/\d/g).length===10){
        return true;
      }else{
        return false;
      }
    }

  },

  focusPhone: function(){
    this.refs.phone.focus();
  },
  focusPartySize: function() {
    this.refs.partySize.focus();
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
          <TextInput returnKeyType='next' onSubmitEditing={this.focusPhone} value={this.state.name} placeholder='name' style={this.state.nameValid?styles.inputText:styles.inputTextError} onChangeText={(text) => this.setState({name: text})} />
          <TextInput returnKeyType='next' value={this.state.phone} onSubmitEditing={this.focusPartySize} ref='phone' placeholder='phone' keyboardType='number-pad' style={this.state.phoneValid?styles.inputText:styles.inputTextError} onChangeText={(text) => this.setState({phone: text})} />
          <TextInput returnKeyType='done' value={this.state.partySize} ref='partySize' placeholder='party size' keyboardType='number-pad' style={this.state.partySizeValid?styles.inputText:styles.inputTextError} onChangeText={(text) => this.setState({partySize: text})} />

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

  inputText: {
    fontSize: 10,
    borderRadius: 5, 
    padding: 7, 
    marginBottom: 15, 
    height: 30, 
    borderColor: '#C8C8C8', 
    borderWidth: 1
  },
  inputTextError: {
    fontSize: 10,
    borderRadius: 5, 
    padding: 7, 
    marginBottom: 15, 
    height: 30, 
    borderColor: 'red', 
    borderWidth: 1
  },
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
