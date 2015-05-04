
'use strict'

var React = require('react-native');
var Theme = require('../style/theme');
var VCalUtil = require('../utils/VCalUtil');

var {
    ListView,
    View,
    StyleSheet,
    TouchableHighlight,
    PanResponder,
    Text
} = React;

var TimeLineListView = React.createClass({
    
    statics: {
        title: "Timeline View",
        description: "time line for today's events"
    },

    _dayTimeDesc: VCalUtil.statics.dailyTimeSection,
    _panResponder: {},

    componentWillMount: function() {
      this._panResponder = PanResponder.create({
        onStartShouldSetPanResponder: this._handleStartShouldSetPanResponder,
        onMoveShouldSetPanResponder: this._handleMoveShouldSetPanResponder,
        onPanResponderGrant: this._handlePanResponderGrant,
        onPanResponderMove: this._handlePanResponderMove,
        onPanResponderRelease: this._handlePanResponderEnd,
        onPanResponderTerminate: this._handlePanResponderEnd,
      });
    },

    _handleStartShouldSetPanResponder: function(e: Object, gestureState: Object): boolean {
    // Should we become active when the user presses down on the circle?
      return true;
    },

    _handleMoveShouldSetPanResponder: function(e: Object, gestureState: Object): boolean {
      // Should we become active when the user moves a touch over the circle?
      return false;
    },

    _handlePanResponderGrant: function(e: Object, gestureState: Object) {
      console.log("press grant");
      console.log(e);
      console.log(gestureState);
    },
    _handlePanResponderMove: function(e: Object, gestureState: Object) {
      console.log("move")
    },
    _handlePanResponderEnd: function(e: Object, gestureState: Object) {
      console.log("end")
    },

    getInitialState: function() {

        var getSectionData = (dataBlob, sectionID) => {
          return dataBlob[sectionID];
        };
        var getRowData = (dataBlob, sectionID, rowID) => {
          return dataBlob[rowID];
        };
        //genreate datasource
        var dataSource = new ListView.DataSource({
          getRowData: getRowData,
          getSectionHeaderData: getSectionData,
          rowHasChanged: (row1, row2) => row1 !== row2,
          sectionHeaderHasChanged: (s1, s2) => s1 !== s2,
        });

        var dataBlob = {};
        var sectionIDs = [];
        var rowIDs = [];

        var date = new Date();
        var hour = date.getHours();
        var sectionIndex = VCalUtil.getSecionIndexByHour(hour);

        for (var ii = 0; ii < this._dayTimeDesc.length; ii++) {
          var timeSection = this._dayTimeDesc[sectionIndex];
          var sectionName = timeSection.title;
          sectionIDs.push(sectionName);
          dataBlob[sectionName] = sectionName;
          var timeLots = VCalUtil.genrate30MinIntervalTimeStringsByHour(timeSection.startHour, timeSection.endHour);
          rowIDs[ii] = timeLots;
          for (var jj = 0; jj < timeLots.length; jj++) {
              dataBlob[timeLots[jj]] = timeLots[jj];
          };

          //grab section from begining.
          if(sectionIndex == this._dayTimeDesc.length - 1){
            sectionIndex = 0;
          }
          sectionIndex++;
        }

        return {
          dataSource: dataSource.cloneWithRowsAndSections(dataBlob, sectionIDs, rowIDs),
          headerPressCount: 0,
          pressedRow: []
        };
    },

    render: function(){
        return(
            <ListView
                bounces={false}
                dataSource={this.state.dataSource}
                initialListSize={48}
                renderRow={this._renderRow}
                renderSectionHeader={this._renderSectionHeader}
                >
                </ListView>
        );
    },

    _renderRow: function(rowData: string, sectionID: number, rowID: number) {
        console.log(rowID);
        return (
            <TouchableHighlight 
            onLongPress={() => this._pressRow(rowID)}
            onPressIn={() => this._pressRow(rowID)}
            underlayColor={Theme.primaryHeaderBackgroundColor}>

                <View style={[styles.timelineSingleGrid, {backgroundColor: this.state.pressedRow[rowID] ? Theme.primaryContentBackgroundColor : "#ffffff"}]}
                      {...this._panResponder.panHandlers}>

                    <Text style={styles.text}>
                        {rowData}
                    </Text>
                    <View style={rowID % 2 === 0 ? styles.separator : null} />

                </View>

            </TouchableHighlight>
        );
    },

    _renderSectionHeader: function(sectionData: string, sectionID: string) {
        return (
          <View style={styles.sectionHeader}>
            <Text style={styles.sectionHeaderText}>
              {sectionData}
            </Text>
          </View>
        );
    },

    _pressRow: function(rowID: number) {
        console.log("touched " + rowID +  "row");
    }
});


var styles = StyleSheet.create({
    timelineSingleGrid:{
        flexDirection: 'row',
        flexWrap: 'nowrap',
        alignItems: 'center',
        paddingLeft: 5
    },
    separator: {
        height: 2,
        flex: 1, 
        backgroundColor: Theme.primaryBorderColor
    },
    text: {
        flex: 0,
    },
    sectionHeaderText: {
        flex: 0,
        color: Theme.secondHeaderFontColor
    },
    sectionHeader: {
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'flex-start',
        padding: 6,
        backgroundColor: Theme.secondHeaderBackgroundColor
    },
});

module.exports = TimeLineListView;