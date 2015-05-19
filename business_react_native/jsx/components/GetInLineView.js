/*
top view estimate wait time and customerinput
*/
'use strict';

var React = require('react-native');
var CustomerInput = require('../components/CustomerInput');

var {
  StyleSheet,
  View,
} = React;

var GetInLineView = React.createClass({
  render: function() {
    return (
      <CustomerInput />
    );
  }
});


var styles = StyleSheet.create({

});


module.exports = GetInLineView;
