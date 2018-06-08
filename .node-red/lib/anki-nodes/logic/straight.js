module.exports = function(RED) {
    function checkStraight(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			if ( "straightPiece" == msg.fact){
				var newMsg = {"type":"OK"};
				node.send(newMsg);
			}
			else{
				return null;
			}
        });
    }
    RED.nodes.registerType("Straight",checkStraight);
}
