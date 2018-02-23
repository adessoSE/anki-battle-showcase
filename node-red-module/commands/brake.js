module.exports = function(RED) {
    function fireRocket(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			var newMsg = {"payload":{"type":"bremse", "veloc":config.velocity}};
			node.send(newMsg);
        });
    }
    RED.nodes.registerType("Bremsen",fireRocket);
}