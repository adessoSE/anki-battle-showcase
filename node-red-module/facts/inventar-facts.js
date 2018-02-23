module.exports = function(RED) {
    function InventarNode(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			var weapon = msg.payload.inv;
			var newMsg = {"weaponType" : weapon};
			console.log(newMsg);
            node.send(newMsg);
        });
    }
    RED.nodes.registerType("Inventarfakten",InventarNode);
}