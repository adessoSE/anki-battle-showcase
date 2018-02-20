import React from 'react';

import SockJS from 'sockjs-client';
import 'stompjs/lib/stomp.js';

import $ from 'jquery';

import VisualizationPanel from 'Components/VisualizationPanel';
import CardPanel from 'Components/CardPanel';
import LogPanel from 'Components/LogPanel';
import AudioComponent from 'Components/AudioComponent'

import styles from './styles.scss';

export default class Application extends React.Component {
  
  constructor(props) {
    super(props);        
    this.stompConnected = this.stompConnected.bind(this);    
    this.stompDisconnected = this.stompDisconnected.bind(this);
    this.stompDataReceived = this.stompDataReceived.bind(this);
    this.stompRoadmapReceived = this.stompRoadmapReceived.bind(this);
    this.stompWebsocketLogsReceived = this.stompWebsocketLogsReceived.bind(this);
    this.stompLogsReceived = this.stompLogsReceived.bind(this);
    this.stompBluetoothLogsReceived = this.stompBluetoothLogsReceived.bind(this);
    this.stompStateReceived = this.stompStateReceived.bind(this); 
    this.state = {
  	  vehicles: [],
  	  roadmap: { roadpieces: [] },
      log: [],
      ws: [],
      bt: []
  	}
    this.connect();
  }
  
  render() {
    return (
        <div className="mdl-layout mdl-js-layout mdl-layout--fixed-header mdl-layout--no-drawer-button my-layout">
        <header className="mdl-layout__header">
          <div className="mdl-layout__header-row">
            <img src="assets/logo_adesso.png" className="my-layout__logo" />
            <div className="mdl-layout-spacer"></div>
            <span className="mdl-layout__title">InVerSiV-Showcase</span>
          </div>
        </header>
        <main className="mdl-layout__content">
          <div className="mdl-grid mdl-grid--full-height">
            <div id="visualization_panel" className="mdl-cell mdl-cell--9-col mdl-cell--8-col-tablet">
              <VisualizationPanel stomp={this.stomp} vehicles={this.state.vehicles} roadmap={this.state.roadmap}/>
            </div>
            <div className="mdl-cell mdl-cell--3-col mdl-cell--8-col-tablet">
              <CardPanel stomp={this.stomp} vehicles={this.state.vehicles} roadmap={this.state.roadmap} state={this.state.state}/>
            </div>
          </div>
        </main>
        <footer>
          <LogPanel log={this.state.log} bt={this.state.bt} ws={this.state.ws}/>
        </footer>
        <AudioComponent></AudioComponent>
      </div>
    );
  }
  
  componentDidMount() {
    
  }

    connect(){
      this.socket = new SockJS('http://localhost:8081/inversiv');
      this.stomp = Stomp.over(this.socket);
      this.stomp.debug = null;       
      console.log("Try connect...");
      this.stomp.connect('', '', this.stompConnected, this.stompDisconnected);
    }

    stompConnected(frame) {
    console.log('Connected: ' + frame);
    this.stomp.subscribe('/topic/vehicles', this.stompDataReceived);
    this.stomp.subscribe('/topic/roadmap', this.stompRoadmapReceived);
    this.stomp.subscribe('/topic/logs/ws', this.stompWebsocketLogsReceived);
    this.stomp.subscribe('/topic/logs/log', this.stompLogsReceived);
    this.stomp.subscribe('/topic/logs/bt', this.stompBluetoothLogsReceived);
    this.stomp.subscribe('/topic/state', this.stompStateReceived);
  }

  stompDisconnected(){
    console.log("Socket closed. Starte reconnect...");
    document.getElementById('audioLogoff').play(); 
    this.setState({vehicles: []});
    this.setState({roadmap: []});
    this.setState({log: []});
    this.setState({ws: []});
    this.setState({bt: []});
    document.getElementById("behavior").value = "default";
    setTimeout(this.connect(), 2000);
  }
  
  stompDataReceived(data) {
      var vehicles = JSON.parse(data.body);
      for(var i=0;i < vehicles.length;i++){
        vehicles[i].index = i;
      }
      this.setState({vehicles: vehicles});
	  }
  
  stompRoadmapReceived(data) {
      var roadmap = JSON.parse(data.body);
      this.setState({roadmap: roadmap});
      window.dispatchEvent(new Event('resize'));
    }
  
  stompLogsReceived(data) {
      var log = JSON.parse(data.body);
      log.reverse();
      this.setState({log: log});
    }

    stompWebsocketLogsReceived(data) {
      var ws = JSON.parse(data.body);
      ws.reverse();
      this.setState({ws: ws});
    }

      stompBluetoothLogsReceived(data) {
      var bt = JSON.parse(data.body);
      bt.reverse();
      this.setState({bt: bt});
    }
      stompStateReceived(data) {
      var state = JSON.parse(data.body);
      this.setState({state: state});
    }    
}
