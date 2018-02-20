import React from 'react';

import styles from './styles.css';

export default class LogPanel extends React.Component {

  constructor(props) {
    super(props);    
    this.index = 0;
  }
  
  render() {
      this.index = this.index + 1;
      this.log = this.props.log;
      this.bt = this.props.bt;
      this.ws = this.props.ws;
      var logNodes = this.log.map( (v, index) =>       
        <p key={"log" + this.index + index + v}>{v}</p>     
      );
            var btNodes = this.bt.map( (v, index) =>       
        <p key={"bt" + this.index + index + v}>{v}</p>     
      );
            var wsNodes = this.ws.map( (v, index) =>       
        <p key={"ws" + this.index + index + v}>{v}</p>     
      );
    return (
      <div className="mdl-tabs mdl-js-tabs">
        <div className="mdl-tabs__tab-bar">
          <a href="#tab1" className="mdl-tabs__tab">Logs</a>
          <a href="#tab2" className="mdl-tabs__tab">BT Messages</a>
          <a href="#tab3" className="mdl-tabs__tab">Websockets</a>
          <a href="#tabX" className="mdl-tabs__tab">Close</a>
        </div>
        <div id="tab1" className="mdl-tabs__panel mdl-color--grey-800">          
        {logNodes}
        </div>
        <div id="tab2" className="mdl-tabs__panel mdl-color--grey-800">
          {btNodes}
        </div>
        <div id="tab3" className="mdl-tabs__panel mdl-color--grey-800">
          {wsNodes}
        </div>
      </div>
    );
  }
  
  componentDidMount() {
    
  }
  
}