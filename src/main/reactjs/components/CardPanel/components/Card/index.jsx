import React from 'react';

import styles from './styles.css';

export default class Card extends React.Component {

  constructor(props) {
    super(props);    
    this.vehicle = props.vehicle;
    this.hidden = false;
    this.lineHeight = 67.5;
    this.state = {
      velocity: 0,
      line : 0
    };
    this.handleClickCarConnect = this.handleClickCarConnect.bind(this);
    this.handleClickCarDisconnect = this.handleClickCarDisconnect.bind(this);
    this.handleSpeedChange = this.handleSpeedChange.bind(this);
    this.handleLineChange = this.handleLineChange.bind(this);
    this.handleClickScan = this.handleClickScan.bind(this);
      this.handleClickTurn = this.handleClickTurn.bind(this);
      this.handleBehaviorChange = this.handleBehaviorChange.bind(this);
    this.toggleHidden = this.toggleHidden.bind(this);
    this.state.velocity = this.vehicle.lastKnownSpeed;
    this.state.line = this.vehicle.offsetFromCenter + this.lineHeight;
  }  
  
  render() {
    this.stomp = this.props.stomp;
    this.vehicle = this.props.vehicle;
    return (
      <div className="mdl-card mdl-card--nosize mdl-shadow--4dp mdl-cell mdl-cell--12-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
        {this.drawHead()}
        {this.drawBody()}
        <div className="my-card__menu mdl-card__menu">
          <button className="mdl-button mdl-button--icon" hidden>
            <div className="svg-ic svg-ic_signal_wifi_3_bar_24px">&nbsp;</div>
          </button>          
          <button className="mdl-button mdl-button--icon">
            {this.calcBatteryState()}
          </button>
        </div>
      </div>      
    );
  }
  
  speedToKmh(speed) {
     return speed * 53 * 3.6 / 1000;
  }

  toggleHidden(){
    if(this.hidden == false){
      this.hidden = true;
    }else{
      this.hidden = false;
    }
  }

  drawHead(){
    if(this.vehicle.connected){
      if(!this.vehicle.charging){
        var style = {      
          backgroundColor: this.vehicle.color,
          cursor: 'pointer'
        };
        return(
          <div className="mdl-card__title mdl-card--border" style={style} onClick={this.toggleHidden}>
            <h2 className="mdl-card__title-text">{this.vehicle.index + 1}) {this.vehicle.modelName}</h2>
          </div>
        );
      }else{
        var style = {      
          backgroundColor: this.vehicle.color,
          cursor: 'not-allowed'
        };
        return(
          <div className="mdl-card__title mdl-card--border" style={style} onClick={this.toggleHidden}>
            <h2 className="mdl-card__title-text">{this.vehicle.index + 1}) {this.vehicle.modelName}</h2>
          </div>
        );      
      }
    }else{
          var style = {      
          backgroundColor: this.vehicle.color
        };
        return(
          <div className="mdl-card__title mdl-card--border" style={style} onClick={this.toggleHidden}>
            <h2 className="mdl-card__title-text">{this.vehicle.index + 1}) {this.vehicle.modelName} <a onClick={this.handleClickCarConnect} className="mdl-button">Verbinden</a></h2>
          </div>
        );
    }


  }

  drawBody(){
    if(true || this.hidden == false && !this.vehicle.charging && this.vehicle.connected){
      return (
        <div>
            <div className="mdl-card__supporting-text mdl-card--border">
                <div className="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                    <input className="mdl-textfield__input" type="text" id="username" onChange={this.handleBehaviorChange}/>
                    <label className="mdl-textfield__label" for="username">Behavior name</label>
                </div>
          </div>
          <div className="mdl-card__actions">
            {this.stateScanning()}            
          </div>       
        </div>
      );
    }
  }

  stateScanning(){
        if(this.props.state != undefined && this.props.state.scanRunning == false){
      return (
        <div>
          <a onClick={this.handleClickScan} className="mdl-button">Scannen</a>
          <a onClick={this.handleClickTurn} className="mdl-button">Drehung</a>
          <a onClick={this.handleClickCarDisconnect} className="mdl-button">Trennen</a>
        </div>
      );
    }else{
      return (
        <div>
          <a onClick={this.handleClickScan} className="mdl-button" disabled>Scannen</a>
          <a onClick={this.handleClickTurn} className="mdl-button">Drehung</a>
          <a onClick={this.handleClickCarDisconnect} className="mdl-button">Trennen</a>
        </div>
      );
    }
  }
  
  updateValues(){
    if(this.hidden == false && !this.vehicle.charging && this.vehicle.connected){
      var speedSlider = document.getElementById("speed_d "+this.vehicle.id);
      speedSlider.MaterialSlider.change(this.vehicle.lastKnownSpeed);
      var lineSlider = document.getElementById("line_d "+this.vehicle.id);
      lineSlider.MaterialSlider.change(this.vehicle.offsetFromCenter + this.lineHeight);
    }
  }

  componentDidMount() {
    
  }
  
  componentDidUpdate() {
    componentHandler.upgradeDom();    
    this.updateValues();
  }

  handleClickCarConnect(){
    this.stomp.send("/app/commands", {}, '{"command": "carConnect",  "arg0": "'+this.vehicle.id+'"}');
  }

  handleClickCarDisconnect(){
    this.stomp.send("/app/commands", {}, '{"command": "carDisconnect",  "arg0": "'+this.vehicle.id+'"}');
  } 

  handleSpeedChange(event) {
    this.state.velocity = document.getElementById("speed "+this.vehicle.id).value;
    this.stomp.send("/app/commands", {}, '{"command": "speed",  "arg0": "'+this.vehicle.id+'",  "arg1": "'+this.state.velocity+'"}');
  }

  handleLineChange(event) {
    this.state.line = document.getElementById("line "+this.vehicle.id).value;
    var realLine = parseFloat(this.state.line) - parseFloat(this.lineHeight);
    this.stomp.send("/app/commands", {}, '{"command": "line",  "arg0": "'+this.vehicle.id+'",  "arg1": "'+realLine+'"}');
  }

  handleClickScan(event){
    this.stomp.send("/app/commands", {}, '{"command": "scan",  "arg0": "'+this.vehicle.id+'"}');
    document.getElementById("behavior").value = "default";
    this.stomp.send("/app/commands", {}, '{"command": "behavior",  "arg0": "default"}');
  }

  handleClickTurn(){
    this.stomp.send("/app/commands", {}, '{"command": "turn",  "arg0": "'+this.vehicle.id+'"}');
  }

  handleBehaviorChange() {
    this.state.username = document.getElementById("username").value;
    console.log(this.state.username);
    this.stomp.send("/app/commands", {}, JSON.stringify({ command: "changeBehavior", arg0: this.vehicle.id, arg1: this.state.username }));
  }

  calcBatteryState(){
    var battery = this.vehicle.battery;
    var batteryIcon = "";
    
    if (!this.vehicle.charging && battery < 15) {
      batteryIcon = "alert";
      if(!this.vehicle.charging && this.vehicle.connected){
        document.getElementById('audioBatteryCritical').play(); 
      }      
    } else if (battery < 25) {
      batteryIcon = "20";
    } else if (battery < 40) {
      batteryIcon = "30";
    } else if (battery < 55) {
      batteryIcon = "50";
    } else if (battery < 70) {
      batteryIcon = "60";
    } else if (battery < 85) {
      batteryIcon = "80";
    } else if (battery < 95) {
      batteryIcon = "90";
    } else {
      batteryIcon = "full";
    }
    
    if (this.vehicle.charging)
      batteryIcon = "charging_" + batteryIcon;
    
    var iconClass = "svg-ic svg-ic_battery_" + batteryIcon + "_24px";
    
    return <div className={iconClass}>&nbsp;</div>;
  }
  
}
