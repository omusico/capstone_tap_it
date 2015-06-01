'use strict';

var React = require('react-native');
var Theme = require('../style/theme');
var ReservationListView = require('./ReservationListView');


var {
  NavigatorIOS,
  StatusBarIOS,
  StyleSheet,
  Text,
  View
} = React;

var ListPage = React.createClass({

  render: function() {
      return (
        <View style={styles.ListPage}>
          <ReservationListView />
        </View>
      );
    },

  });

var TapEatNavigator = React.createClass({

  statics: {
    title: '<NavigatorIOS> - Custom',
    description: 'iOS navigation with custom nav bar colors',
  },

  render: function() {
    // Set StatusBar with light contents to get better contrast
    StatusBarIOS.setStyle(StatusBarIOS.Style['lightContent']);

    return (
      <NavigatorIOS
        style={styles.container}
        initialRoute={{
          component: ListPage,
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
  ListPage: {
    flex: 1,
  },
  ListPageText: {
    alignSelf: 'center',
    marginTop: 10
  },
});

module.exports = TapEatNavigator;
