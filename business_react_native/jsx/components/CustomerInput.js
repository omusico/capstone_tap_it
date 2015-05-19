/* @flow */
'use strict';

var React = require('react-native');
var Button = require('react-native-button');

var {
  StyleSheet,
  TextInput,
  View,
} = React;

var CustomerInput = React.createClass({

	_handleClick: function(event: Object){
		console.log(event);
	},

	render: function() {
		return (
		  <View>
		  	<View style={styles.textInputBoxBorder}>
		      	<TextInput
				    style={styles.textInputBox}
				    onChangeText={(text) => this.setState({input: text})}
				    placeholder="First Name"
				 />
			 </View>
		  	<View style={styles.textInputBoxBorder}>
				 <TextInput
				    style={styles.textInputBox}
				    onChangeText={(text) => this.setState({input: text})}
				    placeholder="Last Name"
				 />
			 </View>
		  	<View style={styles.textInputBoxBorder}>
				 <TextInput
				    style={styles.textInputBox}
				    onChangeText={(text) => this.setState({input: text})}
				    placeholder="Phone Nubmer"
				    keyboardType="number-pad"
				 />
			 </View>
			 <View>
			 	<Button style={styles.getInLineButton} onPress={this._handleClick}>
			 		Get in line!
			 	</Button>
			 </View>
		  </View>
		);
	}
});


var styles = StyleSheet.create({
	textInputBox : {
		height: 40
	},
	textInputBoxBorder: {
		borderBottomWidth: 1,
		borderColor: 'gray'
	},
	getInLineButton: {
		color: 'white',
		height: 30,
		backgroundColor: 'red',
		borderRadius: 10,
		borderWidth: 2,
		borderColor: 'gray'
	}
});


module.exports = CustomerInput;
