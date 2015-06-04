/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var React = require('react-native');
var SlideMenu = require('./components/SlideMenu');
var TapEatNavigator = require('./components/TapEatNavigator');
var ReservationListView = require('./components/ReservationListView');
var GetInLineForm = require('./components/GetInLineForm');
var Modal = require('react-native-modal');
var Firebase = require('firebase-react-native');
var TapEatFireBase = new Firebase("https://tapeat.firebaseio.com/");

var key= "loggedIn";
var {
  AsyncStorage,
  AppRegistry,
  TouchableHighlight,
  StyleSheet,
  ListView,
  Image,
  Text,
  TextInput,
  View,
  AlertIOS,
} = React;



var NavBar = React.createClass({

  render: function() {
    
    return(
        <View style={styles.navBar}>
          <Text style={styles.signInTitle}>
              {this.props.title}
          </Text>
        </View>
    )
  }
});


var TapEat = React.createClass({
  getInitialState: function(){
    return{
      isModalOpen: true,
      name:'',
      address:'',
      email:'',
      phone:'',
      password:'',
      loggedIn: true,
      repeatedName: false

    }
  },
  componentDidMount: function() {

    AsyncStorage.getItem(key)
      .then((value) => {
        if (value !== null){
          // this.setState({selectedValue: value});

          console.log('Recovered selection from disk: ' + value);
          this.setState({
            loggedIn: true
          })
        } else {
          console.log('Initialized with no selection on disk.');
          this.setState({
            loggedIn: false
          })
        }
      })
      .catch((error) => console.log('AsyncStorage error: ' + error.message))
      .done();
  },
  openModal: function() {
    this.setState({isModalOpen: true});
  },

  submit: function() {

    
    var that = this;
    console.log(that.state.email);
    console.log(that.state.password);

      TapEatFireBase.createUser({
        email: that.state.email,
        password: that.state.password
      }, function(error, userData) {
        if (error) {
          switch (error.code) {
            case "EMAIL_TAKEN":
              var message = "The new user account cannot be created because the email is already in use.";
              console.log(message);
              AlertIOS.alert("Email Taken", message);
              break;
            case "INVALID_EMAIL":
              var message = "The specified email is not a valid email.";
              console.log(message);
              AlertIOS.alert("Email Taken", message);
              break;
            default:
              console.log("Error creating user:", error);
              var message = "The specified email is not a valid email.";
              console.log(message);
              AlertIOS.alert("Email Taken", message);
          }
        } else {
              var restaurantRef = TapEatFireBase.child("restaurants/" + that.state.name);
              restaurantRef.once("value", function(snapshoot){
                  if(snapshoot.val()){
                    that.setState({
                      repeatedName: true
                    })
                  }else{

                        restaurantRef.set({
                            createTime: Date.now(),
                            restaurantName: that.state.name,
                            restaurantPhone: that.state.phone,
                            restaurantAddress: that.state.address,
                            restaurantEmail: that.state.email,
                            restaurantPassword: that.state.password
                        });

                        AsyncStorage.setItem(key, "loggedIn")
                          .then(() => console.log('Saved selection to disk: ' + 'loggedIn'))
                          .catch((error) => console.log('AsyncStorage error: ' + error.message))
                          .done();
                        that.setState({loggedIn: true});
                  };
              });
          console.log("Successfully created user account with uid:", userData);
        }
      });

  },
  focusPhone: function(){
        this.refs.phone.focus();

  },
    focusEmail: function(){
    this.refs.email.focus();

  },
    focusAddress: function(){
    this.refs.address.focus();

  },
    focusPassword: function(){
    this.refs.password.focus();

  },
  removeStorage: function() {

    AsyncStorage.removeItem(key)
      .then(() => console.log('Selection removed from disk.'))
      .catch((error) => { console.log('AsyncStorage error: ' + error.message) })
      .done();
  },

    render: function() {
      var display;

      if(this.state.loggedIn){
        display = (       
          <View style={styles.layout}>     
            <View style={styles.signInView}>
              <NavBar title="Sign In" />

              <GetInLineForm />

            </View>

            <View style={styles.checkInView}>
              <NavBar title="Wait List" />
              <ReservationListView />
            </View>
          </View>)
      }else{
        display = (
            <View style={styles.signupView}>
              <Text style={styles.signUpTitle}>Sign up</Text>
              <TextInput returnKeyType='next' onSubmitEditing={this.focusPhone} value={this.state.name} placeholder='Restaurant Name' style={this.state.repeatedName?styles.inputTextError:styles.inputText} onChangeText={(text) => this.setState({name: text})} />
              <TextInput returnKeyType='next' value={this.state.phone} onSubmitEditing={this.focusAddress} ref='phone' placeholder='Restaurant Phone' keyboardType='number-pad' style={styles.inputText} onChangeText={(text) => this.setState({phone: text})} />
              <TextInput returnKeyType='next' onSubmitEditing={this.focusEmail} value={this.state.address} ref='address' placeholder='Restaurant Address' style={styles.inputText} onChangeText={(text) => this.setState({address: text})} />
              <TextInput returnKeyType='next' onSubmitEditing={this.focusPassword} value={this.state.email} ref='email' placeholder='Restaurant Email' style={styles.inputText} onChangeText={(text) => this.setState({email: text})} />
              <TextInput returnKeyType='done' value={this.state.password} placeholder='Restaurant Password' ref='password' style={styles.inputText} onChangeText={(text) => this.setState({password: text})} />

              <TouchableHighlight style={styles.button} onPress={this.submit} underlayColor='#99d9f4'>
                <Text style={styles.buttonText}>Submit</Text>
              </TouchableHighlight>  
            </View>    

          )

      }

        var date = new Date();
        return (
          <View style={styles.display}>
            {display}
              
          </View>
        );
    }
});

var styles = StyleSheet.create({
  signupView: {
      alignSelf: 'center',
      justifyContent: 'center',
      marginTop: 300
  },
  display: {
    flex: 1
  },
  signUpTitle: {
    alignSelf: 'center',
    marginBottom: 50
  },
  inputText: {
    fontSize: 10,
    borderRadius: 5, 
    padding: 7, 
    marginBottom: 15, 
    height: 30, 
    borderColor: '#C8C8C8', 
    borderWidth: 1,
    width: 500,
    alignSelf: 'center'

  },
  inputTextError: {
    fontSize: 10,
    borderRadius: 5, 
    padding: 7, 
    marginBottom: 15, 
    height: 30, 
    borderColor: 'red', 
    borderWidth: 1,
        width: 500,
    alignSelf: 'center'
  },

  layout: {
    flexDirection: 'row',
    flex: 1
  },
  signInView: {
    backgroundColor: 'grey', 
    flex: 0.3,
    borderColor: '#909090',
    borderRightWidth: 0.5
 
  },
  checkInView: {
    flex: 0.5,
  },

  signInTitle: {
    alignSelf: 'center',
    marginTop: 20
  },
  navBar: {
    backgroundColor: '#E9EAED',
    flex: 0.05,
    borderBottomWidth: 0.5,
    borderColor: '#909090'
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
    alignSelf: 'center',
    justifyContent: 'center',
    backgroundColor: '#15D3A4',
    marginRight: 20,
    marginLeft: 20,
    width: 500
  }

});

AppRegistry.registerComponent('business_react_native', () => TapEat);


