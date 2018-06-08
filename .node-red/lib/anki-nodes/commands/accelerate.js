module.exports = function(RED) {
    function accelerate(config,test) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
		var newMsg = {"payload":{"type":"accelerate", "velocity":config.velocity}};
		node.send(newMsg);
        });
    }
    RED.nodes.registerType("Accelerate",accelerate);
}
