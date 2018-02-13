module.exports = function(RED) {
    function fireRocket(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
            var newMsg = {"type" : "change track", "track":config.track}
            node.send(msg);
        });
    }
    RED.nodes.registerType("Spur wechseln",fireRocket);
}