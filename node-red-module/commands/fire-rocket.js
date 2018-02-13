module.exports = function(RED) {
    function fireRocket(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			var newMsg = {"type":"fireRocket"};
            node.send(newMsg);
        });
    }
    RED.nodes.registerType("Rakete feuern",fireRocket);
}