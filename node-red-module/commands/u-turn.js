module.exports = function(RED) {
    function SendUTurn(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			var newMsg= {"payload":{"type":"uTurn"}};
            node.send(newMsg);
        });
    }
    RED.nodes.registerType("U Turn",SendUTurn);
}