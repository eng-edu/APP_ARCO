package com.developer.edu.app_arco.conectionAPI;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public  class SocketStatic {

    private static Socket socket;


    public static synchronized Socket getSocket() {

        if(socket.connected() == false){
            return socket.connect();
        }
        return socket;
    }

    public static synchronized void setSocket(Socket socket) {
        SocketStatic.socket = socket.connect();
    }

}
