
'use strict'

var React = require('react-native');
var Theme = require('../style/theme');
var Firebase = require('firebase-react-native');

var {
    ListView,
    View,
    StyleSheet,
    TouchableHighlight,
    Text,
    PixelRatio
} = React;

var ReservationListView = React.createClass({
    
    statics: {
        restaurantId: "dtfSeattleUniversityVillage1",
        title: "Rservation List View",
        description: "current waiting line for today's events"
    },

    _dataBlob: [],

    getInitialState: function() {
        //Initialzie firebase, open up websocket
        var that = this;
        var restaurantId = "dtfSeattleUniversityVillage1";
        var TapEatFireBase = new Firebase("https://tapeat.firebaseio.com/");
        TapEatFireBase.child("reservations/" + restaurantId).on('value', function(snapshot) {
            that._processingReservations(snapshot);
        });
        //genreate datasource
        var dataSource = new ListView.DataSource({ rowHasChanged: (row1, row2) => row1 !== row2 });

        return {
          dataSource: dataSource.cloneWithRows(this._dataBlob)
        };
    },

    _processingReservations: function(snapshot: object){
        this._dataBlob = [];
        
        if(snapshot){
          var dataSource = new ListView.DataSource({ rowHasChanged: (row1, row2) => row1 !== row2 });
          var reservations = snapshot.val();
          for(var key in reservations){
            this._dataBlob.push(reservations[key]);  
          }
          console.log(this._dataBlob);
          this.setState({dataSource: dataSource.cloneWithRows(this._dataBlob)});
        }
    },

    render: function(){
        return(
            <ListView style={styles.listView}
                bounces={false}
                dataSource={this.state.dataSource}
                initialListSize={48}
                renderRow={this._renderRow}
                >
            </ListView>

        );
    },

    _renderRow: function(rowData: object, sectionID: number, rowID: number) {
        return (
            <View>
                <View style={styles.row}>

                    <Text style={styles.partyName}>
                        {rowData.customerName}
                    </Text>
                    <Text style={styles.partySize}>
                        {rowData.partySize} people
                    </Text>
                    <View style={styles.separator}  />

                </View>
            <View style={styles.cellBorder} />
            </View>
        );
    },


    _pressRow: function(rowID: number) {
        console.log("touched " + rowID +  "row");
    }
});


var styles = StyleSheet.create({
    listView: {
        flex: 0.93,
        backgroundColor: 'white'
    },
    reservationId:{
      justifyContent : 'space-between'
    },

    timelineSingleGrid:{
        flexDirection: 'row',
        flex: 1,
        paddingLeft: 5
    },
    separator: {
        height: 2,
        backgroundColor: Theme.primaryBorderColor
    },

    sectionHeaderText: {
        flex: 0,
        color: Theme.secondHeaderFontColor
    },
    row: {
        alignItems: 'center',
        backgroundColor: 'white',
        flexDirection: 'row',
        padding: 20,
        justifyContent: 'space-around' 
    },
    partyName: {
        flex: 4
    },
    partySize: {
        flex: 1
    },
    cellBorder: {
    backgroundColor: 'rgba(0, 0, 0, 0.1)',
    // Trick to get the thinest line the device can display
    height: 1 / PixelRatio.get(),
    marginLeft: 4,
  },
});

module.exports = ReservationListView;