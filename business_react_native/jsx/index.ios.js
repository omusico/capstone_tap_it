/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var React = require('react-native');
var SlideMenu = require('./components/SlideMenu');
var TapEatNavigator = require('./components/TapEatNavigator');

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
          <View>
            <TapEatNavigator />
          </View>
        );
    }
});

var styles = StyleSheet.create({
  layout: {
    marginTop: 20,
    backgroundColor: '#527FE4',
  },
  text: {
    color: '#FFFFFF'
  }
});

AppRegistry.registerComponent('business_react_native', () => TapEat);


