module.exports = function(RED) {
    function UseShield(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			var newMsg = {"payload":{"type":"shield"}};
            node.send(newMsg);
        });
    }
    RED.nodes.registerType("Shield",UseShield);
}
