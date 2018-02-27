module.exports = function(RED) {
    function InventarNode(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			var weapon = msg.payload.inv;
			var newMsg = {"weaponType" : weapon};
            node.send(newMsg);
        });
    }
    RED.nodes.registerType("Inventarfakten",InventarNode);
}