import React from 'react';

import styles from './styles.css';

export default class CurvedRoadpiece extends React.Component {

  render() {
    var transform = { transform: 'translate('+this.props.posX+'px, '+this.props.posY+'px)' + ' rotate(-'+this.props.rotation+'deg)' };
    
    return (
      <div className="curve" style={transform}/>
    );
  }

}