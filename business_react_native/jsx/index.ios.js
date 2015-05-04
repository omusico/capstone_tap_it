/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var React = require('react-native');
var TimeLineListView = require('./components/TimeLineListView');
var PressDragSelectGesture = require('./components/PressDragSelectGesture');
var SlideMenu = require('./components/SlideMenu');

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
          <TimeLineListView />
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


