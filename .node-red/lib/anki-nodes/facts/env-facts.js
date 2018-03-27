module.exports = function(RED) {
    function EnvironmentFacts(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
          var obstacles = msg.payload.obstacles;
			    var newMsg = {"obstacles" : obstacles};
          node.send(newMsg);
        });
    }
    RED.nodes.registerType("Umgebungsfakten",EnvironmentFacts);
}

/*            if (msg.payload.obstacles.includes("MineInFront")){
				var msg1 = {"fact": "MineFront"};
			}
			if (msg.payload.obstacles.includes("ObjectInFront")){
				var msg2 = {"fact":"ObjectInFront"};
			}
			if (msg.payload.obstacles.includes("VehicleInFront")){
				var msg3 = {"fact":"VehicleFront"};
			}
			if (msg.payload.obstacles.includes("RocketBehind")){
				var msg4 = {"fact": "RocketBehind"}
			}
			//return  [ msg1, msg2, msg3 ,msg4] ;
			node.send([[ msg1, msg2, msg3 ,msg4]]);
*/
