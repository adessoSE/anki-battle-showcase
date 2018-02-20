import React from 'react';

import $ from 'jquery';
import SockJS from 'sockjs-client';
import 'stompjs/lib/stomp.js';

import IntersectionRoadpiece from './components/IntersectionRoadpiece';
import StraightRoadpiece from './components/StraightRoadpiece';
import CurveRoadpiece from './components/CurvedRoadpiece';
import StartRoadpiece from './components/StartRoadpiece';
import FinishRoadpiece from './components/FinishRoadpiece';

import Vehicle from './components/Vehicle';

import styles from './styles.css';

export default class VisualizationPanel extends React.Component {
    
  constructor(props) {
    super(props);    
  }
    
  render() {
  this.stomp = this.props.stomp;
  var worldMapElem = document.getElementById('world_map');
  if(this.props.roadmap != null && this.props.roadmap.roadpieces != null){

    if(this.props.vehicles != null){
      var vehicleNodes = this.props.vehicles.map( (v ,index) =>       
        <Vehicle key={index} index={v.index} posX={v.posX+1120} posY={v.posY+560} direction={v.direction} color={v.color} />        
      );
    }

  var minX = 0, minY = 0, maxX = 0, maxY = 0;
  this.props.roadmap.roadpieces.forEach( tile => {
    if (tile.position.x > maxX)
      maxX = tile.position.x;
    if (tile.position.x < minX)
      minX = tile.position.x;
    if (tile.position.y > maxY)
      maxY = tile.position.y;
    if (tile.position.y < minY)
      minY = tile.position.y;
  });
  
  var offX = minX - 280;
  var offY = minY - 280;
  
  if(this.props.vehicles != null){
    var vehicleNodes = this.props.vehicles.map( (v, index) => 
      <Vehicle key={index} index={v.index} posX={v.posX - offX} posY={v.posY - offY} direction={v.direction} color={v.color} />
    );
  }

    var roadNodes = this.props.roadmap.roadpieces.map( (tile, index) => {
            switch(tile.type){
                case "StraightRoadpiece":
                    return (<StraightRoadpiece key={index} rotation={tile.position.angle} posX={tile.position.x - offX} posY={tile.position.y - offY} />)
                case "CurvedRoadpiece":
                    return (<CurveRoadpiece key={index} rotation={tile.position.angle} posX={tile.position.x - offX} posY={tile.position.y - offY} />)
                case "IntersectionRoadpiece":
                    return (<IntersectionRoadpiece key={index} rotation={tile.position.angle} posX={tile.position.x - offX} posY={tile.position.y - offY} />)
                case "StartRoadpiece":
                    return (<StartRoadpiece key={index} rotation={tile.position.angle} posX={tile.position.x - offX} posY={tile.position.y - offY} />)
                case "FinishRoadpiece":
                    return (<FinishRoadpiece key={index} rotation={tile.position.angle} posX={tile.position.x - offX} posY={tile.position.y - offY} />)
            }         
    });
    
        
    return (
      <div className="scalebox">
            <div id="world_map" style={{width: maxX - minX + 560, height: maxY - minY + 560}} data-width={maxX - minX + 560} data-height={maxY - minY + 560}>
                {roadNodes}
                <div id="vehicles">
                    {vehicleNodes}
                </div>
            </div>
      </div>
    );
  }else{
        return (
      <div>
      </div>
    );
  }
  }
  
  componentDidMount() {
      $(window).resize(function() {
        $('.scalebox').width($("#visualization_panel").width());
        $('.scalebox').height($("#visualization_panel").height());
        
        var wrapper = $(".scalebox");
        var el = $("#world_map");
        
        var scale = Math.min(1,
          wrapper.width() / el.width(),
          wrapper.height() / el.height()
        );

        el.css({
          position: "relative",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%) " + "scale(" + scale + ")"
        });
      });
  }
}