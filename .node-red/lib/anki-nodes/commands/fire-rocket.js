module.exports = function(RED) {
    function fireRocket(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			var newMsg = {"payload":{"type":"fireRocket"}};
            node.send(newMsg);
        });
    }
    RED.nodes.registerType("Fire rocket",fireRocket);
}
