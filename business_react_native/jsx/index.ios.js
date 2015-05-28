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
              Sign In
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
              <NavBar />
              <GetInLineForm />
            </View>

            <View style={styles.checkInView}>
              <TapEatNavigator />
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
    flex: 0.3
  },
  checkInView: {
    backgroundColor: 'red',
     flex: 0.5
  },

  signInTitle: {
    alignSelf: 'center',
    marginTop: 20
  },
  navBar: {
    backgroundColor: 'green',
    flex: 0.07
  },
  signInBox: {
    backgroundColor: 'orange',
    flex: 0.94
  }
});

AppRegistry.registerComponent('business_react_native', () => TapEat);


