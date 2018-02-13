module.exports = function(RED) {
    function UseShield(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			var newMsg = {"type":"shield"};
            node.send(newMsg);
        });
    }
    RED.nodes.registerType("Schild",UseShield);
}