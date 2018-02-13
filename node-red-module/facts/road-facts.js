module.exports = function(RED) {
    function RoadFacts(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			if (msg.payload.nextRoadPiece == "left"){
				var newMsg = {"fact": "Linkskurve"};
			}
			else if (msg.payload.nextRoadPiece == "right"){
				var newMsg = {"fact":"Rechtskurve"};
			}
			node.send(newMsg);
        });
    }
    RED.nodes.registerType("Streckenfakten",RoadFacts);
}