import {w3cwebsocket as W3CWebSocket} from "websocket";

const AppConfig = {
    PROTOCOL: "ws:",
    // TODO: change to localhost if you wish to run it locally
    //HOST: "//serene-wave-94653.herokuapp.com",
    HOST: "//localhost",
    PORT: ":5678"
}

const Singleton = (function () {
    let instance;

    function createInstance() {
        // TODO: add +  PORT if you want to run it locally
        const socket = new W3CWebSocket(AppConfig.PROTOCOL + AppConfig.HOST + AppConfig.PORT);
        return socket;
    }

    return {
        getInstance: function () {
            if (!instance) {
                instance = createInstance();
            }
            return instance;
        }
    };
})();

export default Singleton;