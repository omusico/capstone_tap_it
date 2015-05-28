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

var {
  AppRegistry,
  StyleSheet,
  ListView,
  Image,
  Text,
  TextInput,
  View,
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
    render: function() {
        var date = new Date();
        return (
          <View style={styles.layout}>
            <View style={styles.signInView}>
              <NavBar title="Sign In" />
              <GetInLineForm />
            </View>

            <View style={styles.checkInView}>
              <NavBar title="Wait List" />
              <ReservationListView />
            </View>
          </View>
        );
    }
});

var styles = StyleSheet.create({
  layout: {
    flexDirection: 'row',
    flex: 1
  },
  signInView: {
    backgroundColor: 'grey', 
    flex: 0.3,
    borderRightColor: '#151515',
    borderRightWidth: 0.5
 
  },
  checkInView: {
    backgroundColor: 'white',
    flex: 0.5,
  },

  signInTitle: {
    alignSelf: 'center',
    marginTop: 20
  },
  navBar: {
    backgroundColor: '#E9EAED',
    flex: 0.05
  }

});

AppRegistry.registerComponent('business_react_native', () => TapEat);


