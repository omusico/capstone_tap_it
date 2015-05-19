/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var React = require('react-native');
var SlideMenu = require('./components/SlideMenu');
var TapEatNavigator = require('./components/TapEatNavigator');
var ReservationListView = require('./components/ReservationListView');
var GetInLineView = require('./components/GetInLineView');

var {
  AppRegistry,
  StyleSheet,
  ListView,
  Image,
  Text,
  View,
} = React;

var TapEat = React.createClass({
    render: function() {
        var date = new Date();
        return (
          <View style={styles.layout}>
            <GetInLineView />
          </View>
        );
    }
});

var styles = StyleSheet.create({
  layout: {
    flexDirection: 'column',
    alignItems: 'stretch',
    justifyContent : 'space-between'
  },
  header: {
    
  }
});

AppRegistry.registerComponent('business_react_native', () => TapEat);


