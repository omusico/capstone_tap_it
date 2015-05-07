'use strict';

var React = require('react-native');
var Theme = require('../style/theme');
var Firebase = require('firebase-react-native');

var {
  NavigatorIOS,
  StatusBarIOS,
  StyleSheet,
  Text,
  View
} = React;

var EmptyPage = React.createClass({

  render: function() {
      return (
        <View style={styles.emptyPage}>
          <Text style={styles.emptyPageText}>
            {this.props.text}
          </Text>
        </View>
      );
    },

  });

var TapEatNavigator = React.createClass({

  statics: {
    title: '<NavigatorIOS> - Custom',
    description: 'iOS navigation with custom nav bar colors',
  },

  componentWillMount: function() {
    var restaurantId = "dtfSeattleUniversityVillage1";
      var TapEatFireBase = new Firebase("https://tapeat.firebaseio.com/");
      TapEatFireBase.child("reservations/" + restaurantId).on('value', function(snapshot) {
        console.log(snapshot.val());
      });
  },

  render: function() {
    // Set StatusBar with light contents to get better contrast
    StatusBarIOS.setStyle(StatusBarIOS.Style['lightContent']);

    return (
      <NavigatorIOS
        style={styles.container}
        initialRoute={{
          component: EmptyPage,
          title: "Ding Tai Feng",
          rightButtonTitle: 'Done',
          onRightButtonPress: () => {
          },
          passProps: {
            text: "Welcome to Ding Tai Feng!"
          },
        }}
        tintColor={Theme.primaryHeaderFrontColor}
        barTintColor={Theme.primaryHeaderBackgroundColor}
        titleTextColor={Theme.primaryHeaderFrontColor}
      />
    );
  },

});

var styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  emptyPage: {
    flex: 1,
    paddingTop: 64,
  },
  emptyPageText: {
    margin: 10,
  },
});

module.exports = TapEatNavigator;
