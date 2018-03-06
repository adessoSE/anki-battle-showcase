module.exports = function(RED) {
    function switchCurve(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			if (config.art == msg.fact){
				var newMsg = {"type":"OK", "curve":config.art};
				node.send(newMsg);
			}
			else{
				return null;
			}
        });
    }
    RED.nodes.registerType("Kurve",switchCurve);
}