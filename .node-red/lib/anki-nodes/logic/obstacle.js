module.exports = function(RED) {
    function switchObstacle(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			if (config.art == msg.fact){
				var newMsg = {"type":"OK", "obstacle":config.art};
				node.send(newMsg);
			}
			else{
				return null;
			}
        });
    }
    RED.nodes.registerType("Umwelt",switchObstacle);
}