module.exports = function(RED) {
    function InventarNode(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			var weapon = msg.payload.inv;
			msg.payload = weapon;
            node.send(msg);
        });
    }
    RED.nodes.registerType("Inventarfakten",InventarNode);
}