module.exports = function(RED) {
    function lapTime(config) {
        RED.nodes.createNode(this,config);
        var node = this;
        node.on('input', function(msg) {
			var context = this.context();
			if (msg.payload.currentRoadpiece == "startPiece"){
				var d = new Date();
				var time = d.getTime();
				var startTime = context.get('startTime')|| time
				context.set('startTime',time);
			}
		var d = new Date();
		var time = d.getTime();
		var lapTime = time - context.get("startTime")


		if (msg.payload.currentRoadpiece == "endPiece"){
			var d = new Date();
			var endTime = d.getTime();
			var startTime = context.get("startTime");
			var lapTime = endTime - startTime;
		}
		newMsg = {"payload":{"time" : lapTime/1000}};
		node.send( newMsg);
        });
    }
    RED.nodes.registerType("Rundenzeit",lapTime);
}