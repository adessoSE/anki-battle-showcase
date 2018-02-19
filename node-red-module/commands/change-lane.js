module.exports = function(RED) {
    function changeTrack(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
            var newMsg = {"type" : "change track", "track":config.track};
            node.send(newMsg);
        });
    }
    RED.nodes.registerType("Spur wechseln",changeTrack);
}