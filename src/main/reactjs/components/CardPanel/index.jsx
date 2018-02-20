import React from 'react';

import Card from './components/Card';

import styles from './styles.css';

export default class CardPanel extends React.Component {

  constructor(props) {
    super(props);
     this.stomp = props.stomp;
     this.connect = this.connect.bind(this);
     this.disconnect = this.disconnect.bind(this);
     this.reload = this.reload.bind(this);
     this.start = this.start.bind(this);
     this.stop = this.stop.bind(this);
     this.carConnect = this.carConnect.bind(this);
     this.carDisconnect = this.carDisconnect.bind(this);  
     this.handleChangeBehavior = this.handleChangeBehavior.bind(this);   
  }
  
  render() {
      this.stomp = this.props.stomp;
      this.state = this.props.state;
      var cardElements = this.props.vehicles.map( (vehicle, index) => 
        <Card key={index} stomp={this.stomp} vehicle={vehicle} state={this.state}/>
      );
        var style = {      
          backgroundColor: "grey"
        };
    return (
      <div className="mdl-grid mdl-grid--nesting">
        <div className="mdl-card mdl-card--nosize mdl-shadow--4dp mdl-cell mdl-cell--12-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
          <div className="mdl-card__title mdl-card--border" style={style} onClick={this.toggleHidden}>
            <h2 className="mdl-card__title-text">Kontrollbereich</h2>
          </div>
          <div className="mdl-card__supporting-text mdl-card--borde">
            {this.connectionState()}          
              <select id="behavior" onChange={this.handleChangeBehavior} className="mdl-select__input">
                <option value="default">Standard</option>
                <option value="4ws">Vier-Wege-Stop</option>
                <option value="is">Intelligentes stoppen</option>
                <option value="noStop">Ohne Stoppen</option>              
              </select>    
              <div>
                <a onClick={this.carConnect} className="mdl-button">Verbinde alle</a>
                <a onClick={this.carDisconnect} className="mdl-button">Trenne alle</a>            
              </div>    
              <div>
                <a onClick={this.start} className="mdl-button">Starte alle</a>
                <a onClick={this.stop} className="mdl-button">Stoppe alle</a>               
              </div>
          </div>                   
        </div>
        {cardElements}   
      </div>
    );
  }
  
  connectionState() {
    if(this.state != undefined && this.state.connected == true){
      return (
        <div>
          <a onClick={this.connect} className="mdl-button" disabled>Anmelden</a>
          <a onClick={this.disconnect} className="mdl-button">Abmelden</a>
          {this.reloadState()}
        </div>
      );
    }else{
      return (
        <div>
          <a onClick={this.connect} className="mdl-button">Anmelden</a>
          <a onClick={this.disconnect} className="mdl-button" disabled>Abmelden</a>
          {this.reloadState()}
        </div>
      );
    }
  }

  reloadState(){
        if(this.state != undefined && this.state.reloading == false){
      return (
        <div>
          <a onClick={this.reload} className="mdl-button">Aktualisieren</a>
        </div>
      );
    }else{
      return (
        <div>
          <a onClick={this.reload} className="mdl-button" disabled>Aktualisieren</a>
        </div>
      );
    }
  }

  printConnect(){

  }

  carConnect(){
    this.stomp.send("/app/commands", {}, '{"command": "carConnectAll"}');
  }
  
  carDisconnect(){
    this.stomp.send("/app/commands", {}, '{"command": "carDisconnectAll"}');
  }

  connect(){
    this.stomp.send("/app/commands", {}, '{"command": "connect"}');
  }
  
  disconnect(){
    this.stomp.send("/app/commands", {}, '{"command": "disconnect"}');    
  }

  reload(){
    this.stomp.send("/app/commands", {}, '{"command": "reload"}');
  }

  start(){
    this.stomp.send("/app/commands", {}, '{"command": "startAll"}');
  }

  stop(){
    this.stomp.send("/app/commands", {}, '{"command": "stopAll"}');
  }
  
  handleChangeBehavior(){
    var behavior = document.getElementById("behavior").value;
    this.stomp.send("/app/commands", {}, '{"command": "behavior",  "arg0": "'+behavior+'"}');
  }

}