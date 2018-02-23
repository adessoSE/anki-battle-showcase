module.exports = function(RED) {
    function fireRocket(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			var newMsg = {"payload":{"type":"brake", "veloc":config.velocity}};
			node.send(newMsg);
        });
    }
    RED.nodes.registerType("Bremsen",fireRocket);
}