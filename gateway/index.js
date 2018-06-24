require("ts-node/register");

process.on("warning", function(e) { console.warn(e.stack) });

require("./src/main");