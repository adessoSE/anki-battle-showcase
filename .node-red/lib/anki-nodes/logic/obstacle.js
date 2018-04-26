module.exports = function(RED) {
    function switchObstacle(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
        var obstacles = msg.obstacles;
        for (i = 0; i < obstacles.length ; i++){
            //currently [direction,type,distance]
            var currentObstacle = obstacles[i];
            if (config.direction != currentObstacle[0]){
            continue;
            }

            if (config.obstacleType != currentObstacle[1]){
              continue;
            }

            if (config.distance > currentObstacle[2] +30 || config.distance < currentObstacle[2]-30 ){
              //Toleranzbereich ? oder min max vom user
              continue;
            }
            if (config.track > currentObstacle[3] +5 || config.track < currentObstacle[3]-5 ){
              // have to rethink how to check track
              continue;
            }
              var newMsg = {"type":"OK", "obstacle":currentObstacle[1]};
              node.send(newMsg);
              return;
        }

			return null;

        });
    }
    RED.nodes.registerType("Obstacle",switchObstacle);
}
