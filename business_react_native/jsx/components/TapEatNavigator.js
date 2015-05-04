/**
 * The examples provided by Facebook are for non-commercial testing and
 * evaluation purposes only.
 *
 * Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
'use strict';

var React = require('react-native');
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
        tintColor="#FFFFFF"
        barTintColor="#183E63"
        titleTextColor="#FFFFFF"
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
