module.exports = function(RED) {
    function switchInventar(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			console.log(config.art);
			console.log(msg.weaponType);
			if ( msg.weaponType.includes(config.art)){
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