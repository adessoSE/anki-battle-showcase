import React from 'react';

import styles from './styles.css';

export default class Vehicle extends React.Component {
        
  render() {
	this.index = this.props.index;
	var style = {
		
	  left: this.props.posX,
	  top: this.props.posY,		
	  transform: 'translate(-50%,-50%) rotate('+(-this.props.direction)+'deg) translate(-15%)',
	  backgroundColor: this.props.color,
		color: "white",
		fontSize: "100px",
		textAlign: "center",
	};
	  
    return (
				<div className="vehicle" style={style}>
					<b>{this.index + 1}</b>
				</div>
    );
  }
}

Vehicle.defaultProps = { posX: 100, posY: 100, color: '#000' };
