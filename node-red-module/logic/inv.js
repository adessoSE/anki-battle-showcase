module.exports = function(RED) {
    function switchInventar(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			if (config.art == msg.weaponType){
				var newMsg = {"type":"OK", "curve":config.art};
				node.send(newMsg);
			}
			else{
				return null;
			}
        });
    }
    RED.nodes.registerType("Inventar",switchInventar);
}