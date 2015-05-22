/* @flow */
'use strict';

var React = require('react-native');
var t = require('tcomb-form-native');
var Form = t.form.Form;


var {
  StyleSheet, 
  Text, 
  View, 
  TouchableHighlight
} = React;


// here we are: define your domain model
var Person = t.struct({
  firstName: t.Str,              // a required string
  lastName: t.Str,  // an optional string
  phone: t.Num,               // a required number
  rememberMe: t.Bool        // a boolean
});

var options = {}; // optional rendering options (see documentation)

var GetInLineForm = React.createClass({

  onPress: function () {
    // call getValue() to get the values of the form
    var value = this.refs.form.getValue();
    if (value) { // if validation fails, value will be null
      console.log(value); // value here is an instance of Person
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
