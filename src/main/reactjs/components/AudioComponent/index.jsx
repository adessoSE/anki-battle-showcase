import React from 'react';

import styles from './styles.css';

export default class AudioComponent extends React.Component {

  constructor(props) {
    super(props);
  }
  
  render() {

    return (
      <div>
        <audio id="audioRemove" preload="auto">
          <source src="/assets/sounds/Remove.wav" />
        </audio>
        <audio id="audioBatteryCritical" preload="auto">
          <source src="/assets/sounds/BatteryCritical.wav" />
        </audio>     
        <audio id="audioLogoff" preload="auto">
          <source src="/assets/sounds/Logoff.wav" />
        </audio>
        <audio id="audioNotify" preload="auto">
          <source src="/assets/sounds/Notify.wav" />
        </audio>                   
      </div>
    );
  }
  
  componentDidMount() {
    
  }
  
}