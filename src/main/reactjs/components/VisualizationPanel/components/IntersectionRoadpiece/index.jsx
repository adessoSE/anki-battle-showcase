import React from 'react';

import styles from './styles.css';

export default class IntersectionRoadpiece extends React.Component {
  render() {
    var transform = { transform: 'translate('+this.props.posX+'px, '+this.props.posY+'px)' + ' rotate('+this.props.rotation+'deg)' };
      
    return (
      <div className="intersection" style={transform}>
        <div className="block block-nw"></div>
        <div className="block block-ne"></div>
        <div className="block block-sw"></div>
        <div className="block block-se"></div>
      </div>
    );
  }
}
